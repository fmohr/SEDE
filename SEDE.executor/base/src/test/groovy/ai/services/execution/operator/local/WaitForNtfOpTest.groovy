package ai.services.execution.operator.local

import ai.services.composition.NotifyRequest
import ai.services.execution.Task
import ai.services.execution.local.LocalFieldContext
import ai.services.composition.graphs.nodes.Notification
import ai.services.composition.graphs.nodes.WaitForNotificationNode
import spock.lang.Specification

class WaitForNtfOpTest extends Specification {

    def ntf =  Notification.builder()
        .qualifier("n1")
        .description("Some notification")
        .build()
    def node = WaitForNotificationNode.builder()
        .notification(ntf)
        .build()
    def op = new WaitForNtfOp()

    def "RunTask"() {
        def fields = new LocalFieldContext("c")
        def task = new Task(fields, node)
        task.set(Task.State.RUNNING)
        when:
        def tr = op.runTask(task)
        tr.performTransition(task)

        then:
        !task.mainTaskPerformed
        task.is(Task.State.WAITING)
        task.testWaitingCondition()

        when:
        task.set(Task.State.RUNNING)
        tr = op.runTask(task)
        tr.performTransition(task)

        then:
        !task.mainTaskPerformed
        task.is(Task.State.WAITING)
        task.testWaitingCondition()

        when:
        fields.pushNotification(ntf)

        then:
        !task.testWaitingCondition()

        when:
        task.set(Task.State.RUNNING)
        tr = op.runTask(task)
        tr.performTransition(task)

        then:
        task.mainTaskPerformed
        task.is(Task.State.RUNNING)
    }

    def "RunTask - Notification already present"() {
        def fields = new LocalFieldContext("c")
        def task = new Task(fields, node)
        task.set(Task.State.RUNNING)
        fields.pushNotification(ntf)
        when:
        def tr = op.runTask(task)
        tr.performTransition(task)

        then:
        task.mainTaskPerformed
        task.is(Task.State.RUNNING)
        !task.testWaitingCondition()
    }

    def "RunTask - Unsuccessful notification"() {
        def fields = new LocalFieldContext("c")
        def task = new Task(fields, node)
        def ntf = NotifyRequest.builder()
            .executionId("e")
            .from(ntf)
            .isSuccessfulNotification(false)
            .build();
        task.set(Task.State.RUNNING)
        when:
        def tr = op.runTask(task)
        tr.performTransition(task)

        then:
        !task.mainTaskPerformed
        task.is(Task.State.WAITING)
        task.testWaitingCondition()


        when:
        fields.pushNotification(ntf)

        then:
        !task.testWaitingCondition()

        when:
        task.set(Task.State.RUNNING)
        tr = op.runTask(task)
        tr.performTransition(task)

        then:
        !task.mainTaskPerformed
        task.is(Task.State.FAILURE)
    }

}
