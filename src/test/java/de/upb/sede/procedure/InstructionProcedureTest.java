package de.upb.sede.procedure;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.junit.Test;

import de.upb.sede.exec.Execution;
import de.upb.sede.exec.ExecutionGraph;
import de.upb.sede.exec.SEDEObject;
import de.upb.sede.exec.Task;

public class InstructionProcedureTest {
	@SuppressWarnings("serial")
	@Test
	public void testReflectingStaticJar() {
		Execution exec = null;
		Map<String, Object> testTaskParameters = new HashMap<>();
		testTaskParameters.put("nodetype", "Instruction");
		testTaskParameters.put("method", "addObject");
		testTaskParameters.put("is-service-construction", false);
		testTaskParameters.put("host", null);
		testTaskParameters.put("leftsidefieldname", "x");
		testTaskParameters.put("context", "de.upb.sede.procedure.InstructionProcedureTest");
		testTaskParameters.put("is-context-a-fieldname", false);
		testTaskParameters.put("params", new ArrayList<String>() {
			{
				add("a");
				add("b");
			}
		});
		testTaskParameters.put("fmInstruction", "de.upb.sede.procedure.InstructionProcedureTest::addObject(a,b)");
		Task task = new Task(exec, "testTask", testTaskParameters);
		ExecutionGraph graph = new ExecutionGraph() {

			@Override
			public Iterator<Task> iterator() {
				return null;
			}

			@Override
			public Task getNextTask() {
				return task;
			}
		};
		exec = new Execution(graph, "test");
		exec.getExecutionEnvironment().put("a", new SEDEObject(Integer.class.getName(), new Integer(1)));
		exec.getExecutionEnvironment().put("b", new SEDEObject(Integer.class.getName(), new Integer(2)));
		InstructionProcedure instructionProcedure = new InstructionProcedure();
		instructionProcedure.process(task);

		//TODO task requires non null for execution but execution requires task. -> Cyclic dependency?
	}
}
