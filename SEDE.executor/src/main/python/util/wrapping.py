""" Defines usefull mixins that can be extended by wrapper classes.
"""
import json
import logging
from json.decoder import JSONDecodeError
from util import reflector


ATTRIBUTE_BLACKLIST = ["__getstate__", "__setstate__"]

class WrappedClassMixin(object):
    """
    Constructs a wrapped object using its classpath.
    """
    wrapped_obj: object = None
    def __init__(self, classpath:str, *args, **kwargs):
        self.wrapped_obj = reflector.construct(classpath, *args, **kwargs)
        self.name = reflector.fullname(self.wrapped_obj)


class DelegateFunctionsMixin(object):
    """ Delegate accesses to a delegate.
    """
    delegate = None
    def __init__(self, delegate):
        self.delegate = delegate

    def __getattr__(self, name):
        """ Attributes are accessed that aren't defined by the subclass.
        Redirect to delegate if it is defined.
        """
        if hasattr(super(), name):
            return getattr(super(), name)
        elif name not in ATTRIBUTE_BLACKLIST and self.delegate is not None and hasattr(self.delegate, name):
            return getattr(self.delegate, name)
        else:
            getattr(super(),name) # should raise an error
