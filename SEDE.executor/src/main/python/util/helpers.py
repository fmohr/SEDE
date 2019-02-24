import logging
from logging import Logger
from logging import handlers

try:
    import ujson as json
except ImportError:
    logging.warn("Couldnt import ujson. using built in json module instead.")
    import json

def require_not_none(some_object):
    """
    Raises an error if the given object is None.
    Else it returns the given object.
    """
    if some_object is None:
        raise ValueError("Object is None.")
    else:
        return some_object

class JsonSerializable:
    """
    Base Class for json serilizable object, or mixin.
    Subclasses have to implement:

        def __init__(self, *args,**kwargs):
            super.__init__(*args, **kwargs)
            .
            .

        def to_dict(self, d):
            super().to_dict(d)
            .
            .
        
    In exe.requests you will see this in action.
    """
    def __init__(self, *args, **kwargs):
        pass

    @classmethod
    def from_json_string(cls, json_string):
        return cls.from_dict(json.loads(json_string))

    def to_json_string(self):
        d = dict()
        self.to_dict(d) 
        return json.dumps(d)

    @classmethod
    def from_dict(cls, d):
        return cls(**d)

    def to_dict(self, d):
        pass

    def __str__(self):
        d = dict()
        self.to_dict(d)
        return self.__class__.__name__ + ":" + \
               json.dumps()

DEBUG_LEVELV_NUM = 5
logging.addLevelName(DEBUG_LEVELV_NUM, "TRACE")
logging.TRACE = 4

def trace(self, message, *args, **kws):
    if self.isEnabledFor(DEBUG_LEVELV_NUM):
        self._log(DEBUG_LEVELV_NUM, message, args, **kws)

logging.Logger.trace = trace

def getlogger(loggername):
    #create logger
    logger = logging.getLogger(loggername)
    logger.setLevel(logging.DEBUG)
    # create console handler and set level to debug
    ch = logging.StreamHandler()
    ch.setLevel(logging.TRACE)
    # create file logger
    import os
    if not os.path.isdir("logs"):
        os.mkdir("logs")
    fh = logging.handlers.RotatingFileHandler('logs/sede-1.log', backupCount=1, maxBytes=1000000)
    fh.setLevel(logging.DEBUG)
    # create formatter
    formatter = logging.Formatter('%(asctime)s - %(name)s - %(levelname)s - %(message)s')
    # add formatter to ch
    ch.setFormatter(formatter)
    fh.setFormatter(formatter)
    # add ch to logger
    logger.addHandler(ch)
    logger.addHandler(fh)
    return logger
