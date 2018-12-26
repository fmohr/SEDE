package de.upb.sede.entity;

import java.util.Optional;
import java.util.function.Function;

public interface FieldTypeLookup extends Function<String, Optional<String>> {

}
