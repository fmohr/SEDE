package de.upb.sede.edd.deploy;

import de.upb.sede.edd.DefaultLockableDir;
import de.upb.sede.util.Uncheck;

import java.net.URI;
import java.net.URISyntaxException;

public class SpecURI {

    private String scheme;

    private URI marketServiceURI;

    public SpecURI(String scheme, URI marketServiceURI) {
        this.scheme = scheme == null ? "http" : scheme;
        this.marketServiceURI = marketServiceURI;

        if(marketServiceURI.getScheme() != null) {
            throw new IllegalArgumentException("Scheme needs to be  separated from the uri: " + marketServiceURI.getScheme());
        }
    }

    public URI getURI() {
        return marketServiceURI;
    }

    public String getAddress() {
        return marketServiceURI.toString().substring(2);
    }

    public String getEncodedAddress() {
        return DefaultLockableDir.urlEncode(getAddress());
    }

    @Override
    public String toString() {
        return scheme + "://" + getAddress();
    }

    public String getScheme() {
        return scheme;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SpecURI specURI = (SpecURI) o;

        if (!scheme.equals(specURI.scheme)) return false;
        return marketServiceURI.equals(specURI.marketServiceURI);
    }

    @Override
    public int hashCode() {
        int result = scheme.hashCode();
        result = 31 * result + marketServiceURI.hashCode();
        return result;
    }

    public static SpecURI parseURI(String uriText) {
        try {
            URI uri = new URI(uriText);
            String service = uri.getFragment();
            URI fragmentLessUri = new URI(null, uri.getUserInfo(), uri.getHost(), uri.getPort(), uri.getPath(), uri.getQuery(), null);
            return new SpecURI(uri.getScheme(), fragmentLessUri);
        } catch (URISyntaxException e) {
            throw Uncheck.throwAsUncheckedException(e);
        }
    }
}
