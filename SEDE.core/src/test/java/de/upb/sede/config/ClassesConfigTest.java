package de.upb.sede.config;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.*;

public class ClassesConfigTest {

	ClassesConfig cc;

	@Before
	public void setUp() throws Exception {
		cc = new ClassesConfig(getConfPath("ABC"));
	}

	@After
	public void tearDown() throws Exception {
	}

	String getConfPath(String name) {
		return "testrsc/config/" + name + "-classconf.json";
	}

	@Test
	public void appendConfigFromJsonStrings() {
		ClassesConfig classesConfig = new ClassesConfig();
		assertFalse(classesConfig.classknown("A"));

		classesConfig.appendConfigFromFiles(getConfPath("ABC"));

		assertTrue(classesConfig.classknown("A"));
		assertTrue(classesConfig.classknown("B"));
		assertTrue(classesConfig.classknown("C"));

		classesConfig.appendConfigFromFiles(getConfPath("DEF"));

		assertTrue(classesConfig.classknown("D"));
		assertTrue(classesConfig.classknown("E"));
		assertTrue(classesConfig.classknown("F"));

		try {
			classesConfig.appendConfigFromFiles(getConfPath("A"));
			fail("Trying to add A again should throw an exception. Non was thrown though.");
		} catch (Exception ex) {
//			ex.printStackTrace();
		}
	}

	@Test
	public void extendsClassConfig() {


	}

	@Test
	public void classknown() {
		ClassesConfig cc = new ClassesConfig();
		assertFalse(cc.classknown("A"));

		cc.appendConfigFromFiles(getConfPath("ABC"));

		assertTrue(cc.classknown("A"));
		assertTrue(cc.classknown("B"));
		assertTrue(cc.classknown("C"));
		assertFalse(cc.classknown("D"));
	}

	@Test
	public void classunknown() {
		ClassesConfig cc = new ClassesConfig();
		assertTrue(cc.classunknown("A"));

		cc.appendConfigFromFiles(getConfPath("ABC"));

		assertFalse(cc.classunknown("A"));
		assertFalse(cc.classunknown("B"));
		assertFalse(cc.classunknown("C"));
		assertTrue(cc.classunknown("D"));
	}

	@Test
	public void isWrapped() {
		assertFalse(cc.classInfo("A").isWrapped());
		assertTrue(cc.classInfo("B").isWrapped());
		assertFalse(cc.classInfo("C").isWrapped());
	}

	@Test
	public void classpath() {
		assertEquals("A", cc.classInfo("A").classpath());
		assertEquals("A", cc.classInfo("B").classpath());

	}


	@Test
	public void testMethodsA() {
		ClassesConfig.ClassInfo A = cc.classInfo("A");
		testMethodsA(A);
	}

//	@Test
	public void testMethodsB() {
		ClassesConfig.ClassInfo B = cc.classInfo("B");
		testMethodsA(B);
	}

	public void testMethodsA(ClassesConfig.ClassInfo classA) {
		ClassesConfig.MethodInfo m1 = classA.methodInfo("m1");
		ClassesConfig.MethodInfo m2 = classA.methodInfo("m2");
		ClassesConfig.MethodInfo m3 = classA.methodInfo("m3");
		ClassesConfig.MethodInfo m4 = classA.methodInfo("m4");

		assertTrue(m1.hasReturnType());
		assertFalse(m2.hasReturnType());
		assertFalse(m3.hasReturnType());
		assertFalse(m4.hasReturnType());

		assertEquals("t1", m1.returnType());

		assertEquals(2, m1.paramCount());
		assertEquals(3, m2.paramCount());
		assertEquals(0, m3.paramCount());
		assertEquals(0, m4.paramCount());

		assertTrue(m1.isStateMutating());
		assertFalse(m2.isStateMutating());
		assertFalse(m3.isStateMutating());
		assertTrue(m4.isStateMutating());

		assertFalse(m1.isStatic());
		assertFalse(m2.isStatic());
		assertTrue(m3.isStatic());
		assertFalse(m4.isStatic());

		assertEquals("t2", m1.paramType(1));
		assertEquals("t2", m2.paramType(1));

		assertFalse( m1.isParamStateMutating(1));
		assertTrue( m2.isParamStateMutating(2));

		assertEquals(2, m2.indexOfNthStateMutatingParam(0));

		assertEquals(Arrays.asList("t1", "t2"), m1.paramTypes());
		assertEquals(Arrays.asList("t1", "t2", "t3"), m2.paramTypes());
		assertEquals(Arrays.asList(), m3.paramTypes());
	}


	@Test
	public void testMethodsC() {
		ClassesConfig.MethodInfo construct = cc.classInfo("C").constructInfo();
		assertTrue(construct.isStatic());
		assertFalse(construct.isStateMutating());
		assertEquals(2, construct.paramCount());
		assertEquals("t3", construct.paramType(0));
		assertEquals("C", construct.returnType());

		ClassesConfig.MethodInfo m1 = cc.classInfo("C").methodInfo("m1");
		assertEquals("t3", m1.paramType(0));
		assertFalse(m1.isStateMutating());
		assertFalse(m1.isStatic());
		assertEquals("t2", m1.returnType());


		ClassesConfig.MethodInfo m2 = cc.classInfo("C").methodInfo("m2");
		assertEquals("t1", m2.paramType(0));
		assertFalse(m2.isStateMutating());
		assertFalse(m2.isStatic());
		assertEquals(2, m2.indexOfNthStateMutatingParam(0));

	}

	@Test
	public void testFixedParams() {
		ClassesConfig.ClassInfo B = cc.classInfo("B");
		ClassesConfig.MethodInfo m2 = B.methodInfo("m2");
		assertEquals(3, m2.paramCount());
		assertEquals(Arrays.asList("t1", "t2", "t3"), m2.paramTypes());
		ArrayList<String> inputs = new ArrayList<>();
		inputs.add("Some Parameter");
		m2.insertFixedConstants(inputs);
		assertEquals(Arrays.asList("constant1", "Some Parameter", "constant3"), inputs);

		inputs.clear();
		inputs.add("TOo many inputs.");
		inputs.add("This is too much.");
		Exception thrownException = null;
		try{
			m2.insertFixedConstants(inputs);
		} catch(Exception ex) {
			thrownException = ex;
		}
		assertNotNull(thrownException);

		inputs.clear();
		thrownException = null;
		try{
			m2.insertFixedConstants(inputs);
		} catch(Exception ex) {
			thrownException = ex;
		}
		assertNotNull(thrownException);

	}

}