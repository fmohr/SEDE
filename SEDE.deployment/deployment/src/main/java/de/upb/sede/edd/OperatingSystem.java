package de.upb.sede.edd;

import javax.annotation.Nullable;
import java.io.File;
import java.util.*;
import java.util.regex.Pattern;

import static de.upb.sede.util.FileUtil.withExtension;


public abstract class OperatingSystem {
    public static final Windows WINDOWS = new Windows();
    public static final MacOs MAC_OS = new MacOs();
    public static final Solaris SOLARIS = new Solaris();
    public static final Linux LINUX = new Linux();
    public static final FreeBSD FREE_BSD = new FreeBSD();
    public static final Unix UNIX = new Unix();

    private static OperatingSystem currentOs;
    private final String toStringValue;
    private final String osName;
    private final String osVersion;

    OperatingSystem() {
        osName = System.getProperty("os.name");
        osVersion = System.getProperty("os.version");
        toStringValue = getName() + " " + getVersion() + " " + System.getProperty("os.arch");
    }

    public static OperatingSystem current() {
        if (currentOs == null) {
            currentOs = forName(System.getProperty("os.name"));
        }
        return currentOs;
    }

    // for testing current()
    static void resetCurrent() {
        currentOs = null;
    }

    public static OperatingSystem forName(String os) {
        String osName = os.toLowerCase();
        if (osName.contains("windows")) {
            return WINDOWS;
        } else if (osName.contains("mac os x") || osName.contains("darwin") || osName.contains("osx")) {
            return MAC_OS;
        } else if (osName.contains("sunos") || osName.contains("solaris")) {
            return SOLARIS;
        } else if (osName.contains("linux")) {
            return LINUX;
        } else if (osName.contains("freebsd")) {
            return FREE_BSD;
        } else {
            // Not strictly true
            return UNIX;
        }
    }

    @Override
    public String toString() {
        return toStringValue;
    }

    public String getName() {
        return osName;
    }

    public String getVersion() {
        return osVersion;
    }

    public boolean isWindows() {
        return false;
    }

    public boolean isUnix() {
        return false;
    }

    public boolean isMacOsX() {
        return false;
    }

    public boolean isLinux() {
        return false;
    }

    public abstract String getNativePrefix();

    public abstract String getScriptName(String scriptPath);

    public abstract String getExecutableName(String executablePath);

    public abstract String getExecutableSuffix();

    public abstract String getSharedLibraryName(String libraryName);

    public abstract String getSharedLibrarySuffix();

    public abstract String getStaticLibraryName(String libraryName);

    public abstract String getStaticLibrarySuffix();

    public abstract String getLinkLibrarySuffix();

    public abstract String getLinkLibraryName(String libraryPath);

    public abstract String getFamilyName();

    /**
     * Locates the given exact file in the given directory. Returns null if not found.
     */
    public Optional<File> findExactInDir(File directory, String name) {
        File candidate = new File(directory, name);
        if (candidate.isFile()) {
            return Optional.of(candidate);
        }
        return Optional.empty();
    }


    public Optional<File> findExecInDir(File directory, String name) {
        String exeName = getExecutableName(name);
        return findExactInDir(directory, exeName);
    }

    public Optional<File> findScriptInDir(File directory, String name) {
        String scriptName = getScriptName(name);
        return findExactInDir(directory, scriptName);
    }

    public Optional<File> findAnyInDir(File directory, String name) {
        return
            Optional.ofNullable(
                findExactInDir(directory, name)
                    .orElse(findExecInDir(directory, name)
                        .orElse(findScriptInDir(directory, name)
                            .orElse(null))));
    }


    /**
     * Locates the given exact file in the system path. Returns empty if not found.
     */
    public Optional<File> findExactInPath(String name) {
        if (name.contains(File.separator)) {
            File candidate = new File(name);
            if (candidate.isFile()) {
                return Optional.of(candidate);
            }
            return Optional.empty();
        }
        for (File dir : getPath()) {
            File candidate = new File(dir, name);
            if (candidate.isFile()) {
                return Optional.of(candidate);
            }
        }
        return Optional.empty();
    }


    public Optional<File> findExecInPath(String name) {
        String exeName = getExecutableName(name);
        return findExactInPath(exeName);
    }

    public Optional<File> findScriptInPath(String name) {
        String scriptName = getScriptName(name);
        return findExactInPath(scriptName);
    }

    public Optional<File> findAnyInPath(String name) {
        return
            Optional.ofNullable(
                findExactInPath(name)
                    .orElse(findExecInPath(name)
                        .orElse(findScriptInPath(name)
                            .orElse(null))));
    }

    public List<File> findAllInPath(String name) {
        List<File> all = new LinkedList<File>();

        for (File dir : getPath()) {
            File candidate = new File(dir, name);
            if (candidate.isFile()) {
                all.add(candidate);
            }
        }

        return all;
    }

    public List<File> getPath() {
        String path = System.getenv(getPathVar());
        if (path == null) {
            return Collections.emptyList();
        }
        List<File> entries = new ArrayList<File>();
        for (String entry : path.split(Pattern.quote(File.pathSeparator))) {
            entries.add(new File(entry));
        }
        return entries;
    }

    public String getPathVar() {
        return "PATH";
    }


    static class Windows extends OperatingSystem {
        private final String nativePrefix;

        Windows() {
            nativePrefix = resolveNativePrefix();
        }

        @Override
        public boolean isWindows() {
            return true;
        }

        @Override
        public String getFamilyName() {
            return "windows";
        }

        @Override
        public String getScriptName(String scriptPath) {
            return withExtension(scriptPath, ".bat");
        }

        @Override
        public String getExecutableSuffix() {
            return ".exe";
        }

        @Override
        public String getExecutableName(String executablePath) {
            return withExtension(executablePath, ".exe");
        }

        @Override
        public String getSharedLibrarySuffix() {
            return ".dll";
        }

        @Override
        public String getSharedLibraryName(String libraryPath) {
            return withExtension(libraryPath, ".dll");
        }

        @Override
        public String getLinkLibrarySuffix() {
            return ".lib";
        }

        @Override
        public String getLinkLibraryName(String libraryPath) {
            return withExtension(libraryPath, ".lib");
        }

        @Override
        public String getStaticLibrarySuffix() {
            return ".lib";
        }

        @Override
        public String getStaticLibraryName(String libraryName) {
            return withExtension(libraryName, ".lib");
        }

        @Override
        public String getNativePrefix() {
            return nativePrefix;
        }

        private String resolveNativePrefix() {
            String arch = System.getProperty("os.arch");
            if ("i386".equals(arch)) {
                arch = "x86";
            }
            return "win32-" + arch;
        }

        @Override
        public String getPathVar() {
            return "Path";
        }
    }

    static class Unix extends OperatingSystem {
        private final String nativePrefix;

        Unix() {
            this.nativePrefix = resolveNativePrefix();
        }

        @Override
        public String getScriptName(String scriptPath) {
            return scriptPath;
        }

        @Override
        public String getFamilyName() {
            return "unknown";
        }

        @Override
        public String getExecutableSuffix() {
            return "";
        }

        @Override
        public String getExecutableName(String executablePath) {
            return executablePath;
        }

        @Override
        public String getSharedLibraryName(String libraryName) {
            return getLibraryName(libraryName, getSharedLibrarySuffix());
        }

        private String getLibraryName(String libraryName, String suffix) {
            if (libraryName.endsWith(suffix)) {
                return libraryName;
            }
            int pos = libraryName.lastIndexOf('/');
            if (pos >= 0) {
                return libraryName.substring(0, pos + 1) + "lib" + libraryName.substring(pos + 1) + suffix;
            } else {
                return "lib" + libraryName + suffix;
            }
        }

        @Override
        public String getSharedLibrarySuffix() {
            return ".so";
        }

        @Override
        public String getLinkLibrarySuffix() {
            return getSharedLibrarySuffix();
        }

        @Override
        public String getLinkLibraryName(String libraryPath) {
            return getSharedLibraryName(libraryPath);
        }

        @Override
        public String getStaticLibrarySuffix() {
            return ".a";
        }

        @Override
        public String getStaticLibraryName(String libraryName) {
            return getLibraryName(libraryName, ".a");
        }

        @Override
        public boolean isUnix() {
            return true;
        }

        @Override
        public String getNativePrefix() {
            return nativePrefix;
        }

        private String resolveNativePrefix() {
            String arch = getArch();
            String osPrefix = getOsPrefix();
            osPrefix += "-" + arch;
            return osPrefix;
        }

        protected String getArch() {
            String arch = System.getProperty("os.arch");
            if ("x86".equals(arch)) {
                arch = "i386";
            }
            if ("x86_64".equals(arch)) {
                arch = "amd64";
            }
            if ("powerpc".equals(arch)) {
                arch = "ppc";
            }
            return arch;
        }

        protected String getOsPrefix() {
            String osPrefix = getName().toLowerCase();
            int space = osPrefix.indexOf(" ");
            if (space != -1) {
                osPrefix = osPrefix.substring(0, space);
            }
            return osPrefix;
        }
    }

    static class MacOs extends Unix {
        @Override
        public boolean isMacOsX() {
            return true;
        }

        @Override
        public String getFamilyName() {
            return "os x";
        }

        @Override
        public String getSharedLibrarySuffix() {
            return ".dylib";
        }

        @Override
        public String getNativePrefix() {
            return "darwin";
        }
    }

    static class Linux extends Unix {
        @Override
        public boolean isLinux() {
            return true;
        }

        @Override
        public String getFamilyName() {
            return "linux";
        }
    }

    static class FreeBSD extends Unix {
    }

    static class Solaris extends Unix {
        @Override
        public String getFamilyName() {
            return "solaris";
        }

        @Override
        protected String getOsPrefix() {
            return "sunos";
        }

        @Override
        protected String getArch() {
            String arch = System.getProperty("os.arch");
            if (arch.equals("i386") || arch.equals("x86")) {
                return "x86";
            }
            return super.getArch();
        }
    }
}
