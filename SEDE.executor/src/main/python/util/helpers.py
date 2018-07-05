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
