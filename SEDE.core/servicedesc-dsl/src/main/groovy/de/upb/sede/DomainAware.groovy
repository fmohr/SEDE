package de.upb.sede

import groovy.transform.PackageScope

abstract class DomainAware implements GroovyObject{

    @PackageScope Object model

    @PackageScope Map binding = new HashMap()

    @PackageScope
    abstract String getBindingName();

    @PackageScope
    void delegateDown(Closure describer) {
        def code = describer.rehydrate(model, this, this)
        code.resolveStrategy = Closure.DELEGATE_FIRST
        code.run()
    }

    @PackageScope
    void delegateDown(DomainAware subDomain, Closure describer) {
        subDomain.binding.putAll(binding)
        subDomain.binding[getBindingName()] = model

        subDomain.delegateDown(describer)

        subDomain.binding.remove(getBindingName())
        subDomain.binding.removeAll {it in binding.keySet()}
    }

    def comment(String ... comments) {
        for(def comment : comments) {
            model.comments += comment
        }
    }

    def propertyMissing(String name) {
        if(name in binding) {
            return binding[name]
        } else {
            throw new MissingPropertyException("No property '" + name + "' in " + getBindingName())
        }
    }

}
