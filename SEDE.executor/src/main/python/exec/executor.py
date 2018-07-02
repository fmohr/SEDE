import json
import logging
import uuid

from exec import requests
from exec.execution import Execution
from exec.workers import WorkerPool
from util.helpers import require_not_none
from util.locking import synchronized_method as synchronized
from util.observing import Observer


class ExecutorConfig:
    capabilities = []
    services = []
    gateways = []
    thread_count = 2
    executor_id = "NO_ID"
    service_store_location = "instances"

    @classmethod
    def from_json_string(cls, json_string):
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


class ExecutionPool:
    execMap: dict

    def __init__(self, config):
        pass

    def remove_execution(self, execution: Execution) -> Execution:
        pass

    def get_orcreate_execution(self, execution_id: str) -> Execution:
        pass

    def execIdTaken(self, execId) -> bool:
        return False


class Executor:
    execPool: ExecutionPool
    config: ExecutorConfig
    worker_pool: WorkerPool
    task_enqueuer: Observer
    executor_garbage_collector: Observer

    def __init__(self, config: ExecutorConfig):
        self.execPool = ExecutionPool(config)
        self.config = config
        self.worker_pool = WorkerPool(config.thread_count)

        self.task_enqueuer = Observer(lambda task: True,
                                      self.worker_pool.submit_task,
                                      lambda task: False)
        self.executor_garbage_collector = Observer(
            Execution.has_execution_finished, self.remove_execution)
        self.bind_procedure_names()

    def bind_procedure_names(self):
        pass  # TODO

    @synchronized
    def remove_execution(self, execution: Execution):
        self.execPool.remove_execution(execution)
        self.worker_pool.remove_execution(execution)

    @synchronized
    def put(self, dataputrequest: requests.DataPutRequest):
        execution = self.execPool.get_orcreate_execution(
            dataputrequest.requestId)
        environment = execution.environment()
        if dataputrequest.unavailable:
            # The request indicates that the data is unavailable. (wont be delivered)
            environment.mark_unavailable(dataputrequest.fieldname)
        else:
            # The request contains the data:
            environment.put(dataputrequest.fieldname, dataputrequest.data)

    @synchronized
    def execute(self, execrequest: requests.ExecRequest):
        execId = execrequest.requestId
        # First check if execution id is taken:
        if self.execPool.execIdTaken(execId):
            raise Exception("Execution Id is already taken: {}".format(execId))
        execution = self.execPool.get_orcreate_execution(execId)
        execution.runnable_tasks.observe(self.task_enqueuer)
        execution.deserialize_graph(execrequest.graph)
        execution.state.observe(self.executor_garbage_collector)
        logging.debug("Execution request {} started.".format(execId))

    @synchronized
    def interrupt(self, executionId: str):
        if self.execPool.execIdTaken(executionId):
            execution = self.execPool.get_orcreate_execution(executionId)
            self.worker_pool.interrupt(execution)
            execution.interrupt()

    def contact_info(self):
        return {"id": self.config.executor_id}

    def registration(self):
        return requests.ExecutorRegistration.from_config(self.contact_info, self.config)
