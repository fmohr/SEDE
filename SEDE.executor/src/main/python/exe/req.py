from util.helpers import JsonSerializable
from exe.config import ExecutorConfig

class RequestIdMixin(JsonSerializable):
    requestId = None

    def __init__(self, requestId, *args,**kwargs):
        super().__init__(*args, **kwargs)
        self.requestId = requestId

    def to_dict(self, d):
        super().to_dict(d)
        d.update(requestId=self.requestId)

class FieldnameMixin(JsonSerializable):
    fieldname = None
    def __init__(self, fieldname, *args,**kwargs):
        super().__init__(*args, **kwargs)
        self.fieldname = fieldname

    def to_dict(self, d):
        super().to_dict(d)
        d.update(fieldname=self.fieldname)

class GraphMixin(JsonSerializable):
    graph = None
    def __init__(self, graph, *args,**kwargs):
        super().__init__(*args, **kwargs)
        self.graph = graph

    def to_dict(self, d):
        super().to_dict(d)
        d.update(graph=self.graph)

class DataMixin(JsonSerializable):
    data = None
    def __init__(self, data=None, *args,**kwargs):
        super().__init__(*args, **kwargs)
        self.data = data

    def to_dict(self, d):
        super().to_dict(d)
        from exe.data import encode
        d.update("data", encode(self.data))


class CompositionMixin(JsonSerializable):
    composition = None
    def __init__(self, composition, *args,**kwargs):
        super().__init__(*args, **kwargs)
        self.composition = composition

    def to_dict(self, d):
        super().to_dict(d)
        d.update(composition=self.composition)

# class ResolvePolicy(JsonSerializable):
#     returnPolicy = "ALL"
#     servicePolicy = "ALL"
#     blockTillFinished = True
#     returnDotGraph = False

#     def __init__(self, *args,**kwargs):
#         super().__init__(*args, **kwargs)
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
    def __init__(self, unavailable=False, *args,**kwargs):
        super().__init__(*args, **kwargs)
        self.unavailable = unavailable
        if self.unavailable == (self._data_present()): # Xor
            raise ValueError("BUG: DataPutRequest: unavailable is set to {} but data is {}given."
                             .format(self.unavailable,
                                "" if self._data_present() else "not "))

    def _data_present(self) -> bool:
        return self.data is not None

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
        super().__init__(*args, **kwargs)
        self.contact_information = kwargs.get("contact-info")
        self.capabilities = kwargs.get("capabilities")
        self.supported_services = kwargs.get("supported-services")

    @classmethod
    def from_config(cls, contact_information, executorConfig: ExecutorConfig):
        return cls.from_dict({
            "contact-info" : contact_information,
            "capabilities" : executorConfig.capabilities,
            "supported-services": executorConfig.services})

    def to_dict(self, d):
        super().to_dict(d)
        d.update({
            "contact-info":self.contact_information,
            "capabilities":self.capabilities,
            "supported-services":self.supported_services})

class Result(RequestIdMixin, FieldnameMixin, DataMixin):
    pass

class RunRequest(RequestIdMixin, CompositionMixin):
    policy = None # ResolvePolicy see the java implementation
    inputs = None # inputs dictionary

    def __init__(self, *args,**kwargs):
        super().__init__(*args, **kwargs)
        self.policy = kwargs["policy"]
        self.inputs = kwargs["inputs"]

    def to_dict(self, d):
        super().to_dict(d)
        d.update(policy=self.policy)
        d.update(inputs=self.inputs)
