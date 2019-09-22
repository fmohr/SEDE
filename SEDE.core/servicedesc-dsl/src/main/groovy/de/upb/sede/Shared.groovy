package de.upb.sede

import de.upb.sede.exec.IServiceDesc
import de.upb.sede.exec.aux.MutableJavaDispatchAux
import groovy.transform.PackageScope

@PackageScope
class Shared {

    final static MutableJavaDispatchAux createJava(
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

    @PackageScope
    trait JavaDispatchAware {

        def java(@DelegatesTo(MutableJavaDispatchAux) javaAuxDescriber) {
            model.javaAux = Shared.createJava(model.javaAux as MutableJavaDispatchAux,
                javaAuxDescriber)
            return model.javaAux as MutableJavaDispatchAux
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


