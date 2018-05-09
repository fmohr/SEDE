package de.upb.sede.procedure;

import java.util.ArrayList;
import java.util.HashMap;

import org.junit.Assert;
import org.junit.Test;

import de.upb.sede.core.ServiceInstanceHandle;
import de.upb.sede.exec.Execution;
import de.upb.sede.exec.ExecutionEnvironment;
import de.upb.sede.core.SEDEObject;
import de.upb.sede.exec.Task;
import de.upb.sede.webinterfaces.client.BasicClientRequest;

public class InstructionProcedureTest {
	@Test
	public void testStaticConstantTypeInvocation() {
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

		execution.getExecutionEnvironment().put("Number_1", new SEDEObject("Number", new Integer(1)));
		execution.getExecutionEnvironment().put("Number_2", new SEDEObject("Number", new Integer(2)));
		execution.getExecutionEnvironment().put("String_1", new SEDEObject("String", "Con"));
		execution.getExecutionEnvironment().put("String_2", new SEDEObject("String", "Cat"));

		Task task;
		String methodResultName;
		methodResultName = "resultAddObject";
		task = new Task(execution, "testTask", new TestTaskParametersNumber("addObject", methodResultName));
		new InstructionProcedure(task).process(task);
		Assert.assertTrue(execution.getExecutionEnvironment().get(methodResultName) != null);
		Assert.assertTrue(execution.getExecutionEnvironment().get(methodResultName).getObject() instanceof Integer);
		Integer addObject = (Integer) execution.getExecutionEnvironment().get(methodResultName).getObject();
		Assert.assertEquals(new Integer(3), addObject);

		methodResultName = "resultSubtractObject";
		task = new Task(execution, "testTask", new TestTaskParametersNumber("subtractObject", methodResultName));
		new InstructionProcedure(task).process(task);
		Assert.assertTrue(execution.getExecutionEnvironment().get(methodResultName) != null);
		Assert.assertTrue(execution.getExecutionEnvironment().get(methodResultName).getObject() instanceof Integer);
		Integer subtractObject = (Integer) execution.getExecutionEnvironment().get(methodResultName).getObject();
		Assert.assertEquals(new Integer(-1), subtractObject);

		methodResultName = "resultMultiplyObject";
		task = new Task(execution, "testTask", new TestTaskParametersNumber("multiplyObject", methodResultName));
		new InstructionProcedure(task).process(task);
		Assert.assertTrue(execution.getExecutionEnvironment().get(methodResultName) != null);
		Assert.assertTrue(execution.getExecutionEnvironment().get(methodResultName).getObject() instanceof Integer);
		Integer multiplyObject = (Integer) execution.getExecutionEnvironment().get(methodResultName).getObject();
		Assert.assertEquals(new Integer(2), multiplyObject);

		methodResultName = "resultDivideObject";
		task = new Task(execution, "testTask", new TestTaskParametersNumber("divideObject", methodResultName));
		new InstructionProcedure(task).process(task);
		Assert.assertTrue(execution.getExecutionEnvironment().get(methodResultName) != null);
		Assert.assertTrue(execution.getExecutionEnvironment().get(methodResultName).getObject() instanceof Float);
		Float divideObject = (Float) execution.getExecutionEnvironment().get(methodResultName).getObject();
		Assert.assertEquals(new Float(0.5), divideObject);

		methodResultName = "resultAddPrimitive";
		task = new Task(execution, "testTask", new TestTaskParametersNumber("addPrimitive", methodResultName));
		new InstructionProcedure(task).process(task);
		Assert.assertTrue(execution.getExecutionEnvironment().get(methodResultName) != null);
		Assert.assertTrue(execution.getExecutionEnvironment().get(methodResultName).getObject() instanceof Integer);
		Integer addPrimitive = (Integer) execution.getExecutionEnvironment().get(methodResultName).getObject();
		Assert.assertEquals(new Integer(3), addPrimitive);

		methodResultName = "resultSubtractPrimitive";
		task = new Task(execution, "testTask", new TestTaskParametersNumber("subtractPrimitive", methodResultName));
		new InstructionProcedure(task).process(task);
		Assert.assertTrue(execution.getExecutionEnvironment().get(methodResultName) != null);
		Assert.assertTrue(execution.getExecutionEnvironment().get(methodResultName).getObject() instanceof Integer);
		Integer subtractPrimitive = (Integer) execution.getExecutionEnvironment().get(methodResultName).getObject();
		Assert.assertEquals(new Integer(-1), subtractPrimitive);

		methodResultName = "resultMultiplyPrimitive";
		task = new Task(execution, "testTask", new TestTaskParametersNumber("multiplyPrimitive", methodResultName));
		new InstructionProcedure(task).process(task);
		Assert.assertTrue(execution.getExecutionEnvironment().get(methodResultName) != null);
		Assert.assertTrue(execution.getExecutionEnvironment().get(methodResultName).getObject() instanceof Integer);
		Integer multiplyPrimitive = (Integer) execution.getExecutionEnvironment().get(methodResultName).getObject();
		Assert.assertEquals(new Integer(2), multiplyPrimitive);

		methodResultName = "resultDividePrimitive";
		task = new Task(execution, "testTask", new TestTaskParametersNumber("dividePrimitive", methodResultName));
		new InstructionProcedure(task).process(task);
		Assert.assertTrue(execution.getExecutionEnvironment().get(methodResultName) != null);
		Assert.assertTrue(execution.getExecutionEnvironment().get(methodResultName).getObject() instanceof Float);
		Float dividePrimitive = (Float) execution.getExecutionEnvironment().get(methodResultName).getObject();
		Assert.assertEquals(new Float(0.5), dividePrimitive);

		methodResultName = "resultStringLength";
		task = new Task(execution, "testTask", new TestTaskParametersString1("length", methodResultName));
		new InstructionProcedure(task).process(task);
		Assert.assertTrue(execution.getExecutionEnvironment().get(methodResultName) != null);
		Assert.assertTrue(execution.getExecutionEnvironment().get(methodResultName).getObject() instanceof String);
		Integer lengthString = (Integer) execution.getExecutionEnvironment().get(methodResultName).getObject();
		Assert.assertEquals(new Integer(3), lengthString);

		methodResultName = "resultConcat";
		task = new Task(execution, "testTask", new TestTaskParametersString2("concat", methodResultName));
		new InstructionProcedure(task).process(task);
		Assert.assertTrue(execution.getExecutionEnvironment().get(methodResultName) != null);
		Assert.assertTrue(execution.getExecutionEnvironment().get(methodResultName).getObject() instanceof String);
		String concat = (String) execution.getExecutionEnvironment().get(methodResultName).getObject();
		Assert.assertEquals("ConCat", concat);
	}

	class TestTaskParametersNumber extends HashMap<String, Object> {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1438116688789645674L;

		@SuppressWarnings("serial")
		public TestTaskParametersNumber(String methodName, String resultName) {
			put("nodetype", "Instruction");
			put("method", methodName);
			put("is-service-construction", false);
			put("host", null);
			put("leftsidefieldname", resultName);
			put("context", "de.upb.sede.procedure.StaticMathTestfile");
			put("is-context-a-fieldname", false);
			put("fmInstruction",
					"de.upb.sede.procedure.InstructionProcedureTest::" + methodName + "(Number_1,Number_2)");
			put("leftsidefieldtype", "Number");
			put("params", new ArrayList<String>() {
				{
					add("Number_1");
					add("Number_2");
				}
			});
		}
	}

	class TestTaskParametersString2 extends HashMap<String, Object> {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1438116688789645674L;

		@SuppressWarnings("serial")
		public TestTaskParametersString2(String methodName, String resultName) {
			put("nodetype", "Instruction");
			put("method", methodName);
			put("is-service-construction", false);
			put("host", null);
			put("leftsidefieldname", resultName);
			put("context", "de.upb.sede.procedure.StaticMathTestfile");
			put("is-context-a-fieldname", false);
			put("fmInstruction",
					"de.upb.sede.procedure.InstructionProcedureTest::" + methodName + "(String_1,String_2)");
			put("leftsidefieldtype", "String");
			put("params", new ArrayList<String>() {
				{
					add("String_1");
					add("String_2");
				}
			});
		}
	}

	class TestTaskParametersString1 extends HashMap<String, Object> {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1438116688789645674L;

		@SuppressWarnings("serial")
		public TestTaskParametersString1(String methodName, String resultName) {
			put("nodetype", "Instruction");
			put("method", methodName);
			put("is-service-construction", false);
			put("host", null);
			put("leftsidefieldname", resultName);
			put("context", "de.upb.sede.procedure.StaticMathTestfile");
			put("is-context-a-fieldname", false);
			put("fmInstruction", "de.upb.sede.procedure.InstructionProcedureTest::" + methodName + "(String_1)");
			put("leftsidefieldtype", "Number");
			put("params", new ArrayList<String>() {
				{
					add("String_1");
				}
			});
		}
	}

	class TestExecutionEnvironment extends HashMap<String, SEDEObject> implements ExecutionEnvironment {
		private static final long serialVersionUID = 7699263841623117565L;

	}
}
