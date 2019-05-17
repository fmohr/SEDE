package de.upb.sede.util;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;

public class KneadForm implements Kneadable, JsonKnibble {

    @JsonIgnore
    private final MutableWobblyField<JsonKnibble> source = MutableWobblyField.empty();

    public void setSource(JsonKnibble newSource) {
        this.source.set(newSource);
    }

    @Override
    public <T> T knead(Class<T> form) {
        return source.orElseThrow(this::noSourceSet).knead(form);
    }

    @Override
    public List<Kneadable> knibbleList(String field) {
        return source.orElseThrow(this::noSourceSet).knibbleList(field);
    }

    @Override
    public Kneadable knibbleObject(String field) {
        return source.orElseThrow(this::noSourceSet).knibbleObject(field);
    }

    @Override
    public Number knibbleNumber(String field) {
        return source.orElseThrow(this::noSourceSet).knibbleNumber(field);
    }

    @Override
    public String knibbleString(String field) {
        return source.orElseThrow(this::noSourceSet).knibbleString(field);
    }

    @Override
    public Boolean knibbleBoolean(String field) {
        return source.orElseThrow(this::noSourceSet).knibbleBoolean(field);
    }

    private IllegalStateException noSourceSet() {
        return new IllegalStateException(this.getClass().getName() + " was not correctly knibbled: No source was set.");
    }

    public <T> void setField(String fieldName, T value) {
        if(source.isPresent()) {
            source.get().setField(fieldName, value);
        }
    }


}
