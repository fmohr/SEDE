package de.upb.sede.util;

import java.util.List;

public interface JsonKnibble extends Kneadable {

    List<Kneadable> knibbleList(String field);

    Kneadable knibbleObject(String field);

    Number knibbleNumber(String field);

    String knibbleString(String field);

    Boolean knibbleBoolean(String field);

    <T> void setField(String fieldName, T value);

    void setSource(JsonKnibble source);
}
