from enum import Enum
try:
    import ujson as json
except ImportError:
    import json

from util.helpers import JsonSerializable, require_not_none
from util.com import *
from util.reflector import traverse_package as tp
import re
import exe
logging = exe.logging

class PrimitiveType(Enum):
    NULL = 0
    String = 1
    Number = 2
    Bool = 3

    @staticmethod
    def from_ci_str(case_insesitive_typname: str) -> 'PrimitiveType':
        """
        Given the case insesitive name this method will return the corresponding PritimiveType object.
        For example: for 'null' this method returns PrimitiveType.NULL.
        :param case_insesitive_typname: Case insentice type name
        :return: corresponding Primitive type
        """
        search_name = case_insesitive_typname.upper()
        for primType in PrimitiveType:
            if primType.name.upper() == search_name:
                return primType
        raise Exception("BUG: primitive type '%s' not defined." % case_insesitive_typname)

    @classmethod
    def has_name(cls, name:str)->bool:
        return any(name.upper() == item.name.upper() for item in cls)

class ServiceInstanceHandle(JsonSerializable):
    executorId:str
    classpath:str
    serviceId:str

    def __init__(self, executorId:str, classpath:str, id:str, *args,**kwargs):
        super().__init__(*args, **kwargs)
        self.executorId = executorId
        self.classpath = classpath
        self.serviceId = id

    def to_dict(self, d=dict()):
        super().to_dict(d)
        d.update(executorId=self.executorId, classpath=self.classpath, id=self.serviceId)
        return d


class ServiceInstance(ServiceInstanceHandle):
    service_instance = None

    def has_service_instance(self) -> bool:
        return self.service_instance is not None


SEMANTIC = "SEMANTIC".upper()

class SEDEObject(object):
    type: str
    data: None

    def __init__(self, type, data):
        if not isinstance(type, str):
            raise ValueError("BUG: given type is not a string: " + str(type))

        self.type = require_not_none(type)
        self.data = require_not_none(data)

        if isinstance(data, ServiceInstanceHandle) and \
            type.upper() != ServiceInstanceHandle.__name__.upper():
            raise ValueError("BUG: given object is service instance handle "
                             "but t givenype is: " + type)


    @classmethod
    def from_primitive(cls, primitive_type:PrimitiveType, data)-> 'SEDEObject':
        return SEDEObject(primitive_type.name, data)

    @classmethod
    def from_ServiceInstance(cls, serviceInstance: ServiceInstanceHandle)-> 'SEDEObject':
        return SEDEObject(type=ServiceInstanceHandle.__name__, data=serviceInstance)

    @classmethod
    def from_Semantic(cls, byte_arr:bytes):
        return SEDEObject(type = SEMANTIC, data=byte_arr)


    def is_service_instance(self)->bool:
        if isinstance(self, SEDEObject):
            type_str = self.type
        else:
            type_str = str(self)
        return type_str.upper() == ServiceInstanceHandle.__name__.upper()


    def is_primitive(self)->bool:
        if isinstance(self, SEDEObject):
            type_str = self.type
        else:
            type_str = str(self)

        return PrimitiveType.has_name(type_str)

    def is_semantic(self):
        if isinstance(self, SEDEObject):
            type_str = self.type
        else:
            type_str = str(self)
        if type_str.upper() == SEMANTIC:
            if isinstance(self, SEDEObject):
                assert is_bytes(self.data)
            return True
        else:
            return False



def readfrom(input_bytes:bytes, input_type:str)-> SEDEObject:
    if SEDEObject.is_primitive(input_type):
        primitive_string = input_bytes.decode()
        return SEDEObject.from_primitive(PrimitiveType.from_ci_str(input_type), primitive_string)
    elif SEDEObject.is_service_instance(input_type):
        json_string = input_bytes.decode()
        sih = ServiceInstanceHandle.from_json_string(json_string)
        return SEDEObject.from_ServiceInstance(sih)
    else:
        # else assume its semantic:
        byte_arr = input_bytes
        return SEDEObject.from_Semantic(byte_arr)

def read_object_from(input, caster:str, target_real_classpath:str)->SEDEObject:
    if isinstance(input, SEDEObject):
        if input.is_semantic():
            input = input.data # is bytes
        else:
            return input # incase its not sematic there is nothing to do.

    logging.debug("Casting from semantic type to '{}' using caster class: {}."
                  .format(target_real_classpath, caster))
    target_type = simplename_from_classpath(target_real_classpath)
    cast_methodname = get_cast_methodname(target_type, False)
    cast_methodpath = caster + "." + cast_methodname
    logging.debug("cast method: " + cast_methodpath)
    method = tp(cast_methodpath)
    casted_object = method(input)
    return SEDEObject(target_real_classpath, casted_object)

def decode(input, input_type = None, caster = None, target_real_classpath=None)->SEDEObject:
    if isinstance(input, SEDEObject) and not input.is_semantic():
        return input # incase its not sematic there is nothing to do.
    if caster is not None and target_real_classpath is not None:
        return read_object_from(input, caster, target_real_classpath)

    if not is_bytes(input):
        raise ValueError("Input is expected to be a SEDEObject or byte. Its '%s' instead." % str(input.__class__))

    if input_type is None:
        input_type = SEMANTIC

    return readfrom(input, input_type)

def encode(content:SEDEObject, caster: str = None)->bytes:
    if content.is_primitive():
        return prim_to_str(content.data).encode()
    elif content.is_service_instance():
        serviceInstace : ServiceInstanceHandle = content.data
        return serviceInstace.to_json_string().encode()
    elif content.is_semantic():
        return content.data
    else:
        # content contains real data.
        # encode it to bytes using the given caster
        if caster is None:
            raise ValueError("Cannot encode the given sedeobject of type %s "
                             "without a specified caster." %content.type)
        logging.debug("Casting from '%s' to semantic type using caster class: %s.",
                      content.type, caster);

        source_real_type = simplename_from_classpath(content.type)
        caster_methodname = get_cast_methodname(source_real_type, True)

        cast_methodpath = caster + "." + caster_methodname
        logging.trace("cast method: " + cast_methodpath)
        method = tp(cast_methodpath)
        encoded_bytes: bytes = method(content.data)
        return encoded_bytes

def get_cast_methodname(realtype:str, to_semantic:bool)-> str:
    if to_semantic:
        methodname = "cts_"
    else:
        methodname = "cfs_"
    methodname += realtype
    return methodname


classname_regex = re.compile("([a-zA-Z_0-9\.]+\.)*(?P<classpath>[a-zA-Z_]\w*)$")
def simplename_from_classpath(classpath:str)->str:
    m = classname_regex.match(classpath)
    if m is None:
        raise ValueError("Type is invalid: %s" % classpath)
    else:
        return m.group("classpath")

def prim_to_str(primitive:object)->str:
    if primitive is None:
        return "null"
    else:
        return str(primitive)