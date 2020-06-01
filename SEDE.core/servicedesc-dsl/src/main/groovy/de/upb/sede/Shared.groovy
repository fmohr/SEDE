package de.upb.sede

import de.upb.sede.exec.IMethodDesc
import de.upb.sede.exec.IServiceDesc
import de.upb.sede.exec.auxiliary.DynamicAuxAware
import de.upb.sede.exec.auxiliary.MutableJavaDispatchAux
import de.upb.sede.util.DynRecord
import de.upb.sede.util.DynTypeField
import groovy.transform.PackageScope

@PackageScope
class Shared {

    final static MutableJavaDispatchAux createJavaDispatchAux(
        MutableJavaDispatchAux javaAux,
        Closure javaAuxDescriber) {
        if(javaAux == null) {
            javaAux = MutableJavaDispatchAux.create()
        }

        javaAuxDescriber.delegate = javaAux
        javaAuxDescriber.resolveStrategy = Closure.DELEGATE_FIRST
        javaAuxDescriber.run()

        def newJavaAux = javaAux
        return newJavaAux
    }

    trait DomainExtension {
        DomainAware getDom() {
            if(!(this instanceof DomainAware)) {
                throw new RuntimeException("Bug: " + this.class.name + " is AuxDomAware but not DomainAware.");
            }
            return this as DomainAware;
        }
    }

    @PackageScope
    trait AuxDomAware extends DomainExtension {
        void aux(@DelegatesTo(AuxDomain) Closure describer) {
            if(!(getDom().model instanceof DynamicAuxAware)) {
                throw new RuntimeException("Bug: model is instance of ${getDom().model.class} but not of DynamicAuxAware.")
            }
            def disAware = getDom().model as DynamicAuxAware
            def dynRecord
            if(disAware.dynAux  == null) {
                dynRecord = new DynRecord(new HashMap())
                disAware.setDynAux(dynRecord)
            } else {
                dynRecord = disAware.dynAux
            }
            AuxDomain dom = new AuxDomain();
            getDom().delegateDown(dom, dynRecord, describer)
        }
    }

    @PackageScope
    final static String readQualifier(Object o) {
        if(o instanceof IServiceDesc) {
            def service = o as IServiceDesc
            /*
             * Return state type if defined:
             */
            if(service.stateType != null) {
                return service.stateType
            } else {
                return service.qualifier
            }
        }
        if(o instanceof  String) {
            return o
        } else if(o instanceof IQualifiable) {
            return (o as IQualifiable).qualifier
        } else {
            throw new IllegalArgumentException("Cannot parse qualifier of: " + o.toString() + " of type " + o.class.simpleName)
        }
    }

    @PackageScope
    final static boolean matchingMethods(IMethodDesc method1, IMethodDesc method2) {
        // qualifier needs to match
        if(method1.qualifier != method2.qualifier) {
            return false;
        }
        // number of inputs needs to match
        if(method1.inputs.size() != method2.inputs.size()) {
            return false
        }
        // else they match
        return true
    }

    @PackageScope
    trait CommentAware {
        def comment(String ... comments) {
            for(def comment : comments) {
                model.comments += comment
            }
        }

        def setInfo(String commentBlock) {
            commentBlock = commentBlock
                .replaceFirst("\\n","")
                .stripIndent()
                .trim()

            def newComments = new ArrayList<>(Arrays.asList(commentBlock.split("\\n")))
            newComments.removeIf { it.isEmpty() }
            model.comments = newComments
        }
    }

}


