package de.upb.sede.composition

import de.upb.sede.composition.graphs.nodes.AcceptDataNode
import de.upb.sede.composition.graphs.nodes.IAcceptDataNode
import de.upb.sede.composition.graphs.nodes.IMarshalNode
import de.upb.sede.composition.graphs.nodes.ITransmitDataNode
import de.upb.sede.composition.types.DataValueType
import de.upb.sede.composition.types.IDataValueType
import de.upb.sede.composition.types.IRefType
import de.upb.sede.composition.types.IServiceInstanceType
import de.upb.sede.composition.types.RefType
import de.upb.sede.composition.types.ServiceInstanceType
import de.upb.sede.composition.types.serialization.IMarshalling
import de.upb.sede.composition.types.serialization.Marshalling
import de.upb.sede.core.ServiceInstanceHandle
import spock.lang.Specification

class TransmissionRRTest extends Specification {

    def "basic transmissions of data"() {
        def description =
            """Tests the basic transmission types among two executors.""".stripMargin()

        when:
        def rr = RRGen.fromClosure {
            composition = """
            s0 = c0.S0::__construct();
            t0 = s0::m__T0();
            t1 = s0::m__T1();
            s1::mT0({t0});
            s1::mT1({t1});
            """
            resolvePolicy = RRGen.defaultResolvePolicy()
            initialContext += FieldType.builder()
                    .fieldname("s1")
                    .type(ServiceInstanceType.builder()
                        .typeQualifier("c0.S1")
                        .build())
                    .build()
            initialServices["s1"] = new ServiceInstanceHandle("executor2", "c0.S1", "s1Id1")
        }

        def testRunner = new ResolutionTestBaseRunner("Transmissions", "01_BasicTransmissions")
        testRunner.setClientExecutor("client")

        testRunner.testPlainText = description
        testRunner.addExecutor("executor1", "c0.S0")
        testRunner.addExecutor("executor2", "c0.S1")

        then:
        testRunner.start(rr)
        testRunner.assertStaticAnalysisExceptionMatches(null)
        testRunner.assertSimulationExceptionMatches(null)
        testRunner.writeOutputs()

//        def cc = testRunner.getCc()
        def ch = testRunner.getChoreography()

        def clientGraph = ch.compositionGraph.find { it.client }
        def ex1Graph = ch.compositionGraph.find {it.executorHandle.qualifier == "executor1"}
        def ex2Graph = ch.compositionGraph.find {it.executorHandle.qualifier == "executor2"}

        /*
         * Check if the client transmits service handle s1 to executor 2 before executor 2 uses it:
         */
        def s1HandleT = clientGraph.nodes.find { it instanceof ITransmitDataNode } as ITransmitDataNode
        s1HandleT != null
        s1HandleT.contactInfo == ex2Graph.executorHandle.contactInfo
        s1HandleT.fieldName == "s1"
        s1HandleT.marshalling.direction == IMarshalling.Direction.MARSHAL
        s1HandleT.marshalling.semanticName == IRefType.SEMANTIC_SERVICE_INSTANCE_HANDLE_TYPE
        s1HandleT.marshalling.valueType instanceof IRefType
        ((IRefType)s1HandleT.marshalling.valueType).typeOfRef instanceof IServiceInstanceType
        s1HandleT.marshalling.valueType.typeQualifier == "c0.S1"

        def s1HandleA = ex2Graph.nodes.find { (it instanceof IAcceptDataNode) && it.fieldName == "s1" } as IAcceptDataNode
        s1HandleA != null
        s1HandleA.marshalling.direction == IMarshalling.Direction.UNMARSHAL
        s1HandleA.marshalling.semanticName == IRefType.SEMANTIC_SERVICE_INSTANCE_HANDLE_TYPE
        s1HandleA.marshalling.valueType instanceof IRefType
        s1HandleA.marshalling.valueType.typeQualifier == "c0.S1"


        /*
         * Check inter executor transmissions:
         * executor1 needs to transmit fields t0 and t1 to executor2.
         * executor2 needs to accept t0 and t1.
         */
        def t0T = ex1Graph.nodes.find { (it instanceof  ITransmitDataNode)
            && it.fieldName == "t0"
            && it.contactInfo.qualifier == "executor2" } as ITransmitDataNode
        def t1T = ex1Graph.nodes.find { (it instanceof  ITransmitDataNode)
            && it.fieldName == "t1"
            && it.contactInfo.qualifier == "executor2" } as ITransmitDataNode

        [t0T, t1T].each {
            assert it != null
            assert it.contactInfo.qualifier == "executor2"
            assert it.marshalling.valueType instanceof IDataValueType
            def m = it.marshalling
            assert m.valueType instanceof IDataValueType
            assert  m.direction == IMarshalling.Direction.MARSHAL
        }
        t0T.marshalling.semanticName == "sem0"
        t1T.marshalling.semanticName == "sem1"
        t0T.marshalling.valueType.typeQualifier == "c0.T0"
        t1T.marshalling.valueType.typeQualifier == "c0.T1"

        def t0A = ex2Graph.nodes.find{(it instanceof IAcceptDataNode) && it.fieldName == "t0"} as IAcceptDataNode
        def t1A = ex2Graph.nodes.find{(it instanceof IAcceptDataNode) && it.fieldName == "t1"} as IAcceptDataNode

        [t0A, t1A].each {
            assert it != null
            def m = it.marshalling
            assert m.valueType instanceof IDataValueType
            assert m.direction == IMarshalling.Direction.UNMARSHAL
        }
        t0A.fieldName == "t0"
        t1A.fieldName == "t1"
        t0A.marshalling.tap {
            semanticName == "sem0"
            valueType.typeQualifier == "c0.T0"
        }
        t1A.marshalling.tap {
            semanticName == "sem1"
            valueType.typeQualifier == "c0.T1"
        }

        /*
         * Check if the client receives the outputs:
         * The client needs to receive a service handle for s0 and s1 from executor1 and executor2
         * The client also needs t0 and t1 from executor1
         */
        def res0T = ex1Graph.nodes.find { (it instanceof ITransmitDataNode) && it.fieldName == "s0" } as ITransmitDataNode
        def res1T = ex2Graph.nodes.find { (it instanceof ITransmitDataNode) && it.fieldName == "s1" } as ITransmitDataNode

        [res0T, res1T].each {
            it.tap {
                assert marshalling.direction == IMarshalling.Direction.MARSHAL
                assert marshalling.valueType instanceof IRefType
            }
        }

        def res0A = clientGraph.nodes.find { (it instanceof IAcceptDataNode) && it.fieldName == "s0" } as IAcceptDataNode
        def res1A = clientGraph.nodes.find { (it instanceof IAcceptDataNode) && it.fieldName == "s1" } as IAcceptDataNode

        res0A == AcceptDataNode.builder()
            .index(res0A.index)
            .fieldName("s0")
            .hostExecutor("client")
            .marshalling(Marshalling.builder()
                .direction(IMarshalling.Direction.UNMARSHAL)
                .semanticName(IRefType.SEMANTIC_SERVICE_INSTANCE_HANDLE_TYPE)
                .valueType(RefType.builder().typeOfRef(ServiceInstanceType.builder().typeQualifier("c0.S0").build()).build())
                .build())
            .build()
        res1A == AcceptDataNode.builder()
            .index(res1A.index)
            .fieldName("s1")
            .hostExecutor("client")
            .marshalling(Marshalling.builder()
                .direction(IMarshalling.Direction.UNMARSHAL)
                .semanticName(IRefType.SEMANTIC_SERVICE_INSTANCE_HANDLE_TYPE)
                .valueType(RefType.builder().typeOfRef(ServiceInstanceType.builder().typeQualifier("c0.S1").build()).build())
                .build())
            .build()

        def ret0T = ex1Graph.nodes.find { (it instanceof ITransmitDataNode) && it.fieldName == "t0" } as ITransmitDataNode
        def ret1T = ex1Graph.nodes.find { (it instanceof ITransmitDataNode) && it.fieldName == "t1" } as ITransmitDataNode

        ret0T.tap {
            assert hostExecutor == "executor1"
            marshalling.tap {
                assert direction == IMarshalling.Direction.MARSHAL
                assert semanticName == "sem0"
                assert valueType == DataValueType.builder().typeQualifier("c0.T0").build()
            }
        }
        ret1T.tap {
            assert hostExecutor == "executor1"
            marshalling.tap {
                assert direction == IMarshalling.Direction.MARSHAL
                assert semanticName == "sem1"
                assert valueType == DataValueType.builder().typeQualifier("c0.T1").build()
            }
        }

        /*
         * The client should receive the fields.
         * But there are no MarshalNodes because we dont have any information on what is expected.
         */

        def ret0A = clientGraph.nodes.find { (it instanceof IAcceptDataNode) && it.fieldName == "t0" } as IAcceptDataNode
        def ret1A = clientGraph.nodes.find { (it instanceof IAcceptDataNode) && it.fieldName == "t1" } as IAcceptDataNode

        ret0A.tap {
            assert hostExecutor == "client"
            assert marshalling == null
        }
        ret1A.tap {
            assert hostExecutor == "client"
            assert marshalling == null
        }

        /*
         * Check if the fields are marshalled before and after they are sent:
         */
        def s1HandleMarshal = clientGraph.nodes.find { (it instanceof IMarshalNode) && it.fieldName == "s1" && it.marshalling.direction == IMarshalling.Direction.MARSHAL } as IMarshalNode
        def s1HandleUnmarshal = ex2Graph.nodes.find { (it instanceof IMarshalNode) && it.fieldName == "s1" && it.marshalling.direction == IMarshalling.Direction.UNMARSHAL } as IMarshalNode

        RRTestHelpers.assertExecutedBefore(clientGraph, s1HandleMarshal, s1HandleT)
        RRTestHelpers.assertExecutedBefore(ex2Graph, s1HandleA, s1HandleUnmarshal)

    }

}
