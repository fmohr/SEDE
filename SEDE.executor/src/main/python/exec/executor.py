import logging
import json
import uuid
from exec.execution import Execution, ExecutionEnvironment
from util.helpers import require_not_none
from exec.workers import WorkerPool
from util.observing import Observable, Observer
from util.locking import synchronized_method as synchronized
from exec import requests

class ExecutorConfig:
    capabilities = []
    services = []
    gateways = []
    thread_count = 2
    executor_id = "NO_ID"
    service_store_location = "instances"

    @classmethod
    def from_json_string(cls, json_string) :
        return cls.from_dict(json.loads(json_string))
    
    @classmethod
    def from_dict(cls, d):
        config = ExecutorConfig()

        config.executor_id = require_not_none(d["executorId"])
        config.thread_count = require_not_none(d["threadNumber"])

        if "capabilities" in d:
            config.capabilities = d["capabilities"]
        if "services" in d:
            config.services = d["services"]
        if "gateways" in d:
            config.gateways = d["gateways"]
        if "serviceStoreLocation" in d:
            config.service_store_location = d["serviceStoreLocation"]
        return config

    
    @classmethod
    def empty_config(cls):
        config = ExecutorConfig()
        config.executor_id = uuid.uuid4().hex[0:5]
        return config

    pass

class Executor :
    execPool = None
    config = None
    worker_pool = None
    task_enqueuer = None
    executor_garbage_collector = None

    def __init__(self, config: ExecutorConfig):
        self.execPool = ExecutionPool(config)
        self.config = config
        self.worker_pool = WorkerPool(config.thread_count)

        self.task_enqueuer = Observer(lambda task: True, 
                self.worker_pool.submit_task,
                lambda task: False)
        self.executor_garbage_collector = Observer(Execution.has_execution_finished, self.remove_execution)
        self.bind_procedure_names()

    def bind_procedure_names(self):
        pass # TODO

    def remove_execution(self, execution: Execution):
        self.execPool.remove_execution(execution)
        self.worker_pool.remove_execution(execution)

    @synchronized
    def put(self, dataputrequest : requests.DataPutRequest):
        execution = self.execPool.get_orcreate_execution(dataputrequest.requestId)
        environment = execution.environment()
        if dataputrequest.unavailable :
            # The request indicates that the data is unavailable. (wont be delivered)
            environment.mark_unavailable(dataputrequest.fieldname)
        else:
            # The request contains the data:
            environment.put(dataputrequest.fieldname, dataputrequest.data)

        

class ExecutionPool:
    def __init__(self, config):
        pass

    def remove_execution(self, execution: Execution) -> Execution:
        pass
    
    def get_orcreate_execution(self, execution_id: str) -> Execution:
        pass