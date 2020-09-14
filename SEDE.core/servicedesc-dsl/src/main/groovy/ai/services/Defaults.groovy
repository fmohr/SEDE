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

    def methodSignature = Collections.emptyMap()

    static def clearMethod() {
        defaults.method = DEFAULT_CONSTRUCTOR
    }

    static def clearMethodSignature() {
        defaults.methodSignature = Collections.emptyMap()
    }

    static def clearConstructor() {
        defaults.constructor = DEFAULT_CONSTRUCTOR
    }

    static def clearDefaults() {
        clearConstructor()
        clearMethod()
        clearMethodSignature()
    }

    static def setDefaultConstructor(@DelegatesTo(MutableMethodDesc) Closure defaultConstructor) {
        defaults.constructor = defaultConstructor
    }

    static def setDefaultMethod(@DelegatesTo(value = MutableMethodDesc, strategy = Closure.DELEGATE_FIRST) Closure defaultMethod) {
        defaults.method = defaultMethod
    }

    static def setDefaultMethodSignature(Map signatureDefaults) {
        defaults.methodSignature = signatureDefaults
    }

    static def withDefaults(@DelegatesTo(value= Defaults) Closure defaultSetter,
                            Closure runner) {
        Defaults backup = defaults
        defaults = new Defaults()
        defaults.tap(defaultSetter)
        runner()
        defaults = backup
    }

}
