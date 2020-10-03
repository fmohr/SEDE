package ai.services.composition

import ai.services.composition.choerography.emulation.executors.ExecutionGraph
import ai.services.composition.graphs.nodes.BaseNode
import ai.services.composition.graphs.nodes.CompositionGraph
import ai.services.composition.graphs.nodes.IAcceptDataNode
import ai.services.composition.graphs.nodes.IDeleteFieldNode
import ai.services.composition.graphs.nodes.IMarshalNode
import ai.services.composition.graphs.nodes.ITransmitDataNode
import ai.services.core.ServiceInstanceHandle
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
                    .type(ai.services.composition.types.ServiceInstanceType.builder()
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
        def s1HandleT = clientGraph.nodes.find { it instanceof ai.services.composition.graphs.nodes.ITransmitDataNode } as ai.services.composition.graphs.nodes.ITransmitDataNode
        s1HandleT != null
        s1HandleT.contactInfo == ex2Graph.executorHandle.contactInfo
        s1HandleT.fieldName == "s1"
        s1HandleT.marshalling.direction == ai.services.composition.types.serialization.IMarshalling.Direction.MARSHAL
        s1HandleT.marshalling.semanticName == ai.services.composition.types.IRefType.SEMANTIC_SERVICE_INSTANCE_HANDLE_TYPE
        s1HandleT.marshalling.valueType instanceof ai.services.composition.types.IRefType
        ((ai.services.composition.types.IRefType)s1HandleT.marshalling.valueType).typeOfRef instanceof ai.services.composition.types.IServiceInstanceType
        s1HandleT.marshalling.valueType.typeQualifier == "c0.S1"

        def s1HandleA = ex2Graph.nodes.find { (it instanceof ai.services.composition.graphs.nodes.IAcceptDataNode) && it.fieldName == "s1" } as ai.services.composition.graphs.nodes.IAcceptDataNode
        s1HandleA != null
        s1HandleA.marshalling.direction == ai.services.composition.types.serialization.IMarshalling.Direction.UNMARSHAL
        s1HandleA.marshalling.semanticName == ai.services.composition.types.IRefType.SEMANTIC_SERVICE_INSTANCE_HANDLE_TYPE
        s1HandleA.marshalling.valueType instanceof ai.services.composition.types.IRefType
        s1HandleA.marshalling.valueType.typeQualifier == "c0.S1"


        /*
         * Check inter executor transmissions:
         * executor1 needs to transmit fields t0 and t1 to executor2.
         * executor2 needs to accept t0 and t1.
         */
        def t0T = ex1Graph.nodes.find { (it instanceof  ai.services.composition.graphs.nodes.ITransmitDataNode)
            && it.fieldName == "t0"
            && it.contactInfo.qualifier == "executor2" } as ai.services.composition.graphs.nodes.ITransmitDataNode
        def t1T = ex1Graph.nodes.find { (it instanceof  ai.services.composition.graphs.nodes.ITransmitDataNode)
            && it.fieldName == "t1"
            && it.contactInfo.qualifier == "executor2" } as ai.services.composition.graphs.nodes.ITransmitDataNode

        [t0T, t1T].each {
            assert it != null
            assert it.contactInfo.qualifier == "executor2"
            assert it.marshalling.valueType instanceof ai.services.composition.types.IDataValueType
            def m = it.marshalling
            assert m.valueType instanceof ai.services.composition.types.IDataValueType
            assert  m.direction == ai.services.composition.types.serialization.IMarshalling.Direction.MARSHAL
        }
        t0T.marshalling.semanticName == "sem0"
        t1T.marshalling.semanticName == "sem1"
        t0T.marshalling.valueType.typeQualifier == "c0.T0"
        t1T.marshalling.valueType.typeQualifier == "c0.T1"

        def t0A = ex2Graph.nodes.find{(it instanceof ai.services.composition.graphs.nodes.IAcceptDataNode) && it.fieldName == "t0"} as ai.services.composition.graphs.nodes.IAcceptDataNode
        def t1A = ex2Graph.nodes.find{(it instanceof ai.services.composition.graphs.nodes.IAcceptDataNode) && it.fieldName == "t1"} as ai.services.composition.graphs.nodes.IAcceptDataNode

        [t0A, t1A].each {
            assert it != null
            def m = it.marshalling
            assert m.valueType instanceof ai.services.composition.types.IDataValueType
            assert m.direction == ai.services.composition.types.serialization.IMarshalling.Direction.UNMARSHAL
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
        def res0T = ex1Graph.nodes.find { (it instanceof ai.services.composition.graphs.nodes.ITransmitDataNode) && it.fieldName == "s0" } as ai.services.composition.graphs.nodes.ITransmitDataNode
        def res1T = ex2Graph.nodes.find { (it instanceof ai.services.composition.graphs.nodes.ITransmitDataNode) && it.fieldName == "s1" } as ai.services.composition.graphs.nodes.ITransmitDataNode

        [res0T, res1T].each {
            it.tap {
                assert marshalling.direction == ai.services.composition.types.serialization.IMarshalling.Direction.MARSHAL
                assert marshalling.valueType instanceof ai.services.composition.types.IRefType
            }
        }

        def res0A = clientGraph.nodes.find { (it instanceof ai.services.composition.graphs.nodes.IAcceptDataNode) && it.fieldName == "s0" } as ai.services.composition.graphs.nodes.IAcceptDataNode
        def res1A = clientGraph.nodes.find { (it instanceof ai.services.composition.graphs.nodes.IAcceptDataNode) && it.fieldName == "s1" } as ai.services.composition.graphs.nodes.IAcceptDataNode

        res0A == ai.services.composition.graphs.nodes.AcceptDataNode.builder()
            .index(res0A.index)
            .fieldName("s0")
            .hostExecutor("client")
            .marshalling(ai.services.composition.types.serialization.Marshalling.builder()
                .direction(ai.services.composition.types.serialization.IMarshalling.Direction.UNMARSHAL)
                .semanticName(ai.services.composition.types.IRefType.SEMANTIC_SERVICE_INSTANCE_HANDLE_TYPE)
                .valueType(ai.services.composition.types.RefType.builder().typeOfRef(ai.services.composition.types.ServiceInstanceType.builder().typeQualifier("c0.S0").build()).build())
                .build())
            .build()
        res1A == ai.services.composition.graphs.nodes.AcceptDataNode.builder()
            .index(res1A.index)
            .fieldName("s1")
            .hostExecutor("client")
            .marshalling(ai.services.composition.types.serialization.Marshalling.builder()
                .direction(ai.services.composition.types.serialization.IMarshalling.Direction.UNMARSHAL)
                .semanticName(ai.services.composition.types.IRefType.SEMANTIC_SERVICE_INSTANCE_HANDLE_TYPE)
                .valueType(ai.services.composition.types.RefType.builder().typeOfRef(ai.services.composition.types.ServiceInstanceType.builder().typeQualifier("c0.S1").build()).build())
                .build())
            .build()

        def ret0T = ex1Graph.nodes.find { (it instanceof ai.services.composition.graphs.nodes.ITransmitDataNode) && it.fieldName == "t0" } as ai.services.composition.graphs.nodes.ITransmitDataNode
        def ret1T = ex1Graph.nodes.find { (it instanceof ai.services.composition.graphs.nodes.ITransmitDataNode) && it.fieldName == "t1" } as ai.services.composition.graphs.nodes.ITransmitDataNode

        ret0T.tap {
            assert hostExecutor == "executor1"
            marshalling.tap {
                assert direction == ai.services.composition.types.serialization.IMarshalling.Direction.MARSHAL
                assert semanticName == "sem0"
                assert valueType == ai.services.composition.types.DataValueType.builder().typeQualifier("c0.T0").build()
            }
        }
        ret1T.tap {
            assert hostExecutor == "executor1"
            marshalling.tap {
                assert direction == ai.services.composition.types.serialization.IMarshalling.Direction.MARSHAL
                assert semanticName == "sem1"
                assert valueType == ai.services.composition.types.DataValueType.builder().typeQualifier("c0.T1").build()
            }
        }

        /*
         * The client should receive the fields.
         * But there are no MarshalNodes because we dont have any information on what is expected.
         */

        def ret0A = clientGraph.nodes.find { (it instanceof ai.services.composition.graphs.nodes.IAcceptDataNode) && it.fieldName == "t0" } as ai.services.composition.graphs.nodes.IAcceptDataNode
        def ret1A = clientGraph.nodes.find { (it instanceof ai.services.composition.graphs.nodes.IAcceptDataNode) && it.fieldName == "t1" } as ai.services.composition.graphs.nodes.IAcceptDataNode

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
        def s1HandleMarshal = clientGraph.nodes.find { (it instanceof IMarshalNode) && it.fieldName == "s1" && it.marshalling.direction == ai.services.composition.types.serialization.IMarshalling.Direction.MARSHAL } as IMarshalNode
        def s1HandleUnmarshal = ex2Graph.nodes.find { (it instanceof IMarshalNode) && it.fieldName == "s1" && it.marshalling.direction == ai.services.composition.types.serialization.IMarshalling.Direction.UNMARSHAL } as IMarshalNode

        RRTestHelpers.assertExecutedBefore(clientGraph, s1HandleMarshal, s1HandleT)
        RRTestHelpers.assertExecutedBefore(ex2Graph, s1HandleA, s1HandleUnmarshal)

    }

    def "field repeated transmission"() {
        def testRunner = new ResolutionTestBaseRunner("Transmissions", "02_FieldReTransmission")
        testRunner.testPlainText =
            """
            |Transmitting the same field t0 multiple times.
            |Each time the field needs to be deleted before it is accepted.""".stripMargin()

        when:
        def rr = RRGen.fromClosure {
            composition = """
            s0 = c0.S0::__construct();
            s1 = c0.S1::__construct();

            t0 = s0::m__T0();
            s1::mT0({t0});

            t0 = s0::m__T0();
            s1::mT0({t0});

            t0 = s0::m__T0();
            s1::mT0({t0});
            """
            resolvePolicy = RRGen.defaultResolvePolicy()
        }

        testRunner.setClientExecutor("client")
        testRunner.addExecutor("executor1", "c0.S0")
        testRunner.addExecutor("executor2", "c0.S1")

        then:
        testRunner.start(rr)
        testRunner.assertStaticAnalysisExceptionMatches(null)
        testRunner.assertSimulationExceptionMatches(null)
        testRunner.writeOutputs()

        def cc = testRunner.getCc()
        def ch = testRunner.getChoreography()

        def clientGraph = ch.compositionGraph.find { it.client }
        def ex1Graph = ch.compositionGraph.find {it.executorHandle.qualifier == "executor1"}
        def ex2Graph = ch.compositionGraph.find {it.executorHandle.qualifier == "executor2"}
        def ex2G = new ExecutionGraph(ex2Graph)

        def acceptNodes = new ArrayList<>(ex2Graph.nodes.findAll {
            it instanceof IAcceptDataNode && it.fieldName == "t0"
        })
        assert acceptNodes.size() == 3
        RRTestHelpers.sortInFlow(ex2G, acceptNodes)
        RRTestHelpers.assertExecutedInOrder(ex2G, acceptNodes)

        def deleteNodes = new ArrayList<>(ex2Graph.nodes.findAll {
            it instanceof IDeleteFieldNode && it.fieldName == "t0"
        })
        assert deleteNodes.size() >= acceptNodes.size() - 1
        RRTestHelpers.sortInFlow(ex2G, deleteNodes)
        RRTestHelpers.assertExecutedInOrder(ex2G, deleteNodes)

        RRTestHelpers.assertExecutedBefore(ex2G, acceptNodes[0], deleteNodes[0])
        RRTestHelpers.assertExecutedBefore(ex2G, acceptNodes[1], deleteNodes[1])

        RRTestHelpers.assertExecutedBefore(ex2G, deleteNodes[0], acceptNodes[1])
        RRTestHelpers.assertExecutedBefore(ex2G, deleteNodes[1], acceptNodes[2])
    }

    def "field transmission back"() {
        def testRunner = new ResolutionTestBaseRunner("Transmissions", "03_FieldReturnTransmission")
        testRunner.testPlainText =
            """
            |Transmitting the field t0 from executor1 to executor2 and then immediately back.
            |There is only a single delete operation needed.""".stripMargin()

        when:
        def rr = RRGen.fromClosure {
            composition = """
            s0 = c0.S0::__construct();
            s1 = c0.S1::__construct();

            t0 = s0::m__T0();

            t0 = s1::mT0__T0({t0});

            s0::mT0({t0});
            """
            resolvePolicy = RRGen.defaultResolvePolicy()
        }

        testRunner.setClientExecutor("client")
        testRunner.addExecutor("executor1", "c0.S0")
        testRunner.addExecutor("executor2", "c0.S1")

        then:
        testRunner.start(rr)
        testRunner.assertStaticAnalysisExceptionMatches(null)
        testRunner.assertSimulationExceptionMatches(null)
        testRunner.writeOutputs()

        def cc = testRunner.getCc()
        def ch = testRunner.getChoreography()

        def clientGraph = ch.compositionGraph.find { it.client }
        def ex1Graph = ch.compositionGraph.find {it.executorHandle.qualifier == "executor1"}
        def ex2Graph = ch.compositionGraph.find {it.executorHandle.qualifier == "executor2"}

        def ex1G = new ExecutionGraph(ex1Graph)

        def transmitNode = ex1G.nodes.find{it instanceof ITransmitDataNode && it.fieldName == "t0" && it.contactInfo.qualifier == "executor2"} as ITransmitDataNode
        def acceptNode = ex1G.nodes.find{it instanceof IAcceptDataNode && it.fieldName == "t0"} as IAcceptDataNode
        transmitNode != null
        acceptNode != null


        RRTestHelpers.assertExecutedBefore(ex1G, transmitNode, acceptNode)



        def deleteNodes = ex1G.nodes.findAll { it instanceof IDeleteFieldNode && it.fieldName == "t0"} as List<BaseNode>

        def nodes = new ArrayList<BaseNode>()
        nodes.addAll([transmitNode, acceptNode])
        nodes.addAll(deleteNodes)

        RRTestHelpers.sortInFlow(ex1G, nodes)

        def deleteNodesBetween = []
        def indexTransmit = nodes.indexOf(transmitNode)
        def indexAccept = nodes.indexOf(acceptNode)
        for (i in indexTransmit+1..<indexAccept) {
            deleteNodesBetween.add(nodes[i])
        }

        assert deleteNodesBetween.size() == 1
    }

}
