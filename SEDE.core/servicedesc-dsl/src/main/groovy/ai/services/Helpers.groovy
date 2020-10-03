package ai.services

class Helpers {

    private static void runDescriber(Object o, Closure describer) {
        describer.delegate = o
        describer.resolveStrategy = Closure.DELEGATE_FIRST
        describer.run()
    }

    public static ai.services.exec.auxiliary.MutableJavaDispatchAux newJavaDispatchAux(@DelegatesTo(ai.services.exec.auxiliary.MutableJavaDispatchAux) Closure describer = Closure.IDENTITY) {
        def javaAux = ai.services.exec.auxiliary.MutableJavaDispatchAux.create()
        runDescriber(javaAux, describer)
        return javaAux
    }

    public static ai.services.exec.auxiliary.MutableJavaParameterizationAux newJavaParamAux(@DelegatesTo(ai.services.exec.auxiliary.MutableJavaParameterizationAux) Closure describer) {
        def javaAux = ai.services.exec.auxiliary.MutableJavaParameterizationAux.create()
        runDescriber(javaAux, describer)
        return javaAux
    }

    public static ai.services.exec.auxiliary.MutableStdTypeAux newJavaTypeAux(@DelegatesTo(ai.services.exec.auxiliary.MutableStdTypeAux) Closure describer) {
        def javaAux = ai.services.exec.auxiliary.MutableStdTypeAux.create()
        runDescriber(javaAux, describer)
        return javaAux
    }
    // TODO add more static builders
}
