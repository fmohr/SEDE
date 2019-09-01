package de.upb.sede;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.immutables.value.Value;

@JsonSerialize
@Value.Style(
    typeAbstract = "I*",
    typeImmutable = "*",
    builder = "builder",
    visibility = Value.Style.ImplementationVisibility.PUBLIC)
public @interface SModelStyle {}
