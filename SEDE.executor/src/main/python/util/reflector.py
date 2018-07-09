import inspect
import logging
from importlib import \
    import_module  # used to dereference module names. see function: construct
from inspect import \
    signature  # used to inspect function signature. see function: _validate_parameters

from types import ModuleType

def validate_parameters(callable_, *given_param_list, **given_param_dict):
    """ validates the parameters from the given_param and fills it with the argument from given_param_list and returns a clean paramter dictionary which matches the signature of the callable_.
    Parameters:
        given_param: Dictionary given to call the callable_
        callable_: Python callable object. Can be a function or a constructor.
        weak: if true, is doesn't check if every necessary argument is found in given_param.
    Returns:
        A sanitized parameter dictionary that can be used to call the callable_.
    Raises:
        ValueError, if there is a parameter in callable's signature that is mandatory and given_param doesn't contain it.
    """
    # Now create a validated dictionary based on the signature from the method:
    weak = given_param_dict.get("weak", False)
    given_param_list = list(given_param_list)
    validated_list = []
    validated_dict = {}
    signature_ = signature(callable_)
    given_param = given_param_dict
    # Extract args from kwargs
    # given_param_list: contains the list of positional arguments which are given by the client.
    # iterate over every parameter:
    for param_key in signature_.parameters:
        if len(given_param_list) > 0:  # there are still positional arguments to be dealt with
            # Delete positional arguments until its empty.
            argument = given_param_list.pop(0)  # extract first argument.
            validated_list.append(argument)  # add the argument to the validated list

        elif (param_key in given_param):
            # Copy parameters to the validated dictionary:
            validated_dict[param_key] = given_param[param_key]

        elif (not weak and signature_.parameters[param_key].default == inspect._empty):  # No default is set.
            # The parameter wasn't sent by the client.
            raise ValueError("Parameter is not given: {}".format(param_key))
    # logging.debug("validated dict: " + str(validated_dict.keys()))
    return validated_list, validated_dict


def fullname(o):
    """ Get fully qualified class name of an object o.
    """
    try:
        return o.__module__ + "." + o.__class__.__name__
    except AttributeError:  # o object has no attribute '__module__'
        return o.__class__.__name__


def traverse_package(package_path, package_string=""):
    """ Recursively traverses package_path list and calls traverse_module.
    """

    def traverse_module(package_path, module):
        """ Recursevly traverses package_path until it its empty and the module is found. Else None is returned.
        """
        if not package_path:
            return module
        attribute = package_path[0]
        if hasattr(module, attribute):
            return traverse_module(package_path[1:], getattr(module, attribute))
        else:
            return None

    if isinstance(package_path, str):
        package_path = package_path.split(".")

    package_string = package_string + package_path[0]
    package = import_module(package_string)
    package_path = package_path[1:]
    if len(package_path) == 0:
        if isinstance(package, ModuleType):
            return package
        else:
            raise ValueError("Failed reflection: " + package_string)

    attribute = package_path[0]
    if hasattr(package, attribute):
        module = traverse_module(package_path[1:], getattr(package, attribute))
        if module:
            return module
        else:
            raise ValueError("Failed reflection: " + package_string + str(package_path))
    else:
        return traverse_package(package_path, package_string + ".")


def construct(package_path, *args, **kwargs):
    """ Dereferences a callable by its package_path and calls it using the given parameters.
    """
    try:
        module = traverse_package(package_path)
    except ModuleNotFoundError as ex:
        raise ValueError(f"{ex}")

    if not module:
        # No module found.
        raise ValueError("No module found: '{}'".format(".".join(package_path)))
    try:
        clazz = module
        if (not callable(clazz)):
            raise ValueError("{} is not callable. found by de-referencing: '{}'".format(clazz.__name__, ".".join(package_path)))
        # Validate the parameters:
        validated_args, validated_kwargs = validate_parameters(clazz, *args, **kwargs)
        # Call the constructor:
        instance = clazz(*validated_args, **validated_kwargs)
        return instance
    except AttributeError as e:
        logging.error(e, exc_info=True)
        raise e  # ValueError(error.const.class_not_found_in_module.format( class_name, module_name))


def call(instance, method_name, *args, **kwargs):
    """ call is used to access a instance's method or field.
    Parameters:
        instance: Python object whose attribute will be accessed.
        method_name: String containing the name of the attribute.
        parameters: Dictionary containing the parameters used to call the requested function.
    Returns:
        If method_name is a function within instance its return value will be returned.
    Raises:
        ValueError if the given instance doesn't contain the method_name.
        ValueError if parameters doesn't contain all the needed parameter function that is requiered to call the requested function.
    """
    # Check if the instance owns the requested attribute. If it doesn't return None.
    if hasattr(instance, method_name):
        attribute_ = getattr(instance, method_name)
    else:
        raise ValueError("Instance doesn't own {}".format(method_name))
    # If the requested attribute is a method, call it:
    # Note that: callable(None) returns False.

    if (callable(attribute_)):
        method_ = attribute_
        validated_args, validated_kwargs = validate_parameters(method_, *args, **kwargs)
        returned_val = method_(*validated_args, **validated_kwargs)
        return returned_val
    else:
        raise ValueError("{} is not callable.".format(method_name))