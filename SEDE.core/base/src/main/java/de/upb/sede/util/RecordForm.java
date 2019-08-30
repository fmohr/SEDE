package de.upb.sede.util;

import java.util.List;
import java.util.Optional;

//@JsonDeserialize(using = KneadableJsonObject.Deserializer.class, converter = JsonKnibble.ToForm.class)
//@JsonSerialize(using = KneadableJsonObject.Serializer.class, converter = JsonKnibble.FromForm.class)
public interface RecordForm extends DynType {

    List<DynType> knibbleList(String field);

    DynType knibbleObject(String field);

    Number knibbleNumber(String field);

    String knibbleString(String field);

    Boolean knibbleBoolean(String field);

    <T> void setField(String fieldName, T value);

    void setSource(RecordForm source);

    Optional<RecordForm> getSource();
}

