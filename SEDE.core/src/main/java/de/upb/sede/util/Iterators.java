package de.upb.sede.util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Iterators {
	public static <T> List<T> TO_LIST(Iterator<T> iterator) {
		List<T> list = new ArrayList<>();
		iterator.forEachRemaining(list::add);
		return list;
	}
}
