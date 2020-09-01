package ai.services.execution.operator.local;

import ai.services.execution.Task;
import ai.services.execution.TaskTransition;
import ai.services.execution.operator.MainTaskOperator;
import de.upb.sede.composition.graphs.nodes.IServiceInstanceStorageNode;
import de.upb.sede.core.SEDEObject;
import de.upb.sede.core.ServiceInstanceField;
import de.upb.sede.core.ServiceInstanceHandle;
import de.upb.sede.util.Streams;
import de.upb.sede.webinterfaces.client.BasicClientRequest;
import de.upb.sede.webinterfaces.client.ReadFileRequest;
import de.upb.sede.webinterfaces.client.WriteFileRequest;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.UncheckedIOException;

public class ServiceStorageOp extends MainTaskOperator {

    private final String serviceStoreLocation;

    public ServiceStorageOp(String serviceStoreLocation) {
        super(IServiceInstanceStorageNode.class);
        this.serviceStoreLocation = serviceStoreLocation;
    }

    @Override
    public TaskTransition runTask(Task t) throws Exception {
        IServiceInstanceStorageNode storageNode = (IServiceInstanceStorageNode) t.getNode();
        handleStorage(t, storageNode);
        return TaskTransition.success();
    }

    private void handleStorage(Task task, IServiceInstanceStorageNode node) {
        /* gather information regarding load store operation */
        boolean isLoadInstruction = node.isLoadInstruction();
        String fieldname = node.getFieldName();
        String serviceClasspath = node.getServiceClasspath();

        String instanceId;

        if (isLoadInstruction) {
            /* load the service instance and put it into the execution environment */
            instanceId = node.getService
            BasicClientRequest loadRequest = getLoadRequest(task, instanceId, serviceClasspath);
            SEDEObject loadedSedeObject;
            try (ObjectInputStream objectIn = new ObjectInputStream(loadRequest.receive())) {
                Object instanceObject = objectIn.readObject();
                ServiceInstance serviceInstance = new ServiceInstance(
                    task.getExecution().getConfiguration().getExecutorId(),
                    serviceClasspath, instanceId, instanceObject);
                loadedSedeObject = new ServiceInstanceField(serviceInstance);
            } catch (IOException e) {
                throw new UncheckedIOException(e);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
            task.getExecution().performLater( () -> {
                task.getExecution().getEnvironment().put(fieldname, loadedSedeObject);
                task.setSucceeded();
            });
        } else {
            /* store the service instance which the fieldname is pointing to */
            ServiceInstanceHandle instanceHandle = task.getExecution().getEnvironment().get(fieldname)
                .getServiceHandle();
            instanceId = instanceHandle.getId();
            BasicClientRequest storeRequest = getStoreRequest(task, instanceId, serviceClasspath);

            try (ObjectOutputStream objectOut = new ObjectOutputStream(storeRequest.send())) {
                objectOut.writeObject(instanceHandle.getServiceInstance().get());
            } catch (IOException e) {
                throw new UncheckedIOException(e);
            }
            String answer = Streams.InReadString(storeRequest.receive());
            if (!answer.isEmpty()) {
                throw new RuntimeException(
                    "Error during service storage: " + instanceHandle.toString() + "\nmessage: " + answer);
            }
            task.setSucceeded();
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

    private BasicClientRequest getStoreRequest(Task task, String instanceId, String serviceClassPath) {
        String path = pathFor(serviceStoreLocation, serviceClassPath, instanceId);
        return new WriteFileRequest(path, "");
    }

    private BasicClientRequest getLoadRequest(Task task, String instanceId, String serviceClassPath) {
        String path = pathFor(serviceStoreLocation, serviceClassPath, instanceId);
        return new ReadFileRequest(path);
    }
}
