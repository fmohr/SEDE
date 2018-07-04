import threading
from util.locking import synchronized_method as synchronized


class Observer:
    def __init__(self, condition_checker, notification_consumer, remove_checker=lambda task: True):
        self.condition_checker = condition_checker
        self.notification_consumer = notification_consumer
        self.remove_checker = remove_checker
        self.lock = threading.RLock()

    def notify_condition(self, observed):
        return self.condition_checker(observed)

    def notification(self, observed):
        return self.notification_consumer(observed)

    def remove_when_notified(self, observed):
        return self.remove_checker(observed)


class Observable:
    """
    Objects of this class can be observed using Observer instances.
    Using the update function it notifies its observers with the observed instance.
    """

    def __init__(self, observedInstance=None):
        self.observedInstace = observedInstance
        self.observers = list()

    @synchronized
    def observe(self, observer: Observer):
        self.observers.append(observer)

    def update(self, instance=None):
        if instance is None and self.observedInstace is not None:
            instance = self.observedInstace

        remainingObservers = list(self.observers)
        for observer in self.observers:
            with observer.lock:
                if observer.notify_condition(instance):
                    observer.notification(instance)
                if(observer.remove_when_notified(instance)):
                    remainingObservers.remove(observer)

        # replace observer list by the remaining list:
        self.observers = remainingObservers
