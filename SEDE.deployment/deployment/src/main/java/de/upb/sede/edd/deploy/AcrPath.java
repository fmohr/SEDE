package de.upb.sede.edd.deploy;

import javax.annotation.concurrent.Immutable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Acronym Path.
 * A Path that starts off with a acronym.
 */
@Immutable
public class AcrPath {

    private final static String REGEX_GROUP_NAME_ACR = "acr";
    private final static String REGEX_GROUP_NAME_PATH = "path";

    /**
     * e.g.
     * Matches:
     *      [0]/path/to/file
     * Groups:
     *      acr = 0
     *      path = path/to/file
     */
    private final static String REGEX_EXPRESSION_ACRPATH =
        "^" + // matches start
            "(\\[(?<"+ REGEX_GROUP_NAME_ACR  + ">[a-zA-Z0-9\\._-]+)\\]/)?" + // matches optional base acronym. Defaults to "[0]"
                "(?<"+ REGEX_GROUP_NAME_PATH + ">[a-zA-Z0-9\\._-]+(/[a-zA-Z0-9\\._-]+)*)" + // matches the path to the file.
            "/?" + // optionally end with slash. (Is not captured)
        "$" // matches end
    ;

    private final static Pattern PATTERN_ACRPATH = Pattern.compile(REGEX_EXPRESSION_ACRPATH);

    private final static String defaultAcr = "0"; // the first acr

    private final String path, acr;

    public AcrPath(String acr, String path) {
        this.acr = acr;
        this.path = path;
    }

    public static AcrPath parse(String acrPath) {
        Matcher matcher = PATTERN_ACRPATH.matcher(acrPath);
        boolean match = matcher.matches();
        if(match) {
            String acr = matcher.group(REGEX_GROUP_NAME_ACR);
            if(acr == null || acr.isEmpty()) {
                acr = defaultAcr;
            }
            String path = matcher.group(REGEX_GROUP_NAME_PATH);
            if(path == null || path.isEmpty()) {
                throw new IllegalArgumentException("The given acr-path doesnt contain any path: " + acrPath);
            }
            return new AcrPath(acr, path);
        } else {
            throw new IllegalArgumentException("The given acr-path cannot be parsed: " + acrPath);
        }
    }

    public String getPath() {
        return path;
    }

    public String getAcr() {
        return acr;
    }
}
