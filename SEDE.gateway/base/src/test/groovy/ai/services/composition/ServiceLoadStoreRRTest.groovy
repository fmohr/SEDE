package ai.services.composition


import ai.services.composition.choerography.emulation.executors.ExecutionGraph
import ai.services.composition.choerography.emulation.executors.GraphTraversal
import ai.services.composition.graphs.nodes.IInstructionNode
import ai.services.composition.graphs.nodes.IServiceInstanceStorageNode
import ai.services.composition.graphs.nodes.ITransmitDataNode
import de.upb.sede.composition.graphs.nodes.*
import ai.services.core.ServiceInstanceHandle
import ai.services.requests.resolve.beta.IResolvePolicy
import spock.lang.Specification
import spock.lang.Unroll

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

            resolvePolicy = ai.services.requests.resolve.beta.ResolvePolicy.builder()
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

            resolvePolicy = ai.services.requests.resolve.beta.ResolvePolicy.builder()
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
                    type(ai.services.composition.types.ServiceInstanceType.builder()
                        .typeQualifier("c0.S0")
                        .build())
                }.build()
            )
            initialServices.put("s0",
                new ServiceInstanceHandle("executor1", "c0.S0", "serviceInstance1")
            )
            resolvePolicy = ai.services.requests.resolve.beta.ResolvePolicy.builder()
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

        def s0Load = execGraph.nodes.find { (it instanceof IServiceInstanceStorageNode) && it.isLoadInstruction() } as IServiceInstanceStorageNode
        assert s0Load != null
        s0Load.tap {
            assert instanceIdentifier == "serviceInstance1"
        }

        RRTestHelpers.assertExecutedBefore(execGraph, s0Load, 0L)
        RRTestHelpers.assertExecutedBefore(execGraph, s0Load, 1L)


        def s0Store = execGraph.nodes.find { (it instanceof IServiceInstanceStorageNode) && !it.isLoadInstruction() } as IServiceInstanceStorageNode
        if(storeService) {
            assert s0Store != null
            s0Store.tap {
                assert instanceIdentifier == "serviceInstance1"
            }
            RRTestHelpers.assertExecutedAfter(execGraph, s0Store, 0L);
            RRTestHelpers.assertExecutedAfter(execGraph, s0Store, 1L);
        } else {
            assert s0Store == null
        }

        where:
        storeService << [true, false]
    }


    @Unroll("overwrite injected service: store(s0)=#storeService")
    def "overwrite injected service"() {
        def description =
            """A service is injected into the composition. But on the field a new service is initialed. Only the new one is returned. Both are stored though.
               |Check that the service is being stored if requested.""".stripMargin()

        when:
        def rr = RRGen.fromClosure {
            composition = """
            t0 = s0::m__T0();
            s0 = c0.S0::__construct();
            t1 = s0::m__T1();
            """
            initialContext.add(
                FieldType.builder().tap {
                    fieldname("s0")
                    type(ai.services.composition.types.ServiceInstanceType.builder()
                        .typeQualifier("c0.S0")
                        .build())
                }.build()
            )
            initialServices.put("s0",
                new ServiceInstanceHandle("executor1", "c0.S0", "serviceInstance1")
            )
            resolvePolicy = ai.services.requests.resolve.beta.ResolvePolicy.builder()
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
            "04_OverwriteInjectedService(store=${storeService})")
        testRunner.setClientExecutor("client")

        testRunner.testPlainText = description
        testRunner.addExecutor("executor1", "c0.S0")

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

        def s0Load = execGraph.nodes.find { (it instanceof IServiceInstanceStorageNode) && it.isLoadInstruction() } as IServiceInstanceStorageNode
        assert s0Load != null
        s0Load.tap {
            assert instanceIdentifier == "serviceInstance1"
        }
        RRTestHelpers.assertExecutedBefore(execGraph, s0Load, 0L)

        def s0InjectedStore = execGraph.nodes.find { (it instanceof IServiceInstanceStorageNode) && !it.isLoadInstruction() && it.instanceIdentifier != null  } as IServiceInstanceStorageNode
        if(storeService) {
            assert s0InjectedStore != null
            s0InjectedStore.tap {
                assert instanceIdentifier == "serviceInstance1"
            }
            RRTestHelpers.assertExecutedAfter(execGraph, s0InjectedStore, 0L)
            RRTestHelpers.assertExecutedBefore(execGraph, s0InjectedStore, 1L)
        } else {
            assert s0InjectedStore == null
        }

        def s0NewStore = execGraph.nodes.find { (it instanceof IServiceInstanceStorageNode) && !it.isLoadInstruction() && it.instanceIdentifier == null  } as IServiceInstanceStorageNode
        if(storeService) {
            assert s0NewStore != null
            s0NewStore.tap {
                assert instanceIdentifier == null
            }
            RRTestHelpers.assertExecutedAfter(execGraph, s0NewStore, 2L)
        } else {
            assert s0NewStore == null
        }

        def s0NewReturn = execGraph.nodes.find {(it instanceof ITransmitDataNode) && it.fieldName == "s0" } as ITransmitDataNode

        if(storeService) {
            assert s0NewReturn != null
            RRTestHelpers.assertExecutedBefore(execGraph, s0NewStore, s0NewReturn)

        } else {
            assert s0NewReturn == null
        }

        where:
        storeService << [true, false]
    }

}
