package ai.services.execution.operator.local

import ai.services.channels.ChannelService
import ai.services.channels.DownloadLink
import ai.services.channels.ServiceStorageChannel
import ai.services.channels.UploadLink
import ai.services.execution.FieldContext
import ai.services.execution.Task
import ai.services.execution.local.LocalFieldContext
import com.fasterxml.jackson.databind.ObjectMapper
import ai.services.composition.graphs.nodes.ServiceInstanceStorageNode
import ai.services.core.ServiceInstance
import ai.services.core.ServiceInstanceField
import ai.services.core.ServiceInstanceHandle
import demo.math.Gerade
import spock.lang.Specification

class ServiceStorageOpTest extends Specification {

    String executor_id = "Executor_id";
    FieldContext fields = new LocalFieldContext(executor_id);


    def "test storage"() {
        def fieldname = "geradeService"
        def serviceId= "GeradeId"
        def serialisation = new ByteArrayOutputStream()
        def mockChannel = Mock(ChannelService) {
            0 * interExecutorCommChannel(_)
            1 * serviceStorageChannel(_) >> Mock (ServiceStorageChannel) {
                1 * storeService(_) >> Mock(UploadLink) {
                    (0..1) * getPayloadStream() >> serialisation
                    (0..1) * setPayload(_) >> {serialisation.write(args[0])}
                    1 * close()
                }
            }
        }
        def operator = new ServiceStorageOp(mockChannel)
        Task task = new Task(fields, ServiceInstanceStorageNode.builder()
            .tap {
                isLoadInstruction(false)
                fieldName(fieldname)
                serviceClasspath(Gerade.name)
            }.build())
        task.set(Task.State.RUNNING)
        fields.setFieldValue(fieldname, new ServiceInstanceField(new ServiceInstance(executor_id, Gerade.name, serviceId, new Gerade(0, 1))))

        when:
        def transition = operator.runTask(task)
        transition.performTransition(task)

        then:
        task.isMainTaskPerformed()
        task.getCurrentState() == Task.State.RUNNING

        def serviceHandle = new ObjectMapper().readValue(serialisation.toByteArray(), Map)
        serviceHandle != null
        serviceHandle["classpath"] == Gerade.name
        serviceHandle["executorId"] == executor_id
        serviceHandle["id"] == serviceId
        serviceHandle["instance"] instanceof String
        (serviceHandle["instance"]as String).size() > 0
    }

    def "test load"(){
        def fieldname = "geradeService"
        def serviceId = "GeradeId"
        def serviceHandle = new ServiceInstanceHandle(executor_id, Gerade.name, serviceId)
        Gerade g = new Gerade(0, 1)
        def serialisationBytes = ServiceInstanceHandleSerialisation.serialiseServiceInstance(new ServiceInstance(serviceHandle, g))
        def serialisationIn = new ByteArrayInputStream(serialisationBytes)
        def mockChannel = Mock(ChannelService) {
            0 * interExecutorCommChannel(_)
            1 * serviceStorageChannel(_) >> Mock (ServiceStorageChannel) {
                1 * loadService(_) >> Mock(DownloadLink) {
                    (0..1) * getStream() >> serialisationIn
                    (0..1) * getBytes() >> serialisationBytes
                    1 * close()
                }
            }
        }

        def operator = new ServiceStorageOp(mockChannel)
        Task task = new Task(fields, ServiceInstanceStorageNode.builder()
            .tap {
                isLoadInstruction(true)
                fieldName(fieldname)
                serviceClasspath(Gerade.name)
            }.build())
        task.set(Task.State.RUNNING)
        fields.setFieldValue(fieldname, new ServiceInstanceField(new ServiceInstanceHandle(executor_id, Gerade.name, serviceId)))

        when:
        def transition = operator.runTask(task)
        transition.performTransition(task)

        then:
        task.isMainTaskPerformed()
        task.getCurrentState() == Task.State.RUNNING

        def loadedField = fields.getFieldValue(fieldname) as ServiceInstanceField
        def loadedService = loadedField.getDataField()
        loadedService.classpath == Gerade.name
        loadedService.id == serviceId
        loadedService.serviceInstance.isPresent()
        loadedService.executorId == executor_id
        def loadedGerade = loadedService.getServiceInstance().get() as Gerade
        loadedGerade == g

    }


}
