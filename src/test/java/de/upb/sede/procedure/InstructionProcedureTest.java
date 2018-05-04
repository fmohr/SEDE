package de.upb.sede.procedure;

import java.util.ArrayList;
import java.util.HashMap;

import org.junit.Assert;
import org.junit.Test;

import de.upb.sede.core.ServiceInstanceHandle;
import de.upb.sede.exec.Execution;
import de.upb.sede.exec.ExecutionEnvironment;
import de.upb.sede.exec.SEDEObject;
import de.upb.sede.exec.Task;
import de.upb.sede.webinterfaces.client.BasicClientRequest;

public class InstructionProcedureTest {
	@Test
	public void testReflectingStaticJar() {
		Execution execution = new Execution("testID") {
			@Override
			public BasicClientRequest createClientRequest(Object o) {
				return null;
			}

			@Override
			public ServiceInstanceHandle createServiceInstanceHandle(Object serviceInstance) {
				return null;
			}
		};

		execution.setEnvironment(new TestExecutionEnvironment());
		execution.getExecutionEnvironment().put("a", new SEDEObject(Integer.class.getName(), new Integer(1)));
		execution.getExecutionEnvironment().put("b", new SEDEObject(Integer.class.getName(), new Integer(2)));
		Task task;
		String methodResultName;
		methodResultName = "resultAddObject";
		task = new Task(execution, "testTask", new TestTaskParametersObject("addObject", methodResultName));
		new InstructionProcedure().process(task);
		Assert.assertTrue(execution.getExecutionEnvironment().get(methodResultName) != null);
		Assert.assertTrue(execution.getExecutionEnvironment().get(methodResultName).getObject() instanceof Integer);
		Integer addObject = (Integer) execution.getExecutionEnvironment().get(methodResultName).getObject();
		Assert.assertEquals(new Integer(3), addObject);

		methodResultName = "resultSubtractObject";
		task = new Task(execution, "testTask", new TestTaskParametersObject("subtractObject", methodResultName));
		new InstructionProcedure().process(task);
		Assert.assertTrue(execution.getExecutionEnvironment().get(methodResultName) != null);
		Assert.assertTrue(execution.getExecutionEnvironment().get(methodResultName).getObject() instanceof Integer);
		Integer subtractObject = (Integer) execution.getExecutionEnvironment().get(methodResultName).getObject();
		Assert.assertEquals(new Integer(-1), subtractObject);

		methodResultName = "resultMultiplyObject";
		task = new Task(execution, "testTask", new TestTaskParametersObject("multiplyObject", methodResultName));
		new InstructionProcedure().process(task);
		Assert.assertTrue(execution.getExecutionEnvironment().get(methodResultName) != null);
		Assert.assertTrue(execution.getExecutionEnvironment().get(methodResultName).getObject() instanceof Integer);
		Integer multiplyObject = (Integer) execution.getExecutionEnvironment().get(methodResultName).getObject();
		Assert.assertEquals(new Integer(2), multiplyObject);

		methodResultName = "resultDivideObject";
		task = new Task(execution, "testTask", new TestTaskParametersObject("divideObject", methodResultName));
		new InstructionProcedure().process(task);
		Assert.assertTrue(execution.getExecutionEnvironment().get(methodResultName) != null);
		Assert.assertTrue(execution.getExecutionEnvironment().get(methodResultName).getObject() instanceof Float);
		Float divideObject = (Float) execution.getExecutionEnvironment().get(methodResultName).getObject();
		Assert.assertEquals(new Float(0.5), divideObject);

		methodResultName = "resultAddPrimitive";
		task = new Task(execution, "testTask", new TestTaskParametersObject("addPrimitive", methodResultName));
		new InstructionProcedure().process(task);
		Assert.assertTrue(execution.getExecutionEnvironment().get(methodResultName) != null);
		Assert.assertTrue(execution.getExecutionEnvironment().get(methodResultName).getObject() instanceof Integer);
		Integer addPrimitive = (Integer) execution.getExecutionEnvironment().get(methodResultName).getObject();
		Assert.assertEquals(new Integer(3), addPrimitive);

		methodResultName = "resultSubtractPrimitive";
		task = new Task(execution, "testTask", new TestTaskParametersObject("subtractPrimitive", methodResultName));
		new InstructionProcedure().process(task);
		Assert.assertTrue(execution.getExecutionEnvironment().get(methodResultName) != null);
		Assert.assertTrue(execution.getExecutionEnvironment().get(methodResultName).getObject() instanceof Float);
		Integer subtractPrimitive = (Integer) execution.getExecutionEnvironment().get(methodResultName).getObject();
		Assert.assertEquals(new Integer(-1), subtractPrimitive);

		methodResultName = "resultMultiplyPrimitive";
		task = new Task(execution, "testTask", new TestTaskParametersObject("multiplyPrimitive", methodResultName));
		new InstructionProcedure().process(task);
		Assert.assertTrue(execution.getExecutionEnvironment().get(methodResultName) != null);
		Assert.assertTrue(execution.getExecutionEnvironment().get(methodResultName).getObject() instanceof Float);
		Integer multiplyPrimitive = (Integer) execution.getExecutionEnvironment().get(methodResultName).getObject();
		Assert.assertEquals(new Integer(2), multiplyPrimitive);

		methodResultName = "resultDividePrimitive";
		task = new Task(execution, "testTask", new TestTaskParametersObject("dividePrimitive", methodResultName));
		new InstructionProcedure().process(task);
		Assert.assertTrue(execution.getExecutionEnvironment().get(methodResultName) != null);
		Assert.assertTrue(execution.getExecutionEnvironment().get(methodResultName).getObject() instanceof Float);
		Float dividePrimitive = (Float) execution.getExecutionEnvironment().get(methodResultName).getObject();
		Assert.assertEquals(new Float(0.5), dividePrimitive);
	}

	class TestTaskParametersObject extends HashMap<String, Object> {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1438116688789645674L;

		@SuppressWarnings("serial")
		public TestTaskParametersObject(String methodName, String resultName) {
			put("nodetype", "Instruction");
			put("method", methodName);
			put("is-service-construction", false);
			put("host", null);
			put("leftsidefieldname", resultName);
			put("context", "de.upb.sede.procedure.StaticMathReflectionTestfile");
			put("is-context-a-fieldname", false);
			put("fmInstruction", "de.upb.sede.procedure.InstructionProcedureTest::" + methodName + "(param_1,param_2)");
			put("params", new ArrayList<String>() {
				{
					add("param_1");
					add("param_2");
				}
			});
		}
	}

	class TestExecutionEnvironment extends HashMap<String, SEDEObject> implements ExecutionEnvironment {
		private static final long serialVersionUID = 7699263841623117565L;

	}
}
