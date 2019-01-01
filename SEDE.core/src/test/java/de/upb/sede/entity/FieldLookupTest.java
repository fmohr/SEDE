package de.upb.sede.entity;

import de.upb.sede.dsl.seco.Field;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static de.upb.sede.dsl.SecoUtil.*;

public class FieldLookupTest {

	private FieldLookup<String> fieldLookup;

	@Before
	public void setUp() throws Exception {
		fieldLookup = new FieldLookup<>();
	}

	@Test
	public void testDumpLookupNoDereference() {
		Field a = createField("field.A");
		long versiona1 = fieldLookup.dump(a, "a1");
		Assert.assertEquals(0L, versiona1);

		long versiona2 = fieldLookup.dump(a, "a2");
		Assert.assertEquals(1L, versiona2);

		Assert.assertEquals("a2", fieldLookup.lookupLatest(a).get());
		Assert.assertEquals("a1", fieldLookup.lookup(a, versiona1).get());
		Assert.assertEquals("a2", fieldLookup.lookup(a, versiona2).get());
		Assert.assertEquals(versiona2, fieldLookup.latestVersion(a).get().longValue());

		versiona1 = fieldLookup.dump(a, versiona1, "a3");

		Assert.assertEquals(0L, versiona1);

		Assert.assertEquals("a3", fieldLookup.lookup(a, versiona1).get());


		long versiona3 = fieldLookup.dump(a, "a3");

		long versiona4 = fieldLookup.dump(a, "a4");

		long versiona5 = fieldLookup.dump(a, "a5");

		Assert.assertEquals(2L, versiona3);
		Assert.assertEquals(3L, versiona4);
		Assert.assertEquals(4L, versiona5);
		Assert.assertEquals("a5", fieldLookup.lookupLatest(a).get());

		Assert.assertFalse(fieldLookup.lookupLatest(createField("b")).isPresent());
		Assert.assertFalse(fieldLookup.lookup(createField("b"), 1L).isPresent());
	}

	@Test
	public void testDumpLookupDereference() {
		Field a = createField("field.A");
		Field ab = createField("field.A", "b");
		Field abc = createField("field.A", "b", "c");
		Field ac = createField("field.A", "c");


		Assert.assertFalse(fieldLookup.lookupLatest(a).isPresent());
		Assert.assertFalse(fieldLookup.lookupLatest(ab).isPresent());
		Assert.assertFalse(fieldLookup.lookupLatest(abc).isPresent());


		long a0 = fieldLookup.dump(a, "a0");
		Assert.assertEquals(0L, a0);

		long ab0 = fieldLookup.dump(ab, "ab0");
		Assert.assertEquals(0L, ab0);

		Assert.assertEquals("a0", fieldLookup.lookupLatest(a).get());
		Assert.assertEquals("ab0", fieldLookup.lookupLatest(ab).get());
		Assert.assertEquals(a0, fieldLookup.latestVersion(a).get().longValue());
		Assert.assertEquals(ab0, fieldLookup.latestVersion(ab).get().longValue());


		long a1 = fieldLookup.dump(a, "a1");
		Assert.assertEquals(1L, a1);

		Assert.assertEquals("a1", fieldLookup.lookupLatest(a).get());
		Assert.assertEquals("ab0", fieldLookup.lookupLatest(ab).get());
		Assert.assertEquals(a1, fieldLookup.latestVersion(a).get().longValue());
		Assert.assertEquals(ab0, fieldLookup.latestVersion(ab).get().longValue());



		long abc0 = fieldLookup.dump(abc, "abc0");
		Assert.assertEquals(0L, abc0);

		Assert.assertEquals("a1", fieldLookup.lookupLatest(a).get());
		Assert.assertEquals("ab0", fieldLookup.lookupLatest(ab).get());
		Assert.assertEquals("abc0", fieldLookup.lookupLatest(abc).get());
		Assert.assertEquals(a1, fieldLookup.latestVersion(a).get().longValue());
		Assert.assertEquals(ab0, fieldLookup.latestVersion(ab).get().longValue());
		Assert.assertEquals(abc0, fieldLookup.latestVersion(abc).get().longValue());


		long ac0 = fieldLookup.dump(ac, "ac0");
		Assert.assertEquals(0L, ac0);

		Assert.assertEquals("a1", fieldLookup.lookupLatest(a).get());
		Assert.assertEquals("ab0", fieldLookup.lookupLatest(ab).get());
		Assert.assertEquals("abc0", fieldLookup.lookupLatest(abc).get());
		Assert.assertEquals(a1, fieldLookup.latestVersion(a).get().longValue());
		Assert.assertEquals(ab0, fieldLookup.latestVersion(a).get().longValue());
		Assert.assertEquals(abc0, fieldLookup.latestVersion(ab).get().longValue());

		long a5 = fieldLookup.dump(a, 5L, "a5");
		Assert.assertEquals(5L, a5);
		Assert.assertEquals("a5", fieldLookup.lookupLatest(a).get());
		Assert.assertEquals("ab0", fieldLookup.lookupLatest(ab).get());
		Assert.assertEquals("abc0", fieldLookup.lookupLatest(abc).get());
		Assert.assertEquals(a5, fieldLookup.latestVersion(a).get().longValue());
		Assert.assertEquals(ab0, fieldLookup.latestVersion(a).get().longValue());
		Assert.assertEquals(abc0, fieldLookup.latestVersion(ab).get().longValue());


		long a6 = fieldLookup.dump(a,  "a6");
		Assert.assertEquals(6L, a6);
		Assert.assertEquals("a6", fieldLookup.lookupLatest(a).get());
		Assert.assertEquals("ab0", fieldLookup.lookupLatest(ab).get());
		Assert.assertEquals("abc0", fieldLookup.lookupLatest(abc).get());
		Assert.assertEquals(a6, fieldLookup.latestVersion(a).get().longValue());
		Assert.assertEquals(ab0, fieldLookup.latestVersion(a).get().longValue());
		Assert.assertEquals(abc0, fieldLookup.latestVersion(ab).get().longValue());

	}
}