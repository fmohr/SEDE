from util.locking import synchronized_method as synchronized
from util.observing import Observable, Observer


class ExecutionEnvironment:
    @synchronized
    def mark_unavailable(self, fieldname: str):
        pass

    @synchronized
    def put(self, fieldname: str, data):
        pass


class Execution:

    runnable_tasks: Observable
    state: Observable
    has_graph: bool

    def has_execution_finished(self) -> bool:
        return False

    def environment(self) -> ExecutionEnvironment:
        return None

    def deserialize_graph(self, graph: str):
        pass
        
    def interrupt(self):
        pass


class Task:

    def __init__(self, attributes):
        self.attributes = attributes
        self.taskname = attributes["procedurename"]
        self.execution = None  # reference to execution

    def setExecution(self, execution: Execution):
        self.execution = execution

    pass
