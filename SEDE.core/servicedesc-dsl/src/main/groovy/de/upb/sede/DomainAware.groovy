package de.upb.sede


import groovy.transform.PackageScope

abstract class DomainAware<M, T> implements GroovyObject{

    @PackageScope M model

    @PackageScope Map binding = new HashMap()

    @PackageScope
    abstract String getBindingName();

    @PackageScope T topDomain

    def defaults = new Defaults()

    // TODO add tags or extension. An Expando which can be filled with extra informations

    @PackageScope void read(Closure describer) {
        def code = describer.rehydrate(model, this, this)
        code.resolveStrategy = Closure.DELEGATE_FIRST
        code.run()
    }

    @PackageScope
    void delegateDown(DomainAware subDomain, m, Closure describer) {
        subDomain.topDomain = this
        subDomain.model = m
        subDomain.binding.putAll(binding)
        subDomain.binding[getBindingName()] = this.model
        subDomain.defaults = defaults

        subDomain.read(describer)

        subDomain.binding.remove(getBindingName())
        subDomain.binding.removeAll {it in binding.keySet()}
        subDomain.model = null
        subDomain.topDomain = null
    }


    def propertyMissing(String name) {
        if(name == this.bindingName) {
            return model
        }
        if(name in binding) {
            return binding[name]
        } else {
            throw new MissingPropertyException("No property '" + name + "' in " + getBindingName())
        }
    }


}
