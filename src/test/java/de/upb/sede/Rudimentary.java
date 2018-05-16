package de.upb.sede;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;

import org.junit.BeforeClass;
/*
 * A rudimentary test case.
 */
import org.junit.Test;

import guru.nidi.graphviz.attribute.Label;
import guru.nidi.graphviz.engine.Format;
import guru.nidi.graphviz.engine.Graphviz;
import guru.nidi.graphviz.model.Graph;
import guru.nidi.graphviz.model.Node;

import static guru.nidi.graphviz.model.Factory.*;

public class Rudimentary {

	@Test
	public void testTrue() {
		assertTrue("abc".equalsIgnoreCase("ABC"));
	}
	
}
