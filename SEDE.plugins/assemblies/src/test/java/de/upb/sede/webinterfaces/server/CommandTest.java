package de.upb.sede.webinterfaces.server;

import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;
import static de.upb.sede.webinterfaces.server.CommandTree.node;

public class CommandTest {

	@Test
	public void test1() {
		Command c = new Command.Strings("Hallo", "Bello");
		assertTrue(c.matches(arr("Hallo")));
		assertFalse(c.matches(arr("Hallos")));
		assertFalse(c.matches(arr("Hall")));
		assertTrue(c.matches(arr("Hallo", "123")));
		assertFalse(c.matches(arr("Halloa", "123")));

		assertEquals(c.match(arr("Hallo", "123")).getMatch(), "Hallo");
		assertArrayEquals(c.match(arr("Hallo", "123")).getRest(), arr("123"));


		Command d = new Command.File();
		assertTrue(c.matches(arr("Hallo")));
		assertEquals(c.match(arr("Hallo", "123")).getMatch(), "Hallo");
		assertArrayEquals(c.match(arr("Hallo", "123")).getRest(), arr("123"));


		Command c1 = new Command.Strings("Hello", "Hi");

		CommandTree e = new CommandTree(
				node(new Command.Strings(false, "Say"),
						node(new Command.Strings("DE", "GER"),
								node(c)
						),
						node(new Command.Strings("ENG"),
								node(c1)
						)
				)
			);

		assertTrue(e.matches(arr("say", "ENG", "Hello")));
		assertTrue(e.matches(arr("say", "ENG", "Hi")));
		assertTrue(e.matches(arr("SAY", "DE", "Hallo")));
		assertTrue(e.matches(arr("say", "GER", "Bello")));


		assertFalse(e.matches(arr("saz", "DE", "Bello")));
		assertFalse(e.matches(arr("say", "GEM", "Bello")));
		assertFalse(e.matches(arr("say", "DE", "Hello")));
		assertFalse(e.matches(arr("say", "ENG", "Hallo")));

		List<Command.Match> matches = e.createMatches(arr("say", "ENG", "Hello"));
		assertEquals(matches.size(), 3);
		assertEquals(matches.get(0).getMatch(), "say");
		assertArrayEquals(matches.get(0).getRest(), arr("ENG", "Hello"));
		assertEquals(matches.get(1).getMatch(), "ENG");
		assertArrayEquals(matches.get(1).getRest(), arr( "Hello"));
		assertEquals(matches.get(2).getMatch(), "Hello");
		assertArrayEquals(matches.get(2).getRest(), arr( ));
	}
	private static String[] arr(String... strings) {
		return strings;
	}

}
