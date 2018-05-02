package de.upb.sede.procedure;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import de.upb.sede.exec.Execution;
import de.upb.sede.exec.ExecutionEnvironment;
import de.upb.sede.exec.SEDEObject;
import de.upb.sede.exec.Task;
import de.upb.sede.exec.graphs.EGraph;

public class InstructionProcedureTest {
	@Test
	public void testReflectingStaticJar() {
		Execution execution = new Execution("testID");
		execution.setEnvironment(new TestExecutionEnvironment());
		execution.getExecutionEnvironment().put("a", new SEDEObject(Integer.class.getName(), new Integer(1)));
		execution.getExecutionEnvironment().put("b", new SEDEObject(Integer.class.getName(), new Integer(2)));
		EGraph graph = new EGraph(execution);
		Map<String, Object> testTaskParameters = new TestTaskParameters("addObject");
		Task task = new Task(graph, "testTask", testTaskParameters);
		graph.addTask(task);

		InstructionProcedure instructionProcedure = new InstructionProcedure();
		instructionProcedure.process(task);
		//TODO This is not how you write a test
		System.out.println(((Integer) execution.getExecutionEnvironment().get("x").getObject()).toString());
	}

	class TestTaskParameters extends HashMap<String, Object> {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1438116688789645674L;

		@SuppressWarnings("serial")
		public TestTaskParameters(String methodName) {
			put("nodetype", "Instruction");
			put("method", methodName);
			put("is-service-construction", false);
			put("host", null);
			put("leftsidefieldname", "x");
			put("context", "de.upb.sede.procedure.StaticMathReflectionTestfile");
			put("is-context-a-fieldname", false);
			put("fmInstruction", "de.upb.sede.procedure.InstructionProcedureTest::" + methodName + "(a,b)");
			put("params", new ArrayList<String>() {
				{
					add("a");
					add("b");
				}
			});
		}
	}

	class TestExecutionEnvironment extends HashMap<String, SEDEObject> implements ExecutionEnvironment {

		/**
		 * 
		 */
		private static final long serialVersionUID = 7699263841623117565L;

	}
}
