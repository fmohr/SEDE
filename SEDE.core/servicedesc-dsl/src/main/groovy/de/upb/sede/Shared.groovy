package de.upb.sede

import de.upb.sede.exec.IMethodDesc
import de.upb.sede.exec.IServiceDesc
import de.upb.sede.exec.auxiliary.DynamicAuxAware
import de.upb.sede.exec.auxiliary.MutableJavaDispatchAux
import de.upb.sede.util.DynRecord
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


    static void aux(DynamicAuxAware disAware, Closure describer) {
        def dynRecord
        if(disAware.dynAux  == null) {
            dynRecord = new DynRecord(new HashMap())
            disAware.setDynAux(dynRecord)
        } else {
            dynRecord = disAware.dynAux
        }
        DomainAware.delegateDown(new AuxDomain(), dynRecord, describer)
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

    static def comment(de.upb.sede.CommentAware model, String ... comments) {
        for(def comment : comments) {
            model.comments += comment
        }
    }
    static def setInfo(de.upb.sede.CommentAware model, String commentBlock) {
        commentBlock = commentBlock
            .replaceFirst("\\n","")
            .stripIndent()
            .trim()

        def newComments = new ArrayList<>(Arrays.asList(commentBlock.split("\\n")))
        newComments.removeIf { it.isEmpty() }
        model.comments = newComments
    }


}


