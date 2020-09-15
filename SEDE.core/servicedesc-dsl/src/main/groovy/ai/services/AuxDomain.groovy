package ai.services


import ai.services.exec.auxiliary.IJavaDispatchAux
import ai.services.exec.auxiliary.IPythonDispatchAux
import ai.services.exec.auxiliary.MutableJavaDispatchAux
import ai.services.exec.auxiliary.MutableJavaMarshalAux
import ai.services.exec.auxiliary.IJavaParameterizationAux
import ai.services.exec.auxiliary.IStdTypeAux
import ai.services.exec.auxiliary.MutableJavaParameterizationAux
import ai.services.exec.auxiliary.MutablePythonDispatchAux
import ai.services.exec.auxiliary.MutableStdTypeAux
import ai.services.util.DeepImmutableCopier
import ai.services.util.DeepMutableCopier
import ai.services.util.DynRecord
import groovy.transform.PackageScope

class AuxDomain {

    @PackageScope
    static void runDescriberOnValue(DynRecord model, Class valueClass, Closure describer) {
        def value = model.cast(valueClass)
        def mutable = DeepMutableCopier.copyAsMutable(value)
        describer.delegate = mutable
        describer.resolveStrategy = Closure.DELEGATE_FIRST
        describer.run()
        model.set(DeepImmutableCopier.copyAsImmutable(mutable))
    }

    static def javaDispatch(DynRecord model, @DelegatesTo(MutableJavaDispatchAux) Closure dispatchDescriber) {
        runDescriberOnValue(model, IJavaDispatchAux, dispatchDescriber)
    }

    static def pythonDispatch(DynRecord model, @DelegatesTo(MutablePythonDispatchAux) Closure dispatchDescriber) {
        runDescriberOnValue(model, IPythonDispatchAux, dispatchDescriber)
    }

    static def javaParam(DynRecord model, @DelegatesTo(MutableJavaParameterizationAux) Closure dispatchDescriber) {
        runDescriberOnValue(model, IJavaParameterizationAux, dispatchDescriber)
    }

    static def javaMarshalling(DynRecord model, @DelegatesTo(MutableJavaMarshalAux) Closure dispatchDescriber) {
        runDescriberOnValue(model, IStdTypeAux, {
            setJavaMarshalAux(dispatchDescriber)
        })
    }

    static def setJavaMarshalAux(MutableStdTypeAux aux, Closure auxDescriber) {
        def javaAux = MutableJavaMarshalAux.create().tap (auxDescriber)
        aux.setJavaMarshalAux(javaAux)
    }

    static def setFields(DynRecord model, @DelegatesTo(Expando) Closure expandoDescriber) {
        Expando expando = new Expando()
        expandoDescriber.delegate = expando
        expandoDescriber.resolveStrategy = Closure.DELEGATE_FIRST
        expandoDescriber.run()
        model.set(expando.getProperties())
    }

}
