import com.fasterxml.jackson.databind.ObjectMapper
import ai.services.beta.ExecutorRegistration
import ai.services.composition.FieldType
import ai.services.composition.IFieldType
import ai.services.composition.types.DataValueType
import ai.services.composition.types.PrimitiveValueType
import ai.services.composition.types.ServiceInstanceType
import ai.services.core.PrimitiveType
import ai.services.core.ServiceInstanceHandle
import ai.services.exec.ExecutorCapabilities
import ai.services.exec.ExecutorContactInfo
import ai.services.exec.ExecutorHandle
import ai.services.exec.IExecutorHandle
import ai.services.requests.resolve.beta.IResolveRequest
import ai.services.requests.resolve.beta.ResolvePolicy
import ai.services.requests.resolve.beta.ResolveRequest
import ai.services.util.DefaultMap

import java.util.concurrent.atomic.AtomicInteger

ResolveRequest.builder().with {
    composition("")
}

IResolveRequest rr = ResolveRequest.builder()
    .composition("a;b;c;")
    .resolvePolicy(ResolvePolicy.builder()
        .build())
    .clientExecutorRegistration(
        ExecutorRegistration.builder()
            .executorHandle(ExecutorHandle.builder()
                .contactInfo(ExecutorContactInfo.builder()
                    .qualifier("Cl_Exec")
                    .hostAddress("localhost:5000")
                    .isReachable(true)
                    .build())
                .capabilities(ExecutorCapabilities.builder()
                    .addFeatures("java", "inplacecast")
                    .addServices("S1", "S2", "S3")
                    .build())
                .build())
            .build())
    .addInitialContext(
        FieldType.builder()
            .fieldname("f1")
            .type(DataValueType.builder()
                .qualifier("T1")
                .build())
            .build(),
        FieldType.builder()
            .fieldname("f2")
            .type(DataValueType.builder()
                .qualifier("T2")
                .build())
            .build(),
        FieldType.builder()
            .fieldname("f3")
            .type(PrimitiveValueType.builder()
                .primitiveType(PrimitiveType.Number)
                .build())
            .build(),
        FieldType.builder()
            .fieldname("f4")
            .type(ServiceInstanceType.builder()
                .qualifier("S1")
                .build())
            .build())
    .putInitialServices("f4", new ServiceInstanceHandle(
        "ex1", "S1", "instanceId"))
    .build()


def getColQualifier(int colId) {
    return "col" + colId
}

def getServiceQualifier(int colId, int serviceId) {
    return getColQualifier(colId) + ".S" + serviceId
}

def getMethodDef(int colId, List<String> inputs, List<String> outputs) {
    def methodName = "m__" + inputs.join("_")
    if(!outputs.isEmpty()) {
        methodName += "__" + outputs.join("_")
    }
    return [
        name: methodName,
        inputs: inputs,
        outputs: outputs
    ]
}

def getTypeQualifier(Integer colId, Integer typeId) {
    return getColQualifier(colId) + "T" + typeId
}

def getTypeSemanticType(Integer typeId) {
    return "sem" + (typeId % 2)
}


class TestCase {

    List<String> composition = new ArrayList<>()

    List<IFieldType> initialFields = new ArrayList<>()

    List<IFieldType> currentFields = new ArrayList<>()

    Random r = new Random(0)

    List<IExecutorHandle> handle = new ArrayList<>()

    Map<String, AtomicInteger> ids = new DefaultMap<>()

    def initialize_client() {
        def services = []

        handle.add(ExecutorHandle.builder()
            .contactInfo(ExecutorContactInfo.builder()
                .qualifier("Cl_Exec")
                .hostAddress("localhost:5000")
                .isReachable(true)
                .build())
            .capabilities(ExecutorCapabilities.builder()
                .addFeatures("java", "inplacecast")
                .addServices(services)
                .build())
            .build())
    }

    def nextInst() {

    }
}

def mapper = new ObjectMapper().writerWithDefaultPrettyPrinter()
mapper.writeValue(new File("ResolveRequest.json"), rr)
