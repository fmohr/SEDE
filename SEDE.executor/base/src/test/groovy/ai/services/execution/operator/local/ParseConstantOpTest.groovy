package ai.services.execution.operator.local

import ai.services.execution.Task
import ai.services.execution.local.LocalFieldContext
import de.upb.sede.composition.graphs.nodes.ParseConstantNode
import de.upb.sede.core.PrimitiveDataField
import de.upb.sede.core.PrimitiveType
import de.upb.sede.core.SEDEObject
import spock.lang.Specification

class ParseConstantOpTest extends Specification {


    def "RunTask"() {
        def fields = new LocalFieldContext("c")
        def task = new Task(fields, ParseConstantNode.builder().constantType(PrimitiveType.Number).constantValue("159.2").build())
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
        fields.getFieldValue("159.2").type == PrimitiveType.Number.name()
    }

    def "ParsePrimitive"() {
        when:
        def parsed = ParseConstantOp.parsePrimitive("\"Some Content\"", PrimitiveType.String)
        then:
        verifyAll {
            assertPrimitiveFlagsAreSet(parsed)
            parsed.type == PrimitiveType.String.name()
            parsed.dataField == "Some Content"
        }

        when:
        parsed = ParseConstantOp.parsePrimitive("1.0", PrimitiveType.Number)
        then:
        verifyAll {
            assertPrimitiveFlagsAreSet(parsed)
            parsed.type == PrimitiveType.Number.name()
            parsed.dataField == 1.0
        }
        when:
        parsed = ParseConstantOp.parsePrimitive("true", PrimitiveType.Bool)
        then:
        verifyAll {
            assertPrimitiveFlagsAreSet(parsed)
            parsed.type == PrimitiveType.Bool.name()
            parsed.dataField == true
        }
        when:
        parsed = ParseConstantOp.parsePrimitive("NULL", PrimitiveType.NULL)
        then:
        verifyAll {
            assertPrimitiveFlagsAreSet(parsed)
            parsed.type == PrimitiveType.NULL.name()
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
