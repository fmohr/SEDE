package de.upb.sede.composition.graphs.typing;

import javax.annotation.Nullable;
import java.util.HashMap;

public class InstructionMethodResolver extends HashMap<Long, MethodCognition> {

    public InstructionMethodResolver() {
    }

    public class Indexed {
        long index;

        private Indexed(long index) {
            this.index = index;
        }

        @Nullable
        public MethodCognition getMethodCognition() {
            return get(index);
        }

        public void setMethodCognition(MethodCognition methodCognition) {
            put(index, methodCognition);
        }
    }

    public Indexed ofIndex(long index) {
        return new Indexed(index);
    }

}
