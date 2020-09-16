package requests

import ai.services.beta.ExecutorRegistration
import ai.services.composition.FieldType
import ai.services.composition.RRGen
import ai.services.composition.types.DataValueType
import ai.services.composition.types.PrimitiveValueType
import ai.services.composition.types.ServiceInstanceType
import ai.services.core.Primitives
import ai.services.core.ServiceInstanceHandle
import ai.services.exec.ExecutorCapabilities
import ai.services.exec.ExecutorContactInfo
import ai.services.exec.ExecutorHandle
import ai.services.requests.resolve.beta.ResolvePolicy
import ai.services.requests.resolve.beta.ResolveRequest
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
                .primitiveType(Primitives.Number)
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
