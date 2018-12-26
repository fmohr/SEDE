package de.upb.sede.entity;

import de.upb.sede.dsl.seco.Field;

import java.util.Optional;
import java.util.function.Function;

public interface FieldTypeLookup extends Function<Field, Optional<String>> {

}
