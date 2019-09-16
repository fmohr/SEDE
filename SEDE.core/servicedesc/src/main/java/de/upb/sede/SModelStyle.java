package de.upb.sede;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.immutables.value.Value;

import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@JsonSerialize
@Value.Style(
    typeAbstract = "I*",
    typeImmutable = "*",
    typeModifiable = "Mutable*",
    builder = "builder",
    visibility = Value.Style.ImplementationVisibility.PUBLIC)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface SModelStyle {}
