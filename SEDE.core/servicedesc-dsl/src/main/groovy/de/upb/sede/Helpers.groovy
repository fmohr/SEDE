package de.upb.sede

import de.upb.sede.exec.auxiliary.MutableJavaDispatchAux
import de.upb.sede.exec.auxiliary.MutableJavaParameterizationAux
import de.upb.sede.exec.auxiliary.MutableJavaTypeAux

class Helpers {

    private static void runDescriber(Object o, Closure describer) {
        describer.delegate = o
        describer.resolveStrategy = Closure.DELEGATE_FIRST
        describer.run()
    }

    public static MutableJavaDispatchAux newJavaDispatchAux(@DelegatesTo(MutableJavaDispatchAux) Closure describer = Closure.IDENTITY) {
        def javaAux = MutableJavaDispatchAux.create()
        runDescriber(javaAux, describer)
        return javaAux
    }

    public static MutableJavaParameterizationAux newJavaParamAux(@DelegatesTo(MutableJavaParameterizationAux) Closure describer) {
        def javaAux = MutableJavaParameterizationAux.create()
        runDescriber(javaAux, describer)
        return javaAux
    }

    public static MutableJavaTypeAux newJavaTypeAux(@DelegatesTo(MutableJavaTypeAux) Closure describer) {
        def javaAux = MutableJavaTypeAux.create()
        runDescriber(javaAux, describer)
        return javaAux
    }
    // TODO add more static builders
}
