package de.upb.sede.entity;

import de.upb.sede.config.ClassesConfig;
import de.upb.sede.dsl.SecoUtil;
import de.upb.sede.dsl.seco.EntityMethod;
import de.upb.sede.dsl.seco.EntityMethodParam;
import de.upb.sede.dsl.seco.EntityMethodParamSignature;
import de.upb.sede.util.FileUtil;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.theories.ParameterSignature;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

public class ClassLinTest {
	ClassLinearization cl;

	@Before
	public void setUp() throws Exception {
		cl = new ClassLinearization();
		cl.add(SecoUtil.parseSources(getSecoFile("ABC")));
	}

	@After
	public void tearDown() throws Exception {
	}

	File getSecoFile(String name) {
		return new File(FileUtil.getPathOfResource("entities/" + name + ".seco"));
	}


	@Test
	public void appendEntitiesTest() throws IOException {
		ClassLinearization cl = new ClassLinearization();
		assertFalse(cl.isKnown("A"));

		cl.add(SecoUtil.parseSources(getSecoFile("ABC")));

		assertTrue(cl.isKnown("A"));
		assertTrue(cl.isKnown("B"));
		assertTrue(cl.isKnown("C"));

		cl.add(SecoUtil.parseSources(getSecoFile("DEF")));

		assertTrue(cl.isKnown("D"));
		assertTrue(cl.isKnown("_E"));
		assertTrue(cl.isKnown("F"));

		cl.add(SecoUtil.parseSources(getSecoFile("A")));
	}


	@Test
	public void isWrapped() {
		assertFalse(cl.entityView("A").isWrapper());
		assertTrue(cl.entityView("B").isWrapper());
		assertFalse(cl.entityView("C").isWrapper());
	}

	@Test
	public void classpath() {
		assertEquals("A", cl.entityView("A").targetEntityName());
		assertEquals("A", cl.entityView("B").targetEntityName());
	}

	@Test
	public void testMethodsA() {
		ClassView A = cl.entityView("A");
		testMethodsA(A);
	}

	@Test
	public void testMethodsB() {
		ClassView B = cl.entityView("B");
		testMethodsA(B);
	}

	public void testMethodsA(ClassView classA) {

		EntityMethod querry;
		EntityMethodParamSignature signature;
		EntityMethodParam param;

		querry = new EntityMethod();
		querry.setMethodName("m1");
		signature = new EntityMethodParamSignature();
		param = new EntityMethodParam();
		param.setParameterType("t1");
		signature.getParameters().add(param);
		param = new EntityMethodParam();
		param.setParameterType("t2");
		signature.getParameters().add(param);
		param = new EntityMethodParam();
		param.setParameterType("t1");
		signature.getOutputs().add(param);
		querry.setParamSignature(signature);

		MethodView m1 = classA.resolveMethod(querry).get();

		querry = new EntityMethod();
		querry.setMethodName("m2");
		signature = new EntityMethodParamSignature();
		param = new EntityMethodParam();
		param.setParameterType("t1");
		signature.getParameters().add(param);
		param = new EntityMethodParam();
		param.setParameterType("t2");
		signature.getParameters().add(param);
		param = new EntityMethodParam();
		param.setParameterType("t3");
		signature.getParameters().add(param);
		querry.setParamSignature(signature);

		MethodView m2 = classA.resolveMethod(querry).get();

		querry = new EntityMethod();
		querry.setMethodName("m3");
		querry.setParamSignature(new EntityMethodParamSignature());

		MethodView m3 = classA.resolveMethod(querry).get();


		querry = new EntityMethod();
		querry.setMethodName("m4");
		querry.setParamSignature(new EntityMethodParamSignature());

		MethodView m4 = classA.resolveMethod(querry).get();

		assertFalse(m1.outputParams().isEmpty());
		assertTrue(m2.outputParams().isEmpty());
		assertTrue(m3.outputParams().isEmpty());
		assertTrue(m4.outputParams().isEmpty());

		assertEquals("t1", m1.outputParams().get(0).getParameterType());

		assertEquals(2, m1.inputParams().size());
		assertEquals(3, m2.inputParams().size());
		assertEquals(0, m2.outputParams().size());
		assertEquals(0, m3.outputParams().size());

		assertFalse(m1.isPure());
		assertTrue(m2.isPure());
		assertTrue(m3.isPure());
		assertFalse(m4.isPure());

		assertFalse(m1.isStatic());
		assertFalse(m2.isStatic());
		assertTrue(m3.isStatic());
		assertFalse(m4.isStatic());

		assertEquals("t2", m1.inputParams().get(1).getParameterType());
		assertEquals("t2", m2.inputParams().get(1).getParameterType());

		assertFalse(m1.inputParams().get(1).isFinal());
		assertTrue( m2.inputParams().get(2).isFinal());
	}



}
