package ai.services.execution.operator;

import ai.services.execution.GraphTaskExecution;
import ai.services.execution.Task;
import ai.services.execution.operator.local.InstructionOp;
import de.upb.sede.composition.graphs.nodes.IInstructionNode;
import de.upb.sede.composition.graphs.nodes.InstructionNode;
import de.upb.sede.composition.types.DataValueType;
import de.upb.sede.composition.types.PrimitiveValueType;
import de.upb.sede.composition.types.ServiceInstanceType;
import de.upb.sede.config.ExecutorConfiguration;
import de.upb.sede.core.*;
import de.upb.sede.util.ExecutorConfigurationCreator;
import org.junit.Assert;
import org.junit.Test;

import demo.math.Gerade;
import demo.types.Punkt;

public class InstructionOpTest {
    private static String executor_id = "Executor_id";
    private static GraphTaskExecution execution = new GraphTaskExecution(executor_id);
    private static TaskOperator op = new InstructionOp(new ServiceInstanceFactory(executor_id));
	@Test
	public void testStaticConstantTypeInvocation() {
		execution.setFieldValue("Number_1", new PrimitiveDataField(1));
		execution.setFieldValue("Number_2", new PrimitiveDataField(2));
		execution.setFieldValue("String_1", new PrimitiveDataField("Con"));
		execution.setFieldValue("String_2", new PrimitiveDataField("Cat"));
        Task task;
		final String resultAddObject = "resultAddObject";
		task = new Task(execution, testMathInst("addObject", resultAddObject));
        assert op.test(task);
        op.apply(task).performTransition(task);
        assert task.isMainTaskPerformed();

        Assert.assertNotNull(execution.getFieldValue(resultAddObject));
		Assert.assertEquals(Integer.class, execution.getFieldValue(resultAddObject).getDataField().getClass());
		Integer addObject = (Integer) execution.getFieldValue(resultAddObject).getDataField();
		Assert.assertEquals(new Integer(3), addObject);

		final String resultSubtractObject = "resultSubtractObject";
		task = new Task(execution, testMathInst("subtractObject", resultSubtractObject));

        assert op.test(task);
        op.apply(task).performTransition(task);
        assert task.isMainTaskPerformed();
		Assert.assertTrue(execution.getFieldValue(resultSubtractObject) != null);
		Assert.assertTrue(execution.getFieldValue(resultSubtractObject).getDataField() instanceof Integer);
		Integer subtractObject = (Integer) execution.getFieldValue(resultSubtractObject).getDataField();
		Assert.assertEquals(new Integer(-1), subtractObject);

		final String resultMultiplyObject = "resultMultiplyObject";
		task = new Task(execution, testMathInst("multiplyObject", resultMultiplyObject));

        assert op.test(task);
        op.apply(task).performTransition(task);
        assert task.isMainTaskPerformed();
		Assert.assertTrue(execution.getFieldValue(resultMultiplyObject) != null);
		Assert.assertTrue(execution.getFieldValue(resultMultiplyObject).getDataField() instanceof Integer);
		Integer multiplyObject = (Integer) execution.getFieldValue(resultMultiplyObject).getDataField();
		Assert.assertEquals(new Integer(2), multiplyObject);

		final String resultDivideObject = "resultDivideObject";
		task = new Task(execution, testMathInst("divideObject", resultDivideObject));

        assert op.test(task);
        op.apply(task).performTransition(task);
        assert task.isMainTaskPerformed();
		Assert.assertTrue(execution.getFieldValue(resultDivideObject) != null);
		Assert.assertTrue(execution.getFieldValue(resultDivideObject).getDataField() instanceof Float);
		Float divideObject = (Float) execution.getFieldValue(resultDivideObject).getDataField();
		Assert.assertEquals(new Float(0.5), divideObject);

		final String resultAddPrimitive = "resultAddPrimitive";
		task = new Task(execution, testMathInst("addPrimitive", resultAddPrimitive));

        assert op.test(task);
        op.apply(task).performTransition(task);
        assert task.isMainTaskPerformed();
		Assert.assertTrue(execution.getFieldValue(resultAddPrimitive) != null);
		Assert.assertTrue(execution.getFieldValue(resultAddPrimitive).getDataField() instanceof Integer);
		Integer addPrimitive = (Integer) execution.getFieldValue(resultAddPrimitive).getDataField();
		Assert.assertEquals(new Integer(3), addPrimitive);

		final String resultSubtractPrimitive = "resultSubtractPrimitive";
		task = new Task(execution, testMathInst("subtractPrimitive", resultSubtractPrimitive));

        assert op.test(task);
        op.apply(task).performTransition(task);
        assert task.isMainTaskPerformed();
		Assert.assertTrue(execution.getFieldValue(resultSubtractPrimitive) != null);
		Assert.assertTrue(
				execution.getFieldValue(resultSubtractPrimitive).getDataField() instanceof Integer);
		Integer subtractPrimitive = (Integer) execution.getFieldValue(resultSubtractPrimitive)
				.getDataField();
		Assert.assertEquals(new Integer(-1), subtractPrimitive);

		final String resultMultiplyPrimitive = "resultMultiplyPrimitive";
		task = new Task(execution, testMathInst("multiplyPrimitive", resultMultiplyPrimitive));

        assert op.test(task);
        op.apply(task).performTransition(task);
        assert task.isMainTaskPerformed();
		Assert.assertTrue(execution.getFieldValue(resultMultiplyPrimitive) != null);
		Assert.assertTrue(
				execution.getFieldValue(resultMultiplyPrimitive).getDataField() instanceof Integer);
		Integer multiplyPrimitive = (Integer) execution.getFieldValue(resultMultiplyPrimitive)
				.getDataField();
		Assert.assertEquals(new Integer(2), multiplyPrimitive);

		final String resultDividePrimitive = "resultDividePrimitive";
		task = new Task(execution, testMathInst("dividePrimitive", resultDividePrimitive));

        assert op.test(task);
        op.apply(task).performTransition(task);
        assert task.isMainTaskPerformed();
		Assert.assertTrue(execution.getFieldValue(resultDividePrimitive) != null);
		Assert.assertTrue(execution.getFieldValue(resultDividePrimitive).getDataField() instanceof Float);
		Float dividePrimitive = (Float) execution.getFieldValue(resultDividePrimitive).getDataField();
		Assert.assertEquals(new Float(0.5), dividePrimitive);

		final String resultStringLength = "resultStringLength";
		task = new Task(execution, testStrInst("length", resultStringLength, "String_1"));

        assert op.test(task);
        op.apply(task).performTransition(task);
        assert task.isMainTaskPerformed();
		Assert.assertTrue(execution.getFieldValue(resultStringLength) != null);
		Assert.assertTrue(execution.getFieldValue(resultStringLength).getDataField() instanceof Integer);
		Integer lengthString = (Integer) execution.getFieldValue(resultStringLength).getDataField();
		Assert.assertEquals(new Integer(3), lengthString);

		final String resultConcat = "resultConcat";
		task = new Task(execution, testStrInst("concat", resultConcat, "String_1", "String_2"));

        assert op.test(task);
        op.apply(task).performTransition(task);
        assert task.isMainTaskPerformed();
		Assert.assertTrue(execution.getFieldValue(resultConcat) != null);
		Assert.assertTrue(execution.getFieldValue(resultConcat).getDataField() instanceof String);
		String concat = (String) execution.getFieldValue(resultConcat).getDataField();
		Assert.assertEquals("ConCat", concat);
	}
    static IInstructionNode testMathInst(String methodName, String fieldName) {
        return InstructionNode.builder()
            .method(methodName)
            .fieldName(fieldName)
            .fieldType(PrimitiveValueType.builder()
                .primitiveType(PrimitiveType.Number)
                .build())
            .context(ai.services.SMath.class.getName())
            .contextIsFieldFlag(false)
            .fMInstruction("ai.services.execution.operator.InstructionProcedureTest::" + methodName + "(Number_1,Number_2)")
            .addParameterFields("Number_1", "Number_2")
            .build();
    }
    static IInstructionNode testStrInst(String methodName, String fieldName, String... parameters) {
        return InstructionNode.builder()
            .method(methodName)
            .fieldName(fieldName)
            .fieldType(PrimitiveValueType.builder()
                .primitiveType(PrimitiveType.String)
                .build())
            .context(ai.services.SString.class.getName())
            .contextIsFieldFlag(false)
            .fMInstruction("ai.services.execution.operator.InstructionProcedureTest::" + methodName + "(" +
                String.join(", ", parameters) +
                ")")
            .addParameterFields(parameters)
            .build();
    }
    static IInstructionNode gradeInst(String methodName, String fieldName) {
        return InstructionNode.builder()
            .method(methodName)
            .fieldName(fieldName)
            .fieldType(PrimitiveValueType.builder()
                .primitiveType(PrimitiveType.String)
                .build())
            .context("de.upb.sede.procedure.StaticStringTestfile")
            .contextIsFieldFlag(false)
            .fMInstruction("de.upb.sede.procedure.InstructionProcedureTest::" + methodName + "(String_1,String_2)")
            .addParameterFields("String_1", "String_2")
            .build();
    }

	@Test
	public void testServiceHandleInvocation() {
		ServiceInstance testGeradeInstance = new ServiceInstance("testID", "demo.math.Gerade", "test_instance",
				new Gerade(5, 0));

		execution.setFieldValue("geradeFieldName",
				new ServiceInstanceField(testGeradeInstance));
		execution.setFieldValue("2", new PrimitiveDataField( 2));

		final String resultCalc = "resultCalc";
		Task task = new Task(execution, InstructionNode.builder()
            .method("calc")
            .fieldName(resultCalc)
            .fieldType(DataValueType.builder()
                .typeQualifier(Punkt.class.getName())
                .build())
            .context("geradeFieldName")
            .contextIsFieldFlag(true)
            .fMInstruction("geradeFieldName::calc(2)")
            .addParameterFields("2")
            .build());

        assert op.test(task);
        op.apply(task).performTransition(task);
        assert task.isMainTaskPerformed();
        Assert.assertNotNull(execution.getFieldValue(resultCalc));
		Assert.assertTrue(execution.getFieldValue(resultCalc).getDataField() instanceof Punkt);
		Punkt calc = (Punkt) execution.getFieldValue(resultCalc).getDataField();
		Assert.assertEquals(new Punkt(2, 5), calc);
	}

	@Test
	public void testInstantiation() {
		ExecutorConfiguration config = ExecutorConfiguration.parseJSON(
				new ExecutorConfigurationCreator().withExecutorId(executor_id).toString());
		execution.setFieldValue("0", new PrimitiveDataField(0));
		execution.setFieldValue("1", new PrimitiveDataField(1));
		final String newInstance = "newInstance";
		Task task = new Task(execution, InstructionNode.builder()
            .method("__construct")
            .fieldName(newInstance)
            .fieldType(ServiceInstanceType.builder()
                .typeQualifier(Gerade.class.getName())
                .build())
            .context("demo.math.Gerade")
            .contextIsFieldFlag(false)
            .fMInstruction("demo.math.Gerade::demo.math.Gerade(0,1)")
            .addParameterFields("0", "1")
            .build());

        assert op.test(task);
        op.apply(task).performTransition(task);
        assert task.isMainTaskPerformed();
		SEDEObject newService = execution.getFieldValue(newInstance);
		Assert.assertTrue(newService.isServiceInstance());
		Assert.assertEquals(Gerade.class, newService.getServiceInstance().getClass());
		Gerade gerade = newService.getServiceInstance();
		Assert.assertEquals("demo.math.Gerade", newService.getServiceHandle().getClasspath());
		Assert.assertEquals(executor_id, newService.getServiceHandle().getExecutorId());
		Assert.assertEquals(0, gerade.achsenabschnitt().y, 0.0001);
		Assert.assertEquals(0, gerade.nullstelle().x, 0.0001);


		Task task_2 = new Task(execution, InstructionNode.builder()
            .method("randomGerade")
            .fieldName("randomInstance")
            .fieldType(ServiceInstanceType.builder()
                .typeQualifier(Gerade.class.getName())
                .build())
            .context("demo.math.Gerade")
            .contextIsFieldFlag(false)
            .fMInstruction("demo.math.Gerade::randomGerade()")
            .build());
        assert op.test(task_2);
        op.apply(task_2).performTransition(task_2);
        assert task.isMainTaskPerformed();
		SEDEObject randomService = execution.getFieldValue("randomInstance");
		Assert.assertTrue(randomService.isServiceInstance());
		Assert.assertEquals(ServiceInstanceHandle.class.getSimpleName(), randomService.getType());
		Assert.assertEquals("demo.math.Gerade", randomService.getServiceHandle().getClasspath());
		Assert.assertEquals(executor_id, randomService.getServiceHandle().getExecutorId());
	}

}
