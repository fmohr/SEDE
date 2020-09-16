package ai.services


import groovy.transform.PackageScope

class DomainAware<M, T> implements GroovyObject{

//    @PackageScope Map binding = new HashMap()

//    @PackageScope
//    abstract String getBindingName();

//    @PackageScope T topDomain

    @PackageScope static void read(model, Closure describer) {
//        def code = describer.rehydrate(model, this, this)
        def code = describer
        describer.delegate = model
        code.resolveStrategy = Closure.DELEGATE_FIRST
        code.run()
    }

    @PackageScope
    static void delegateDown(domain, model, Closure describer) {
        read(model, describer)
    }
}
