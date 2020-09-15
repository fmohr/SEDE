package ai.services.execution.operator.local

import ai.services.channels.ChannelService
import ai.services.channels.ExecutorCommChannel
import ai.services.execution.Task
import ai.services.execution.local.LocalFieldContext
import ai.services.composition.graphs.nodes.INotification
import ai.services.composition.graphs.nodes.Notification
import ai.services.composition.graphs.nodes.NotifyNode
import ai.services.exec.ExecutorContactInfo
import ai.services.exec.IExecutorContactInfo
import spock.lang.Specification

class SendNtfOpTest extends Specification {

    def "RunTask"() {
        def fields = new LocalFieldContext("c")
        IExecutorContactInfo contactInfo = ExecutorContactInfo.builder()
            .qualifier("someOtherExecutor")
            .uRL("http://SomeoneElse.org")
            .build()
        INotification ntf = Notification.builder()
            .qualifier("n1")
            .description("Some notification")
            .build()
        def node = NotifyNode.builder()
            .notification(ntf)
            .contactInfo(contactInfo)
            .build()

        def channel = Mock(ChannelService) {
            interExecutorCommChannel(contactInfo) >> Mock(ExecutorCommChannel) {
                1 * pushNotification("c", ntf)
                0 * _
            }
        }
        def op = new SendNtfOp(channel)
        def task = new Task(fields, node)

        when:
        task.set(Task.State.RUNNING)
        def tr = op.runTask(task)
        tr.performTransition(task)

        then:
        task.mainTaskPerformed
        task.is(Task.State.RUNNING)
    }
}
