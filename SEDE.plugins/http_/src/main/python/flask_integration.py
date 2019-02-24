import os
# gunicorn imports
import gunicorn.app.base
from gunicorn.six import iteritems

# flask imports
from werkzeug.routing import BaseConverter
from flask import Flask, request, make_response

# sede executor imports
from exe.config import ExecutorConfig
from exe.executor import Executor
from exe.httpexecutor import PutDataHandler, TransmitDataOverHttp, FinishOverHttp, ExecuteGraphHandler, InterruptHandler
from util.helpers import getlogger
from util.com import HttpClientRequest, StringServerResponse
logging = getlogger("HTTP_HANDLER")
logging.setLevel("TRACE")

# functional imports
from functools import partial
from uuid import uuid4 as uuid

class FlaskApp:
    app: Flask

    def __init__(self, app_name="FLASK_HANDLER"):
        self.app = Flask(app_name)

        # @self.app.route('/test/<test_name>', methods=['GET', 'POST'])
        # def test(**kwargs):
        #     return BodyReader().receive_bytes(request.data, **kwargs)



    def add_context(self, route:str, responder, methods =['GET', 'POST']):
        def route_handler(**kwargs):
            try:
                if callable(responder):
                    responder_ = responder()
                else:
                    responder_ = responder

                resp = make_response(responder_.receive_bytes(request.get_data(), **kwargs), 200)
                resp.headers['Access-Control-Allow-Origin'] = '*'
                resp.headers["Content-type"]='bytes'
                return resp
            except Exception as ex:
                resp = make_response("couldn't handle request: " + str(ex), 500)
                resp.headers['Access-Control-Allow-Origin'] = '*'
                return resp

        self.app.add_url_rule(route, str(uuid()), route_handler, **{"methods":methods})

    def start_with_gunicorn(self, port:int, workers=1, threads=1):
        options = {
            'bind': '%s:%s' % ("0.0.0.0", port),
            'workers': workers,
            'threads' : threads,
            'forwarded-allow-ips' : "*"
        }
        logging.info("Starting executor with %d workers and %d thread each.", workers, threads)
        self.server = StandaloneApplication(self.app, options).run()

class StandaloneApplication(gunicorn.app.base.BaseApplication):

    def __init__(self, app, options=None):
        self.options = options or {}
        self.application = app
        super(StandaloneApplication, self).__init__()

    def load_config(self):
        config = dict([(key, value) for key, value in iteritems(self.options)
                       if key in self.cfg.settings and value is not None])
        for key, value in iteritems(config):
            self.cfg.set(key.lower(), value)

    def load(self):
        return self.application

class HeartbeatHandler(StringServerResponse):
    def __init__(self):
        pass

    def receive_str(self, input_str: str, executorId) -> str:
        return "true";

class ReadLogsHandler(StringServerResponse):
    def __init__(self):
        pass

    def receive_str(self, input_str: str, executorId) -> str:
        with open("logs/sede-1.log") as logged:
            logs = logged.read()
        return logs

class RemoveLogsHandler(StringServerResponse):
    def __init__(self):
        pass

    def receive_str(self, input_str: str, executorId) -> str:
        os.remove("logs/sede-1.log")

        return "Logs cleared."

class HTTPExecutor(Executor):
    def __init__(self, config, host_address:str, port:int):
        super().__init__(config)
        self.address = host_address
        self.port = port
        self.bind_http_procedure_names()
        self.httpserver = FlaskApp("PYTHON_EXECUTOR")

        self.httpserver.add_context('/put/<executorId>/<executionId>/<fieldname>/<semtype>', partial(PutDataHandler, self))
        self.httpserver.add_context("/execute/<executorId>", partial(ExecuteGraphHandler, self))
        self.httpserver.add_context("/interrupt/<executorId>/<executionId>", partial(InterruptHandler, self))
        self.httpserver.add_context("/cmd/<executorId>/heartbeat", HeartbeatHandler)
        self.httpserver.add_context("/cmd/<executorId>/cat/logs/sede-1.log", ReadLogsHandler)
        self.httpserver.add_context("/cmd/<executorId>/rm/logs/sede-1.log", RemoveLogsHandler)

        logging.info("Starting Python executor http server: '%s'. host: %s port: %i", config.executor_id, host_address, port)
        self.register_toall()

    def bind_http_procedure_names(self):
        self.worker_pool.bind_procedure("TransmitData", TransmitDataOverHttp)
        self.worker_pool.bind_procedure("Finish", FinishOverHttp)

    def contact_info(self):
        d = super().contact_info()
        d["host-address"] = self.address
        return d

    def start_listening(self):
        self.httpserver.start_with_gunicorn(self.port)

    def register_toall(self):
        logging.debug("Registering to every gateway stated by the config: %s", self.config.gateways)
        for gatewayaddress in self.config.gateways:
            try:
                self.register_perhttp(gatewayaddress)
            except Exception as registrationException:
                # logging.exception(registrationException)
                logging.warn("Couldn't register to %s, because:\n%s", gatewayaddress, str(registrationException))

    def register_perhttp(self, gatewayaddress: str):
        url = gatewayaddress + "/register"
        registration_str = self.registration().to_json_string()
        registration_req = HttpClientRequest(url)
        answer:str = registration_req.send_receive_str(registration_str)
        answer = answer.strip()
        if answer is not None:
            if len(answer) == 0:
                logging.info("Successfully registered to %s.", gatewayaddress)
            else:
                raise RuntimeError("the returned answer was: " + answer)
        else:
            raise ValueError("Answer received was None")


def retrieve_executoraddr():
    if 'EXECUTOR_ADDRESS' in os.environ:
        addr = os.environ['EXECUTOR_ADDRESS']
        logging.info("Retrieved the address of this executor from '$EXECUTOR_ADDRESS': %s", addr)
        return addr
    else:
        return None

def signup_proxy(id, internal_address):
    if 'PROXY_ADDRESS' in os.environ:
        proxy_addr = os.environ['PROXY_ADDRESS']
        logging.info("Retrieved the address of PROXY from 'PROXY_ADDRESS': %s.", proxy_addr)
        url = proxy_addr + "/signup/" + str(id) + "/" + internal_address
        registration_req = HttpClientRequest(url)
        answer = registration_req.send_receive_str("")
        if answer is not None and len(answer) == 0:
            logging.info("Successfully signed up to the proxy: %s.", url)
            return proxy_addr
        else:
            logging.error("Error singing up to the proxy: %s", url)
    return None


def main():
    import sys
    import json
    # remove the module from the top of the list:
    program = sys.argv.pop(0)
    # first argument is the executor configuration path:
    if sys.argv:
        with open(sys.argv.pop(0)) as fp:
            config_dict = json.load(fp)
        executorConfig = ExecutorConfig.from_dict(config_dict)
    else:
        executorConfig = ExecutorConfig.empty_config()

    #  second argument is the port which should be opened by the http executor:
    if sys.argv:
        port = int(sys.argv.pop(0))
    else:
        port = 5000

    # third argument is the local host address.
    # Like an ip address or 'localhost'
    host_address = retrieve_executoraddr()
    if host_address is None:
        if sys.argv:
            host_address = str(sys.argv.pop(0))
        else:
            host_address = "localhost:" + str(port)

    # restore the first argument:
    sys.argv.insert(0, program)
    id = executorConfig.executor_id
    if host_address is None or id is None:
        logging.fatal("Define host address or id.")
    proxy_addr = signup_proxy(id, host_address)
    if proxy_addr is not None:
        logging.info("Replacing own address with the one from the proxy: %s", proxy_addr)
        host_address = proxy_addr
    executor = HTTPExecutor(executorConfig, host_address, port)
    executor.start_listening()



def test():
    class Printer(StringServerResponse):
        def receive_str(self, input_string: str, **kwargs) -> str:
            print("PRINT:" + input_string)
            return "hello"
    testServer = FlaskApp()
    testServer.add_context("/", Printer())
    testServer.start_with_gunicorn(5000)

if __name__ == '__main__':
    main()
