package de.upb.sede

import de.upb.sede.exec.auxiliary.IJavaDispatchAux
import de.upb.sede.exec.auxiliary.MutableJavaDispatchAux
import de.upb.sede.exec.auxiliary.MutablePythonDispatchAux
import de.upb.sede.param.auxiliary.MutableJavaParameterizationAux
import de.upb.sede.types.auxiliary.MutableJavaTypeAux
import de.upb.sede.util.DeepMutableCopier
import de.upb.sede.util.DynTypeField
import groovy.transform.PackageScope

class AuxDomain
    extends DomainAware<DynTypeField, DomainAware> {

    @Override
    String getBindingName() {
        return "aux"
    }

    @PackageScope
    void runDelegate(Object aux, Closure describer) {
        describer.delegate = Closure.aux
        describer.resolveStrategy = Closure.DELEGATE_FIRST
        describer.run()
    }

    def javaDispatch(@DelegatesTo(MutableJavaDispatchAux) Closure dispatchDescriber) {
        def javaDispatchAux = model.cast(IJavaDispatchAux)
        def mutable = DeepMutableCopier.copyAsMutable(javaDispatchAux)
        runDelegate(mutable, dispatchDescriber)
    }

    def pythonDispatch(@DelegatesTo(MutablePythonDispatchAux) Closure dispatchDescriber) {
        def pythonDispatchAux = model.cast(MutablePythonDispatchAux)
        runDelegate(pythonDispatchAux, dispatchDescriber)
    }

    def javaParam(@DelegatesTo(MutableJavaParameterizationAux) Closure dispatchDescriber) {
        def aux = model.cast(MutableJavaParameterizationAux)
        runDelegate(aux, dispatchDescriber)
    }

    def javaType(@DelegatesTo(MutableJavaTypeAux) Closure dispatchDescriber) {
        def aux = model.cast(MutableJavaTypeAux)
        runDelegate(aux, dispatchDescriber)
    }

}
