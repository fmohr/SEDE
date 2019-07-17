package de.upb.sede.util;

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


//    public static String setPort(String uri, int port) {
//        URI givenUri = URI.create(uri);
//        URI outputUri = new URI(uri)
//        String[] colonSplit = uri.split(":");
//        if(colonSplit.length == 1) {
//            /*
//             * No colons
//             */
//
//        }
//    }

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
