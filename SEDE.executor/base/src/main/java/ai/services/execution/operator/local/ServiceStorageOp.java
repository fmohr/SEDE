package ai.services.execution.operator.local;

import ai.services.execution.Task;
import ai.services.execution.TaskTransition;
import ai.services.execution.operator.MainTaskOperator;
import com.fasterxml.jackson.core.*;
import de.upb.sede.composition.graphs.nodes.IServiceInstanceStorageNode;
import de.upb.sede.core.SEDEObject;
import de.upb.sede.core.ServiceInstance;
import de.upb.sede.core.ServiceInstanceField;
import de.upb.sede.core.ServiceInstanceHandle;
import de.upb.sede.util.ExtendedByteArrayOutputStream;
import de.upb.sede.webinterfaces.client.BasicClientRequest;
import de.upb.sede.webinterfaces.client.ReadFileRequest;
import de.upb.sede.webinterfaces.client.WriteFileRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.Objects;

import static ai.services.execution.operator.local.ServiceInstanceHandleSerialisation.readServiceInstance;
import static ai.services.execution.operator.local.ServiceInstanceHandleSerialisation.writeServiceInstance;

public class ServiceStorageOp extends MainTaskOperator {

    private static final Logger logger = LoggerFactory.getLogger(ServiceStorageOp.class);

    private final String serviceStoreLocation;

    public ServiceStorageOp(String serviceStoreLocation) {
        super(IServiceInstanceStorageNode.class);
        this.serviceStoreLocation = Objects.requireNonNull(serviceStoreLocation);
    }

    @Override
    public TaskTransition runTask(Task t) throws Exception {
        IServiceInstanceStorageNode storageNode = (IServiceInstanceStorageNode) t.getNode();
        TaskTransition taskTransition = handleStorage(t, storageNode);
        t.setMainTaskPerformed();
        return taskTransition;
    }

    private TaskTransition handleStorage(Task task, IServiceInstanceStorageNode node) throws IOException, ClassNotFoundException {
        boolean isLoadInstruction = node.isLoadInstruction();
        if (isLoadInstruction) {
            SEDEObject loadedSedeObject = load(node);
            return TaskTransition.fieldAssignment(node.getFieldName(), loadedSedeObject);
        } else {
            SEDEObject replacementObj = store(task, node);
            return TaskTransition.fieldAssignment(node.getFieldName(), replacementObj);
        }
    }

    private ServiceInstanceField store(Task task, IServiceInstanceStorageNode node) throws IOException {
        ServiceInstanceField serviceInstanceObj = (ServiceInstanceField) task.getFieldContext().getFieldValue(node.getFieldName());
        try(BasicClientRequest storeRequest = getStoreRequest(node.getInstanceIdentifier(), node.getServiceClasspath())) {
            writeServiceInstance(serviceInstanceObj.getServiceHandle(), storeRequest.send());
            return new ServiceInstanceField(new ServiceInstanceHandle(serviceInstanceObj.getServiceHandle()));
        }
    }

    private SEDEObject load(IServiceInstanceStorageNode node) throws IOException, ClassNotFoundException {
        try (BasicClientRequest loadRequest = getLoadRequest(node.getInstanceIdentifier(), node.getServiceClasspath())) {
            ServiceInstanceHandle serviceInstance = readServiceInstance(loadRequest.receive());
            return new ServiceInstanceField(serviceInstance);
        }
    }


    /**
     * Returns the path of storage for the requested instance.
     *
     * @param serviceClasspath
     *            class path of the service
     * @param instanceid
     *            id of the instance to get the path for.
     * @return Path of the requested instance.
     */
    public static String pathFor(String servicesPath, String serviceClasspath, String instanceid) {
        int max = 200;
        /*
         * maximum number of characters that is allowed service classpath to be. the
         * first few characters are cut in order to get under the max amount.
         */
        if (serviceClasspath.length() > max) {
            serviceClasspath = serviceClasspath.substring(serviceClasspath.length() - max, serviceClasspath.length());
        }
        return servicesPath + "/" + serviceClasspath + "/" + instanceid;
    }

    private BasicClientRequest getStoreRequest(String instanceId, String serviceClassPath) {
        String path = pathFor(serviceStoreLocation, serviceClassPath, instanceId);
        return new WriteFileRequest(path, "");
    }

    private BasicClientRequest getLoadRequest( String instanceId, String serviceClassPath) {
        String path = pathFor(serviceStoreLocation, serviceClassPath, instanceId);
        return new ReadFileRequest(path);
    }
}
