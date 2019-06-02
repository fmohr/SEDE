package de.upb.sede.edd.process;


import java.io.File;
import java.util.Map;

/**
 * <p>Specifies the options to use to fork a process.</p>
 */
public interface ProcessForkOptions {
    /**
     * Returns the name of the executable to use.
     *
     * @return The executable.
     */
    String getExecutable();

    /**
     * Sets the name of the executable to use.
     *
     * @param executable The executable. Must not be null.
     */
    void setExecutable(String executable);


    /**
     * Sets the name of the executable to use.
     *
     * @param executable The executable. Must not be null.
     * @return this
     */
    default ProcessForkOptions executable(String executable) {
        setExecutable(executable);
        return this;
    }

    /**
     * Returns the working directory for the process. Defaults to the project directory.
     *
     * @return The working directory. Never returns null.
     */
    File getWorkingDir();

    /**
     * Sets the working directory for the process.
     *
     * @param dir The working directory. Must not be null.
     */
    void setWorkingDir(File dir);


    /**
     * Sets the working directory for the process.
     *
     * @param dir The working directory. Must not be null.
     * @return this
     */
    default ProcessForkOptions workingDir(File dir) {
        setWorkingDir(dir);
        return this;
    }

    /**
     * The environment variables to use for the process. Defaults to the environment of this process.
     *
     * @return The environment. Returns an empty map when there are no environment variables.
     */
    Map<String, Object> getEnvironment();

    /**
     * Sets the environment variable to use for the process.
     *
     * @param environmentVariables The environment variables. Must not be null.
     */
    void setEnvironment(Map<String, ?> environmentVariables);

    /**
     * Adds some environment variables to the environment for this process.
     *
     * @param environmentVariables The environment variables. Must not be null.
     * @return this
     */
    default ProcessForkOptions environment(Map<String, ?> environmentVariables) {
        getEnvironment().putAll(environmentVariables);
        return this;
    }

    /**
     * Adds an environment variable to the environment for this process.
     *
     * @param name The name of the variable.
     * @param value The value for the variable. Must not be null.
     * @return this
     */
    default ProcessForkOptions environment(String name, Object value) {
        this.getEnvironment().put(name, value);
        return this;
    }

    /**
     * Copies these options to the given target options.
     *
     * @param options The target options
     * @return this
     */
    ProcessForkOptions copyTo(ProcessForkOptions options);
}
