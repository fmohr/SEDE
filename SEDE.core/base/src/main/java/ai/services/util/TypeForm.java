package ai.services.util;

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
    public List<DynType> list(String field) {
        return source.orElseThrow(this::noSourceSet).list(field);
    }

    @Override
    public DynType dynObject(String field) {
        return source.orElseThrow(this::noSourceSet).dynObject(field);
    }

    @Override
    public Number number(String field) {
        return source.orElseThrow(this::noSourceSet).number(field);
    }

    @Override
    public String string(String field) {
        return source.orElseThrow(this::noSourceSet).string(field);
    }

    @Override
    public Boolean bool(String field) {
        return source.orElseThrow(this::noSourceSet).bool(field);
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
