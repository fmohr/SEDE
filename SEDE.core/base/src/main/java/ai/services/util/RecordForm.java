package ai.services.util;

import java.util.List;
import java.util.Optional;

//@JsonDeserialize(using = KneadableJsonObject.Deserializer.class, converter = JsonKnibble.ToForm.class)
//@JsonSerialize(using = KneadableJsonObject.Serializer.class, converter = JsonKnibble.FromForm.class)
public interface RecordForm extends DynType {

    List<DynType> list(String field);

    DynType dynObject(String field);

    Number number(String field);

    String string(String field);

    Boolean bool(String field);

    <T> void setField(String fieldName, T value);

    void setSource(RecordForm source);

    Optional<RecordForm> getSource();
}

