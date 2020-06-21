package de.upb.sede

import de.upb.sede.exec.MutableMethodDesc

class Defaults {

    static Defaults defaults = new Defaults()

    /**
     * Default constructor signature describer.
     */
    static final Closure DEFAULT_CONSTRUCTOR = Closure.IDENTITY
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
//        if(service.stateType != null) {
//            addOutputTypes(service.stateType)
//        }
//    }

    static final Closure DEFAULT_METHOD = Closure.IDENTITY


    def constructor = DEFAULT_CONSTRUCTOR

    def method = DEFAULT_METHOD

    static def clearMethod() {
        defaults.method = DEFAULT_CONSTRUCTOR
    }

    static def clearConstructor() {
        defaults.constructor = DEFAULT_CONSTRUCTOR
    }

    static def setDefaultConstructor(@DelegatesTo(MutableMethodDesc) Closure defaultConstructor) {
        defaults.constructor = defaultConstructor
    }

    static def setDefaultMethod(@DelegatesTo(MutableMethodDesc) Closure defaultMethod) {
        defaults.method = defaultMethod
    }

}
