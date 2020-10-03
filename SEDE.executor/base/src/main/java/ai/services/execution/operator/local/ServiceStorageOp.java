package ai.services.execution.operator.local;

import ai.services.channels.ChannelService;
import ai.services.channels.DownloadLink;
import ai.services.channels.ServiceStorageChannel;
import ai.services.channels.UploadLink;
import ai.services.execution.Task;
import ai.services.execution.TaskTransition;
import ai.services.execution.operator.MainTaskOperator;
import ai.services.composition.graphs.nodes.IServiceInstanceStorageNode;
import ai.services.core.SEDEObject;
import ai.services.core.ServiceInstanceField;
import ai.services.core.ServiceInstanceHandle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;

import static ai.services.execution.operator.local.ServiceInstanceHandleSerialisation.readServiceInstance;
import static ai.services.execution.operator.local.ServiceInstanceHandleSerialisation.writeServiceInstance;

public class ServiceStorageOp extends MainTaskOperator {

    private static final Logger logger = LoggerFactory.getLogger(ServiceStorageOp.class);

    private final ChannelService channelService;

    public ServiceStorageOp(ChannelService channelService) {
        super(IServiceInstanceStorageNode.class);
        this.channelService = channelService;
    }

    @Override
    public TaskTransition runTask(Task t) throws Exception {
        IServiceInstanceStorageNode storageNode = (IServiceInstanceStorageNode) t.getNode();
        TaskTransition taskTransition = handleStorage(t, storageNode);
        t.setMainTaskPerformed();
        return taskTransition;
    }

    private TaskTransition handleStorage(Task task, IServiceInstanceStorageNode node) throws Exception {
        boolean isLoadInstruction = node.isLoadInstruction();
        if (isLoadInstruction) {
            SEDEObject loadedSedeObject = load(task, node);
            return TaskTransition.fieldAssignment(node.getFieldName(), loadedSedeObject);
        } else {
            SEDEObject replacementObj = store(task, node);
            return TaskTransition.fieldAssignment(node.getFieldName(), replacementObj);
        }
    }

    private ServiceInstanceField store(Task task, IServiceInstanceStorageNode node) throws Exception {
        ServiceInstanceField serviceInstanceObj = (ServiceInstanceField) task.getFieldContext().getFieldValue(node.getFieldName());
        ServiceStorageChannel serviceStorageChannel = channelService.serviceStorageChannel(node.getServiceClasspath());
        try(UploadLink uploadLink = serviceStorageChannel.storeService(serviceInstanceObj.getServiceHandle())) {
            OutputStream uploadStream = uploadLink.getPayloadStream();
            writeServiceInstance(serviceInstanceObj.getServiceHandle(), uploadStream);
            return new ServiceInstanceField(new ServiceInstanceHandle(serviceInstanceObj.getServiceHandle()));
        }
    }

    private SEDEObject load(Task task,IServiceInstanceStorageNode node) throws Exception {
        ServiceInstanceField serviceInstanceObj = (ServiceInstanceField) task.getFieldContext().getFieldValue(node.getFieldName());
        ServiceStorageChannel serviceStorageChannel = channelService.serviceStorageChannel(node.getServiceClasspath());
        try(DownloadLink downloadLink = serviceStorageChannel.loadService(serviceInstanceObj.getServiceHandle())) {
            InputStream downloadStream = downloadLink.getStream();
            ServiceInstanceHandle serviceInstance = readServiceInstance(downloadStream);
            return new ServiceInstanceField(serviceInstance);
        }
    }

}
