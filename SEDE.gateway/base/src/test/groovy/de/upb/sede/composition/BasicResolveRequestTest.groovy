package de.upb.sede.composition

import de.upb.sede.composition.choerography.ChoreographyException
import de.upb.sede.composition.choerography.emulation.executors.ExecutionGraph
import de.upb.sede.composition.choerography.emulation.executors.GraphTraversal
import de.upb.sede.composition.graphs.nodes.IAcceptDataNode
import de.upb.sede.composition.graphs.nodes.ICompositionGraph
import de.upb.sede.composition.graphs.nodes.IInstructionNode
import de.upb.sede.composition.graphs.nodes.IParseConstantNode
import de.upb.sede.composition.graphs.nodes.IServiceInstanceStorageNode
import de.upb.sede.composition.types.DataValueType
import de.upb.sede.composition.types.ServiceInstanceType
import de.upb.sede.composition.typing.TypeCheckException
import de.upb.sede.core.PrimitiveType
import de.upb.sede.core.ServiceInstanceHandle
import spock.lang.Specification
import spock.lang.Unroll

class BasicResolveRequestTest extends Specification {

    def "3 instructions on the same executor"() {
        def description =
            """This is a very basic composition where a service is created and two methods are called upon it.
              |The output of each instruction is used for the following instruction so no two instruction should be able to run in parallel.""".stripMargin()

        when:
        def rr = RRGen.fromClosure {
            composition = """
            s0 = c0.S0::__construct();
            t0 = s0::m__T0();
            t1 = s0::mT0__T1({t0});
            """
            resolvePolicy = RRGen.defaultResolvePolicy()
        }

        def testRunner = new ResolutionTestBaseRunner("BasicResolutions", "01_SimpleSingleService")
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

        cc.programOrder == [0L, 1L, 2L]
        cc.fields.every {
            it.initialType == null
        }
        def t0 = cc.fields.find { it.fieldname == "t0" }
        t0.fieldAccesses.size() == 3
        t0.fieldAccesses[0].with{ it.accessType == IFieldAccess.AccessType.ASSIGN && it.index == 1L }
        t0.fieldAccesses[1].with{ it.accessType == IFieldAccess.AccessType.READ && it.index == 2L }
        t0.fieldAccesses[2].with{ it.accessType == IFieldAccess.AccessType.WRITE && it.index == 2L }

        def t1 = cc.fields.find { it.fieldname == "t1" }
        t1.fieldAccesses.size() == 1
        t1.fieldAccesses[0].with{ it.accessType == IFieldAccess.AccessType.ASSIGN && it.index == 2L }

        def s0 = cc.fields.find { it.fieldname == "s0" }
        s0.fieldAccesses.size() == 5
        s0.fieldAccesses[0].with{ it.accessType == IFieldAccess.AccessType.ASSIGN && it.index == 0L }
        s0.fieldAccesses[1].with{ it.accessType == IFieldAccess.AccessType.READ && it.index == 1L }
        s0.fieldAccesses[2].with{ it.accessType == IFieldAccess.AccessType.WRITE && it.index == 1L }
        s0.fieldAccesses[3].with{ it.accessType == IFieldAccess.AccessType.READ && it.index == 2L }
        s0.fieldAccesses[4].with{ it.accessType == IFieldAccess.AccessType.WRITE && it.index == 2L }

        [ "t0", "t1", "s0" ].every { ch.returnFields.contains(it) }

    }

    @Unroll
    def "injected service"() {
        def description =
            """This is a very basic composition where a service is injected and two methods are called upon it.
              |The output of each instruction is used for the following instruction so no two instruction should be able to run in parallel.""".stripMargin()

        when:
        def rr = RRGen.fromClosure {
            composition = """
            t0 = s0::m__T0();
            t1 = s0::mT0__T1({t0});
            """
            resolvePolicy = RRGen.defaultResolvePolicy()
            if(addContext) {
                initialServices["s0"] = new ServiceInstanceHandle("executor1", "c0.S0", "serviceS0Id")
                initialContext.add FieldType.builder().tap {
                    fieldname "s0"
                    type ServiceInstanceType.builder().tap {
                        typeQualifier( "c0.S0")
                    }.build()
                }.build()
            }
            null
        }

        def testRunner = new ResolutionTestBaseRunner("BasicResolutions", "02_SimpleInjectedService")
        testRunner.setClientExecutor("client")

        testRunner.testPlainText = description
        if(executorRegistered) {
            testRunner.addExecutor("executor1", "c0.S0")
        }
        testRunner.addExecutor("executor2", "c0.S1")
        def ccErrorMatcher = null
        if(!addContext) {
            ccErrorMatcher = { it instanceof TypeCheckException &&
                it.getCause().getMessage() == "Field is not defined: s0"}
        }
        def simErrorMatcher = null
        if(addContext && !executorRegistered) {
            simErrorMatcher = { it instanceof ChoreographyException &&
                it.getMessage() == "Field 's0' is an initial service with id 'serviceS0Id' of type 'c0.S0' " +
                "but its host 'executor1' is no registered to the executor."}
        }

        def successRun = addContext && executorRegistered

        then:
        testRunner.start(rr)
        testRunner.assertStaticAnalysisExceptionMatches(ccErrorMatcher)
        testRunner.assertSimulationExceptionMatches(simErrorMatcher)
        if(successRun)
            testRunner.writeOutputs()

        def cc = testRunner.getCc()
        def ch = testRunner.getChoreography()

        assert addContext == (cc != null)
        assert successRun == (ch != null)
        if(!successRun) {
            return
        }
        def s0 = cc.fields.find { it.fieldname == 's0' }
        assert s0.injected
        assert s0.initialType.typeQualifier == "c0.S0"
        assert s0.fieldAccesses.size() == 4
        assert s0.fieldAccesses[0].with { it.accessType == IFieldAccess.AccessType.READ && it.index == 0L }
        assert s0.fieldAccesses[1].with { it.accessType == IFieldAccess.AccessType.WRITE && it.index == 0L }
        assert s0.fieldAccesses[2].with { it.accessType == IFieldAccess.AccessType.READ && it.index == 1L }
        assert s0.fieldAccesses[3].with { it.accessType == IFieldAccess.AccessType.WRITE && it.index == 1L }

        [ "t0", "t1", "s0" ].every { ch.returnFields.contains(it) }

        assertServiceLoadStore(ch.compositionGraph.find { !it.client }, "s0")

        where:
        addContext | executorRegistered
        true  | true
        true  | false
        false | true
        false | false
    }

    void assertServiceLoadStore(ICompositionGraph graph, String serviceFieldName) {
        def acceptNode = graph.nodes.find {
            it instanceof IAcceptDataNode &&
                it.fieldName == serviceFieldName
        }
        def loadNode = graph.nodes.find {
            it instanceof IServiceInstanceStorageNode &&
                it.serviceInstanceFieldName == serviceFieldName &&
                it.loadInstruction
        }
        def serviceInstructions = graph.nodes.find{
            it instanceof IInstructionNode &&
                it.contextIsFieldFlag &&
                it.fieldName == serviceFieldName
        }
        def storeNode = graph.nodes.find {
            it instanceof IServiceInstanceStorageNode &&
                it.serviceInstanceFieldName == serviceFieldName &&
                !it.loadInstruction
        }
        assert acceptNode != null
        assert loadNode != null
        assert storeNode != null
        def exGraph = new ExecutionGraph(graph)
        assert GraphTraversal.isTherePathFromTo(exGraph, acceptNode, loadNode)
        assert serviceInstructions.every {
            GraphTraversal.isTherePathFromTo(exGraph, loadNode, it) &&
                GraphTraversal.isTherePathFromTo(exGraph, it, storeNode)
        }
        assert GraphTraversal.isTherePathFromTo(exGraph, loadNode, storeNode)
    }

    @Unroll
    def "unmatching param types"() {
        def description =
            """This test tries to invoke a method with a parameter that cannot be coerced.
            |t1=s1::mT0_T1__T1({t0,t1}): Cannot coerce type c0.T0 -> c0.T1""".stripMargin()

        when:
        def rr = RRGen.fromClosure {
            composition = """
            s0 = c0.S0::__construct();
            s1 = c0.S1::__construct();
            t0 = s0::mT0_Number__T0({t0, 12});
            t1 = s1::mT0_T1__T1({t0, t1});
            t0 = s0::mT1_T1__T0({t1, t1});
            s1 = c0.S1::constT0_T1({t0, t1});
            """
            resolvePolicy = RRGen.defaultResolvePolicy()
            initialContext.add FieldType.builder().tap {
                fieldname "t0"
                type DataValueType.builder().tap {
                    typeQualifier "c0.T0"
                }.build()
            }.build()
            if(addContext) {
                initialContext.add FieldType.builder().tap {
                    fieldname "t1"
                    type DataValueType.builder().tap {
                        typeQualifier "c0.T0"
                    }.build()
                }.build()
            }
        }

        def testRunner = new ResolutionTestBaseRunner("BasicResolutions", "03_UncoercableTypes")
        testRunner.setClientExecutor("client")

        testRunner.testPlainText = description
        testRunner.addExecutor("executor1", "c0.S0")
        testRunner.addExecutor("executor2", "c0.S1")
        def ccErrorMatcher = null
        if(!addContext) {
            ccErrorMatcher = { it instanceof TypeCheckException &&
                it.getCause().getMessage() == "Field is not defined: t1"}
        } else {
            ccErrorMatcher = {
                it instanceof TypeCheckException &&
                    it.getCause().getMessage() == "Cannot coerce type c0.T0 -> c0.T1 because their semantic types doesn't match: sem0 != sem1"
            }
        }


        then:
        testRunner.start(rr)
        testRunner.writeOutputs()
        testRunner.assertStaticAnalysisExceptionMatches(ccErrorMatcher)
        testRunner.assertSimulationExceptionMatches(null)

        def cc = testRunner.getCc()
        def ch = testRunner.getChoreography()

        where:
        addContext << [true, false]
    }

    @Unroll
    def "injected data"() {
        def description =
            """This composition depends on client data. t0, t1.
            |It also uses a method with the same field as two inputs.""".stripMargin()

        when:
        def rr = RRGen.fromClosure {
            composition = """
            s0 = c0.S0::__construct();
            s1 = c0.S1::__construct();
            t0 = s0::mT0_Number__T0({t0, 12});
            t1 = s1::mT0_T1__T1({t0, t1});
            t0 = s0::mT1_T1__T0({t1, t1});
            s1 = c0.S1::constT0_T1({t0, t1});
            """
            if(addContext) {
                resolvePolicy = RRGen.defaultResolvePolicy()
                initialContext.add FieldType.builder().tap {
                    fieldname "t0"
                    type DataValueType.builder().tap {
                        typeQualifier "c0.T0"
                    }.build()
                }.build()
                initialContext.add FieldType.builder().tap {
                    fieldname "t1"
                    type DataValueType.builder().tap {
                        typeQualifier "c0.T1"
                    }.build()
                }.build()
            }
        }

        def testRunner = new ResolutionTestBaseRunner("BasicResolutions", "04_ClientData")
        testRunner.setClientExecutor("client")

        testRunner.testPlainText = description
        testRunner.addExecutor("executor1", "c0.S0")
        testRunner.addExecutor("executor2", "c0.S1")
        def ccErrorMatcher = null
        if(!addContext) {
            ccErrorMatcher = { it instanceof TypeCheckException &&
                it.getCause().getMessage() == "Field is not defined: t0"}
        }


        then:
        testRunner.start(rr)
        testRunner.writeOutputs()
        testRunner.assertStaticAnalysisExceptionMatches(ccErrorMatcher)
        testRunner.assertSimulationExceptionMatches(null)
        if(!addContext) return
        def cc = testRunner.getCc()
        def ch = testRunner.getChoreography()
        def ex1Graph = ch.compositionGraph.find {it.executorHandle.qualifier == "executor1"}
        assertParseToNumber(ex1Graph, "12")
        assertAcceptDataBeforeUsage(ex1Graph, "t0")
        def ex2Graph = ch.compositionGraph.find {it.executorHandle.qualifier == "executor2"}
        assertAcceptDataBeforeUsage(ex2Graph, "t1")


        where:
        addContext << [true, false]
    }

    void assertParseToNumber(ICompositionGraph graph, String constant) {
        def parseNode = graph.nodes.find {
            it instanceof IParseConstantNode &&
                it.constantValue == constant
        } as IParseConstantNode
        assert parseNode != null
        assert parseNode.constantType == PrimitiveType.Number
    }

    void assertAcceptDataBeforeUsage(ICompositionGraph graph, String fieldname) {
        def exGraph = new ExecutionGraph(graph)
        def nodes = GraphTraversal.topologicalSort(exGraph)
        def accept = nodes.find {
            it instanceof IAcceptDataNode &&
                it.fieldName == fieldname
        } as IAcceptDataNode
        def instructions = graph.nodes.findAll {
            it instanceof IInstructionNode &&
                it.parameterFields.contains(fieldname)
        }
        assert accept != null
        assert instructions.every { inst ->
            GraphTraversal.isTherePathFromTo(exGraph, accept, inst)
        }
    }

}
