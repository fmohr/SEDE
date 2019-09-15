package de.upb.sede

import de.upb.sede.exec.MutableServiceDesc
import de.upb.sede.exec.MutableSignatureDesc
import de.upb.sede.exec.aux.MutableJavaReflectionAux

class Defaults {

    /**
     * Default constructor signature describer.
     */
    def Closure constructor = {
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
        def serviceName = service.qualifier as String
        addOutputTypes( service.stateType
            .orElseThrow({
                new IllegalStateException("Cannot declare constructor before declaring state type: " + serviceName)
            })
        )
    }

    def Closure method = Closure.IDENTITY

//    TODO clean this up.
//        { MutableSignatureDesc desc ->
//
//    }

}
