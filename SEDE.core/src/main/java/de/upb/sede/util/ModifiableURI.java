package de.upb.sede.util;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;

import java.net.URI;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static de.upb.sede.util.StringUtil.hasLength;
import static de.upb.sede.util.StringUtil.hasText;

public class ModifiableURI extends UnmodifiableURI implements ResourceIdentifier {

    private static final Pattern QUERY_PARAM_PATTERN = Pattern.compile("([^&=]+)(=?)([^&]+)?");

    private static final String SCHEME_PATTERN = "([^:/?#]+):";

    private static final String HTTP_PATTERN = "(?i)(http|https):";

    private static final String USERINFO_PATTERN = "([^@\\[/?#]*)";

    private static final String HOST_IPV4_PATTERN = "[^\\[/?#:]*";

    private static final String HOST_IPV6_PATTERN = "\\[[\\p{XDigit}\\:\\.]*[%\\p{Alnum}]*\\]";

    private static final String HOST_PATTERN = "(" + HOST_IPV6_PATTERN + "|" + HOST_IPV4_PATTERN + ")";

    private static final String PORT_PATTERN = "(\\d*(?:\\{[^/]+?\\})?)";

    private static final String PATH_PATTERN = "([^?#]*)";

    private static final String QUERY_PATTERN = "([^#]*)";

    private static final String LAST_PATTERN = "(.*)";

    // Regex patterns that matches URIs. See RFC 3986, appendix B
    private static final Pattern URI_PATTERN = Pattern.compile(
        "^(" + SCHEME_PATTERN + ")?" + "(//(" + USERINFO_PATTERN + "@)?" + HOST_PATTERN + "(:" + PORT_PATTERN +
            ")?" + ")?" + PATH_PATTERN + "(\\?" + QUERY_PATTERN + ")?" + "(#" + LAST_PATTERN + ")?");

    private static final Pattern HTTP_URL_PATTERN = Pattern.compile(
        "^" + HTTP_PATTERN + "(//(" + USERINFO_PATTERN + "@)?" + HOST_PATTERN + "(:" + PORT_PATTERN + ")?" + ")?" +
            PATH_PATTERN + "(\\?" + LAST_PATTERN + ")?");

    private static final Object[] EMPTY_VALUES = new Object[0];


    /**
     * Default constructor. Protected to prevent direct instantiation.
     * @see #newInstance()
     * @see #fromPath(String)
     */
    public ModifiableURI() {
    }

    /**
     * Create a deep copy of the given MutableURI.
     * @param other the other builder to copy from
     * @since 4.1.3
     */
    public ModifiableURI(ResourceIdentifier other) {
        uri(other);
    }


    /**
     * Create a new, empty builder.
     * @return the new {@code MutableURI}
     */
    public static ModifiableURI newInstance() {
        return new ModifiableURI();
    }

    /**
     * Create a builder that is initialized with the given path.
     * @param path the path to initialize with
     * @return the new {@code MutableURI}
     */
    public static ModifiableURI fromPath(String path) {
        ModifiableURI builder = new ModifiableURI();
        builder.path(path);
        return builder;
    }

    /**
     * Create a builder that is initialized with the given {@code URI}.
     * @param uri the URI to initialize with
     * @return the new {@code MutableURI}
     */
    public static ModifiableURI fromUri(URI uri) {
        ModifiableURI builder = new ModifiableURI();
        builder.uri(uri);
        return builder;
    }

    /**
     * Create a builder that is initialized with the given URI string.
     * <p><strong>Note:</strong> The presence of reserved characters can prevent
     * correct parsing of the URI string. For example if a query parameter
     * contains {@code '='} or {@code '&'} characters, the query string cannot
     * be parsed unambiguously.
     * @param uri the URI string to initialize with
     * @return the new {@code MutableURI}
     */
    public static ModifiableURI fromUriString(String uri) {
        Objects.requireNonNull(uri, "URI must not be null");
        Matcher matcher = URI_PATTERN.matcher(uri);
        if (matcher.matches()) {
            ModifiableURI builder = new ModifiableURI();
            String scheme = matcher.group(2);
            String userInfo = matcher.group(5);
            String host = matcher.group(6);
            String port = matcher.group(8);
            String path = matcher.group(9);
            String query = matcher.group(11);
            String fragment = matcher.group(13);

            builder.scheme(scheme);
            builder.userInfo(userInfo);
            builder.host(host);
            if (hasLength(port)) {
                builder.port(port);
            }
            builder.replacePath(path);
            builder.query(query);

            if (hasText(fragment)) {
                builder.fragment(fragment);
            }

            return builder;
        }
        else {
            throw new IllegalArgumentException("[" + uri + "] is not a valid URI");
        }
    }



    /**
     * Create a Mutable URI from the given HTTP URL String.
     * <p><strong>Note:</strong> The presence of reserved characters can prevent
     * correct parsing of the URI string. For example if a query parameter
     * contains {@code '='} or {@code '&'} characters, the query string cannot
     * be parsed unambiguously.
     * @param httpUrl the source URI
     * @return the URI components of the URI
     */
    public static ModifiableURI fromHttpUrl(String httpUrl) {
        Objects.requireNonNull(httpUrl, "HTTP URL must not be null");
        Matcher matcher = HTTP_URL_PATTERN.matcher(httpUrl);
        if (matcher.matches()) {
            ModifiableURI builder = new ModifiableURI();
            String scheme = matcher.group(1);
            builder.scheme(scheme != null ? scheme.toLowerCase() : null);
            builder.userInfo(matcher.group(4));
            String host = matcher.group(5);
            if (hasLength(scheme) && !hasLength(host)) {
                throw new IllegalArgumentException("[" + httpUrl + "] is not a valid HTTP URL");
            }
            builder.host(host);
            String port = matcher.group(7);
            if (hasLength(port)) {
                builder.port(port);
            }
            builder.path(matcher.group(8));
            builder.query(matcher.group(10));
            return builder;
        }
        else {
            throw new IllegalArgumentException("[" + httpUrl + "] is not a valid HTTP URL");
        }
    }


    // Instance methods

    /**
     * Initialize components of this builder from components of the given URI.
     * @param uri the URI
     * @return this MutableURI
     */
    public ModifiableURI uri(URI uri) {
        Objects.requireNonNull(uri, "URI must not be null");
        this.scheme = uri.getScheme();
        if (uri.isOpaque()) {
            throw new IllegalArgumentException("Opaque uris are not supported..");
        }
        else {
            if (uri.getRawUserInfo() != null) {
                this.userInfo = uri.getRawUserInfo();
            }
            if (uri.getHost() != null) {
                this.host = uri.getHost();
            }
            if (uri.getPort() != -1) {
                this.port = uri.getPort();
            }
            if (hasLength(uri.getRawPath())) {
                setPath(uri.getRawPath());
            }
            if (hasLength(uri.getRawQuery())) {
                this.queryParams.clear();
                query(uri.getRawQuery());
            }
        }
        if (uri.getRawFragment() != null) {
            this.fragment = uri.getRawFragment();
        }
        return this;
    }

    public ModifiableURI uri(ResourceIdentifier uri) {
        scheme = uri.getScheme().orElse(scheme);
        userInfo = uri.getUserInfo().orElse(userInfo);
        host = uri.getHost().orElse(host);
        port = uri.getPort().orElse(port);

        uri.getPath().ifPresent(this::setPath);
        replaceQueryParams(uri.getQueryParams());

        fragment = uri.getFragment().orElse(fragment);

        return this;
    }

    public UnmodifiableURI unmodifiableCopy() {
        ArrayListMultimap<String, String> queryParamsCopy = ArrayListMultimap.create(queryParams);
        return new UnmodifiableURI(scheme, userInfo, host, port, path, queryParamsCopy, fragment);
    }

    public ResourceIdentifier unmodifiableView() {
        return new ResourceIdentifier() {
            public Optional<String> getScheme() {
                return Optional.ofNullable(scheme);
            }

            public Optional<String> getUserInfo() {
                return Optional.ofNullable(userInfo);
            }

            public Optional<String> getHost() {
                return Optional.ofNullable(host);
            }

            public Optional<Integer> getPort() {
                return Optional.ofNullable(port);
            }

            public Optional<String> getPath() {
                return Optional.ofNullable(path);
            }

            public Multimap<String, String> getQueryParams() {
                return queryParams;
            }

            public Optional<String> getFragment() {
                return Optional.ofNullable(fragment);
            }
        };
    }

    /**
     * Set or append individual URI components of this builder from the values
     * of the given {@link ModifiableURI} instance.
     * <p>For the semantics of each component (i.e. set vs append) check the
     * builder methods on this class. For example {@link #host(String)} sets
     * while {@link #path(String)} appends.
     * @param uriComponents the MutableUri to copy from
     * @return this MutableURI
     */
    public ModifiableURI overwriteWith(ModifiableURI uriComponents) {
        Objects.requireNonNull(uriComponents, "MutableUri must not be null");
        uriComponents.overwrite(this);
        return this;
    }

    public ModifiableURI overwrite(ModifiableURI uri) {
        Objects.requireNonNull(uri, "MutableUri must not be null");

        getScheme().ifPresent(uri::scheme);
        getUserInfo().ifPresent(uri::userInfo);
        getHost().ifPresent(uri::host);
        getPort().ifPresent(uri::port);
        getPath().ifPresent(uri::replacePath);
        uri.queryParams(getQueryParams());
        getFragment().ifPresent(uri::fragment);
        return this;
    }

    /**
     * Set the URI scheme.
     * @param scheme the URI scheme
     * @return this MutableURI
     */
    public ModifiableURI scheme(/* nullable */ String scheme) {
        this.scheme = scheme;
        return this;
    }

    /**
     * Set the URI user info.
     * @param userInfo the URI user info
     * @return this MutableURI
     */
    public ModifiableURI userInfo(/* nullable */ String userInfo) {
        this.userInfo = userInfo;
        return this;
    }

    /**
     * Set the URI host.
     * @param host the URI host
     * @return this MutableURI
     */
    public ModifiableURI host(/* nullable */ String host) {
        this.host = host;
        return this;
    }

    /**
     * Set the URI port. Passing {@code -1} will clear the port of this builder.
     * @param port the URI port
     * @return this MutableURI
     */
    public ModifiableURI port(int port) {
        if(port== -1) {
            this.port = null;
        }
        if (port < 0) throw new IllegalArgumentException("Port must be >= 0");
        this.port = port;
        return this;
    }

    /**
     * Set the URI port. use {@link #port(int)}.
     * Passing {@code null} will clear the port of this builder.
     * @param port the URI port
     * @return this MutableURI
     */
    public ModifiableURI port(/* nullable */ String port) {
        if(port == null) {
            this.port = null;
            return this;
        }
        return port(Integer.parseInt(port));
    }

    /**
     * Append the given path to the existing path of this builder.
     * @param path the URI path
     * @return this MutableURI
     */
    public ModifiableURI path(String path) {
        String currentPath = getPath().orElse("");

        if(path.startsWith("/")) {
            path = path.substring(1);
        }

        if(!currentPath.endsWith("/")) {
            currentPath += "/";
        }

        currentPath += path;
        setPath(currentPath);
        return this;
    }

    /**
     * Append path segments to the existing path.
     * Path segments should not contain any slashes except at the last position.
     * null is treated as empty path segment and is therefore encoded as "//".
     *
     * @param pathSegments the URI path segments
     * @return this MutableURI
     */
    public ModifiableURI pathSegment(String... pathSegments) throws IllegalArgumentException {
        if(Objects.requireNonNull(pathSegments).length == 0) throw new IllegalArgumentException("No segments were provided.");
        String currentPath = this.path;
        if(currentPath == null) {
            currentPath = "/";
        }
        for(String segment : pathSegments) {
            if(!currentPath.endsWith("/")) {
               currentPath += "/";
            }
            if(segment == null) {
                currentPath += "/";
                continue;
            }
            currentPath += segment;
        }
        setPath(currentPath);
        return this;
    }

    /**
     * Set the path of this builder overriding all existing path and path segment values.
     * @param path the URI path (a {@code null} value results in an empty path)
     * @return this MutableURI
     */
    public ModifiableURI replacePath(/* nullable */ String path) {
        if(!StringUtil.hasLength(path)) {
            unsetPath();
            return this;
        }
        setPath(path);
        return this;
    }

    private void setPath(String path) {
//        if(!path.startsWith("/")) {
//            path = "/" + path;
//        }
        this.path = path;
    }


    /**
     * Formats the given pattern and replaces the path.
     *
     * @param pattern Format pattern
     * @param argument format arguments
     * @return this
     */
    public ModifiableURI formatPath(String pattern, Object... argument) {
        if(!StringUtil.hasLength(pattern)) {
            throw new IllegalArgumentException("Path must not be empty.");
        }
        replacePath(String.format(pattern, argument));
        return this;
    }

    public ModifiableURI unsetPath() {
        this.path = null;
        return this;
    }



    /**
     * Append the given query to the existing query of this builder.
     * <p><strong>Note:</strong> The presence of reserved characters can prevent
     * correct parsing of the URI string. For example if a query parameter
     * contains {@code '='} or {@code '&'} characters, the query string cannot
     * be parsed unambiguously.
     * @param query the query string
     * @return this MutableURI
     */
    public ModifiableURI query(/* nullable */ String query) {
        if (query != null) {
            Matcher matcher = QUERY_PARAM_PATTERN.matcher(query);
            while (matcher.find()) {
                String name = matcher.group(1);
                String eq = matcher.group(2);
                String value = matcher.group(3);
                queryParam(name, (value != null ? value : (hasLength(eq) ? "" : null)));
            }
        }
        else {
            this.queryParams.clear();
        }
        return this;
    }

    /**
     * Set the query of this builder overriding all existing query parameters.
     * @param query the query string; a {@code null} value removes all query parameters.
     * @return this MutableURI
     */
    public ModifiableURI replaceQuery(/* nullable */ String query) {
        this.queryParams.clear();
        if (query != null) {
            query(query);
        }
        return this;
    }

    /**
     * Append the given query parameter to the existing query parameters.
     * @param name the query parameter name
     * @param values the query parameter values
     * @return this MutableURI
     * @see #queryParam(String, Collection)
     */
    public ModifiableURI queryParam(String name, Object... values) {
        Objects.requireNonNull(name, "Name must not be null");
        if (values!=null && values.length > 0) {
            for (Object value : values) {
                String valueAsString = (value != null ? value.toString() : null);
                this.queryParams.put(name, valueAsString);
            }
        }
        else {
            this.queryParams.put(name, null);
        }
        return this;
    }

    /**
     * Append the given query parameter to the existing query parameters.
     * @param name the query parameter name
     * @param values the query parameter values
     * @return this MutableURI
     * @since 5.2.0
     * @see #queryParam(String, Object...)
     */
    public ModifiableURI queryParam(String name, /* nullable */ Collection<?> values) {
        return queryParam(name, values != null ? values.toArray() : EMPTY_VALUES);
    }

    /**
     * Add the given query parameters.
     * @param params the params
     * @return this MutableURI
     * @since 4.0
     */
    public ModifiableURI queryParams(/* nullable */ Multimap<String, String> params) {
        if (params != null) {
            this.queryParams.putAll(params);
        }
        return this;
    }

    /**
     * Set the query parameter values overriding all existing query values for
     * the same parameter. If no values are given, the query parameter is removed.
     * @param name the query parameter name
     * @param values the query parameter values
     * @return this MutableURI
     * @see #replaceQueryParam(String, Collection)
     */
    public ModifiableURI replaceQueryParam(String name, Object... values) {
        Objects.requireNonNull(name, "Name must not be null");
        this.queryParams.removeAll(name);
        if (values != null && values.length > 0) {
            queryParam(name, values);
        }
        return this;
    }

    /**
     * Set the query parameter values overriding all existing query values for
     * the same parameter. If no values are given, the query parameter is removed.
     * @param name the query parameter name
     * @param values the query parameter values
     * @return this MutableURI
     * @see #replaceQueryParam(String, Object...)
     * @since 5.2.0
     */
    public ModifiableURI replaceQueryParam(String name, /* nullable */ Collection<?> values) {
        return replaceQueryParam(name, values != null ? values.toArray() : EMPTY_VALUES);
    }

    /**
     * Set the query parameter values overriding all existing query values.
     * @param params the query parameter name
     * @return this MutableURI
     * @since 4.2
     */
    public ModifiableURI replaceQueryParams(/* nullable */ Multimap<String, String> params) {
        this.queryParams.clear();
        if (params != null) {
            this.queryParams.putAll(params);
        }
        return this;
    }

    /**
     * Set the URI fragment. May also be {@code null} to clear the fragment of this builder.
     * @param fragment the URI fragment
     * @return this MutableURI
     */
    public ModifiableURI fragment(String fragment) {
        if (fragment != null) {
            hasLength(fragment, "Fragment must not be empty");
            this.fragment = fragment;
        }
        else {
            this.fragment = null;
        }
        return this;
    }


    public void discardEmptyPathSegments() {
        if(!getPath().isPresent()) {
            return;
        }
        String tmp = path;
        while(tmp.startsWith("/")) {
            tmp = tmp.substring(1);
        }
        while (true) {
            int index = tmp.indexOf("//");
            if (index == -1) {
                break;
            }
            tmp = tmp.substring(0, index) + tmp.substring(index + 1);
        }
        setPath(tmp);
    }

    public String toString() {
        return buildString();
    }

    public ModifiableURI interpretPathAsHost() {
        if (!getHost().isPresent() && getPath().isPresent()) {
            host(getPath().get());
            replacePath(null);
        }
        return this;
    }

    public ModifiableURI amendHttpScheme() {
        if(!getScheme().isPresent())
            scheme("http");
        return this;
    }

    public boolean isHTTPHostAddress() {
        if(getScheme()
            .map(s -> !s.equals("http") && !s.equals("https"))
            .orElse(true)) {
            return false;
        }
        if(getHost()
            .map(s -> !hasText(s))
            .orElse(true)) {
            return false;
        }
        if(getPath().isPresent() || !getQueryParams().isEmpty() || getFragment().isPresent()) {
            return false;
        }
        return true;
    }

    public UnmodifiableURI unmod() {
        return unmodifiableCopy();
    }
}
