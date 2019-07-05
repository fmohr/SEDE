package de.upb.sede.edd.deploy;

import de.upb.sede.util.Uncheck;

import java.net.URI;
import java.net.URISyntaxException;

public class AscribedService {

    private SpecURI uri;

    private String service;

    public AscribedService(String scheme, URI marketServiceURI, String service) {
        this.uri = new SpecURI(scheme, marketServiceURI);
        this.service = service;
    }

    public AscribedService(SpecURI uri, String service) {
        this.uri = uri;
        this.service = service;
    }

    public static AscribedService fromGateway(String gatewayAddress, String service) {
        return new AscribedService(new SpecURI("http", URI.create(gatewayAddress)), service);
    }

    public static AscribedService fromClasspath(String filePath, String service) {
        while(!filePath.startsWith("//")) {
            filePath = "/" + filePath;
        }
        return new AscribedService(new SpecURI("classpath", URI.create(filePath)), service);
    }

    public static AscribedService fromFile(String filePath, String service) {
        while(!filePath.startsWith("//")) {
            filePath = "/" + filePath;
        }
        return new AscribedService(new SpecURI("file", URI.create(filePath)), service);
    }

    public SpecURI getSpecUri() {
        return uri;
    }

    public String getService() {
        return service;
    }

    public static AscribedService parseURI(String uriText) {
        return parseURI(uriText, false);
    }

    private static AscribedService parseURI(String uriText, boolean schemaAppended) {
        try {
            URI uri = new URI(uriText);
            String service = uri.getFragment();
            URI fragmentLessUri = new URI(null, uri.getUserInfo(), uri.getHost(), uri.getPort(), uri.getPath(), uri.getQuery(), null);
            return new AscribedService(uri.getScheme(), fragmentLessUri, service);
        } catch (URISyntaxException e) {
            if(!schemaAppended)
                return parseURI("http://" + uriText, true);
            else
                throw Uncheck.throwAsUncheckedException(e);
        }
    }
}
