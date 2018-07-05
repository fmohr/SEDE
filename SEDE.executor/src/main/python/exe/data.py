from enum import Enum
from util.helpers import JsonSerializable

class PrimitiveType(Enum):
    NULL = 0
    String = 1
    Number = 2
    Bool = 3

    @staticmethod
    def from_ci_str(case_insesitive_typname: str) -> 'PrimitiveType':
        search_name = case_insesitive_typname.upper()
        if search_name.upper() == "NULL":
            return PrimitiveType.NULL
        elif search_name.upper() == "STRING":
            return PrimitiveType.String
        elif search_name.upper() == "NUMBER":
            return PrimitiveType.Number
        elif search_name.upper() == "BOOL":
            return PrimitiveType.Bool
        raise Exception("BUG: primitive type '%s' not defined." % case_insesitive_typname)

class ServiceInstanceHandle(JsonSerializable):
    pass

class SEDEObject(object):

    pass
