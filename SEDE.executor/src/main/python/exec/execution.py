
class Execution: 

    def has_execution_finished(self):
        return False
    pass


class Task:

    def __init__(self, attributes):
        self.attributes = attributes
        self.taskname = attributes["procedurename"]
        self.execution = None # reference to execution

    def setExecution(self, execution : Execution) :
        self.execution = execution

    pass