
from exe.executor import Executor
from exe.config import ExecutorConfig
from exe.requests import ExecRequest
from time import sleep


with open("testresources/config.json", "r") as configfile:
    config = ExecutorConfig.from_json_string(configfile.read())

executor = Executor(config)

with open("testresources/e_1.json", "r") as executefile:
    exec_1 = ExecRequest.from_json_string(executefile.read())

execution_1 = executor.execute(exec_1)

while executor.execPool.execIdTaken(exec_1.requestId):
    sleep(1)

print("done execution")