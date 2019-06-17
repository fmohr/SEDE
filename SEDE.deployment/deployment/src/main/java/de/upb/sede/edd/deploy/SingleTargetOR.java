package de.upb.sede.edd.deploy;

import de.upb.sede.edd.process.ClassPath;
import de.upb.sede.util.OptionalField;

import java.util.Optional;

public class SingleTargetOR implements DeploymentOutputReceiver{

    private OptionalField<ClassPath> classPath = OptionalField.empty();

    public Optional<ClassPath> getOptionalClassPath() {
        return classPath.opt();
    }

    public ClassPath getClassPath() {
        return classPath.orElseThrow(() -> new IllegalStateException("Class path was not specified"));
    }

    public void setClassPath(OptionalField<ClassPath> classPath) {
        this.classPath = classPath;
    }

    public void setClassPath(ClassPath classPath) {
        this.classPath = OptionalField.of(classPath);
    }

    @Override
    public ClassPath cp(String spec) {
        return classPath.orElseThrow(() -> new IllegalStateException("Class path was not specified"));
    }
}
