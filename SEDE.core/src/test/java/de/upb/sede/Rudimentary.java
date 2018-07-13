package de.upb.sede;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.BeforeClass;
/*
 * A rudimentary test case.
 */
import org.junit.Test;

public class Rudimentary {

	private final static Logger logger = LogManager.getLogger();

	@Test
	public void testTrue() {
		assertTrue("abc".equalsIgnoreCase("ABC"));
	}

	@Test
	public void testStaticReflection() {
		logger.info("hello {}!", 1);
		for (Method m : A.class.getDeclaredMethods()) {
			System.out.println(m.getName());
		}
	}

}
class A {
	public  int a(int a, int b){
		return a+b;
	}
	public double a(double a, double b){
		return a-b;
	}
}
