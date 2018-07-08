import struct
import binascii
from io import BytesIO

class B:
    """ Dummy class B.
    Contains 2 numbers: a and b.
    """
    def __init__(self, a , b):
        self.a = a
        self.b = b
    
    def calc(self, c):
        return self.a + self.b * c

    def new_numbers(self):
        return Numbers()

    def calc_append_inplace(self, numbers, c):
        numbers.ns.append(self.calc(c))

    def calc_append(self, numbers, c):
        return Numbers(numbers.ns + [self.calc(c)])
    
    def set_a(self, a):
        self.a = a
    
    def set_b(self, b):
        self.b = b

    @staticmethod
    def upper(text):
        return text.upper()

    def __str__(self):
        return "a = {} b = {}".format(self.a, self.b)

    @classmethod
    def random(cls):
        from random import randint
        b = B(randint(0, 9), randint(0, 9))
        return b

class A:
    """ Dummy class A.
    References any object.
    """
    def __init__(self, ref):
        self.ref = ref


class Numbers:
    def __init__(self, *args):
        self.ns = list(args)

    @staticmethod
    def cts_Numbers(numbers:'Numbers'):
        format_string = "!i" + "!d" * len(numbers.ns)
        return struct.pack(format_string, *[len(numbers.ns), numbers.ns])

    @staticmethod
    def cfs_Numbers(byte_arr: bytes):
        integer_byte_size = struct.calcsize("!i")
        length = struct.unpack("!i", byte_arr[:integer_byte_size])
        format_string = "!d" * length
        ns = list(struct.unpack(format_string, byte_arr[integer_byte_size:]))
        return Numbers(*ns)
