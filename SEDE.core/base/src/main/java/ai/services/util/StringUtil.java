package ai.services.util;

import java.io.PrintWriter;
import java.io.StringWriter;

public class StringUtil {

    public static boolean hasLength(String s) {
        return s != null && !s.isEmpty();
    }

    public static String hasLength(String s, String errorMessage) {
        if(!hasLength(s)) {
            throw new IllegalArgumentException(errorMessage);
        }
        return s;
    }

    public static boolean hasText(String s) {
        if(!hasLength(s)) {
            return false;
        }
        int strLen = s.length();
        for (int i = 0; i < strLen; i++) {
            if (!Character.isWhitespace(s.charAt(i))) {
                return true;
            }
        }
        return false;
    }

    public static String unexpectedTypeMsg(String expectedType, Object object) {
        return String.format("Expected type '%s' but found object of unexpected type: Class:'%s'\n .toString(): %s",
            expectedType, object.getClass().toString(), object.toString());
    }

    /**
     * Returns the stacktrace of the given exception as a String.
     * @param ex Exception whose stack trace will be returned.
     * @return Stack trace of the given exception.
     */
    public static String ErrToString(Exception ex) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        ex.printStackTrace(pw);
        String sStackTrace = sw.toString();
        return sStackTrace;
    }
}
