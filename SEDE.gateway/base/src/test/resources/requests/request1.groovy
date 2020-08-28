package requests

import de.upb.sede.beta.ExecutorRegistration
import de.upb.sede.composition.FieldType
import de.upb.sede.composition.RRGen
import de.upb.sede.composition.types.DataValueType
import de.upb.sede.composition.types.PrimitiveValueType
import de.upb.sede.composition.types.ServiceInstanceType
import de.upb.sede.core.PrimitiveType
import de.upb.sede.core.ServiceInstanceHandle
import de.upb.sede.exec.ExecutorCapabilities
import de.upb.sede.exec.ExecutorContactInfo
import de.upb.sede.exec.ExecutorHandle
import de.upb.sede.requests.resolve.beta.ResolvePolicy
import de.upb.sede.requests.resolve.beta.ResolveRequest
import groovy.transform.BaseScript

@BaseScript RRGen gen


ResolveRequest.builder()
    .composition("a;b;c;")
    .resolvePolicy(ResolvePolicy.builder()
        .build())
    .clientExecutorRegistration(
        ExecutorRegistration.builder()
            .executorHandle(ExecutorHandle.builder()
                .contactInfo(ExecutorContactInfo.builder()
                    .qualifier("Cl_Exec")
                    .uRL("localhost:5000")
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
                .typeQualifier("T1")
                .build())
            .build(),
        FieldType.builder()
            .fieldname("f2")
            .type(DataValueType.builder()
                .typeQualifier("T2")
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
                .typeQualifier("S1")
                .build())
            .build())
    .putInitialServices("f4", new ServiceInstanceHandle(
        "ex1", "S1", "instanceId"))
    .build()
