package ai.services.execution.operator.local


import ai.services.execution.Task
import ai.services.execution.local.LocalFieldContext
import ai.services.composition.graphs.nodes.ParseConstantNode
import ai.services.core.Primitives
import ai.services.core.SEDEObject
import spock.lang.Specification

class ParseConstantOpTest extends Specification {


    def "RunTask"() {
        def fields = new LocalFieldContext("c")
        def task = new Task(fields, ParseConstantNode.builder().constantType(Primitives.Number).constantValue("159.2").build())
        task.set(Task.State.RUNNING)
        def op = new ParseConstantOp()

        when:
        def tr = op.runTask(task)
        tr.performTransition(task)

        then:
        task.mainTaskPerformed
        task.is(Task.State.RUNNING)
        fields.hasField("159.2")
        fields.getFieldValue("159.2").dataField == 159.2
        fields.getFieldValue("159.2").type == Primitives.Number.name()
    }

    def "ParsePrimitive"() {
        when:
        def parsed = ParseConstantOp.parsePrimitive("\"Some Content\"", Primitives.String)
        then:
        verifyAll {
            assertPrimitiveFlagsAreSet(parsed)
            parsed.type == Primitives.String.name()
            parsed.dataField == "Some Content"
        }

        when:
        parsed = ParseConstantOp.parsePrimitive("1.0", Primitives.Number)
        then:
        verifyAll {
            assertPrimitiveFlagsAreSet(parsed)
            parsed.type == Primitives.Number.name()
            parsed.dataField == 1.0
        }
        when:
        parsed = ParseConstantOp.parsePrimitive("true", Primitives.Bool)
        then:
        verifyAll {
            assertPrimitiveFlagsAreSet(parsed)
            parsed.type == Primitives.Bool.name()
            parsed.dataField == true
        }
        when:
        parsed = ParseConstantOp.parsePrimitive("NULL", Primitives.NULL)
        then:
        verifyAll {
            assertPrimitiveFlagsAreSet(parsed)
            parsed.type == Primitives.NULL.name()
            parsed.dataField == null
        }

    }

    static def assertPrimitiveFlagsAreSet(SEDEObject obj) {
        assert obj.primitive
        assert !obj.isServiceInstance()
        assert !obj.real
        assert !obj.semantic
        return true;
    }
}
