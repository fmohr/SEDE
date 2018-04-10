package de.upb.sede.util;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

public class DefaultMapTest {

	@Test
	public void testGet() {
		DefaultMap<Integer, List<String>> defaultMap = new DefaultMap<>(ArrayList::new);
		
		Assert.assertNotNull(defaultMap.get(1));
		Assert.assertNotNull(defaultMap.get(0));
		
		defaultMap.put(2, new ArrayList<>());
		Assert.assertNotNull(defaultMap.get(2));
		
		
		ArrayList<String> someList = new ArrayList<>();
		someList.add("abc");
		someList.add("def");
		someList.add("ghi");
		
		Assert.assertFalse(defaultMap.get(3).equals(someList));
		defaultMap.put(4, someList);
		Assert.assertEquals(someList, defaultMap.get(4));
		Assert.assertFalse(defaultMap.get(3).equals(defaultMap.get(4)));
		Assert.assertFalse(defaultMap.get(5).equals(defaultMap.get(4)));
	}

}
