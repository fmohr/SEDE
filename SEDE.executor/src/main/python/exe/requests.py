from util.helpers import JsonSerializable
from exe.executor import ExecutorConfig

class RequestIdMixin(JsonSerializable):
    requestId = None

    def __init__(self, *args,**kwargs):
        super.__init__(*args, **kwargs)
        self.requestId = kwargs["requestId"]
            
    def to_dict(self, d):
        super().to_dict(d)
        d.update(requestId=self.requestId)

class FieldnameMixin(JsonSerializable):
    fieldname = None
    def __init__(self, *args,**kwargs):
        super.__init__(*args, **kwargs)
        self.fieldname = kwargs["fieldname"]

    def to_dict(self, d):
        super().to_dict(d)
        d.update(fieldname=self.fieldname)

class GraphMixin(JsonSerializable):
    graph = None
    def __init__(self, *args,**kwargs):
        super.__init__(*args, **kwargs)
        self.graph = kwargs["graph"]

    def to_dict(self, d):
        super().to_dict(d)
        d.update(graph=self.graph)

class DataMixin(JsonSerializable):
    data = None
    def __init__(self, *args,**kwargs):
        super.__init__(*args, **kwargs)
        self.data = kwargs.get("data")

    def to_dict(self, d):
        super().to_dict(d)

        # TODO write data into dictionary
    

class CompositionMixin(JsonSerializable):
    composition = None
    def __init__(self, *args,**kwargs):
        super.__init__(*args, **kwargs)
        self.composition = kwargs["composition"]

    def to_dict(self, d):
        super().to_dict(d)
        d.update(composition=self.composition)
    
# class ResolvePolicy(JsonSerializable):
#     returnPolicy = "ALL"
#     servicePolicy = "ALL"
#     blockTillFinished = True
#     returnDotGraph = False

#     def __init__(self, *args,**kwargs):
#         super.__init__(*args, **kwargs)
#         self.returnPolicy = kwargs.get("return-policy", self.returnPolicy)
#         self.servicePolicy = kwargs.get("service-policy", self.servicePolicy)
#         self.blockTillFinished = kwargs.get("block-till-finished", self.blockTillFinished)
#         self.blockTillFinished = kwargs.get("return-dot", self.blockTillFinished)

#     @classmethod
#     def default(cls):
#         return cls.from_dict(dict())

#     def to_dict(self, d):
#         super().to_dict(d)
#         d.update(returnPolicy=self.returnPolicy)

class DataPutRequest(RequestIdMixin, FieldnameMixin, DataMixin):
    unavailable = False
    def __init__(self, *args,**kwargs):
        super.__init__(*args, **kwargs)
        self.unavailable = kwargs.get("unavailable", False)

    def to_dict(self, d):
        super().to_dict(d)
        d.update(unavailable=self.unavailable)

class ExecRequest(RequestIdMixin, GraphMixin):
    pass


class ExecutorRegistration(JsonSerializable):
    contact_information = None
    capabilities = None
    supported_services = None

    def __init__(self, *args,**kwargs):
        super.__init__(*args, **kwargs)
        self.contact_information = kwargs.get("contact-info")
        self.capabilities = kwargs.get("capabilities")
        self.supported_services = kwargs.get("supported-services")

    @classmethod
    def from_config(cls, contact_information, executorConfig: ExecutorConfig):
        return cls.from_dict({
            "contact-info" : contact_information,
            "capabilities" : executorConfig.capabilities,
            "supported-servies": executorConfig.services})

    def to_dict(self, d):
        super().to_dict(d)
        d.update({
            "contact-info":self.contact_information,
            "capabilities":self.capabilities,
            "supported-servies":self.supported_services})

class Result(RequestIdMixin, FieldnameMixin, DataMixin):
    pass

class RunRequest(RequestIdMixin, CompositionMixin):
    policy = None # ResolvePolicy see the java implementation
    inputs = None # inputs dictionary

    def __init__(self, *args,**kwargs):
        super.__init__(*args, **kwargs)
        self.policy = kwargs["policy"]
        self.inputs = kwargs["inputs"]

    def to_dict(self, d):
        super().to_dict(d)
        d.update(policy=self.policy)
        d.update(inputs=self.inputs)
