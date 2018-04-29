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
	
	@Test
	public void testNidiGraphviz() throws IOException {
		Node n1 = node("1").with(Label.of("A"));
		Node n2 = node("2").with(Label.of("A"));
		Node n3 = node("3").with(Label.of("A"));
		n3.port("a");
		Graph g = graph("Graph_name").directed();
		g = g.with(n1).with(n2).with(n3);
		g = g.with(n1.link(n2), n2.link(n3));
		Graphviz.fromGraph(g).width(200).render(Format.SVG).toFile(new File("testout/ex1.svg"));
	}
}
