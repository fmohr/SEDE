package de.upb.sede.edd.deploy;

import de.upb.sede.edd.OperatingSystem;
import de.upb.sede.edd.deploy.model.DeploymentMethodStepWorkdir;
import de.upb.sede.edd.deploy.model.GradleProcess;
import de.upb.sede.edd.process.DefaultProcessHandleBuilder;
import de.upb.sede.util.Kneadable;

import java.io.File;
import java.util.Optional;

public class GradleProjectBuild extends AbstractProcessBuild{

    public static final String TYPE = "gradle";

    private GradleProcess gradleProcess;

    public GradleProjectBuild(String displayName, Kneadable buildDef) {
        super(displayName, buildDef);
        gradleProcess = getBuildDef().knead(GradleProcess.class);
    }

    @Override
    protected void configureProcess(DefaultProcessHandleBuilder builder, ServiceDeployment current) {
        DeploymentMethodStepWorkdir workDirDef = getBuildDef().knead(DeploymentMethodStepWorkdir.class);
        AcrPath acronymPath = workDirDef.getWorkingDir();
        File workingDir = current.resolvePath(acronymPath);
        if(! workingDir.isDirectory()) {
            throw new DeploymentException(getDisplayName() + " specified working directory is not a directory: " + workingDir);
        }
        builder.setWorkingDir(workingDir);

        String gradleExecPath = findGradleExecutable(workingDir);
        builder.setExecutable(gradleExecPath);

        builder.setArgs(gradleProcess.getTasks());
    }


    public static String findGradleExecutable(File gradleProjectDir) {
        Optional<File> gradleExecFile = OperatingSystem.current().findScriptInDir(gradleProjectDir, "gradlew");
        if(! gradleExecFile.isPresent()) {
            gradleExecFile = OperatingSystem.current().findExecInPath("gradle");
        }
        String gradleExec = gradleExecFile.map(File::toString).orElse("gradle");
        return gradleExec;
    }

}
