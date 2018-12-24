package de.upb.sede.entity;

import de.upb.sede.dsl.SecoUtil;
import de.upb.sede.dsl.seco.EntityClassDefinition;
import de.upb.sede.dsl.seco.EntityMethod;
import de.upb.sede.dsl.seco.Entries;
import de.upb.sede.util.FileUtil;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.xtend2.lib.StringConcatenation;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

public class ParseTest {

	File getSecoFilePath(String name) {
		return new File(FileUtil.getPathOfResource("entities/" + name + ".seco"));
	}

	@Test
	public void testDef1() throws IOException {
		final Entries result = SecoUtil.parseSources(getSecoFilePath("Def1"));

		System.out.println("Parsing text: \n" + FileUtil.readFileAsString(getSecoFilePath("Def1").getPath()));

		EntityClassDefinition a =  (result.getEntities().get(0));
		Assert.assertEquals(a.getQualifiedName(), "a.b.C");
		Assert.assertEquals(a.getWrappedEntity(), "d.se.F");
		Assert.assertEquals(a.getBaseEntities(), Arrays.asList("g.H", "a.ai.J"));

		EntityMethod m1 = a.getMethods().get(0);
		EntityMethod m2 = a.getMethods().get(1);

		Assert.assertTrue(EcoreUtil.equals(m1, m2));

		Assert.assertEquals(a.getCasts().get(0).getResultingEntity(), "Some.Other.Entity");

	}

	@Test
	public void testDef1_Def2() throws IOException {
		final Entries result = SecoUtil.parseSources(
				getSecoFilePath("Def1"),
				getSecoFilePath("Def2"));

		EntityClassDefinition a =  (result.getEntities().get(0));
		Assert.assertEquals(a.getQualifiedName(), "a.b.C");
		Assert.assertEquals(a.getWrappedEntity(), "d.se.F");
		Assert.assertEquals(a.getBaseEntities(), Arrays.asList("g.H", "a.ai.J"));

		EntityMethod m1 = a.getMethods().get(0);
		EntityMethod m2 = a.getMethods().get(1);

		Assert.assertTrue(EcoreUtil.equals(m1, m2));

		Assert.assertEquals(a.getCasts().get(0).getResultingEntity(), "Some.Other.Entity");

		Assert.assertEquals(2, result.getEntities().size());

	}


}
