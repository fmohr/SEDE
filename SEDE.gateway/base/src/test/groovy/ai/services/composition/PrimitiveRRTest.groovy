package ai.services.composition


import ai.services.composition.choerography.emulation.executors.ExecutionGraph
import ai.services.composition.choerography.emulation.executors.GraphTraversal
import ai.services.composition.graphs.nodes.IParseConstantNode
import ai.services.composition.types.PrimitiveValueType
import ai.services.core.PrimitiveType
import spock.lang.Specification

class PrimitiveRRTest extends Specification {

    def "constant parameters"() {
        def description =
            """Constants are used as parameters.
            |Make sure the constant are parsed before they are used.""".stripMargin()

        when:
        def rr = RRGen.fromClosure {
            composition = """
            s0 = p.PrimService0::constNumber_Number({5, 9.3});
            s1 = p.PrimService0::constStr_Str({"abc", "def"});
            s2 = p.PrimService0::constBool_Bool({true, false});
            s0::mNumber_Str_Bool({5,"abc", true});
            """
            resolvePolicy = RRGen.defaultResolvePolicy()
        }

        def testRunner = new ResolutionTestBaseRunner("Primitives", "01_constantParams")
        testRunner.setClientExecutor("client")

        testRunner.testPlainText = description
        testRunner.addExecutor("executor1", "p.PrimService0")

        then:
        testRunner.start(rr)
        testRunner.assertStaticAnalysisExceptionMatches(null)
        testRunner.assertSimulationExceptionMatches(null)
        testRunner.writeOutputs()

        def cc = testRunner.getCc()
        def ch = testRunner.getChoreography()

        def execGraph = testRunner.getExecGraph("executor1")

        def parse5 = assertParseConstantExists(execGraph, "5", PrimitiveType.Number)
        def parse9p3 = assertParseConstantExists(execGraph, "9.3", PrimitiveType.Number)
        RRTestHelpers.assertExecutedBefore(execGraph, parse5, 0L)
        RRTestHelpers.assertExecutedBefore(execGraph, parse9p3, 0L)

        def parseabc = assertParseConstantExists(execGraph, "\"abc\"", PrimitiveType.String)
        def parsedef = assertParseConstantExists(execGraph, "\"def\"", PrimitiveType.String)
        RRTestHelpers.assertExecutedBefore(execGraph, parseabc, 1L)
        RRTestHelpers.assertExecutedBefore(execGraph, parsedef, 1L)

        def parsetrue = assertParseConstantExists(execGraph, "true", PrimitiveType.Bool)
        def parsefalse = assertParseConstantExists(execGraph, "false", PrimitiveType.Bool)
        RRTestHelpers.assertExecutedBefore(execGraph, parsetrue, 2L)
        RRTestHelpers.assertExecutedBefore(execGraph, parsefalse, 2L)

        RRTestHelpers.assertExecutedBefore(execGraph, parse5, 3L)
        RRTestHelpers.assertExecutedBefore(execGraph, parseabc, 3L)
        RRTestHelpers.assertExecutedBefore(execGraph, parsetrue, 3L)
    }

    IParseConstantNode assertParseConstantExists(ExecutionGraph graph, String constant, PrimitiveType expectedType) {
        def parseNode = GraphTraversal.topologicalSort(graph).find {
            it instanceof  IParseConstantNode &&
                it.constantValue == constant
        } as IParseConstantNode

        assert parseNode != null
        assert parseNode.constantType == expectedType
        return parseNode
    }

    def "parse same constant on two executors"() {
        def description =
            """Then same constant is used on two services that are each executed on a distinct executor.
            |Make sure each executor has a parse.""".stripMargin()

        when:
        def rr = RRGen.fromClosure {
            composition = """
            s0 = p.PrimService0::constNumber_Number({5, 6});
            s1 = p.PrimService1::constNumber_Number({5, 7});
            """
            resolvePolicy = RRGen.defaultResolvePolicy()
        }

        def testRunner = new ResolutionTestBaseRunner("Primitives", "02_parseOnTwoExecutors")
        testRunner.setClientExecutor("client")

        testRunner.testPlainText = description
        testRunner.addExecutor("executor1", "p.PrimService0")
        testRunner.addExecutor("executor2", "p.PrimService1")

        then:
        testRunner.start(rr)
        testRunner.assertStaticAnalysisExceptionMatches(null)
        testRunner.assertSimulationExceptionMatches(null)
        testRunner.writeOutputs()

        def cc = testRunner.getCc()
        def ch = testRunner.getChoreography()

        def execGraph1 = testRunner.getExecGraph("executor1")
        def execGraph2 = testRunner.getExecGraph("executor2")

        assertParseConstantExists(execGraph1, "5", PrimitiveType.Number)
        assertParseConstantExists(execGraph1, "6", PrimitiveType.Number)

        assertParseConstantExists(execGraph2, "5", PrimitiveType.Number)
        assertParseConstantExists(execGraph2, "7", PrimitiveType.Number)
    }



    def "null plug"() {
        def description =
            """Null is used to plug as parameter""".stripMargin()

        when:
        def rr = RRGen.fromClosure {
            composition = """
            s0 = p.PrimService0::constNumber_Number({56, null});
            s1 = p.PrimService1::constStr_Str({null, "abc"});
            s2 = p.PrimService0::constBool_Bool({null, true});
            s0::mNumber_Str_Bool({null, null, null});
            s2::mT0_Str({null, "def"});
            """
            resolvePolicy = RRGen.defaultResolvePolicy()
        }

        def testRunner = new ResolutionTestBaseRunner("Primitives", "03_nullPlug")
        testRunner.setClientExecutor("client")

        testRunner.testPlainText = description
        testRunner.addExecutor("executor1", "p.PrimService0")
        testRunner.addExecutor("executor2", "p.PrimService1")

        then:
        testRunner.start(rr)
        testRunner.assertStaticAnalysisExceptionMatches(null)
        testRunner.assertSimulationExceptionMatches(null)
        testRunner.writeOutputs()

        def cc = testRunner.getCc()
        def ch = testRunner.getChoreography()

        def execGraph1 = testRunner.getExecGraph("executor1")
        def execGraph2 = testRunner.getExecGraph("executor2")

    }


    def "primitive fields"() {
        def description =
            """Primitive fields (instead of consts) are used to plug into methods""".stripMargin()

        when:
        def rr = RRGen.fromClosure {
            composition = """
            s0 = p.PrimService0::constNumber_Number({nrField1, nrField1});
            s0::mNumber_Str_Bool({nrField2, stringField1, boolField1});
            nrField3 = p.PrimService1::produce_Number();
            stringField2 = p.PrimService1::produce_Str();
            boolField2 = p.PrimService1::produce_Bool();
            s0::mNumber_Str_Bool({nrField3, stringField2, boolField2});
            """
            resolvePolicy = RRGen.defaultResolvePolicy()
            initialContext.add(FieldType.builder()
                .fieldname("nrField1")
                .type(PrimitiveValueType.builder()
                    .primitiveType(PrimitiveType.Number)
                    .build())
                .build())
            initialContext.add(FieldType.builder()
                .fieldname("nrField2")
                .type(PrimitiveValueType.builder()
                    .primitiveType(PrimitiveType.Number)
                    .build())
                .build())
            initialContext.add(FieldType.builder()
                .fieldname("boolField1")
                .type(PrimitiveValueType.builder()
                    .primitiveType(PrimitiveType.String)
                    .build())
                .build())
            initialContext.add(FieldType.builder()
                .fieldname("stringField1")
                .type(PrimitiveValueType.builder()
                    .primitiveType(PrimitiveType.Bool)
                    .build())
                .build())
        }

        def testRunner = new ResolutionTestBaseRunner("Primitives", "04_primFields")
        testRunner.setClientExecutor("client")

        testRunner.testPlainText = description
        testRunner.addExecutor("executor1", "p.PrimService0")
        testRunner.addExecutor("executor2", "p.PrimService1")

        then:
        testRunner.start(rr)
        testRunner.assertStaticAnalysisExceptionMatches(null)
        testRunner.assertSimulationExceptionMatches(null)
        testRunner.writeOutputs()
    }



}
