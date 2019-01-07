package de.upb.sede.entity;

import de.upb.sede.dsl.SecoUtil;
import de.upb.sede.dsl.seco.*;
import de.upb.sede.util.FileUtil;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;
import static de.upb.sede.dsl.SecoUtil.*;

public class ClassLinTest {
	ClassLinearization cl;

	@Before
	public void setUp() throws Exception {
		cl = new ClassLinearization();
		cl.add(SecoUtil.parseSources(getSecoFile("ABC")));
		cl.add(SecoUtil.parseSources(getSecoFile("animals")));
		cl.add(SecoUtil.parseSources(getSecoFile("builtin")));
		cl.add(SecoUtil.parseSources(getSecoFile("TypeCasting")));
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
		assertFalse(cl.classView("A").isWrapper());
		assertTrue(cl.classView("B").isWrapper());
		assertFalse(cl.classView("C").isWrapper());
	}

	@Test
	public void classpath() {
		assertEquals("A", cl.classView("A").targetEntityName());
		assertEquals("A", cl.classView("B").targetEntityName());
	}

	@Test
	public void testMethodsA() {
		ClassView classA = cl.classView("A");
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

	@Test
	public void testMethodsB() {
		ClassView classB = cl.classView("B");
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

		MethodView m1 = classB.resolveMethod(querry).get();

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

		MethodView m2 = classB.resolveMethod(querry).get();

		querry = new EntityMethod();
		querry.setMethodName("m3");
		querry.setParamSignature(new EntityMethodParamSignature());

		MethodView m3 = classB.resolveMethod(querry).get();


		querry = new EntityMethod();
		querry.setMethodName("m4");
		querry.setParamSignature(new EntityMethodParamSignature());

		MethodView m4 = classB.resolveMethod(querry).get();

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

	@Test
	public void testAllParents() {
		assertEquals(
				Arrays.asList(
						cl.classView("Bird"),
						cl.classView("CanFly"),
						cl.classView("Animal")),
				cl.classView("Goose").allParents());
		assertEquals(
				Arrays.asList(
						cl.classView("Bird"),
						cl.classView("CanFly")),
				cl.classView("Goose").declaredParents());

		assertEquals(
				Arrays.asList(
						cl.classView("Bird"),
						cl.classView("CanFly"),
						cl.classView("Animal")),
				cl.classView("Duck").allParents());
		assertEquals(
				Arrays.asList(
						cl.classView("Bird"),
						cl.classView("CanFly")),
				cl.classView("Duck").declaredParents());

		assertEquals(
				Arrays.asList(
						cl.classView("Mammal"),
						cl.classView("CanFly"),
						cl.classView("Animal")),
				cl.classView("Bat").allParents());
		assertEquals(
				Arrays.asList(
						cl.classView("Mammal"),
						cl.classView("CanFly")),
				cl.classView("Bat").declaredParents());

		assertEquals(
				Arrays.asList(
						cl.classView("Primate"),
						cl.classView("Mammal"),
						cl.classView("CanClimb"),
						cl.classView("Animal")),
				cl.classView("Human").allParents());
		assertEquals(
				Collections.singletonList(
						cl.classView("Primate")),
				cl.classView("Human").declaredParents());

	}

	@Test
	public void testCastGraph() {
		List<ClassCastPath> a ;
 		a = cl.entityCast().querry("A", "B");
		assertTrue(a.isEmpty());

		a = cl.entityCast().querry("BA", "B");
		assertEquals(1, a.size());

		a = cl.entityCast().querry("BA", "ABC");
		assertEquals(0, a.size());

		a = cl.entityCast().querry("BA", "A");
		assertEquals(1, a.size());

		a = cl.entityCast().querry("BA", "AB");
		assertEquals(1, a.size());

		a = cl.entityCast().querry("BAC", "BAC");
		assertEquals(1, a.size());

		a = cl.entityCast().querry("BAC", "ABC");
		assertEquals(1, a.size());

		a = cl.entityCast().querry("BAC", "A");
		assertEquals(3, a.size());

		a = cl.entityCast().querry("BAC", "O");
		assertEquals(6, a.size());
	}



	@Test
	public void testResolveOp1() {
		Operation op1 = createOp
		op1.getArgs();
		cl.resolveOperation(op1, null);
	}


}
