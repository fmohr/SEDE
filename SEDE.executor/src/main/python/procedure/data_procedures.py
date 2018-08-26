from procedure import Procedure
from exe.execution import Task
from exe.execution import ExecutionEnvironment
from exe.execution import Execution
from util.observing import Observer
from exe import data
from util.com import BasicClientRequest, in_read_string, out_write_bytes, WriteFileRequest, ReadFileRequest
from exe import req as requests
from os import path
import pickle

class AcceptDataProcedure(Procedure):
    def __init__(self):
        self.fieldname = None
        self.field_observer = Observer(self.check_field, self.signal_field)

    def process_task(self, task: Task):
        self.task = task
        self.fieldname = task["fieldname"]
        task.execution.env.state.observe(self.field_observer)



    def check_field(self, env: ExecutionEnvironment) -> bool:
        return self.fieldname in env or self.fieldname in env.unavailables

    def signal_field(self, env: ExecutionEnvironment) -> None:
        if self.fieldname in env:
            self.task.set_succeeded()
        elif self.fieldname in env.unavailables:
            self.task.set_failed()


class CastTypeProcedure(Procedure):
    def __init__(self):
        pass

    def process_task(self, task: Task):
        fieldname = task["fieldname"]
        caster = task["caster-classpath"]
        field : data.SEDEObject = task.execution.env[fieldname]
        if task["cast-to-semantic"]:
            encoded_bytes = data.encode(content=field, caster=caster)
            casted_field = data.SEDEObject.from_Semantic(encoded_bytes)
        elif field.is_semantic():
            encoded_bytes = field.data
            casted_field = data.decode(encoded_bytes, caster=caster, target_real_classpath=task["target-type"])
        else:
            raise ValueError("Cannot cast field: " + str(task))
        task.execution.env[fieldname] = casted_field
        task.set_succeeded()

class FinishProcedure(Procedure):
    def process_task(self, task):
        self.finish_notice(task)

    def process_fail(self, task):
        self.finish_notice(task)

    def finish_notice(self, task:Task):
        success = task.dependecy_has_failed
        successObject = data.SEDEObject.from_primitive(data.PrimitiveType.Bool, success)

        with self.get_finish_flag_request(task) as request:
            request : BasicClientRequest
            result = request.send_receive_str(data.encode(successObject))

        if result is not None and len(result) > 0:
            raise Exception("Finish notice to client failed. Answer was not empty: " + result)

        task.set_succeeded()

    def get_finish_flag_request(self, task)-> BasicClientRequest:
        raise Exception("BUG: must be implemented in a subclass.")

class ParseConstatProcedure(Procedure):
    def process_task(self, task: Task):
        constant = task["constant"]
        if task["isBool"]:
            primtype = data.PrimitiveType.Bool
            primdata = constant.lower() == ("true")
        elif task["isNumber"]:
            primtype = data.PrimitiveType.Number
            primdata = eval(constant)
        elif task["isNULL"]:
            primtype = data.PrimitiveType.NULL
            primdata = None
        elif task["isString"]:
            primtype = data.PrimitiveType.String
            primdata = str(constant)[1:-1]
        else:
            raise Exception("GATEWAY ERROR: None of the flags in the constant node were true.")
        primitiveObject = data.SEDEObject.from_primitive(primtype, primdata)
        task.execution.env[constant] = primitiveObject
        task.set_succeeded()

class SendGraphProcedure(Procedure):

    def process_task(self, task):
        self.task = task

        interruption_observer = Observer(Execution.is_interrupted, self.send_interrupt)
        self.task.execution.state.observe(interruption_observer)

        graph = task["graph"]
        executionId = task.execution.exec_id
        execRequest = requests.ExecRequest(executionId, graph)

        with self.get_exec_request() as request:
            result = request.send_receive_str(execRequest.to_json_string())

        if result is not None and len(result) > 0:
            raise Exception("Exec request to executor failed. Answer was not empty: " + result)
        task.set_succeeded()

    def send_interrupt(self, execution):
        raise Exception("BUG: must be implemented in a subclass.")


    def get_exec_request(self):
        raise Exception("BUG: must be implemented in a subclass.")

class ServiceInstanceStorageProcedure(Procedure):
    def process_task(self, task: Task):
        fieldname = task["serviceinstance-fieldname"]
        service_classpath = task["service-classpath"]

        if task["is-load-instruction"]:
            instance_id = task["instance-id"]
            with self.get_load_request(task, instance_id, service_classpath) as load_request:
                load_request: BasicClientRequest
                instance_obj = pickle.load(load_request.receive())
                instanceHandle = data.ServiceInstance(executorId=task.execution.config.executor_id, classpath=service_classpath, id=instance_id)
                instanceHandle.service_instance = instance_obj
                loadedSedeObject = data.SEDEObject.from_ServiceInstance(instanceHandle)
                task.execution.env[fieldname] = loadedSedeObject
        else:
            field: data.SEDEObject = task.execution.env[fieldname]
            # if not field.is_service_instance():
            assert field.is_service_instance()
            instanceHandle: data.ServiceInstance = field.data
            assert instanceHandle.service_instance is not None
            instance_id = instanceHandle.serviceId
            with self.get_store_request(task, instance_id, service_classpath) as store_request:
                store_request : BasicClientRequest
                pickle.dump(instanceHandle.service_instance, store_request.send())
                answer = in_read_string(store_request.receive())
                if answer is not None and len(answer) > 0:
                    raise Exception("Error during storing service storage: " + instanceHandle.to_json_string() + "\nmessage: " + answer)
        task.set_succeeded()

    def default_service_path(self, services_path, service_classpath, instance_id):
        max_folder_length = 200
        if len(service_classpath) > max_folder_length:
            service_classpath = service_classpath[-max_folder_length:]
        return path.join(services_path, service_classpath, instance_id)

    def get_load_request(self, task, instance_id, service_classpath)-> BasicClientRequest:
        path = self.default_service_path(task.execution.config.service_store_location, service_classpath, instance_id)
        return ReadFileRequest(path)

    def get_store_request(self, task, instance_id, service_classpath)-> BasicClientRequest:
        path = self.default_service_path(task.execution.config.service_store_location, service_classpath, instance_id)
        return WriteFileRequest(path)

class TransmitDataProcedure(Procedure):
    def process_task(self, task):
        fieldname = task["fieldname"]
        objToSend = task.execution.env[fieldname]
        with self.get_put_request(task, unavailable=False) as put_request:
            put_request : BasicClientRequest
            if "caster" in task:
                caster = task["caster"]
            else:
                caster = None
            put_request.write_bytes(data.encode(objToSend, caster=caster))
            answer = in_read_string(put_request.receive())
            if answer is not None and len(answer) > 0:
                raise Exception("Error transmitting data:\n\t" + fieldname +
						"\nto:\n\t" + str(task["contact-info"])
                        + "\nReturned message is not empty:\n\t" + answer)
        task.set_succeeded()

    def process_fail(self, task):
        with self.get_put_request(task, unavailable=True) as put_request:
            put_request : BasicClientRequest
            answer = put_request.send_receive_str()
            if answer is not None and len(answer) > 0:
                raise Exception("Error giving unavailability notice to:\n\t" + str(task["contact-info"]) +
						"\nReturned message is not empty:\n\t" + answer)
            

    def get_put_request(self, task, unavailable)->BasicClientRequest:
        raise Exception("BUG: must be implemented in a subclass.")