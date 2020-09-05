package ai.services.execution.operator.local

import ai.services.channels.ChannelService
import ai.services.channels.ServiceStorageChannel
import ai.services.channels.UploadLink
import ai.services.execution.GraphTaskExecution
import ai.services.execution.Task
import com.fasterxml.jackson.databind.ObjectMapper
import de.upb.sede.composition.graphs.nodes.ServiceInstanceStorageNode
import de.upb.sede.core.ServiceInstance
import de.upb.sede.core.ServiceInstanceField
import demo.math.Gerade
import spock.lang.Specification

class ServiceStorageOpTest extends Specification {

    String executor_id = "Executor_id";
    GraphTaskExecution execution = new GraphTaskExecution(executor_id);


    def "test storage"() {
        def fieldname = "geradeService"
        def serviceId= "GeradeId"
        def serialisation = new ByteArrayOutputStream()
        def mockChannel = Mock(ChannelService) {
            0 * interExecutorCommChannel(_)
            1 * serviceStorageChannel(_) >> Mock (ServiceStorageChannel) {
                1 * storeService(_) >> Mock(UploadLink) {
                    getPayloadStream() >> serialisation
                    setPayload(_) >> {serialisation.write(args[0])}
                }
            }
        }
        def operator = new ServiceStorageOp(mockChannel)
        Task task = new Task(execution, ServiceInstanceStorageNode.builder()
            .tap {
                isLoadInstruction(false)
                fieldName(fieldname)
                serviceClasspath(Gerade.name)
            }.build())
        task.set(Task.State.RUNNING)
        execution.setFieldValue(fieldname, new ServiceInstanceField(new ServiceInstance(executor_id, Gerade.name, serviceId, new Gerade(0, 1))))

        when:
        def transition = operator.runTask(task)
        transition.performTransition(task)

        then:
        task.isMainTaskPerformed()
        task.getCurrentState() == Task.State.RUNNING

        when:
        def serviceHandle = new ObjectMapper().readValue(serialisation.toByteArray(), Map)

        then:
        serviceHandle != null
        serviceHandle["classpath"] == Gerade.name
        serviceHandle["executorId"] == executor_id
        serviceHandle["id"] == serviceId
        serviceHandle["instance"] instanceof String
        (serviceHandle["instance"]as String).size() > 0
    }


}
