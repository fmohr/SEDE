from util.locking import synchronized_method as synchronized
from util.observing import Observable, Observer
from exec.executor import ExecutorConfig
import json


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
    exec_id: str
    config: ExecutorConfig
    unfinished_tasks:set
    waiting_tasks:set
    task_observer:Observer

    def __init__(self, exec_id, config):
        self.exec_id = exec_id
        self.config = config
        self.unfinished_tasks = set()
        self.waiting_tasks = set()
        # TODO set other variables

    def has_execution_finished(self) -> bool:
        return False

    def environment(self) -> ExecutionEnvironment:
        return None

    def deserialize_graph(self, graph: str):
        g = json.loads(graph)
        if "nodes" not in g or "edges" not in g:
            raise Exception("Cannot create a graph from a json object that doesn't contain fields: nodes and edges")
        nodes = g["nodes"]
        edges = g["edges"]
        tasks = list()
        # Deserialize tasks:
        for node in nodes:
            task = Task(self, node)
            tasks.append(task)
            self.add_task(task)
        # Connect tasks as stated by the graph:
        for edge in edges:
            source_index = int(edge)
            source_task = tasks[source_index]
            for target_index in edges[edge]:
                target_task = tasks[target_index]
                target_task.add_dependency(source_task)

        for task in tasks:
            task.update_dependecy()

        self.has_graph = True

    def add_task(self, task):
        if task not in self.unfinished_tasks:
            self.unfinished_tasks.add(task)
            self.waiting_tasks.add(task)
            task.state.observe(self.task_observer)



    def interrupt(self):
        pass


class Task:
    execution: Execution

    task_name:str

    attributes:dict

    resolved = False
    started = False
    doneRunning = False
    failed = False
    succeeded = False

    dependecy_has_failed = False

    state: Observable
    dependecies_observer: Observer

    error: Exception

    dependencies: set

    def __init__(self, execution, attributes_dict):
        self.attributes = attributes_dict
        self.task_name = attributes_dict["nodetype"]
        self.execution = execution  # reference to its execution
        self.dependencies = set()
        self.state = Observable(self)
        self.dependecies_observer = Observer()

    def add_dependency(self, dependecy_task):
        self.dependencies.add(dependecy_task)
        dependecy_task.state.observe(self.dependecies_observer)

    @synchronized
    def dependecy_notification_condition(self, dependecy_task):
        return dependecy_task.finished() and dependecy_task in self.dependencies

    @synchronized
    def dependecy_notification(self, dependency_task):
        if self.finished():
            return
        self.dependencies.remove(dependency_task)
        if dependency_task.failed:
            self.dependecy_has_failed = True

        self.update_dependecy()

    def update_dependecy(self):
        if len(self.dependencies) == 0:
            self.set_resolved()

    @synchronized
    def set_resolved(self):
        if not self.resolved:
            self.resolved = True
            self.state.update()

    @synchronized
    def set_started(self):
        if not self.started:
            self.resolved = True
            self.started = True
            self.state.update()

    @synchronized
    def set_done(self):
        if not self.doneRunning:
            self.resolved = True
            self.started = True
            self.doneRunning = True
            self.state.update()

    @synchronized
    def set_succeeded(self):
        if not self.succeeded:
            self.resolved = True
            self.started = True
            self.doneRunning = True
            self.succeeded = True
            self.state.update()

    @synchronized
    def set_failed(self):
        if not self.failed:
            self.resolved = True
            self.started = True
            self.doneRunning = True
            self.failed = True
            self.state.update()

