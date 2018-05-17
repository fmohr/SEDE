package de.upb.sede.util;

import java.util.Map;
import java.util.function.Function;

public class Maps {

	public static <T,U,V> void translate(Map<T,U> mapFrom, Map<T,V> mapTo, Function<U,V> translator) {
		for(T t : mapFrom.keySet()) {
			U u = mapFrom.get(t);
			V v = translator.apply(u);
			mapTo.put(t, v);
		}
	}
}
