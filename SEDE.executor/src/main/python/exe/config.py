import json
import uuid
from util.helpers import require_not_none

import exe
logging = exe.logging


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
        config = cls.empty_config()
        if "executorId" in d:
            config.executor_id = require_not_none(d["executorId"])
            logging.info("Actual executor id: %s", config.executor_id)

        if "threadNumber" in d:
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
        logging.info("Generated a new executor id: %s", config.executor_id)
        return config
