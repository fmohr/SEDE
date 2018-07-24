import os
import os.path
import exe
import numpy as np
from ml import larff
from collections import UserList

logging = exe.getlogger("MLS")

# Read the path of data set folder from environment variable: 'DATASET_PATH'

if "DATASET_PATH" in os.environ:
    DATASET_PATH = os.environ["DATASET_PATH"]
else:
    DATASET_PATH = "testrsc/ml/datasets/"
    logging.warn("Environment variable 'DATASET_PATH' isn't defined.")
    logging.info("Using %s as default path to data sets. Change default in: MLDataSets.java",
                 DATASET_PATH)

if not os.path.isdir(DATASET_PATH):
    logging.fatal("Dataset folder %s doesn't exist.", os.path.abspath(DATASET_PATH))

def dataset_abspath(relative_path:str)->str:
    if DATASET_PATH.endswith("/") and relative_path.startswith("/"):
        relative_path = relative_path[1:] # chop the duplicate '/' letter off.
    return os.path.abspath(DATASET_PATH + relative_path)

def load_dataset(relative_path:str, classindex = None)->dict:
    dataset_path = dataset_abspath(relative_path)
    with open(dataset_path, "r") as fp:
        dataset = larff.load(fp)

    prepare_dataset(dataset, classindex)
    return dataset

def create_dataset(arff:str, classindex = None)->dict:
    dataset = larff.loads(arff)
    prepare_dataset(dataset, classindex)
    return dataset

def prepare_dataset(dataset:dict, classindex = None):
    set_classindex(dataset, classindex)

def get_attr_dtype(dataset:dict) -> str:
    dtype = ""
    for attr in dataset["attributes"]:
        type_ = attr[1]
        if isinstance(type_, list):
            dtype += "U"
            length = 0
            for classname in type_:
                if len(classname) > length:
                    length = len(classname)
            dtype += str(length)
        elif isinstance(type_, str) and type_.upper() == "REAL":
            dtype += "f8"
        else:
            dtype += "O"
        # TODO other classes

        dtype +=", "
    return dtype[:-2]



def store_dataset(dataset:dict, relative_path:str):
    dataset_path = dataset_abspath(relative_path)
    parent_dir = os.path.dirname(dataset_path)
    os.makedirs(parent_dir, exist_ok=True)
    if not os.path.isdir(parent_dir):
        raise ValueError("Cannot sore dataset to path: %s. Not a directory." % dataset_path)
    with open(dataset_path, "w") as fp:
        larff.dump(dataset, fp)


def attr_count(dataset:dict)->int:
    return len(dataset["attributes"])

def classindex(dataset) :
    return dataset["classindex"]

def set_classindex(dataset:dict, classindex = None):
    if classindex is not None:
        attrCount = attr_count(dataset)
        dataset["classindex"] = (classindex % attrCount)
    else:
        dataset["classindex"] = None

def has_classindex(dataset:dict)-> bool:
    return "classindex" in dataset and dataset["classindex"] is not None

class X(object):
    def __init__(self, data, classindex):
        self.data = data
        self.classindex = classindex


    def __len__(self):
        return len(self.data)


    def __getitem__(self, i):
        data = self.data[i]
        classindex = self.classindex
        return data[:classindex] + data[classindex + 1:]


class Y(object):
    def __init__(self, data, classindex):
        self.data = data
        self.classindex = classindex


    def __len__(self):
        return len(self.data)


    def __getitem__(self, i):
        data = self.data[i]
        classindex = self.classindex
        return data[classindex]


def x(dataset):
    ci = classindex(dataset)
    if ci is None:
        return dataset["data"]
    else:
        return X(dataset["data"], ci)

def y(dataset):
    ci = classindex(dataset)
    if ci is None:
        raise ValueError("No class index provided")
    else:
        return Y(dataset["data"], ci)