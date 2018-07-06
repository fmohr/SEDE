from enum import Enum
try:
    import ujson as json
except ImportError:
    import json

from util.helpers import JsonSerializable, require_not_none
from util.com import *

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


class SEDEObject(object):
    type: str
    data: None
    SEMANTIC = "$_SEMANTIC".upper()

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
        return SEDEObject(type = SEDEObject.SEMANTIC, data=byte_arr)


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
        return type_str.upper() == SEDEObject.SEMANTIC


def readfrom(input_stream, input_type:str)-> SEDEObject:
    if SEDEObject.is_primitive(input_type):
        primitive_string = in_read_string(input_stream)
        return SEDEObject.from_primitive(PrimitiveType.from_ci_str(input_type, primitive_string))
    elif SEDEObject.is_si(input_type):
        json_string = in_read_string(input_stream)
        sih = ServiceInstanceHandle.from_json_string(json_string)
        return SEDEObject.from_ServiceInstance(sih)
    else:
        # else assume its semantic:
        byte_arr = in_read_bytes(input_stream)
        return SEDEObject.from_Semantic(byte_arr)

