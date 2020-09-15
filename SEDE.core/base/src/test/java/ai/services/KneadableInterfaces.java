package ai.services;

import ai.services.util.RecordForm;
import ai.services.util.TypeForm;

import java.util.List;

public class KneadableInterfaces {

    interface A extends RecordForm {

        default void setA(String a) {
            setField("a", a);
        }

        default String getA() {
            return string("a");
        }

    }

    interface B extends RecordForm {

        default void setB(float b) {
            setField("b", b);
        }

        default float getB() {
            return number("b").floatValue();
        }

    }

//    @JsonDeserialize(using = KneadableJsonObject.Deserializer.class, converter = AB.DC.class)
//    @JsonSerialize(using = KneadableJsonObject.Serializer.class, converter = AB.SC.class)
    public static class AB extends TypeForm implements A, B {
        public AB() {
        }
//        static class DC extends KneadForm.ToFormConverter<AB> {}
//        static class SC extends KneadForm.FromFormConverter<AB> {}
    }


    public static class Container  {
        public List<AB> c;

    }

}
