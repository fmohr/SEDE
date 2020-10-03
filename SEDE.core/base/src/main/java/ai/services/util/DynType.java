package ai.services.util;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.type.TypeFactory;

import java.util.List;
import java.util.function.Function;

public interface DynType {

    <T> T cast(Class<T> form);

    <T> T cast(JavaType formType);
}
