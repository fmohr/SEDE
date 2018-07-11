import json
import exe
logging = exe.logging

from exe.config import ExecutorConfig
from util.locking import synchronized_method as synchronized
from util.observing import Observable, Observer


class ExecutionEnvironment(dict):
    unavailables: set
    state: Observable

    def __init__(self, executor_id, execution_id, *args, **kwargs):
        super().__init__(*args, **kwargs)
        self.unavailables = set()
        self.state = Observable(self)
        self.executor_id = executor_id
        self.execution_id = execution_id

    @synchronized
    def __setitem__(self, key, value):
        logging.trace("%s.%s field update: %s", self.executor_id, self.execution_id, key)
        super().__setitem__(key, value)
        self.state.update()

    @synchronized
    def mark_unavailable(self, fieldname: str):
        self.unavailables.add(fieldname)
        self.state.update()

    @synchronized
    def __contains__(self, item):
        if item in self.unavailables:
            return False
        else:
            return super().__contains__(item)


class Execution:
    exec_id: str

    env: ExecutionEnvironment

    state: Observable
    runnable_tasks: Observable

    tasks_observer: Observer

    has_graph: bool = False
    interrupted: bool = False


    config: 'ExecutorConfig'

    unfinished_tasks: set
    waiting_tasks: set

    def __init__(self, exec_id, config:'ExecutorConfig'):
        self.exec_id = exec_id
        self.config = config
        self.unfinished_tasks = set()
        self.waiting_tasks = set()
        self.tasks_observer = Observer(lambda task: True, self.task_update_event, lambda task: False)
        self.env = ExecutionEnvironment(config.executor_id, exec_id)
        self.state = Observable(self)
        self.runnable_tasks = Observable()

    @synchronized
    def task_update_event(self, task: 'Task'):
        if task.is_waiting():
            self.runnable_tasks.update(task)
        if task.started and task in self.waiting_tasks:
            self.waiting_tasks.remove(task)
            self.state.update()
        if task.has_finished() and task in self.unfinished_tasks:
            self.unfinished_tasks.remove(task)
            self.state.update()

    @synchronized
    def has_execution_finished(self) -> bool:
        return self.interrupted or len(self.unfinished_tasks) == 0

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
            task.state.observe(self.tasks_observer)

    @synchronized
    def interrupt(self):
        self.interrupted = True
        self.state.update()

    @synchronized
    def is_interrupted(self):
        return self.interrupted


class Task:
    execution: Execution

    task_name: str

    attributes: dict

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
        self.dependecies_observer = Observer(self.dependecy_notification_condition,
                                             self.dependecy_notification)

    def add_dependency(self, dependecy_task):
        self.dependencies.add(dependecy_task)
        dependecy_task.state.observe(self.dependecies_observer)

    @synchronized
    def dependecy_notification_condition(self, dependecy_task:'Task'):
        return dependecy_task.has_finished() and dependecy_task in self.dependencies

    @synchronized
    def dependecy_notification(self, dependency_task):
        if self.has_finished():
            return
        self.dependencies.remove(dependency_task)
        if dependency_task.failed:
            self.dependecy_has_failed = True

        self.update_dependecy()

    def update_dependecy(self):
        if len(self.dependencies) == 0:
            self.set_resolved()

    def is_waiting(self):
        return self.resolved and not self.started

    def has_finished(self):
        return self.succeeded or self.failed

    def __str__(self):
        return self["description"]

    def __contains__(self, item):
        return item in self.attributes

    def __getitem__(self, key):
        if key in self.attributes:
            return self.attributes[key]
        else:
            raise ValueError(("GATEWAY BUG: "
                             "'{}' was queried by a procedure "
                             "but was not defined in the task attributes.").format(key))

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
        if not self.has_finished():
            self.resolved = True
            self.started = True
            self.doneRunning = True
            self.succeeded = True
            self.state.update()

    @synchronized
    def set_failed(self):
        if not self.has_finished():
            self.resolved = True
            self.started = True
            self.doneRunning = True
            self.failed = True
            self.state.update()

