package ai.services.execution.operator.local

import ai.services.channels.ChannelService
import ai.services.channels.DataChannel
import ai.services.channels.ExecutorCommChannel
import ai.services.channels.UploadLink
import ai.services.execution.Task
import ai.services.execution.local.LocalFieldContext
import ai.services.composition.graphs.nodes.ITransmitDataNode
import ai.services.core.SemanticDataField
import ai.services.exec.ExecutorContactInfo
import spock.lang.Specification

class TransmitDataOpTest extends Specification {

    def "RunTask"(){
        def exContactInfo = ExecutorContactInfo.builder()
            .qualifier("otherExecutor")
            .uRL("http://otherexecutor.org")
            .build()
        def fields = new LocalFieldContext("c")
        byte[] data = new byte[] {1,2,3,4,5};
        def f1 = new SemanticDataField("sem1", data)
        fields.setFieldValue("f1", f1)
        def task = new Task(fields, Mock(ITransmitDataNode) {
            getFieldName() >> "f1"
            0 * getMarshalling()
            getContactInfo() >> exContactInfo
        })

        ByteArrayOutputStream mockCache = new ByteArrayOutputStream()
        def op = new TransmitDataOp(Mock(ChannelService) {
            interExecutorCommChannel(exContactInfo) >> Mock(ExecutorCommChannel) {
                dataChannel("c") >> Mock(DataChannel) {
                    1 * getUploadLink("f1", "sem1") >> Mock(UploadLink.class) {
                        (0..1) * getPayloadStream() >> mockCache
                        (0..1) * setPayload(_) >> {mockCache.write(it[0])}
                        1 * close() >> {mockCache.close()}
                    }
                }
            }
        })

        when:
        task.set(Task.State.RUNNING)
        def tr = op.runTask(task)
        tr.performTransition(task)

        then:
        task.mainTaskPerformed
        task.is(Task.State.RUNNING)
        Arrays.equals(mockCache.toByteArray(), data)
    }

}
