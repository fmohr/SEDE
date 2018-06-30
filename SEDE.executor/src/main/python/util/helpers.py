
def require_not_none(some_object):
    """
    Raises an error if the given object is None.
    Else it returns the given object.
    """
    if some_object is None:
        raise ValueError("Object is None.")
    else:
        return some_object