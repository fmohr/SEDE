package de.upb.sede.composition;

import de.upb.sede.requests.resolve.beta.IResolvePolicy;
import de.upb.sede.requests.resolve.beta.IResolveRequest;
import de.upb.sede.requests.resolve.beta.MutableResolveRequest;
import de.upb.sede.requests.resolve.beta.ResolvePolicy;
import de.upb.sede.util.FileUtil;
import groovy.lang.Closure;
import groovy.lang.DelegatesTo;
import groovy.lang.GroovyShell;
import groovy.lang.Script;
import org.codehaus.groovy.control.CompilerConfiguration;

public abstract class RRGen extends Script {

    private static final GroovyShell shell;

    static {
        CompilerConfiguration config = new CompilerConfiguration();

        config.setScriptBaseClass(RRGen.class.getName());
        shell = new GroovyShell(Thread.currentThread().getContextClassLoader(), config);

    }

    public static IResolveRequest fromResource(String resourcePath) {
        String scriptContent = FileUtil.readResourceAsString(resourcePath);
        RRGen script = (RRGen) shell.parse(scriptContent, resourcePath);
        Object scriptOutput = script.run();
        if(scriptOutput instanceof IResolveRequest) {
            return (IResolveRequest) scriptOutput;
        } else {
            throw new IllegalArgumentException(String.format("The given script at %s did not output a resolve request. "
                + "Instead it gave an object of type: %s",
                resourcePath, scriptOutput.getClass().getSimpleName()));
        }
    }

    public static IResolveRequest fromClosure(@DelegatesTo(MutableResolveRequest.class) Closure<?> cl) {
        MutableResolveRequest mr = MutableResolveRequest.create();
        cl.setDelegate(mr);
        cl.setResolveStrategy(Closure.DELEGATE_FIRST);
        cl.run();
        return mr;
    }

    public static IResolvePolicy defaultResolvePolicy() {
        return ResolvePolicy.builder()
            .isDotGraphRequested(true)
            .build();
    }


    //    public static IResolveRequest fromClosure() {
//        ObjectGraphBuilder builder = new ObjectGraphBuilder();
//        builder.setClassLoader(Thread.currentThread().getContextClassLoader());
//        builder.setClassNameResolver(new ObjectGraphBuilder.ClassNameResolver() {
//
//            @Override public String resolveClassname(String classname) {
//                IResolveRequest
//            }
//
//        });
//
//        return null;
//    }

}
