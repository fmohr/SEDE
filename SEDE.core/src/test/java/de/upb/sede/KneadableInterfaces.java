package de.upb.sede;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import de.upb.sede.util.JsonKnibble;
import de.upb.sede.util.KneadForm;
import de.upb.sede.util.Kneadable;
import de.upb.sede.util.KneadableJsonObject;

import java.util.ArrayList;
import java.util.List;

public class KneadableInterfaces {

    interface A extends JsonKnibble {

        default void setA(String a) {
            setField("a", a);
        }

        default String getA() {
            return knibbleString("a");
        }

    }

    interface B extends JsonKnibble {

        default void setB(float b) {
            setField("b", b);
        }

        default float getB() {
            return knibbleNumber("b").floatValue();
        }

    }

//    @JsonDeserialize(using = KneadableJsonObject.Deserializer.class, converter = AB.DC.class)
//    @JsonSerialize(using = KneadableJsonObject.Serializer.class, converter = AB.SC.class)
    public static class AB extends KneadForm implements A, B {
        public AB() {
        }
//        static class DC extends KneadForm.ToFormConverter<AB> {}
//        static class SC extends KneadForm.FromFormConverter<AB> {}
    }


    public static class Container  {
        public List<AB> c;

    }

}
