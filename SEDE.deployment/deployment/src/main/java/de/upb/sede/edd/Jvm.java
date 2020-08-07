package de.upb.sede.edd;

import de.upb.sede.util.FileUtil;
import de.upb.sede.util.SystemPropsUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nullable;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

public class Jvm {

    private static final Logger LOGGER = LoggerFactory.getLogger(Jvm.class);

    private final OperatingSystem os;
    //supplied java location
    private final File javaBase;
    //discovered java location
    private final File javaHome;
    private final boolean userSupplied;
    private static final AtomicReference<JvmImplementation> CURRENT = new AtomicReference<JvmImplementation>();

    // Cached resolved executables
    private File javaExecutable;
    private File javacExecutable;
    private File javadocExecutable;
    private File toolsJar;

    public static Jvm current() {
        Jvm jvm = CURRENT.get();
        if (jvm == null) {
            CURRENT.compareAndSet(null, create());
            jvm = CURRENT.get();
        }
        return jvm;
    }

    private static JvmImplementation create() {
        String vendor = System.getProperty("java.vm.vendor");
        if (vendor.toLowerCase().startsWith("apple inc.")) {
            return new AppleJvm(OperatingSystem.current());
        }
        if (vendor.toLowerCase().startsWith("ibm corporation")) {
            return new IbmJvm(OperatingSystem.current());
        }
        return new JvmImplementation(OperatingSystem.current());
    }

    static Jvm create(File javaBase){
        Jvm jvm = new Jvm(OperatingSystem.current(), javaBase);
        Jvm current = current();
        return jvm.getJavaHome().equals(current.getJavaHome()) ? current : jvm;
    }

    /**
     * Constructs JVM details by inspecting the current JVM.
     */
    Jvm(OperatingSystem os) {
        this(os, FileUtil.canonicalize(new File(System.getProperty("java.home"))), false);
    }

    /**
     * Constructs JVM details from the given values
     */
    Jvm(OperatingSystem os, File suppliedJavaBase) {
        this(os, suppliedJavaBase, true);
    }

    private Jvm(OperatingSystem os, File suppliedJavaBase, boolean userSupplied) {
        this.os = os;
        this.javaBase = suppliedJavaBase;
        this.javaHome = findJavaHome(suppliedJavaBase);
        this.userSupplied = userSupplied;
    }

    /**
     * Creates JVM instance for given java home. Attempts to validate if provided javaHome is a valid jdk or jre location.
     * This method is intended to be used for user supplied java homes.
     *
     * @param javaHome - location of your jdk or jre (jdk is safer), cannot be null
     * @return jvm for given java home
     * @throws IllegalArgumentException when supplied javaHome is not a valid folder
     */
    public static Jvm forHome(File javaHome) throws JavaHomeException, IllegalArgumentException {
        if (javaHome == null || !javaHome.isDirectory()) {
            throw new IllegalArgumentException("Supplied javaHome must be a valid directory. You supplied: " + javaHome);
        }
        Jvm jvm = create(javaHome);
        //some validation:
        jvm.getJavaExecutable();
        return jvm;
    }

    /**
     * Creates JVM instance for given values. This method is intended to be used for discovered java homes.
     */
    public static Jvm discovered(File javaHome) {
        return create(javaHome);
    }

    @Override
    public String toString() {
        if (userSupplied) {
            return "User-supplied java: " + javaBase;
        }
        return SystemPropsUtil.getJavaVersion() + " (" + System.getProperty("java.vm.vendor") + " " + System.getProperty("java.vm.version") + ")";
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null || obj.getClass() != getClass()) {
            return false;
        }
        Jvm other = (Jvm) obj;
        return other.javaHome.equals(javaHome);
    }

    @Override
    public int hashCode() {
        return javaHome.hashCode();
    }

    private File findExecutable(String command) {
        File exec = new File(getJavaHome(), "bin/" + command);
        File executable = new File(os.getExecutableName(exec.getAbsolutePath()));
        if (executable.isFile()) {
            return executable;
        }

        if (userSupplied) { //then we want to validate strictly
            throw new JavaHomeException(String.format("The supplied javaHome seems to be invalid."
                + " I cannot find the %s executable. Tried location: %s", command, executable.getAbsolutePath()));
        }

        Optional<File> pathExecutable = os.findExecInPath(command);
        if (pathExecutable.isPresent()) {
            LOGGER.info(String.format("Unable to find the '%s' executable using home: %s. We found it on the PATH: %s.",
                command, getJavaHome(), pathExecutable));
            return pathExecutable.get();
        }

        LOGGER.warn("Unable to find the '{}' executable. Tried the java home: {} and the PATH."
                + " We will assume the executable can be ran in the current working folder.",
            command, getJavaHome());
        return new File(os.getExecutableName(command));
    }

    /**
     */
    public File getJavaExecutable() throws JavaHomeException {
        if (javaExecutable != null) {
            return javaExecutable;
        }
        javaExecutable = findExecutable("java");
        return javaExecutable;
    }

    /**
     */
    public File getJavacExecutable() throws JavaHomeException {
        if (javacExecutable != null) {
            return javacExecutable;
        }
        javacExecutable = findExecutable("javac");
        return javacExecutable;
    }

    /**
     */
    public File getJavadocExecutable() throws JavaHomeException {
        if (javadocExecutable != null) {
            return javadocExecutable;
        }
        javadocExecutable = findExecutable("javadoc");
        return javadocExecutable;
    }

    /**
     */
    public File getExecutable(String name) throws JavaHomeException {
        return findExecutable(name);
    }

    /**
     */
    public File getJavaHome() {
        return javaHome;
    }

    private File findJavaHome(File javaBase) {
        File toolsJar = findToolsJar(javaBase);
        if (toolsJar != null) {
            return toolsJar.getParentFile().getParentFile();
        } else if (javaBase.getName().equalsIgnoreCase("jre") && new File(javaBase.getParentFile(), "bin/java").exists()) {
            return javaBase.getParentFile();
        } else {
            return javaBase;
        }
    }

    /**
     */
    public File getToolsJar() {
        if (toolsJar != null) {
            return toolsJar;
        }
        toolsJar = findToolsJar(javaBase);
        return toolsJar;
    }

    private File findToolsJar(File javaHome) {
        File toolsJar = new File(javaHome, "lib/tools.jar");
        if (toolsJar.exists()) {
            return toolsJar;
        }
        if (javaHome.getName().equalsIgnoreCase("jre")) {
            javaHome = javaHome.getParentFile();
            toolsJar = new File(javaHome, "lib/tools.jar");
            if (toolsJar.exists()) {
                return toolsJar;
            }
        }

        if (os.isWindows()) {
            String version = SystemPropsUtil.getJavaVersion();
            if (javaHome.getName().matches("jre\\d+") || javaHome.getName().equals("jre" + version)) {
                javaHome = new File(javaHome.getParentFile(), "jdk" + version);
                toolsJar = new File(javaHome, "lib/tools.jar");
                if (toolsJar.exists()) {
                    return toolsJar;
                }
            }
        }

        return null;
    }

    public Map<String, ?> getInheritableEnvironmentVariables(Map<String, ?> envVars) {
        return envVars;
    }

    public boolean isIbmJvm() {
        return false;
    }

    /**
     * Details about a known JVM implementation.
     */
    static class JvmImplementation extends Jvm {
        JvmImplementation(OperatingSystem os) {
            super(os);
        }
    }

    static class IbmJvm extends JvmImplementation {
        IbmJvm(OperatingSystem os) {
            super(os);
        }

        @Override
        public boolean isIbmJvm() {
            return true;
        }
    }

    /**
     * Note: Implementation assumes that an Apple JVM always comes with a JDK rather than a JRE, but this is likely an over-simplification.
     */
    static class AppleJvm extends JvmImplementation {
        AppleJvm(OperatingSystem os) {
            super(os);
        }

        /**
         */
        @Override
        public File getToolsJar() {
            return null;
        }

        /**
         */
        @Override
        public Map<String, ?> getInheritableEnvironmentVariables(Map<String, ?> envVars) {
            Map<String, Object> vars = new HashMap<String, Object>();
            for (Map.Entry<String, ?> entry : envVars.entrySet()) {
                if (entry.getKey().matches("APP_NAME_\\d+") || entry.getKey().matches("JAVA_MAIN_CLASS_\\d+")) {
                    continue;
                }
                vars.put(entry.getKey(), entry.getValue());
            }
            return vars;
        }
    }


    public class JavaHomeException extends RuntimeException {
        JavaHomeException(String message) {
            super(message);
        }
    }

}
