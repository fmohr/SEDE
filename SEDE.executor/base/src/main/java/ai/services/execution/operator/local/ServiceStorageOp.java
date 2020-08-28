package ai.services.execution.operator.local;

import ai.services.execution.Task;
import ai.services.execution.TaskTransition;
import ai.services.execution.operator.MainTaskOperator;
import de.upb.sede.composition.graphs.nodes.BaseNode;
import de.upb.sede.composition.graphs.nodes.IServiceInstanceStorageNode;
import de.upb.sede.webinterfaces.client.BasicClientRequest;
import de.upb.sede.webinterfaces.client.ReadFileRequest;
import de.upb.sede.webinterfaces.client.WriteFileRequest;

public class ServiceStorageOp extends MainTaskOperator {



    public ServiceStorageOp() {
        super(IServiceInstanceStorageNode.class);
    }

    @Override
    public TaskTransition runTask(Task t) throws Exception {
        return null;
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
        String path = pathFor(task.getServiceStoreLocation(), serviceClassPath, instanceId);
        return new WriteFileRequest(path, "");
    }

    private BasicClientRequest getLoadRequest(Task task, String instanceId, String serviceClassPath) {
        String path = pathFor(task.getExecution().getConfiguration().getServiceStoreLocation(), serviceClassPath, instanceId);
        return new ReadFileRequest(path);
    }
}
