package de.upb.sede.edd;

import de.upb.sede.util.SystemPropsUtil;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class DefaultProcessForkOptions implements ProcessForkOptions {

    private String executable;
    private File workingDir;
    private Map<String, Object> environment;

    public DefaultProcessForkOptions() {
    }

    public String getExecutable() {
        return executable == null ? null : executable.toString();
    }

    public void setExecutable(String executable) {
        this.executable = executable;
    }


    public File getWorkingDir() {
        if (workingDir == null) {
            workingDir = SystemPropsUtil.getWorkingDir();
        }
        return workingDir;
    }

    public void setWorkingDir(File dir) {
        this.workingDir = dir;
    }

    public Map<String, Object> getEnvironment() {
        if (environment == null) {
            setEnvironment(Jvm.current().getInheritableEnvironmentVariables(System.getenv()));
        }
        return environment;
    }

    public Map<String, String> getActualEnvironment() {
        Map<String, String> actual = new HashMap<String, String>();
        for (Map.Entry<String, Object> entry : getEnvironment().entrySet()) {
            actual.put(entry.getKey(), String.valueOf(entry.getValue()));
        }
        return actual;
    }

    public void setEnvironment(Map<String, ?> environmentVariables) {
        environment = (Map<String, Object>) environmentVariables;
    }


    public ProcessForkOptions copyTo(ProcessForkOptions target) {
        target.setExecutable(executable);
        target.setWorkingDir(getWorkingDir());
        target.setEnvironment(getEnvironment());
        return this;
    }
}
