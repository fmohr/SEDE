"""
functions, methods and class decorators functioning the same way java keyword 'synchronized' works.
"""

import threading
import types


def synchronized_func(func):
	
    func.__lock__ = threading.Lock()
		
    def synced_func(*args, **kws):
        with func.__lock__:
            return func(*args, **kws)

    return synced_func

def synchronized_method(method):
    outer_lock = threading.RLock()
    lock_name = "__lock__method"

    def sync_method(self, *args, **kws):
        with outer_lock:
            if not hasattr(self, lock_name): setattr(self, lock_name, threading.RLock())
            lock = getattr(self, lock_name)
            with lock:
                return method(self, *args, **kws)
    return sync_method

def synchronized_with(lock):
		
    def decorator(func):
			
        def synced_func(*args, **kws):
            with lock:
                return func(*args, **kws)
        return synced_func
		
    return decorator

def synchronized_class(sync_class):
	
    lock = threading.RLock()
	
    orig_init = sync_class.__init__
    def __init__(self, *args, **kws):
        self.__lock__ = lock
        orig_init(self, *args, **kws)
    sync_class.__init__ = __init__
	
    for key in sync_class.__dict__:
        val = sync_class.__dict__[key]
        if type(val) is types.FunctionType:
            decorator = synchronized_with(lock)
            sync_class.__dict__[key] = decorator(val)
    
    return sync_class