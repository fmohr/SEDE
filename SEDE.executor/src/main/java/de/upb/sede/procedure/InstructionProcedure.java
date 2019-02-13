package de.upb.sede.procedure;

import java.lang.reflect.Executable;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

import org.apache.commons.lang3.ClassUtils;
import org.apache.commons.lang3.reflect.ConstructorUtils;
import org.apache.commons.lang3.reflect.MethodUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.upb.sede.core.ObjectDataField;
import de.upb.sede.core.PrimitiveDataField;
import de.upb.sede.core.SEDEObject;
import de.upb.sede.core.ServiceInstanceField;
import de.upb.sede.core.ServiceInstanceHandle;
import de.upb.sede.exec.ExecutionEnvironment;
import de.upb.sede.exec.ServiceInstance;
import de.upb.sede.exec.Task;

/**
 * This procedure is called when an instruction task needs to be executed. This
 * procedure assumes that the necessary classes are already loaded into the
 * class loader. The main task of this class is to call the correct method/
 * constructor based on the signatures dictated by the given parameters.
 *
 */
public class InstructionProcedure implements Procedure {

	private final static Logger logger = LoggerFactory.getLogger(InstructionProcedure.class);

	/**
	 * If there is a fieldname on the leftside of the instruction signal the execution-environment that the fieldname wont be available.
	 * @param task
	 */
	public void processFail(Task task) {
		InstructionNodeAttributes nodeAttributes = new InstructionNodeAttributes(task);
		String fieldname = nodeAttributes.getLeftsidefieldname();
		if(fieldname != null) {
			logger.debug("Instruction {} failed. Marking field {} unavailable.", task.getDescription(), fieldname);
			task.getExecution().getEnvironment().remove(fieldname); // remove the field
			task.getExecution().getEnvironment().markUnavailable(fieldname); // mark the field unavailable
		}
		task.setFailed();
	}

	/**
	 * Processes the given task by carrying out the instruction using reflection.
	 * @param task
	 */
	@Override
	public void processTask(Task task) {
		ExecutionEnvironment environment = task.getExecution().getEnvironment();
		InstructionNodeAttributes attr = new InstructionNodeAttributes(task);
		/*
			Gather parameters:
		 */
		int paramSize = attr.getParameters().size();
		Object[] paramValues = new Object[paramSize];
		for(int paramIndex = 0; paramIndex < paramSize; paramIndex++){
			String fieldname = attr.getParameters().get(paramIndex);
			SEDEObject field = Objects.requireNonNull(environment.get(fieldname));
			Object fieldValue;
			if(field.isServiceInstance()) {
				fieldValue = field.getServiceInstance();
			} else {
				fieldValue = field.getDataField();
			}
			paramValues[paramIndex] = fieldValue;
		}
		/*
			Gather method name and context field:
		 */
		String methodname = attr.getMethod();
		Object contextInstance = null;
		Class<?> contextClass;
		if (attr.isContextAFieldname()) {
			/*
			 * The context is field name which points to a service instance. The context
			 * instance is the field itself.
			 */
			SEDEObject field = environment.get(attr.getContext());
			if (!field.isServiceInstance()) {
				throw new RuntimeException("BUG: trying to operate on a service instance from the environment," +
						" instead the fieldtype is only a handle: " + field.toString());
			}
			contextInstance = field.getServiceInstance();
			if(contextInstance==null) {
				throw new NullPointerException("Context field is null.");
			}
			contextClass = contextInstance.getClass();
		} else{
			// Get class to be called.
			try {
				contextClass = Class.forName(attr.getContext());
			} catch (ClassNotFoundException e) {
				throw new RuntimeException(e);
			}
		}
		/*
			Cast numbers:
		 */
		Executable executable = getExecutable(environment, attr, paramValues);
		castNumbers(executable.getParameterTypes(), paramValues);

		/*
			Do the invocation:
		 */
		Object outputValue;
		try {
			if(attr.isConstructor()) {
				/*
					call constructor and create the service:
				 */
				Class<?> contextCls = Class.forName(attr.getContext());
				outputValue = ConstructorUtils.invokeConstructor(contextCls, paramValues);

			} else if (attr.isContextAFieldname()) {
				/*
					call object method:
				 */
				outputValue = MethodUtils.invokeMethod(contextInstance, methodname, paramValues);
			} else {
				/*
					call a static method:
				 */
				outputValue = MethodUtils.invokeStaticMethod(contextClass, methodname, paramValues);
			}

		} catch (Exception e) {
			throw new RuntimeException(e);
		}

		/*
		 * If the left side of the call is not null, then the result of the invocation
		 * has to be put into the execution environment.
		 */
		if (attr.getLeftsidefieldname() != null) {

			SEDEObject outputSEDEObject;
			/*
			 * If the outputindex is 0 or above, treat the outputindex'th parameter as the output.
			 * This is when a method doesn't return anything but applies changes to the parameters themselves.
			 * If the client defines a leftsidefield then treat the changed parameter as the return value:
			 */
			int outputIndex = attr.getOutputIndex();
			if(outputIndex == -1) {
				/*
				 * construct new sede object:
				 */
				SEDEObject resultfield;
				String type = attr.getLeftsidefieldType();
				if(SEDEObject.isServiceInstanceHandle(attr.getLeftsidefieldClass())){
					/*
						The method creates a new service instance.
						so wrap it into a handle:
					 */
					ServiceInstanceHandle handle = createServiceInstanceHandle(task, attr, outputValue);
					resultfield = new ServiceInstanceField(handle);
				} else if(SEDEObject.isPrimitive(type)){
					/*
						The type is primitive:
					 */
					resultfield = new PrimitiveDataField(type, outputValue);
				} else {
					/*
						Default case where some object is returned:
					 */
					resultfield = new ObjectDataField(type, outputValue);
				}
				outputSEDEObject = resultfield;
			} else {
				/*
				 * Method applies in place:
				 */
				SEDEObject changedParameter = environment.get(attr.getParameters().get(outputIndex));
				outputSEDEObject = changedParameter;
			}
			/*
				Put field into environment:
			 */
			String leftsideFieldname = attr.getLeftsidefieldname();
			task.getExecution().performLater( () -> {
				environment.put(leftsideFieldname, outputSEDEObject);
				task.setSucceeded();
			});

		} else {
			task.setSucceeded();
		}
	}
	private Executable getExecutable(ExecutionEnvironment env, InstructionNodeAttributes attr, Object[] parameterValues) {
		return reflectExecutable(env, attr, parameterValues);
	}
	private Executable reflectExecutable(ExecutionEnvironment env, InstructionNodeAttributes attr, Object[] parameterValues) {
		try {
			boolean isConstructor = attr.isConstructor();
			String methodname = attr.getMethod();
			Class<?> context;
			if(!attr.isContextAFieldname()) {
				context = Class.forName(attr.getContext());
			} else {
				context = env.get(attr.getContext()).getServiceInstance().getClass();
			}
			Executable[] executables;
			if(isConstructor) {
				executables = context.getConstructors();
			} else {
				List<Executable> executableList = new ArrayList<>();
				for(Method method : context.getMethods()){
					if(method.getName().equals(methodname)) {
						executableList.add(method);
					}
				}
				executables = executableList.toArray(new Executable[0]);
			}

			for (Executable exec : executables) {
				if (matchesParams(exec, parameterValues)) {
					return exec;
				}
			}
			throw new RuntimeException("No matching method found for signature:\n\t"
					+ context.getName() + "::" + attr.getMethod()
					+ "(" + Arrays.toString(ClassUtils.toClass(parameterValues))+ ")");


		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private boolean matchesParams(Executable executable, Object[] parameterValues) {
		if(executable.getParameterCount() != parameterValues.length){
			return false;
		}
		boolean fail = false;
		for (int i = 0; i < parameterValues.length; i++) {
			Object param = parameterValues[i];
			if(param == null) {
				continue;
			}
			if(!(param instanceof  Number)&& !(param instanceof Boolean) && !executable.getParameterTypes()[i].isAssignableFrom(param.getClass())) {
				fail = true;
				break;
			}
		}
		if(fail) {
			return false;
		}
		return true;
	}

	private void castNumbers(Class<?>[] expectedClasses, Object[] parameterValues) {
		for(int i = 0, size = parameterValues.length; i < size; i++) {
			if(parameterValues[i] instanceof  Number) {
				Class<?> exptectedClass = expectedClasses[i];
				Object numberValue = parameterValues[i];
				Number givenNmber = (Number) numberValue;
				Number castedNumber;
				if(exptectedClass == int.class || exptectedClass == Integer.class) {
					castedNumber = givenNmber.intValue();
				}
				else if(exptectedClass == byte.class || exptectedClass == Byte.class) {
					castedNumber = givenNmber.byteValue();
				}
				else if(exptectedClass == short.class || exptectedClass == Short.class) {
					castedNumber = givenNmber.shortValue();
				}
				else if(exptectedClass == long.class || exptectedClass == Long.class) {
					castedNumber = givenNmber.longValue();
				}
				else if(exptectedClass == float.class || exptectedClass == Float.class) {
					castedNumber = givenNmber.floatValue();
				}
				else if(exptectedClass == double.class || exptectedClass == Double.class) {
					castedNumber = givenNmber.doubleValue();
				} else{
					throw new RuntimeException("Cannot cast number to the expected class: " + exptectedClass);
				}
				parameterValues[i] = castedNumber;
			}
		}
	}

	/**
	 * Create new new service instance handle with the given service instance.
	 *
	 * @return a new ServiceInstanceHandle
	 */
	private ServiceInstance createServiceInstanceHandle(Task task, InstructionNodeAttributes attr, Object newServiceInstance) {
		String serviceInstanceId = UUID.randomUUID().toString();
		String executorId = task.getExecution().getConfiguration().getExecutorId();
		String classpath = attr.getLeftsidefieldType();
		ServiceInstance si = new ServiceInstance(executorId, classpath, serviceInstanceId, newServiceInstance);
		return si;
	}

	class InstructionNodeAttributes {
		private final boolean isConstructor;
		private final boolean isContextAFieldname;
		private final String method;
		private final String leftsidefieldname;
		private final String leftsidefieldclass;
		private final String host;
		private final String context;
		private final String fmInstruction;
		private final List<String> parameters;
		private final String leftSideFieldType;
		private final int outputIndex;

		@SuppressWarnings("unchecked")
		public InstructionNodeAttributes(Task task) {
			Map<String, Object> parameters = task.getAttributes();
			this.isConstructor = (boolean) parameters.get("is-service-construction");
			this.isContextAFieldname = (boolean) parameters.get("is-context-a-fieldname");
			this.method = (String) parameters.get("method");
			this.leftsidefieldname = (String) parameters.get("leftsidefieldname");
			this.leftsidefieldclass = (String) parameters.get("leftsidefieldclass");
			this.host = (String) parameters.get("host");
			this.context = (String) parameters.get("context");
			this.fmInstruction = (String) parameters.get("fmInstruction");
			this.parameters = (List<String>) parameters.get("params");
			this.leftSideFieldType = (String) parameters.get("leftsidefieldtype");
			Number outIndex = (Number) parameters.get("output-index");
			if(outIndex == null) {
				outputIndex = -1;
			} else {
				outputIndex = outIndex.intValue();
			}
		}

		public String getLeftsidefieldType() {
			return leftSideFieldType;
		}

		public String getFmInstruction() {
			return fmInstruction;
		}

		public boolean isConstructor() {
			return isConstructor;
		}

		public boolean isContextAFieldname() {
			return isContextAFieldname;
		}

		public String getMethod() {
			return method;
		}

		public String getLeftsidefieldname() {
			return leftsidefieldname;
		}

		public String getHost() {
			return host;
		}

		public String getContext() {
			return context;
		}

		public List<String> getParameters() {
			return parameters;
		}

		public int getOutputIndex() {
			return outputIndex;
		}

		public String getLeftsidefieldClass() {
			return  leftsidefieldclass != null ? leftsidefieldclass : "";
		}
	}

}
