import com.fasterxml.jackson.databind.ObjectMapper
import de.upb.sede.beta.ExecutorRegistration
import de.upb.sede.composition.FieldType
import de.upb.sede.composition.IFieldType
import de.upb.sede.composition.types.DataValueType
import de.upb.sede.composition.types.PrimitiveValueType
import de.upb.sede.composition.types.ServiceInstanceType
import de.upb.sede.core.PrimitiveType
import de.upb.sede.core.ServiceInstanceHandle
import de.upb.sede.exec.ExecutorCapabilities
import de.upb.sede.exec.ExecutorContactInfo
import de.upb.sede.exec.ExecutorHandle
import de.upb.sede.exec.IExecutorHandle
import de.upb.sede.requests.resolve.beta.IResolveRequest
import de.upb.sede.requests.resolve.beta.ResolvePolicy
import de.upb.sede.requests.resolve.beta.ResolveRequest
import de.upb.sede.util.DefaultMap

import java.util.concurrent.atomic.AtomicInteger

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
