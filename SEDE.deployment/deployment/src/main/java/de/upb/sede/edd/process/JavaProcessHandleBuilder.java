package de.upb.sede.edd.process;

import de.upb.sede.edd.Jvm;

import java.io.File;
import java.util.*;
import java.util.concurrent.Executor;

/**
 */
public class JavaProcessHandleBuilder extends AbstractProcessHandleBuilder {

    private String mainClass;
    private final List<Object> applicationArgs = new ArrayList<Object>();
    private final JvmOptions javaOptions;

    public JavaProcessHandleBuilder(Executor executor) {
        super(executor);
        javaOptions = new JvmOptions();
        executable(Jvm.current().getJavaExecutable().getAbsolutePath());
    }

    public List<String> getAllJvmArgs() {
        List<String> allArgs = new ArrayList<String>(javaOptions.getAllJvmArgs());
        return allArgs;
    }

    public void setAllJvmArgs(List<String> arguments) {
        throw new UnsupportedOperationException();
    }

    public void setAllJvmArgs(Iterable<?> arguments) {
        throw new UnsupportedOperationException();
    }

    public List<String> getJvmArgs() {
        return javaOptions.getJvmArgs();
    }

    public Map<String, Object> getSystemProperties() {
        return javaOptions.getMutableSystemProperties();
    }

    public void setSystemProperties(Map<String, ?> properties) {
        javaOptions.setSystemProperties(properties);
    }

    public JavaProcessHandleBuilder systemProperties(Map<String, ?> properties) {
        javaOptions.systemProperties(properties);
        return this;
    }

    public JavaProcessHandleBuilder systemProperty(String name, Object value) {
        javaOptions.systemProperty(name, value);
        return this;
    }

    public ClassPath getClasspath() {
        return javaOptions.getClasspath();
    }

    public String getMinHeapSize() {
        return javaOptions.getMinHeapSize();
    }

    public void setMinHeapSize(String heapSize) {
        javaOptions.setMinHeapSize(heapSize);
    }

    public String getDefaultCharacterEncoding() {
        return javaOptions.getDefaultCharacterEncoding();
    }

    public void setDefaultCharacterEncoding(String defaultCharacterEncoding) {
        javaOptions.setDefaultCharacterEncoding(defaultCharacterEncoding);
    }

    public String getMaxHeapSize() {
        return javaOptions.getMaxHeapSize();
    }

    public void setMaxHeapSize(String heapSize) {
        javaOptions.setMaxHeapSize(heapSize);
    }

    public boolean getEnableAssertions() {
        return javaOptions.getEnableAssertions();
    }

    public void setEnableAssertions(boolean enabled) {
        javaOptions.setEnableAssertions(enabled);
    }

    public boolean getDebug() {
        return javaOptions.getDebug();
    }

    public void setDebug(boolean enabled) {
        javaOptions.setDebug(enabled);
    }

    public String getMain() {
        return mainClass;
    }

    public JavaProcessHandleBuilder setMain(String mainClassName) {
        this.mainClass = mainClassName;
        return this;
    }

    public List<String> getArgs() {
        List<String> args = new ArrayList<String>();
        for (Object applicationArg : applicationArgs) {
            args.add(applicationArg.toString());
        }
        return args;
    }

    public JavaProcessHandleBuilder setArgs(List<String> applicationArgs) {
        this.applicationArgs.clear();
        this.applicationArgs.addAll(applicationArgs);
        return this;
    }

    public JavaProcessHandleBuilder setArgs(Iterable<?> applicationArgs) {
        this.applicationArgs.clear();
        for(Object o : applicationArgs) {
            this.applicationArgs.add(o);
        }
        return this;
    }

    public JavaProcessHandleBuilder args(Object... args) {
        args(Arrays.asList(args));
        return this;
    }

    public JavaProcessHandleBuilder args(Iterable<Object> args) {
        for(Object o : args) {
            this.applicationArgs.add(o);
        }
        return this;
    }

    @Override
    public List<String> getAllArguments() {
        List<String> arguments = new ArrayList<String>(getAllJvmArgs());
        arguments.add(mainClass);
        arguments.addAll(getArgs());
        return arguments;
    }

    public DefaultProcessHandle build() {
        if (mainClass == null) {
            throw new IllegalStateException("No main class specified");
        }
        return super.build();
    }

}
