import threading

def always_true(_):
    return True

class Observer:
    def __init__(self, condition_checker, notification_consumer, remove_checker=always_true):
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
        self.lock = threading.RLock()

    def observe(self, observer: Observer):
        with self.lock:
            if self.observedInstace is not None:
                remove = self.notification_process(self.observedInstace, observer)
            else:
                remove = False
            if not remove:
                self.observers.append(observer)

    def update(self, instance=None):
        with self.lock:
            if instance is None and self.observedInstace is not None:
                instance = self.observedInstace

            remainingObservers = list(self.observers)
            for observer in self.observers:
                remove = self.notification_process(instance, observer)
                if remove:
                    remainingObservers.remove(observer)

            # replace observer list by the remaining list:
            self.observers = remainingObservers

    def notification_process(self, instance, observer)-> bool:
        with observer.lock:
            if observer.notify_condition(instance):
                observer.notification(instance)
                return observer.remove_when_notified(instance)
            else:
                return False
