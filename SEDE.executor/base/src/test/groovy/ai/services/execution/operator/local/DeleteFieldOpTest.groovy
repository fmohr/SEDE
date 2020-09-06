package ai.services.execution.operator.local

import ai.services.execution.Task
import ai.services.execution.local.LocalFieldContext
import de.upb.sede.composition.graphs.nodes.DeleteFieldNode
import de.upb.sede.core.PrimitiveDataField
import spock.lang.Specification

class DeleteFieldOpTest extends Specification {

    def "test field deletion"() {
        def fields = new LocalFieldContext("c1")
        def task = new Task(fields, DeleteFieldNode.builder().tap{
            fieldName("f1")
        }.build())

        fields.setFieldValue("f1", new PrimitiveDataField(3.14))


        when:
        task.set(Task.State.RUNNING)
        def op = new DeleteFieldOp()
        def tr = op.runTask(task)
        tr.performTransition(task)

        then:
        task.is(Task.State.RUNNING)
        task.mainTaskPerformed

        !fields.hasField("f1")
    }

    def "test field deletion, not present"() {def fields = new LocalFieldContext("c1")
        def task = new Task(fields, DeleteFieldNode.builder().tap{
            fieldName("f1")
        }.build())

        when:
        task.set(Task.State.RUNNING)
        def op = new DeleteFieldOp()
        def tr = op.runTask(task)

        then:
        thrown(IllegalStateException)
    }

}
