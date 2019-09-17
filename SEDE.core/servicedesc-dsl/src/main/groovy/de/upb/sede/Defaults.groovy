package de.upb.sede

import de.upb.sede.exec.MutableServiceDesc
import de.upb.sede.exec.MutableSignatureDesc
import de.upb.sede.exec.aux.MutableJavaReflectionAux

class Defaults {

    /**
     * Default constructor signature describer.
     */
    def static final Closure DEFAULT_CONSTRUCTOR = {
        /*
         * Add constructor invocation hint:
         */
//        if(javaAux == null) {
//            javaAux = MutableJavaReflectionAux.create()
//        }
//        javaAux.constructorInvocation = true

        /*
         * Add output type:
         */
        if(service.stateType != null) {
            addOutputTypes(service.stateType)
        }
    }

    static final Closure DEFAULT_METHOD = Closure.IDENTITY


    def constructor = DEFAULT_CONSTRUCTOR

    def method = DEFAULT_METHOD

    def clearMethod() {
        method = DEFAULT_CONSTRUCTOR
    }

    def clearConstructor() {
        constructor = DEFAULT_CONSTRUCTOR
    }

}
