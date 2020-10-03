package ai.services.util;

import java.net.URI;
import java.util.Arrays;

import static java.util.Objects.requireNonNull;

public class URIMod {


    public static String addPath(String uri, String subPath) {
        requireNonNull(uri);
        requireNonNull(subPath);
        if(uri.isEmpty() || subPath.isEmpty()) {
            throw new IllegalArgumentException("Uri or subpath are empty...");
        }

        if(uri.contains("?")) {
            String[] docFragSplit = uri.split("\\?");
            if(docFragSplit.length != 2) {
                throw new IllegalArgumentException("Uri has multiple '?': " + uri + ". Splits: " + Arrays.toString(docFragSplit));
            }
            String docUri = docFragSplit[0];
            String fragment = docFragSplit[1];
            return addPath(docUri, subPath) + "?" + fragment;
        }
        if(uri.contains("#")) {
            String[] docFragSplit = uri.split("#");
            if(docFragSplit.length != 2) {
                throw new IllegalArgumentException("Uri has multiple '#': " + uri);
            }
            String docUri = docFragSplit[0];
            String fragment = docFragSplit[1];
            return addPath(docUri, subPath) + "#" + fragment;
        }
        if(!uri.endsWith("/")) {
            uri += "/";
        }
        return uri + subPath;
    }


    public static String setPort(String host, int port) {
        if(port < 0 || port > 65535) {
            throw new IllegalArgumentException("Illegal port: " + port);
        }
        String[] colonSplit = host.split(":");
        if(colonSplit.length == 1) {
            /*
             * No colons
             */
            return host += ":" + port;
        } else if(colonSplit.length == 2) {
//            throw new IllegalArgumentException("Ambiguous colon in host: " + host);
            // TODO reconsider assumption that colon belongs to port
            return colonSplit[0] + ":" + port;
        } else if(colonSplit.length ==3){
            return colonSplit[0] + ":" + colonSplit[1] + ":" + port;
        }
        else {
            throw new IllegalArgumentException("Invalid host: " + host);
        }
    }

    public static String setScheme(String uri, String scheme) {
        if(uri.startsWith(scheme)) {
            return uri;
        } else {
            return scheme + uri;
        }
    }

    public static String setHTTPScheme(String uri) {
        return setScheme(uri, "http://");
    }

}
