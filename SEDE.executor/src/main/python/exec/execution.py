from util.locking import synchronized_method as synchronized


class ExecutionEnvironment:
    @synchronized
    def mark_unavailable(self, fieldname: str):
        pass
    @synchronized
    def put(self, fieldname:str, data):
        pass



class Execution: 

    def has_execution_finished(self):
        return False

    def environment(self) -> ExecutionEnvironment :
        return None



class Task:

    def __init__(self, attributes):
        self.attributes = attributes
        self.taskname = attributes["procedurename"]
        self.execution = None # reference to execution

    def setExecution(self, execution : Execution) :
        self.execution = execution

    pass
