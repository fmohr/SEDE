from plainlib.package1.b import Numbers, NumbersCaster1
from util.com import WriteFileRequest, ReadFileRequest
from time import time

numbers1 = Numbers(*range(1,1000))

# use struct caster

time_struct = time()
start = time()
bytes_ = numbers1.cts_Numbers()
time_struct_write = time() - start
semantic_data_file = "testrsc/semantic-data/numbers/caster-struct.sem"
write = WriteFileRequest(semantic_data_file)
write.send_receive_bytes(bytes_)

read = ReadFileRequest(semantic_data_file)
# bytes_read = read.send_receive_bytes()
start = time()
numbers2 = Numbers.cfs_Numbers(bytes_)
time_struct_read = time() - start
datasize_struct = len(bytes_)

assert numbers1.ns == numbers2.ns

# use json caster

time_json = time()
start = time()
bytes_ = NumbersCaster1.cts_Numbers(numbers1)
time_json_write = time() - start
semantic_data_file = "testrsc/semantic-data/numbers/caster-json.sem"
write = WriteFileRequest(semantic_data_file)
write.send_receive_bytes(bytes_)


read = ReadFileRequest(semantic_data_file)
# bytes_read = read.send_receive_bytes()
start = time()
numbers2 = NumbersCaster1.cfs_Numbers(bytes_)

time_json_read = time() - start
datasize_json = len(bytes_)

assert numbers1.ns == numbers2.ns

print("struct w: %0.3fs - r: %0.3fs - size: %i"% (time_struct_write , time_struct_read, datasize_struct))
print("json   w: %0.3fs - r: %0.3fs - size: %i"% (time_json_write , time_json_read, datasize_json))
