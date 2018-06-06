package de.upb.sede.procedure;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.UncheckedIOException;

import de.upb.sede.core.SEDEObject;
import de.upb.sede.core.ServiceInstanceHandle;
import de.upb.sede.exec.ServiceInstance;
import de.upb.sede.exec.Task;
import de.upb.sede.util.Streams;
import de.upb.sede.webinterfaces.client.BasicClientRequest;
import de.upb.sede.webinterfaces.client.ReadFileRequest;
import de.upb.sede.webinterfaces.client.WriteFileRequest;

/**
 * Procedure to load or store an instance of a service with its current state,
 */
public class ServiceInstanceStorageProcedure implements Procedure {

	@Override
	public void process(Task task) {
		/* gather information regarding load store operation */
		boolean isLoadInstruction = (boolean) task.getAttributes().get("is-load-instruction");
		String fieldname = (String) task.getAttributes().get("serviceinstance-fieldname");
		String serviceClasspath = (String) task.getAttributes().get("service-classpath");
		String instanceId;

		if (isLoadInstruction) {
			/* load the service instance and put it into the execution environment */
			instanceId = (String) task.getAttributes().get("instance-id");
			BasicClientRequest loadRequest = getLoadRequest(task, instanceId, serviceClasspath);
			SEDEObject loadedSedeObject;
			try (ObjectInputStream objectIn = new ObjectInputStream(loadRequest.receive())) {
				Object instanceObject = objectIn.readObject();
				ServiceInstance serviceInstance = new ServiceInstance(task.getExecution().getExecutionId(),
						serviceClasspath, instanceId, instanceObject);
				loadedSedeObject = new SEDEObject(serviceInstance);
			} catch (IOException e) {
				throw new UncheckedIOException(e);
			} catch (ClassNotFoundException e) {
				throw new RuntimeException(e);
			}
			task.getExecution().getEnvironment().put(fieldname, loadedSedeObject);
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
		}
		task.setSucceeded();
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
		String path = pathFor(task.getExecution().getConfiguration().getServiceStoreLocation(), serviceClassPath, instanceId);
		return new WriteFileRequest(path, "");
	}

	private BasicClientRequest getLoadRequest(Task task, String instanceId, String serviceClassPath) {
		String path = pathFor(task.getExecution().getConfiguration().getServiceStoreLocation(), serviceClassPath, instanceId);
		return new ReadFileRequest(path);
	}
}
