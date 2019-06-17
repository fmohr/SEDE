package de.upb.sede.edd.process;

import com.google.common.collect.ImmutableSet;

import java.io.File;
import java.nio.charset.Charset;
import java.util.*;

public class JvmOptions {
    private static final String XMS_PREFIX = "-Xms";
    private static final String XMX_PREFIX = "-Xmx";
    private static final String CLASSPATH_PREFIX = "-classpath";

    public static final String FILE_ENCODING_KEY = "file.encoding";
    public static final String USER_LANGUAGE_KEY = "user.language";
    public static final String USER_COUNTRY_KEY = "user.country";
    public static final String USER_VARIANT_KEY = "user.variant";
    public static final String JMX_REMOTE_KEY = "com.sun.management.jmxremote";
    public static final String JAVA_IO_TMPDIR_KEY = "java.io.tmpdir";

    public static final Set<String> IMMUTABLE_SYSTEM_PROPERTIES = ImmutableSet.of(
        FILE_ENCODING_KEY, USER_LANGUAGE_KEY, USER_COUNTRY_KEY, USER_VARIANT_KEY, JMX_REMOTE_KEY, JAVA_IO_TMPDIR_KEY
    );

    // Store this because Locale.default is mutable and we want the unchanged default
    // We are assuming this class will be initialized before any code has a chance to change the default
    private static final Locale DEFAULT_LOCALE = Locale.getDefault();

    private final List<Object> extraJvmArgs = new ArrayList<Object>();
    private final Map<String, Object> mutableSystemProperties = new TreeMap<String, Object>();

    private ClassPath cp = new ClassPath();
    private String minHeapSize;
    private String maxHeapSize;
    private boolean assertionsEnabled;
    private boolean debug;

    protected final Map<String, Object> immutableSystemProperties = new TreeMap<String, Object>();

    public JvmOptions() {
        immutableSystemProperties.put(FILE_ENCODING_KEY, Charset.defaultCharset().name());
        immutableSystemProperties.put(USER_LANGUAGE_KEY, DEFAULT_LOCALE.getLanguage());
        immutableSystemProperties.put(USER_COUNTRY_KEY, DEFAULT_LOCALE.getCountry());
        immutableSystemProperties.put(USER_VARIANT_KEY, DEFAULT_LOCALE.getVariant());
    }

    /**
     * @return all jvm args including system properties
     */
    public List<String> getAllJvmArgs() {
        List<String> args = new LinkedList<String>();
        formatSystemProperties(getMutableSystemProperties(), args);

        // We have to add these after the system properties so they can override any system properties
        // (identical properties later in the command line override earlier ones)
        args.addAll(getAllImmutableJvmArgs());

        return args;
    }

    protected void formatSystemProperties(Map<String, ?> properties, List<String> args) {
        for (Map.Entry<String, ?> entry : properties.entrySet()) {
            if (entry.getValue() != null && entry.getValue().toString().length() > 0) {
                args.add("-D" + entry.getKey() + "=" + entry.getValue().toString());
            } else {
                args.add("-D" + entry.getKey());
            }
        }
    }

    /**
     * @return all immutable jvm args. It excludes most system properties.
     * Only implicitly immutable system properties like "file.encoding" are included.
     * The result is a subset of options returned by {@link #getAllJvmArgs()}
     */
    public List<String> getAllImmutableJvmArgs() {
        List<String> args = new ArrayList<String>(getJvmArgs());
        args.addAll(getManagedJvmArgs());
        return args;
    }

    /**
     * @return the list of jvm args we manage explicitly, for example, max heaps size or file encoding.
     * The result is a subset of options returned by {@link #getAllImmutableJvmArgs()}
     */
    public List<String> getManagedJvmArgs() {
        List<String> args = new ArrayList<String>();
        if (minHeapSize != null) {
            args.add(XMS_PREFIX + minHeapSize);
        }
        if (maxHeapSize != null) {
            args.add(XMX_PREFIX + maxHeapSize);
        }
        if (cp != null && !cp.isEmpty()) {
            args.add(CLASSPATH_PREFIX);
            args.add(cp.getAsPath());
        }

        // These are implemented as a system property, but don't really function like one
        // So we include it in this “no system property” set.
//        formatSystemProperties(immutableSystemProperties, args);

        if (assertionsEnabled) {
            args.add("-ea");
        }
        return args;
    }

    public void setAllJvmArgs(Iterable<?> arguments) {
        mutableSystemProperties.clear();
        minHeapSize = null;
        maxHeapSize = null;
        extraJvmArgs.clear();
        assertionsEnabled = false;
        debug = false;
        jvmArgs(arguments);
    }

    public List<String> getJvmArgs() {
        List<String> args = new ArrayList<String>();
        for (Object extraJvmArg : extraJvmArgs) {
            args.add(extraJvmArg.toString());
        }
        return args;
    }

    public void jvmArgs(Object... arguments) {
        jvmArgs(Arrays.asList(arguments));
    }

    public Map<String, Object> getMutableSystemProperties() {
        return mutableSystemProperties;
    }

    public Map<String, Object> getImmutableSystemProperties() {
        return immutableSystemProperties;
    }

    public void setSystemProperties(Map<String, ?> properties) {
        mutableSystemProperties.clear();
        systemProperties(properties);
    }

    public void systemProperties(Map<String, ?> properties) {
        for (Map.Entry<String, ?> entry : properties.entrySet()) {
            systemProperty(entry.getKey(), entry.getValue());
        }
    }

    public void systemProperty(String name, Object value) {
        if (IMMUTABLE_SYSTEM_PROPERTIES.contains(name)) {
            immutableSystemProperties.put(name, value);
        } else {
            mutableSystemProperties.put(name, value);
        }
    }

    public ClassPath getClasspath() {
        return cp;
    }

    public void setClasspath(File... classpath) {
       cp.setFiles(classpath);
    }

    public void classpath(File... classpath) {
        cp.files(classpath);
    }

    public String getMinHeapSize() {
        return minHeapSize;
    }

    public void setMinHeapSize(String heapSize) {
        this.minHeapSize = heapSize;
    }

    public String getMaxHeapSize() {
        return maxHeapSize;
    }

    public void setMaxHeapSize(String heapSize) {
        this.maxHeapSize = heapSize;
    }

    public String getDefaultCharacterEncoding() {
        return immutableSystemProperties.get(FILE_ENCODING_KEY).toString();
    }

    public void setDefaultCharacterEncoding(String defaultCharacterEncoding) {
        immutableSystemProperties.put(FILE_ENCODING_KEY, (!defaultCharacterEncoding.isEmpty()) ? defaultCharacterEncoding : Charset.defaultCharset().name());
    }

    public boolean getEnableAssertions() {
        return assertionsEnabled;
    }

    public void setEnableAssertions(boolean enabled) {
        assertionsEnabled = enabled;
    }

    public boolean getDebug() {
        return debug;
    }

    public void setDebug(boolean enabled) {
        debug = enabled;
    }

}
