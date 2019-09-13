package de.upb.sede;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.immutables.value.Value;

@JsonSerialize
@Value.Style(
    typeAbstract = "I*",
    typeImmutable = "*",
    typeModifiable = "Mutable*",
    builder = "builder",
    visibility = Value.Style.ImplementationVisibility.PUBLIC)
public @interface SModelStyle {}
