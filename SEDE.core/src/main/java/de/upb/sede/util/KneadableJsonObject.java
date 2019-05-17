package de.upb.sede.util;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public final class KneadableJsonObject implements Kneadable, JsonKnibble {

    private static final ObjectMapper _MAPPER = new ObjectMapper()
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    private final WobblyField<Map> data;

    public KneadableJsonObject(Map data) {
        this.data = WobblyField.of(data);
    }

    public static KneadableJsonObject fromJson(String jsonString) {
        try {
            JSONParser parser = new JSONParser();
            return new KneadableJsonObject((Map) parser.parse(jsonString));
        } catch (Exception ex) {
            throw new NotKneadableException(ex);
        }
    }

    private <T> void setSource(T kneaded){
        if(kneaded instanceof KneadForm) {
            ((KneadForm) kneaded).setSource(this);
        }
    }


    @Override
    public <T> T knead(Class<T> form) {
        if(form.isInstance(this)) {
            return (T) this;
        }
        T kneaded;
        try {
            kneaded = _MAPPER.convertValue(getData(), form);
        } catch(Exception ex) {
            throw new NotKneadableException("Cannot knead into form " + form.getName(), ex);
        }
        setSource(kneaded);
        return kneaded;
    }

    @Override
    public List<Kneadable> knibbleList(String field) {
        if(! getData().containsKey(field)) {
            throw new IllegalArgumentException("Data does not have field '"
                    + field + "'." );
        }
        if(!(getData().get(field) instanceof List)) {
            throw new IllegalArgumentException("Data type of field " + field + " is not a list.");
        }
        List<JSONObject> objectNodes = (List<JSONObject>) getData().get(field);
        List<Kneadable> kneads = new ArrayList<>();
        try{
            for(JSONObject node : objectNodes) {
                KneadableJsonObject kneadable = new KneadableJsonObject(node);
                kneads.add(kneadable);
            }
        } catch(Exception ex) {
            throw new NotKneadableException("Cannot knead into list of objects.", ex);
        }
        return kneads;
    }

    @Override
    public Kneadable knibbleObject(String field) {
        if(! getData().containsKey(field)) {
            throw new IllegalArgumentException("Data does not have field '"
                    + field + "'." );
        }
        if(!(getData().get(field) instanceof Map)) {
            throw new IllegalArgumentException("Data type of field `" + field + "` is not an object.");
        }
        JSONObject jsonObject = (JSONObject) getData().get(field);
        try {
            return new KneadableJsonObject(jsonObject);
        } catch(Exception ex) {
            throw new NotKneadableException("Cannot form into Kneadable.", ex);
        }
    }

    @Override
    public Number knibbleNumber(String field) {
        if(! getData().containsKey(field)) {
            throw new IllegalArgumentException("Data does not have field '"
                    + field + "'." );
        }
        if(!(getData().get(field) instanceof Number)) {
            throw new IllegalArgumentException("Data type of field `" + field + "` is not an number.");
        }
        return (Number) getData().get(field);
    }

    @Override
    public String knibbleString(String field) {
        if(! getData().containsKey(field)) {
            throw new IllegalArgumentException("Data does not have field '"
                    + field + "'." );
        }
        if(!(getData().get(field) instanceof String)) {
            throw new IllegalArgumentException("Data type of field `" + field + "` is not a string.");
        }
        return (String) getData().get(field);
    }

    @Override
    public Boolean knibbleBoolean(String field) {
        if(! getData().containsKey(field)) {
            throw new IllegalArgumentException("Data does not have field '"
                    + field + "'." );
        }
        if(!(getData().get(field) instanceof Boolean)) {
            throw new IllegalArgumentException("Data type of field `" + field + "` is not a boolean.");
        }
        return (Boolean) getData().get(field);
    }

    @Override
    public <T> void setField(String fieldName, T value) {
        getData().put(fieldName, value);
    }

    @Override
    public void setSource(JsonKnibble source) {
        throw new IllegalArgumentException("Json Object already has a source");
    }

    protected Map getData() {
        if(data.isPresent()) {
            return data.get();
        } else {
            throw new IllegalStateException("No data present.");
        }
    }
}