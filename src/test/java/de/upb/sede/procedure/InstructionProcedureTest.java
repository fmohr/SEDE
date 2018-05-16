package de.upb.sede.procedure;

import java.lang.reflect.Executable;
import java.util.Arrays;
import java.util.HashMap;

import org.junit.Assert;
import org.junit.Test;

import de.upb.sede.core.SEDEObject;
import de.upb.sede.core.ServiceInstanceHandle;
import de.upb.sede.exec.Execution;
import de.upb.sede.exec.ExecutionEnvironment;
import de.upb.sede.exec.ServiceInstance;
import de.upb.sede.exec.Task;
import de.upb.sede.util.Observable;
import demo.math.Gerade;
import demo.types.Punkt;

public class InstructionProcedureTest {
	@Test
	public void testStaticConstantTypeInvocation() {
		Execution execution = new Execution("testID", null);

		execution.getExecutionEnvironment().put("Number_1", new SEDEObject("Number", new Integer(1)));
		execution.getExecutionEnvironment().put("Number_2", new SEDEObject("Number", new Integer(2)));
		execution.getExecutionEnvironment().put("String_1", new SEDEObject("String", "Con"));
		execution.getExecutionEnvironment().put("String_2", new SEDEObject("String", "Cat"));

		Task task;
		final String resultAddObject = "resultAddObject";
		task = new Task(execution, "testTask", new TestTaskParametersNumber("addObject", resultAddObject));
		new InstructionProcedure().process(task);
		Assert.assertTrue(execution.getExecutionEnvironment().get(resultAddObject) != null);
		Assert.assertTrue(execution.getExecutionEnvironment().get(resultAddObject).getObject() instanceof Integer);
		Integer addObject = (Integer) execution.getExecutionEnvironment().get(resultAddObject).getObject();
		Assert.assertEquals(new Integer(3), addObject);

		final String resultSubtractObject = "resultSubtractObject";
		task = new Task(execution, "testTask", new TestTaskParametersNumber("subtractObject", resultSubtractObject));
		new InstructionProcedure().process(task);
		Assert.assertTrue(execution.getExecutionEnvironment().get(resultSubtractObject) != null);
		Assert.assertTrue(execution.getExecutionEnvironment().get(resultSubtractObject).getObject() instanceof Integer);
		Integer subtractObject = (Integer) execution.getExecutionEnvironment().get(resultSubtractObject).getObject();
		Assert.assertEquals(new Integer(-1), subtractObject);

		final String resultMultiplyObject = "resultMultiplyObject";
		task = new Task(execution, "testTask", new TestTaskParametersNumber("multiplyObject", resultMultiplyObject));
		new InstructionProcedure().process(task);
		Assert.assertTrue(execution.getExecutionEnvironment().get(resultMultiplyObject) != null);
		Assert.assertTrue(execution.getExecutionEnvironment().get(resultMultiplyObject).getObject() instanceof Integer);
		Integer multiplyObject = (Integer) execution.getExecutionEnvironment().get(resultMultiplyObject).getObject();
		Assert.assertEquals(new Integer(2), multiplyObject);

		final String resultDivideObject = "resultDivideObject";
		task = new Task(execution, "testTask", new TestTaskParametersNumber("divideObject", resultDivideObject));
		new InstructionProcedure().process(task);
		Assert.assertTrue(execution.getExecutionEnvironment().get(resultDivideObject) != null);
		Assert.assertTrue(execution.getExecutionEnvironment().get(resultDivideObject).getObject() instanceof Float);
		Float divideObject = (Float) execution.getExecutionEnvironment().get(resultDivideObject).getObject();
		Assert.assertEquals(new Float(0.5), divideObject);

		final String resultAddPrimitive = "resultAddPrimitive";
		task = new Task(execution, "testTask", new TestTaskParametersNumber("addPrimitive", resultAddPrimitive));
		new InstructionProcedure().process(task);
		Assert.assertTrue(execution.getExecutionEnvironment().get(resultAddPrimitive) != null);
		Assert.assertTrue(execution.getExecutionEnvironment().get(resultAddPrimitive).getObject() instanceof Integer);
		Integer addPrimitive = (Integer) execution.getExecutionEnvironment().get(resultAddPrimitive).getObject();
		Assert.assertEquals(new Integer(3), addPrimitive);

		final String resultSubtractPrimitive = "resultSubtractPrimitive";
		task = new Task(execution, "testTask",
				new TestTaskParametersNumber("subtractPrimitive", resultSubtractPrimitive));
		new InstructionProcedure().process(task);
		Assert.assertTrue(execution.getExecutionEnvironment().get(resultSubtractPrimitive) != null);
		Assert.assertTrue(
				execution.getExecutionEnvironment().get(resultSubtractPrimitive).getObject() instanceof Integer);
		Integer subtractPrimitive = (Integer) execution.getExecutionEnvironment().get(resultSubtractPrimitive)
				.getObject();
		Assert.assertEquals(new Integer(-1), subtractPrimitive);

		final String resultMultiplyPrimitive = "resultMultiplyPrimitive";
		task = new Task(execution, "testTask",
				new TestTaskParametersNumber("multiplyPrimitive", resultMultiplyPrimitive));
		new InstructionProcedure().process(task);
		Assert.assertTrue(execution.getExecutionEnvironment().get(resultMultiplyPrimitive) != null);
		Assert.assertTrue(
				execution.getExecutionEnvironment().get(resultMultiplyPrimitive).getObject() instanceof Integer);
		Integer multiplyPrimitive = (Integer) execution.getExecutionEnvironment().get(resultMultiplyPrimitive)
				.getObject();
		Assert.assertEquals(new Integer(2), multiplyPrimitive);

		final String resultDividePrimitive = "resultDividePrimitive";
		task = new Task(execution, "testTask", new TestTaskParametersNumber("dividePrimitive", resultDividePrimitive));
		new InstructionProcedure().process(task);
		Assert.assertTrue(execution.getExecutionEnvironment().get(resultDividePrimitive) != null);
		Assert.assertTrue(execution.getExecutionEnvironment().get(resultDividePrimitive).getObject() instanceof Float);
		Float dividePrimitive = (Float) execution.getExecutionEnvironment().get(resultDividePrimitive).getObject();
		Assert.assertEquals(new Float(0.5), dividePrimitive);

		final String resultStringLength = "resultStringLength";
		task = new Task(execution, "testTask", new HashMap<String, Object>() {
			{
				put("nodetype", "Instruction");
				put("method", "length");
				put("is-service-construction", false);
				put("host", null);
				put("leftsidefieldname", resultStringLength);
				put("context", "de.upb.sede.procedure.StaticStringTestfile");
				put("is-context-a-fieldname", false);
				put("fmInstruction", "de.upb.sede.procedure.InstructionProcedureTest::length(String_1)");
				put("leftsidefieldtype", "Number");
				put("params", Arrays.asList("String_1"));
			}
		});
		new InstructionProcedure().process(task);
		Assert.assertTrue(execution.getExecutionEnvironment().get(resultStringLength) != null);
		Assert.assertTrue(execution.getExecutionEnvironment().get(resultStringLength).getObject() instanceof Integer);
		Integer lengthString = (Integer) execution.getExecutionEnvironment().get(resultStringLength).getObject();
		Assert.assertEquals(new Integer(3), lengthString);

		final String resultConcat = "resultConcat";
		task = new Task(execution, "testTask", new HashMap<String, Object>() {
			{
				put("nodetype", "Instruction");
				put("method", "concat");
				put("is-service-construction", false);
				put("host", null);
				put("leftsidefieldname", resultConcat);
				put("context", "de.upb.sede.procedure.StaticStringTestfile");
				put("is-context-a-fieldname", false);
				put("fmInstruction", "de.upb.sede.procedure.InstructionProcedureTest::concat(String_1,String_2)");
				put("leftsidefieldtype", "String");
				put("params", Arrays.asList("String_1", "String_2"));
			}
		});
		new InstructionProcedure().process(task);
		Assert.assertTrue(execution.getExecutionEnvironment().get(resultConcat) != null);
		Assert.assertTrue(execution.getExecutionEnvironment().get(resultConcat).getObject() instanceof String);
		String concat = (String) execution.getExecutionEnvironment().get(resultConcat).getObject();
		Assert.assertEquals("ConCat", concat);
	}

	class TestTaskParametersNumber extends HashMap<String, Object> {
		private static final long serialVersionUID = 1438116688789645674L;

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
			put("params", Arrays.asList("Number_1", "Number_2"));
		}
	}

	@Test
	public void testServiceHandleInvocation() {
		Execution execution = new Execution("testID", null);
		ServiceInstance testGeradeInstance = new ServiceInstance("testID", "demo.math.Gerade", "test_instance",
				new Gerade(5, 0));

		execution.getExecutionEnvironment().put("geradeFieldName",
				new SEDEObject(testGeradeInstance));
		execution.getExecutionEnvironment().put("2", new SEDEObject("Number", 2));

		final String resultCalc = "resultCalc";
		Task task = new Task(execution, "testTask", new HashMap<String, Object>() {
			{
				put("nodetype", "Instruction");
				put("method", "calc");
				put("is-service-construction", false);
				put("host", null);
				put("leftsidefieldname", resultCalc);
				put("context", "geradeFieldName");
				put("is-context-a-fieldname", true);
				put("fmInstruction", "geradeFieldName::calc(2)");
				put("leftsidefieldtype", "Number");
				put("params", Arrays.asList("2"));
			}
		});
		new InstructionProcedure().process(task);
		Assert.assertTrue(execution.getExecutionEnvironment().get(resultCalc) != null);
		Assert.assertTrue(execution.getExecutionEnvironment().get(resultCalc).getObject() instanceof Punkt);
		Punkt calc = (Punkt) execution.getExecutionEnvironment().get(resultCalc).getObject();
		Assert.assertEquals(new Punkt(2, 5), calc);
	}

	@Test
	public void testInstanciation() {
		Execution execution = new Execution("testID", null);
		execution.getExecutionEnvironment().put("0", new SEDEObject("Number", 0));
		execution.getExecutionEnvironment().put("1", new SEDEObject("Number", 1));
		final String newInstance = "newInstance";
		Task task = new Task(execution, "testTask", new HashMap<String, Object>() {
			{
				put("nodetype", "Instruction");
				put("method", "__construct");
				put("is-service-construction", true);
				put("host", null);
				put("leftsidefieldname", newInstance);
				put("context", "demo.math.Gerade");
				put("is-context-a-fieldname", false);
				put("fmInstruction", "demo.math.Gerade::demo.math.Gerade(0,1)");
				put("leftsidefieldtype", "demo.math.Gerade");
				put("params", Arrays.asList("0", "1"));
			}
		});
		new InstructionProcedure().process(task);
		SEDEObject newService = execution.getExecutionEnvironment().get(newInstance);
		Assert.assertTrue(newService.isServiceInstance());
		Assert.assertEquals(Gerade.class, newService.getServiceInstance().getClass());
		Gerade gerade = newService.getServiceInstance();
		Assert.assertEquals(0, gerade.achsenabschnitt().y, 0.0001);
		Assert.assertEquals(0, gerade.nullstelle().x, 0.0001);

	}

	class TestExecutionEnvironment extends HashMap<String, SEDEObject> implements ExecutionEnvironment {
		private static final long serialVersionUID = 7699263841623117565L;

		public Observable<ExecutionEnvironment> getState() {
			return null;
		}
	}
}
