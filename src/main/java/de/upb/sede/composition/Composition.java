package de.upb.sede.composition;

import java.util.Map;

public interface Composition extends Map<String, Operation> {
    	public static Composition fromString(String composition) {
    		throw new RuntimeException("Not implemented!");
    	}
}