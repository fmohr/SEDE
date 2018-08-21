import exe
logging = exe.logging

from exe.executor import Executor
from exe.config import ExecutorConfig
from http import server

from util.com import MultiContextHandler, StringServerResponse,\
    BasicClientRequest, HttpClientRequest, ByteServerResponse

from procedure.data_procedures import TransmitDataProcedure, FinishProcedure
from exe.data import SEDEObject, SEMANTIC, PrimitiveType
from exe import data

from exe.requests import DataPutRequest, ExecRequest


def create_put_request(host, fieldname, executionId, unavailable:bool=True, semtype=SEMANTIC) -> BasicClientRequest:
    dataPutUrl = host + "/put/" + executionId + "/" + fieldname + "/"
    if unavailable:
        dataPutUrl += "unavailable"
    else:
        dataPutUrl += semtype
    return HttpClientRequest(dataPutUrl)


class PutDataHandler(ByteServerResponse):

    def __init__(self, executor):
        self.executor : Executor = executor

    def receive_bytes(self, input_bytes: bytes, executionId, fieldname, semtype) -> str:
        logging.trace("%s received data at %s/%s/%s", self.executor.config.executor_id, executionId, fieldname, semtype)
        try:
            if semtype != "unavailable":
                input_data: SEDEObject = data.decode(input_bytes, semtype)
                unavailable = False
            else:
                input_data = None
                unavailable = True
            put_request = DataPutRequest(requestId=executionId, data=input_data, fieldname=fieldname, unavailable=unavailable)
            self.executor.put(put_request)
            return ""
        except Exception as e:
            logging.exception("Exception in executor %s while receiving data at url: %s/%s/%s",
                              self.executor.config.executor_id, executionId, fieldname, semtype)
            return str(e)


class ExecuteGraphHandler(StringServerResponse):
    def __init__(self, executor):
        self.executor: Executor = executor

    def receive_str(self, input_str: str) -> str:
        try:
            logging.debug("%s received execute request.", self.executor.config.executor_id)
            execRequest: ExecRequest = ExecRequest.from_json_string(input_str)
            self.executor.execute(execRequest)
            return ""
        except Exception as e:
            logging.exception("Exception in executor %s while receiving exec request.",
                              self.executor.config.executor_id)
            return str(e)

class InterruptHandler(StringServerResponse):

    def __init__(self, executor):
        self.executor: Executor = executor

    def receive_str(self, input_str: str, executionId:str) -> str:
        try:
            self.executor.interrupt(executionId)
            return "";
        except Exception as e:
            logging.exception("Exception in executor %s while receiving interrupt request for execution %s",
                              self.executor.config.executor_id, executionId)
            return str(e)



class TransmitDataOverHttp(TransmitDataProcedure):

    def get_put_request(self, task, unavailable:bool)->BasicClientRequest:
        host = task["contact-info"]["host-address"]
        fieldname = task["fieldname"]
        if "semantic-type" in task:
            semtype = task["semantic-type"]
        else:
            semtype = None
        executionId = task.execution.exec_id
        if semtype is None:
            sedeObj:SEDEObject = task.execution.env[fieldname]
            semtype = sedeObj.type
        logging.trace("Execution '%s': Transmitting %s of type %s to %s.", executionId, fieldname, semtype, host)
        return create_put_request(host,fieldname,executionId,unavailable,semtype)


class FinishOverHttp(FinishProcedure):

    def get_finish_flag_request(self, task)-> BasicClientRequest:
        host = task["contact-info"]["host-address"]
        fieldname = task["fieldname"]
        semtype = PrimitiveType.Bool.name
        executionId = task.execution.exec_id
        logging.trace("Execution '%s': Notifying %s with a finish flag.", executionId, host)
        return create_put_request(host, fieldname, executionId, unavailable=False, semtype=semtype)


class HTTPExecutor(Executor):

    def __init__(self, config, host_address:str, port:int):

        super().__init__(config)
        self.host_address = host_address
        self.port = port
        self.bind_http_procedure_names()
        self.request_handler = MultiContextHandler()
        self.request_handler.add_context("/put/(?P<executionId>\w+)/(?P<fieldname>(?:[&_a-zA-Z][&\w]*))/(?P<semtype>\w+)",
                                         self.handler_put_data)
        self.request_handler.add_context("/execute",
                                         self.handler_execute)
        self.request_handler.add_context("/interrupt/(?P<executionId>\w+)",
                                         self.handler_interrupt)

        self.httpserver = server.HTTPServer(("", port), self.request_handler)

        logging.info("Starting Python executor http server: '%s'. host: %s port: %i", config.executor_id, host_address, port)
        self.register_toall()

    def bind_http_procedure_names(self):
        self.worker_pool.bind_procedure("TransmitData", TransmitDataOverHttp)
        self.worker_pool.bind_procedure("Finish", FinishOverHttp)

    def handler_put_data(self):
        return PutDataHandler(self)

    def handler_execute(self):
        return ExecuteGraphHandler(self)

    def handler_interrupt(self):
        return InterruptHandler(self)

    def contact_info(self):
        d = super().contact_info();
        d["host-address"] = self.host_address + ":" + str(self.port)
        return d

    def start_listening(self):
        try:
            self.httpserver.serve_forever()
        except KeyboardInterrupt:
            self.httpserver.shutdown()

    def register_toall(self):
        logging.debug("Registering to every gateway stated by the config: %s", self.config.gateways)
        for gatewayaddress in self.config.gateways:
            url = gatewayaddress + "/register"
            try:
                self.register_perhttp(url)
            except Exception as registrationException:
                # logging.exception(registrationException)
                logging.warn("Couldn't register to %s, because:\n%s", gatewayaddress, str(registrationException))


    def register_perhttp(self, gatewayaddress):
        registration_str = self.registration().to_json_string()
        registration_req = HttpClientRequest(gatewayaddress)
        answer:str = registration_req.send_receive_str(registration_str)
        answer = answer.strip()
        if answer is not None and len(answer) == 0:
            logging.info("Successfully registered to %s.", gatewayaddress)
        else:
            raise RuntimeError("the returned answer was: " + answer)


if __name__ == "__main__":
    import sys
    import json
    logging.info("Number of CLI arguments: %d.", len(sys.argv))
    logging.debug("CLI List: %s.",  str(sys.argv))
    # remove the module from the top of the list:
    sys.argv.pop(0)
    # first argument is the executor configuration path:
    if sys.argv:
        with open(sys.argv.pop(0)) as fp:
            config_dict = json.load(fp)
            logging.trace("configuration %s", config_dict)
        executorConfig = ExecutorConfig.from_dict(config_dict)
    else :
        executorConfig = ExecutorConfig.empty_config()

    # second argument is the local host address.
    # Like an ip address or 'localhost'
    if sys.argv:
        host_address = str(sys.argv.pop(0))
    else:
        host_address = "localhost"

    # third argument is the port which should be opened by the http executor:
    if sys.argv:
        port = int(sys.argv.pop(0))
    else:
        port = 5000

    executor = HTTPExecutor(executorConfig, host_address, port)
    executor.start_listening()


