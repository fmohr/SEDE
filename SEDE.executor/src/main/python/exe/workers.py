import exe
logging = exe.logging

import threading
import inspect
import ctypes

from concurrent.futures.thread import ThreadPoolExecutor
from weakref import WeakKeyDictionary
from util.locking import synchronized_method as synchornized
from exe.execution import Task
from exe.execution import Execution
from procedure import Procedure

class Interruption(Exception):
    """
    Raised while working on a task whose execution has been interrupted.
    """

class TaskRunner:
    """
        Is given to the executor service to run a task.
    """
    task: Task
    procedure: Procedure

    def __init__(self, procedure, task):
        self.procedure = procedure
        self.task = task
        self.thread_id = None

    def run(self):
        """
        Exectues the task.
        """
        self.thread_id = threading.get_ident()
        logging.trace("worker STARTED working on task: %s", self.task)
        try:
            self.task.set_started()
            if not self.task.dependecy_has_failed:
                self.procedure.process_task(self.task)
            else:
                self.procedure.process_fail(self.task)
                self.task.set_failed()
        except Interruption:
            pass
        except Exception as e:
            self.task.error = e
            self.task.set_failed()
            self.procedure.process_fail(self.task)
            logging.exception("ERROR during: %s", self.task)
        finally:
            self.task.set_done()

        logging.trace("worker IS DONE working on task: %s", self.task)
        
def _async_raise(tid, exctype):
    """
    Helper function which raises an exception in the threads with id tid.
    """
    if not inspect.isclass(exctype):
        raise TypeError("Only types can be raised (not instances)")
    res = ctypes.pythonapi.PyThreadState_SetAsyncExc(tid,
                                                  ctypes.py_object(exctype))
    if res == 0:
        raise ValueError("invalid thread id")
    elif res != 1:
        # "if it returns a number greater than one, you're in trouble,
        # and you should call it again with exc=NULL to revert the effect"
        ctypes.pythonapi.PyThreadState_SetAsyncExc(tid, 0)
        raise SystemError("PyThreadState_SetAsyncExc failed")

class WorkerPool:
    """ 
    Class which accepts tasks and executes them in parallel using a thread pool.
    Is also able to cancel/interrupt the execution of every task within a given execution object.
    """

    def __init__(self, workerNumber):
        """
        Creates a workerpool with 'workerNumber' amount of threads.
        """
        self.workers = ThreadPoolExecutor(workerNumber)
        # Use weak hashmap to have executions which are finished, removed and avoid a memory leak.
        self.executionFutureMap = WeakKeyDictionary()
        self.procedure_supplier = dict()
        pass
    
    @synchornized
    def submit_task(self, task: Task):
        """
        Submits the given task to the queue of executions.
        """
        logging.trace("submitted: %s", task)
        execution = task.execution
        procedure = self.procedure_for_task(task.task_name)
        runner = TaskRunner(procedure, task)
        future = self.workers.submit(TaskRunner.run, runner)

        if not execution in self.executionFutureMap:
            self.executionFutureMap[execution] = list()

        self.executionFutureMap[execution].append((future, runner))

    @synchornized
    def interrupt(self, execution):
        """
        Cancels every submitted task within the given execution which hasn't started yet.
        Tasks which have already started to execute are interrupted.
        """
        if execution in self.executionFutureMap:
            for future, runner in self.executionFutureMap[execution]:
                future.cancel()
                if future.running():
                    # task has already started running.
                    # interrupt by asynchronously raising an exception:
                    _async_raise(runner.thread_id, Interruption())
    
    @synchornized
    def bind_procedure(self, procedure_name, procedure_supplier):
        """ 
        Binds the given procedure_name which is the same as the task_name to the given supplier.
        """
        self.procedure_supplier[procedure_name] = procedure_supplier

    def procedure_for_task(self, task_name):
        """
        Returns a new procedure object for the given task name.
        Raises an exception if the task isn't bounded.
        """
        if task_name in self.procedure_supplier:
            return self.procedure_supplier[task_name]()
        else:
            raise Exception("Task '%s' was not bound to a procedure supplier.", task_name)
    
    @synchornized
    def remove_execution(self, execution: Execution):
        """
        Removes the given execution from the futurelist map.
        Use this method when an execution has finished to avoid memory leak.
        """
        # Although the dict used here is a WeakKeyDictionary testing has shown that entries are still not removed by the gc. 
        if execution in self.executionFutureMap : 
            del self.executionFutureMap[execution]

    @synchornized    
    def shutdown(self):
        """
        Shutdowns the workers. Submitting additional tasks would cause an excetion afterwards.
        Threads continue to do their work on the current tasks but waiting tasks are discarded.
        """
        self.workers.shutdown(wait=False)
