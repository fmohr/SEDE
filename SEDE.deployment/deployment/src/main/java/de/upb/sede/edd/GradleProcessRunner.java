package de.upb.sede.edd;

import de.upb.sede.edd.process.DefaultProcessHandleBuilder;
import de.upb.sede.edd.process.ProcessResult;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;

public class GradleProcessRunner implements Runnable {

    private static final SystemSettingLookup GRADLE_EXECUTABLE = new SystemSettingLookup(
            OperatingSystem.current().getScriptName("./gradlew"),
            "edd.sede.executables.gradle",
            "SEDE_CODE_EXEC_GRADLE"
        ) ;

    private File gradleProjectDir;

    private List<String> tasks;

    private boolean success;

    private Executor executor;

    public GradleProcessRunner(File gradleProjectDir, List<String> tasks) {
        this.gradleProjectDir = gradleProjectDir;
        this.tasks = new ArrayList<>(tasks);
    }

    public GradleProcessRunner(File gradleProjectDir) {
        this.gradleProjectDir = gradleProjectDir;
        this.tasks = new ArrayList<>();
    }

    public List<String> tasks() {
        return tasks;
    }

    @Override
    public void run() {
        DefaultProcessHandleBuilder builder = new DefaultProcessHandleBuilder(executor);
        builder.workingDir(gradleProjectDir);
        builder.setExecutable(GRADLE_EXECUTABLE.lookup());
        builder.setIgnoreExitValue(true);
        builder.setArgs(tasks);

        ProcessResult result = builder.build().start().waitForFinish();

        this.success = result.getExitValue() == 0;
    }
}
