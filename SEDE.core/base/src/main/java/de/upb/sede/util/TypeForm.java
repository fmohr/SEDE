package de.upb.sede.util;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.JavaType;

import java.util.List;
import java.util.Optional;


//@JsonDeserialize(using = KneadForm.Deserializer.class)
//@JsonSerialize(using = KneadForm.Serializer.class)
public class TypeForm implements RecordForm {

    @JsonIgnore
    private final MutableOptionalField<RecordForm> source = MutableOptionalField.empty();

    public void setSource(RecordForm newSource) {
        this.source.set(newSource);
    }

    @Override
    public <T> T cast(Class<T> form) {
        return source.orElseThrow(this::noSourceSet).cast(form);
    }

    @Override
    public <T> T cast(JavaType formType) {
        return source.orElseThrow(this::noSourceSet).cast(formType);
    }

    @Override
    public List<DynType> knibbleList(String field) {
        return source.orElseThrow(this::noSourceSet).knibbleList(field);
    }

    @Override
    public DynType knibbleObject(String field) {
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

    public Optional<RecordForm> getSource(){
        return source.opt();
    }


}
