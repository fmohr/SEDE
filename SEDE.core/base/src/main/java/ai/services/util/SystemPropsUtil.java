package ai.services.util;

import java.io.File;

public class SystemPropsUtil {

    public static String getLineSeparator() {
        return System.getProperty("line.separator");
    }

    public static String getFileSeparator() {
        return System.getProperty("file.separator");
    }

    public static String getPathSeperator() {
        return System.getProperty("path.separator");
    }

    public static String getJavaIoTmpDir() {
        return System.getProperty("java.io.tmpdir");
    }

    public static String getUserHome() {
        return System.getProperty("user.home");
    }

    public static String getUserName() {
        return System.getProperty("user.name");
    }

    public static String getJavaVersion() {
        return System.getProperty("java.version");
    }

    public static String getJavaHome() {
        return System.getProperty("java.home");
    }

    public static String getOSName() {
        return System.getProperty("os.name");
    }

    public static String getOSArch() {
        return System.getProperty("os.arch");
    }

    public static String getOSVersion() {
        return System.getProperty("os.version");
    }

    public static File getWorkingDir() {
        return new File(System.getProperty("user.dir"));
    }

    public static File getCurrentDir() {
        return new File(System.getProperty("user.dir"));
    }

}
