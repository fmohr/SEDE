package de.upb.sede.composition

import de.upb.sede.composition.choerography.ChoreographyException
import de.upb.sede.composition.choerography.emulation.executors.ExecutionGraph
import de.upb.sede.composition.choerography.emulation.executors.GraphTraversal
import de.upb.sede.composition.graphs.nodes.*
import de.upb.sede.composition.types.IDataValueType
import de.upb.sede.composition.types.ServiceInstanceType
import de.upb.sede.core.ServiceInstanceHandle
import de.upb.sede.requests.resolve.beta.IResolvePolicy
import de.upb.sede.requests.resolve.beta.ResolvePolicy
import spock.lang.Specification
import spock.lang.Unroll

import java.lang.reflect.Field

class ServiceLoadStoreRRTest extends Specification {

    @Unroll("service init then store: s0(store)=#storeService, s0(return)=#returnService")
    def "service init then store"() {
        def description =
            """A service is being created, stored and returned.""".stripMargin()

        when:
        def rr = RRGen.fromClosure {
            composition = """
            s0 = c0.S0::__construct();
            t0 = s0::m__T0();
            """

            resolvePolicy = ResolvePolicy.builder()
                .tap {
                    isDotGraphRequested(true)
                    returnPolicy(IResolvePolicy.FieldSelection.LISTED)
                    if(returnService) {
                        addReturnFieldnames("s0")
                    }
                    servicePolicy(IResolvePolicy.FieldSelection.LISTED)
                    if(storeService) {
                        addPersistentServices("s0")
                    }
                }
                .build();
        }

        def testRunner = new ResolutionTestBaseRunner("ServiceLoadStore",
            "01_ServiceInitStore(store=${storeService}, return=${returnService})")
        testRunner.setClientExecutor("client")

        testRunner.testPlainText = description
        testRunner.addExecutor("executor1", "c0.S0")
        testRunner.addExecutor("executor2", "c0.S1")

        then:
        testRunner.start(rr)
        testRunner.assertStaticAnalysisExceptionMatches(null)
        testRunner.assertSimulationExceptionMatches(null)
        testRunner.writeOutputs()

        def cc = testRunner.getCc()
        def ch = testRunner.getChoreography()
        def execGraph = new ExecutionGraph(ch.getCompositionGraph().find {it.executorHandle.qualifier=="executor1"})
        if(!storeService)
            assertServiceNotStored(execGraph, "s0")
        else
            assertServiceStoredAndReturned(execGraph, "s0")

        where:
        storeService | returnService
        true | true
        false | true
        true | false
        false | false
    }

    void assertServiceNotStored(ExecutionGraph graph, String fieldname) {
        def storeNode = graph.nodes.find {
            it instanceof IServiceInstanceStorageNode &&
                it.fieldName == fieldname &&
                ! it.isLoadInstruction()
        } as IServiceInstanceStorageNode
        assert storeNode == null
        def transmitNode = graph.nodes.find {
            it instanceof ITransmitDataNode &&
                it.fieldName == fieldname
        } as ITransmitDataNode
        assert transmitNode == null
    }

    void assertServiceStoredAndReturned(ExecutionGraph graph, String fieldname) {
        def storeNode = graph.nodes.find {
            it instanceof IServiceInstanceStorageNode &&
                it.fieldName == fieldname &&
                !it.isLoadInstruction()

        } as IServiceInstanceStorageNode

        assert storeNode != null

        def transmitNode = graph.nodes.find {
            it instanceof ITransmitDataNode &&
                it.fieldName == fieldname
        } as ITransmitDataNode
        assert transmitNode != null
        assert transmitNode.contactInfo.qualifier == "client"

        assert GraphTraversal.isTherePathFromTo(graph, storeNode, transmitNode)

    }

    @Unroll("service init twice, store once: store(s0)=#storeService")
    def "service init twice, store once"() {
        def description =
            """Two services are assigned to the same field and stored to be returned.
            |The first one shouldn't be stored.""".stripMargin()

        when:
        def rr = RRGen.fromClosure {
            composition = """
            s0 = c0.S0::__construct();
            t0 = s0::m__T0();
            s0 = c0.S0::constT0({t0});
            t1 = s0::m__T1();
            """

            resolvePolicy = ResolvePolicy.builder()
                .tap {
                    isDotGraphRequested(true)
                    returnPolicy(IResolvePolicy.FieldSelection.ALL)
                    servicePolicy(IResolvePolicy.FieldSelection.LISTED)
                    if(storeService) {
                        addPersistentServices("s0")
                    }
                }
                .build();
        }

        def testRunner = new ResolutionTestBaseRunner("ServiceLoadStore",
            "02_ServiceReassign(store=${storeService})")
        testRunner.setClientExecutor("client")

        testRunner.testPlainText = description
        testRunner.addExecutor("executor1", "c0.S0")
        testRunner.addExecutor("executor2", "c0.S1")

        then:
        testRunner.start(rr)
        testRunner.assertStaticAnalysisExceptionMatches(null)
        testRunner.assertSimulationExceptionMatches(null)
        testRunner.writeOutputs()

        def cc = testRunner.getCc()
        def ch = testRunner.getChoreography()

        ExecutionGraph graph = new ExecutionGraph(
            ch.getCompositionGraph().find { it.executorHandle.qualifier == "executor1"})
        if(storeService) {
            assertOnlySecondStored(graph, "s0")
        } else {
            assertServiceNotStored(graph, "s0")
        }
        where:
        storeService << [true, false]
    }

    void assertOnlySecondStored(ExecutionGraph graph, String fieldname) {
        def storeNodes = graph.nodes.findAll {
            it instanceof IServiceInstanceStorageNode &&
                it.fieldName == fieldname
        } as List<IServiceInstanceStorageNode>

        assert storeNodes != null
        assert storeNodes.size() == 1

        def firstServiceInst = graph.nodes.find {
            it instanceof IInstructionNode &&
                it.index == 1
        } as IInstructionNode

        def secondServiceInst = graph.nodes.find {
            it instanceof IInstructionNode &&
                it.index == 3
        } as IInstructionNode

        assert firstServiceInst != null
        assert secondServiceInst != null

        assert GraphTraversal.isTherePathFromTo(graph, firstServiceInst, secondServiceInst)

        assert GraphTraversal.isTherePathFromTo(graph, firstServiceInst, storeNodes[0])
        assert GraphTraversal.isTherePathFromTo(graph, secondServiceInst, storeNodes[0])
    }

    @Unroll("injected service load, stored and returned: store(s0)=#storeService")
    def "injected service load, stored and returned"() {
        def description =
            """A service is injected into the composition.
               |Check that the service is being stored if requested.""".stripMargin()

        when:
        def rr = RRGen.fromClosure {
            composition = """
            t0 = s0::m__T0();
            t1 = s0::m__T1();
            """
            initialContext.add(
                FieldType.builder().tap {
                    fieldname("s0")
                    type(ServiceInstanceType.builder()
                        .qualifier("c0.S0")
                        .build())
                }.build()
            )
            initialServices.put("s0",
                new ServiceInstanceHandle("executor1", "c0.S0", "serviceInstance1")
            )
            resolvePolicy = ResolvePolicy.builder()
                .tap {
                    isDotGraphRequested(true)
                    returnPolicy(IResolvePolicy.FieldSelection.ALL)
                    servicePolicy(IResolvePolicy.FieldSelection.LISTED)
                    if(storeService) {
                        addPersistentServices("s0")
                    }
                }
                .build();
        }

        def testRunner = new ResolutionTestBaseRunner("ServiceLoadStore",
            "03_InjectedService(store=${storeService})")
        testRunner.setClientExecutor("client")

        testRunner.testPlainText = description
        testRunner.addExecutor("executor1", "c0.S0")
        testRunner.addExecutor("executor2", "c0.S1")

        then:
        testRunner.start(rr)
        testRunner.assertStaticAnalysisExceptionMatches(null)
        testRunner.assertSimulationExceptionMatches(null)
        testRunner.writeOutputs()

        def cc = testRunner.getCc()
        def ch = testRunner.getChoreography()

        def execGraph = new ExecutionGraph(ch.compositionGraph.find {it.executorHandle.qualifier == "executor1"})
        if(!storeService)
            assertServiceNotStored(execGraph, "s0")
        else
            assertServiceStoredAndReturned(execGraph, "s0")

        where:
        storeService << [true, false]
    }

}
