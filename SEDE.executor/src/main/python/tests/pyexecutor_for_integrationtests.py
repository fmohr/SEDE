from exe.httpexecutor import HTTPExecutor
from exe.config import ExecutorConfig


config = ExecutorConfig.empty_config()
config.executor_id = "PY_EXECUTOR"
config.thread_count = 4

executor = HTTPExecutor(config, "localhost", 5000)
executor.start_listening()

