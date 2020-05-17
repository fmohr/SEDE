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

class AuxDomain
    extends DomainAware<DynRecord, DomainAware> {

    @Override
    String getBindingName() {
        return "aux"
    }

    @PackageScope
    void runDescriberOnValue(Class valueClass, Closure describer) {
        def value = model.cast(valueClass)
        def mutable = DeepMutableCopier.copyAsMutable(value)
        describer.delegate = mutable
        describer.resolveStrategy = Closure.DELEGATE_FIRST
        describer.run()
        model.set(DeepImmutableCopier.copyAsImmutable(mutable))
    }

    def javaDispatch(@DelegatesTo(MutableJavaDispatchAux) Closure dispatchDescriber) {
        runDescriberOnValue(IJavaDispatchAux, dispatchDescriber)
    }

    def pythonDispatch(@DelegatesTo(MutablePythonDispatchAux) Closure dispatchDescriber) {
        runDescriberOnValue(IPythonDispatchAux, dispatchDescriber)
    }

    def javaParam(@DelegatesTo(MutableJavaParameterizationAux) Closure dispatchDescriber) {
        runDescriberOnValue(IJavaParameterizationAux, dispatchDescriber)
    }

    def javaType(@DelegatesTo(MutableJavaTypeAux) Closure dispatchDescriber) {
        runDescriberOnValue(IJavaTypeAux, dispatchDescriber)
    }

    def setFields(@DelegatesTo(Expando) Closure expandoDescriber) {
        Expando expando = new Expando()
        expandoDescriber.delegate = expando
        expandoDescriber.resolveStrategy = Closure.DELEGATE_FIRST
        expandoDescriber.run()
        model.set(expando.getProperties())
    }

}
