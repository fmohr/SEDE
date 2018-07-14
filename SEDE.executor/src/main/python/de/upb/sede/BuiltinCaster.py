import json


def json_encode(value):
    return json.dumps(value).encode()


def json_decode(value):
    return json.loads(value.decode())


cts_List = json_encode
cts_Dict = json_encode

cfs_List = json_decode
cfs_Dict = json_decode
