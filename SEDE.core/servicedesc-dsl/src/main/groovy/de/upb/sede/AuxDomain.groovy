package de.upb.sede

import de.upb.sede.exec.auxiliary.IJavaDispatchAux
import de.upb.sede.exec.auxiliary.IPythonDispatchAux
import de.upb.sede.exec.auxiliary.MutableJavaDispatchAux
import de.upb.sede.exec.auxiliary.MutablePythonDispatchAux
import de.upb.sede.param.auxiliary.IJavaParameterizationAux
import de.upb.sede.param.auxiliary.MutableJavaParameterizationAux
import de.upb.sede.types.auxiliary.IJavaTypeAux
import de.upb.sede.types.auxiliary.MutableJavaTypeAux
import de.upb.sede.util.DeepImmutableCopier
import de.upb.sede.util.DeepMutableCopier
import de.upb.sede.util.DynRecord
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

    static def javaType(DynRecord model, @DelegatesTo(MutableJavaTypeAux) Closure dispatchDescriber) {
        runDescriberOnValue(model, IJavaTypeAux, dispatchDescriber)
    }

    static def setFields(DynRecord model, @DelegatesTo(Expando) Closure expandoDescriber) {
        Expando expando = new Expando()
        expandoDescriber.delegate = expando
        expandoDescriber.resolveStrategy = Closure.DELEGATE_FIRST
        expandoDescriber.run()
        model.set(expando.getProperties())
    }

}
