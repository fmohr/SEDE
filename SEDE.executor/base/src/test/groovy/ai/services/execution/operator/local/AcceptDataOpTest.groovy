package ai.services.execution.operator.local

import ai.services.execution.Task
import ai.services.execution.local.LocalFieldContext
import ai.services.composition.graphs.nodes.IAcceptDataNode
import ai.services.core.PrimitiveDataField
import spock.lang.Specification

class AcceptDataOpTest extends Specification {


    String fieldname = "field"
    def fieldVal = new PrimitiveDataField("Important Data")

    def "RunTask"() {
        LocalFieldContext fieldContext = new LocalFieldContext("context1")
        when:
        Task t = new Task(fieldContext, Mock(IAcceptDataNode) {
            getFieldName() >> fieldname
        })
        def op = new AcceptDataOp()

        then:
        op.test(t)
        !t.testWaitingCondition()

        when:
        def transition = op.runTask(t)
        transition.performTransition(t)

        then:
        t.is Task.State.WAITING
        t.testWaitingCondition()
        !t.mainTaskPerformed

        when:
        transition = op.runTask(t)
        transition.performTransition(t)

        then:
        t.is Task.State.WAITING
        t.testWaitingCondition()
        !t.mainTaskPerformed

        when:
        fieldContext.setFieldValue(fieldname, fieldVal)

        then:
        !t.testWaitingCondition()

        when:
        t.set(Task.State.RUNNING)
        transition = op.runTask(t)
        transition.performTransition(t)

        then:
        t.is Task.State.RUNNING
        !t.testWaitingCondition()
        t.mainTaskPerformed
    }

    def "RunTask - Field present before first iteration"() {
        def fieldContext = new LocalFieldContext("c")
        fieldContext.setFieldValue(fieldname, fieldVal)

        when:
        Task t = new Task(fieldContext, Mock(IAcceptDataNode) {
            getFieldName() >> fieldname
        })
        def op = new AcceptDataOp()

        then:
        op.test(t)
        !t.testWaitingCondition()

        when:
        t.set(Task.State.RUNNING)
        def transition = op.runTask(t)
        transition.performTransition(t)

        then:
        t.is Task.State.RUNNING
        !t.testWaitingCondition()
        t.mainTaskPerformed
    }

}
