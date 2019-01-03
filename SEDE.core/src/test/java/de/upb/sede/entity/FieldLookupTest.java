package de.upb.sede.entity;

import de.upb.sede.dsl.seco.Field;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static de.upb.sede.dsl.SecoUtil.*;

public class FieldLookupTest {

	private FieldLookup<String> fieldLookup;

	@Before
	public void setUp() throws Exception {
		fieldLookup = new FieldLookup<>();
	}

	@Test
	public void testDumpLookupNoDereference() {
		Field a = createField("a");
		long versiona1 = fieldLookup.write(a, "a1");
		Assert.assertEquals(0L, versiona1);

		long versiona2 = fieldLookup.write(a, "a2");
		Assert.assertEquals(1L, versiona2);

		Assert.assertEquals("a2", fieldLookup.readLatest(a).get());
		Assert.assertEquals("a1", fieldLookup.read(a, versiona1).get());
		Assert.assertEquals("a2", fieldLookup.read(a, versiona2).get());
		Assert.assertEquals(versiona2, fieldLookup.latestVersion(a).get().longValue());

		versiona1 = fieldLookup.write(a, versiona1, "a3");

		Assert.assertEquals(0L, versiona1);

		Assert.assertEquals("a3", fieldLookup.read(a, versiona1).get());


		long versiona3 = fieldLookup.write(a, "a3");

		long versiona4 = fieldLookup.write(a, "a4");

		long versiona5 = fieldLookup.write(a, "a5");

		Assert.assertEquals(2L, versiona3);
		Assert.assertEquals(3L, versiona4);
		Assert.assertEquals(4L, versiona5);
		Assert.assertEquals("a5", fieldLookup.readLatest(a).get());

		Assert.assertFalse(fieldLookup.readLatest(createField("b")).isPresent());
		Assert.assertFalse(fieldLookup.read(createField("b"), 1L).isPresent());


		fieldLookup.write(createField("b"), "");
		fieldLookup.write(createField("b"), "");
		fieldLookup.write(createField("c"), "");
		fieldLookup.write(createField("d"), "");
		fieldLookup.write(createField("e"), "");
		fieldLookup.write(createField("b"), "");
		fieldLookup.write(createField("f"), "");
		fieldLookup.write(createField("a"), 7L, "a7");

		Assert.assertEquals(7L, fieldLookup.latestVersion(createField("a")).get().longValue());
		Assert.assertEquals("a7", fieldLookup.readLatest(createField("a")).get());
		Assert.assertEquals("a2", fieldLookup.read(createField("a"), 1L).get());

		Assert.assertEquals(2L, fieldLookup.latestVersion(createField("b")).get().longValue());
		Assert.assertEquals("", fieldLookup.read(createField("b"), 0L).get());
		Assert.assertEquals("", fieldLookup.read(createField("b"), 1L).get());
		Assert.assertEquals("", fieldLookup.read(createField("b"), 2L).get());
		Assert.assertEquals("", fieldLookup.readLatest(createField("b")).get());
	}

	@Test
	public void testDumpLookupDereference() throws IOException {
		Field a = createField("field.A");
		Field ab = createField("field.A", "b");
		Field abc = createField("field.A", "b", "c");
		Field ac = createField("field.A", "c");

		Assert.assertFalse(fieldLookup.readLatest(a).isPresent());
		Assert.assertFalse(fieldLookup.readLatest(ab).isPresent());
		Assert.assertFalse(fieldLookup.readLatest(abc).isPresent());


		long a0 = fieldLookup.write(a, "a0");
		Assert.assertEquals(0L, a0);

		long ab0 = fieldLookup.write(ab, "ab0");
		Assert.assertEquals(0L, ab0);

		Assert.assertEquals("a0", fieldLookup.readLatest(a).get());
		Assert.assertEquals("ab0", fieldLookup.readLatest(ab).get());
		Assert.assertEquals(a0, fieldLookup.latestVersion(a).get().longValue());
		Assert.assertEquals(ab0, fieldLookup.latestVersion(ab).get().longValue());


		long a1 = fieldLookup.write(a, "a1");
		Assert.assertEquals(1L, a1);

		Assert.assertEquals("a1", fieldLookup.readLatest(a).get());
		Assert.assertEquals("ab0", fieldLookup.readLatest(ab).get());
		Assert.assertEquals(a1, fieldLookup.latestVersion(a).get().longValue());
		Assert.assertEquals(ab0, fieldLookup.latestVersion(ab).get().longValue());



		long abc0 = fieldLookup.write(abc, "abc0");
		Assert.assertEquals(0L, abc0);

		Assert.assertEquals("a1", fieldLookup.readLatest(a).get());
		Assert.assertEquals("ab0", fieldLookup.readLatest(ab).get());
		Assert.assertEquals("abc0", fieldLookup.readLatest(abc).get());
		Assert.assertEquals(a1, fieldLookup.latestVersion(a).get().longValue());
		Assert.assertEquals(ab0, fieldLookup.latestVersion(ab).get().longValue());
		Assert.assertEquals(abc0, fieldLookup.latestVersion(abc).get().longValue());


		long ac0 = fieldLookup.write(ac, "ac0");
		Assert.assertEquals(0L, ac0);

		Assert.assertEquals("a1", fieldLookup.readLatest(a).get());
		Assert.assertEquals("ab0", fieldLookup.readLatest(ab).get());
		Assert.assertEquals("abc0", fieldLookup.readLatest(abc).get());
		Assert.assertEquals(a1, fieldLookup.latestVersion(a).get().longValue());
		Assert.assertEquals(ab0, fieldLookup.latestVersion(ab).get().longValue());
		Assert.assertEquals(abc0, fieldLookup.latestVersion(abc).get().longValue());

		long a5 = fieldLookup.write(a, 5L, "a5");
		Assert.assertEquals(5L, a5);
		Assert.assertEquals("a5", fieldLookup.readLatest(a).get());
		Assert.assertEquals("ab0", fieldLookup.readLatest(ab).get());
		Assert.assertEquals("abc0", fieldLookup.readLatest(abc).get());
		Assert.assertEquals(a5, fieldLookup.latestVersion(a).get().longValue());
		Assert.assertEquals(ab0, fieldLookup.latestVersion(ab).get().longValue());
		Assert.assertEquals(abc0, fieldLookup.latestVersion(abc).get().longValue());


		long a6 = fieldLookup.write(a,  "a6");
		Assert.assertEquals(6L, a6);
		Assert.assertEquals("a6", fieldLookup.readLatest(a).get());
		Assert.assertEquals("ab0", fieldLookup.readLatest(ab).get());
		Assert.assertEquals("abc0", fieldLookup.readLatest(abc).get());
		Assert.assertEquals(a6, fieldLookup.latestVersion(a).get().longValue());
		Assert.assertEquals(ab0, fieldLookup.latestVersion(ab).get().longValue());
		Assert.assertEquals(abc0, fieldLookup.latestVersion(abc).get().longValue());

	}


	@Test
	public void testDelete1() {
		Field a = createField("a");
		long a0 = fieldLookup.write(a, "A");
		fieldLookup.deleteLatest(a);
		Assert.assertFalse(fieldLookup.readLatest(a).isPresent());
		Assert.assertFalse(fieldLookup.latestVersion(a).isPresent());
		Assert.assertFalse(fieldLookup.read(a, a0).isPresent());
	}

	@Test
	public void testDelete2() {
		Field a = createField("a");
		long a0 = fieldLookup.write(a, "A");
		long a1 = fieldLookup.write(a, "A2");
		fieldLookup.delete(a, a0);
		Assert.assertEquals("A2", fieldLookup.readLatest(a).get());
		Assert.assertEquals(a1, (long)fieldLookup.latestVersion(a).get());
		Assert.assertFalse(fieldLookup.read(a, a0).isPresent());
		fieldLookup.delete(a, a1);
		Assert.assertFalse(fieldLookup.readLatest(a).isPresent());
		Assert.assertFalse(fieldLookup.latestVersion(a).isPresent());
		Assert.assertFalse(fieldLookup.read(a, a0).isPresent());
	}

	@Test
	public void testDelete3() {
		Field a = createField("a");
		Field b = createField("a", "b");
		long b0 = fieldLookup.write(b, "B");
		long a0 = fieldLookup.write(a, "A");
		long a1 = fieldLookup.write(a, "A2");

		fieldLookup.delete(a, a0);
		Assert.assertEquals("A2", fieldLookup.readLatest(a).get());
		Assert.assertEquals(a1, (long)fieldLookup.latestVersion(a).get());
		Assert.assertFalse(fieldLookup.read(a, a0).isPresent());
		Assert.assertTrue(fieldLookup.read(b, b0).isPresent());
		Assert.assertFalse(fieldLookup.read(b, 2L).isPresent());

		fieldLookup.delete(a, a1);
		long b1 = fieldLookup.write(b, "B2");

		Assert.assertFalse(fieldLookup.readLatest(a).isPresent());
		Assert.assertFalse(fieldLookup.latestVersion(a).isPresent());
		Assert.assertFalse(fieldLookup.read(a, a0).isPresent());
		Assert.assertTrue(fieldLookup.read(b, b0).isPresent());
		Assert.assertFalse(fieldLookup.read(b, 2L).isPresent());
		Assert.assertEquals("B2", fieldLookup.readLatest(b).get());
	}

	@Test
	public void testDelete4() {
		Field a = createField("a");
		Field ab = createField("a", "b");
		Field abc = createField("a", "b", "c");

		long abc0 = fieldLookup.write(abc, "ABC");
		long a0 = fieldLookup.write(a, "A");
		long a1 = fieldLookup.write(a, "A1");
		long ab0 = fieldLookup.write(ab, "A1");

		Assert.assertTrue(fieldLookup.read(a, a0).isPresent());
		Assert.assertTrue(fieldLookup.read(a, a1).isPresent());
		Assert.assertTrue(fieldLookup.readLatest(a).isPresent());

		Assert.assertTrue(fieldLookup.read(ab, ab0).isPresent());
		Assert.assertTrue(fieldLookup.readLatest(ab).isPresent());

		Assert.assertTrue(fieldLookup.read(abc, abc0).isPresent());
		Assert.assertTrue(fieldLookup.readLatest(abc).isPresent());

		fieldLookup.deleteLatest(a);

		Assert.assertTrue(fieldLookup.read(a, a0).isPresent());
		Assert.assertFalse(fieldLookup.read(a, a1).isPresent());
		Assert.assertTrue(fieldLookup.readLatest(a).isPresent());

		Assert.assertTrue(fieldLookup.read(ab, ab0).isPresent());
		Assert.assertTrue(fieldLookup.readLatest(ab).isPresent());

		Assert.assertTrue(fieldLookup.read(abc, abc0).isPresent());
		Assert.assertTrue(fieldLookup.readLatest(abc).isPresent());


		fieldLookup.deleteLatest(ab);

		Assert.assertTrue(fieldLookup.read(a, a0).isPresent());
		Assert.assertFalse(fieldLookup.read(a, a1).isPresent());
		Assert.assertTrue(fieldLookup.readLatest(a).isPresent());

		Assert.assertFalse(fieldLookup.read(ab, ab0).isPresent());
		Assert.assertFalse(fieldLookup.readLatest(ab).isPresent());

		Assert.assertTrue(fieldLookup.read(abc, abc0).isPresent());
		Assert.assertTrue(fieldLookup.readLatest(abc).isPresent());


		fieldLookup.deleteLatest(a);

		Assert.assertFalse(fieldLookup.read(a, a0).isPresent());
		Assert.assertFalse(fieldLookup.read(a, a1).isPresent());
		Assert.assertFalse(fieldLookup.readLatest(a).isPresent());

		Assert.assertFalse(fieldLookup.read(ab, ab0).isPresent());
		Assert.assertFalse(fieldLookup.readLatest(ab).isPresent());

		Assert.assertTrue(fieldLookup.read(abc, abc0).isPresent());
		Assert.assertTrue(fieldLookup.readLatest(abc).isPresent());

		fieldLookup.deleteLatest(a);

		Assert.assertFalse(fieldLookup.read(a, a0).isPresent());
		Assert.assertFalse(fieldLookup.read(a, a1).isPresent());
		Assert.assertFalse(fieldLookup.readLatest(a).isPresent());

		Assert.assertFalse(fieldLookup.read(ab, ab0).isPresent());
		Assert.assertFalse(fieldLookup.readLatest(ab).isPresent());

		Assert.assertTrue(fieldLookup.read(abc, abc0).isPresent());
		Assert.assertTrue(fieldLookup.readLatest(abc).isPresent());

		fieldLookup.deleteLatest(abc);

		Assert.assertFalse(fieldLookup.read(a, a0).isPresent());
		Assert.assertFalse(fieldLookup.read(a, a1).isPresent());
		Assert.assertFalse(fieldLookup.readLatest(a).isPresent());

		Assert.assertFalse(fieldLookup.read(ab, ab0).isPresent());
		Assert.assertFalse(fieldLookup.readLatest(ab).isPresent());

		Assert.assertFalse(fieldLookup.read(abc, abc0).isPresent());
		Assert.assertFalse(fieldLookup.readLatest(abc).isPresent());
	}

	@Test
	public void testLinking() {
		Field a = createField("a");
		Field ab = createField("a", "b");
		Field abc = createField("a", "b", "c");

		long abc0 = fieldLookup.write(abc, "ABC");
		long a0 = fieldLookup.write(a, "A");
		long a1 = fieldLookup.write(a, "A1");
		long ab0 = fieldLookup.write(ab, "AB");
		long ab1 = fieldLookup.write(ab, "AB1");


		Field ad = createField("a", "d");

		Assert.assertFalse(fieldLookup.lookup(ad).isPresent());

		fieldLookup.link(ad, fieldLookup.lookup(abc).get());

		Assert.assertTrue(fieldLookup.lookup(ad).isPresent());

		Assert.assertEquals("ABC", fieldLookup.readLatest(ad).get());

		long abc1 = fieldLookup.write(abc, "ABC1");

		Assert.assertEquals("ABC", fieldLookup.read(ad, abc0).get());
		Assert.assertEquals("ABC1", fieldLookup.read(ad, abc1).get());
		Assert.assertEquals("ABC1", fieldLookup.readLatest(ad).get());

		Assert.assertEquals("ABC", fieldLookup.read(abc, abc0).get());
		Assert.assertEquals("ABC1", fieldLookup.read(abc, abc1).get());
		Assert.assertEquals("ABC1", fieldLookup.readLatest(abc).get());


		long ad0 = fieldLookup.write(ad, "AD");
		Assert.assertEquals("ABC", fieldLookup.read(ad, abc0).get());
		Assert.assertEquals("ABC1", fieldLookup.read(ad, abc1).get());
		Assert.assertEquals("AD", fieldLookup.read(ad, ad0).get());
		Assert.assertEquals("AD", fieldLookup.readLatest(ad).get());

		Assert.assertEquals("ABC", fieldLookup.read(abc, abc0).get());
		Assert.assertEquals("ABC1", fieldLookup.read(abc, abc1).get());
		Assert.assertEquals("AD", fieldLookup.read(abc, ad0).get());
		Assert.assertEquals("AD", fieldLookup.readLatest(abc).get());


		fieldLookup.link(abc, fieldLookup.lookup(ad).get());

		Assert.assertEquals("ABC", fieldLookup.read(ad, abc0).get());
		Assert.assertEquals("ABC1", fieldLookup.read(ad, abc1).get());
		Assert.assertEquals("AD", fieldLookup.read(ad, ad0).get());
		Assert.assertEquals("AD", fieldLookup.readLatest(ad).get());

		Assert.assertEquals("ABC", fieldLookup.read(abc, abc0).get());
		Assert.assertEquals("ABC1", fieldLookup.read(abc, abc1).get());
		Assert.assertEquals("AD", fieldLookup.read(abc, ad0).get());
		Assert.assertEquals("AD", fieldLookup.readLatest(abc).get());


		Field abd = createField("a", "b", "d");
		Field abcd = createField("a", "b", "c", "d");
		fieldLookup.write(abd, "ABD");
		fieldLookup.write(abcd, "ABCD");

		fieldLookup.link(ab, fieldLookup.lookup(abc).get());


		Assert.assertEquals("ABC", fieldLookup.read(ab, abc0).get());
		Assert.assertEquals("ABC1", fieldLookup.read(ab, abc1).get());
		Assert.assertEquals("AD", fieldLookup.read(ab, ad0).get());
		Assert.assertEquals("AD", fieldLookup.readLatest(ab).get());

		Assert.assertEquals("ABC", fieldLookup.read(ad, abc0).get());
		Assert.assertEquals("ABC1", fieldLookup.read(ad, abc1).get());
		Assert.assertEquals("AD", fieldLookup.read(ad, ad0).get());
		Assert.assertEquals("AD", fieldLookup.readLatest(ad).get());

		Assert.assertFalse(fieldLookup.read(abc, abc0).isPresent());
		Assert.assertFalse(fieldLookup.read(abc, abc1).isPresent());
		Assert.assertFalse(fieldLookup.read(abc, ad0).isPresent());
		Assert.assertFalse(fieldLookup.readLatest(abc).isPresent());

		Assert.assertTrue(fieldLookup.readLatest(abd).isPresent());
		Assert.assertEquals("ABCD", fieldLookup.readLatest(abd).get());
	}
}