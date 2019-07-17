/*
 * Inspired and partially taken from:
 *
 * https://github.com/spring-projects/spring-framework/blob/master/spring-web/src/main/java/org/springframework/web/util/MutableURI.java
 *
 * Copyright 2002-2019 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */
package de.upb.sede.util;

import java.net.URI;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MutableURI {

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

    private static final Pattern FORWARDED_HOST_PATTERN = Pattern.compile("host=\"?([^;,\"]+)\"?");

    private static final Pattern FORWARDED_PROTO_PATTERN = Pattern.compile("proto=\"?([^;,\"]+)\"?");

    private static final Object[] EMPTY_VALUES = new Object[0];


    /* Nullable */
    private String scheme;

    /* Nullable */
    private String userInfo;

    /* Nullable */
    private String host;

    /* Nullable */
    private String port;

    /* Nullable */
    private String path;

    private final List<Map.Entry<String, String>> queryParams = new ArrayList<>();

    /* Nullable */
    private String fragment;

    private final Map<String, Object> uriVariables = new HashMap<>(4);

    private boolean encodeTemplate;

    private Charset charset = StandardCharsets.UTF_8;


    /**
     * Default constructor. Protected to prevent direct instantiation.
     * @see #newInstance()
     * @see #fromPath(String)
     */
    protected MutableURI() {
    }

    /**
     * Create a deep copy of the given MutableURI.
     * @param other the other builder to copy from
     * @since 4.1.3
     */
    protected MutableURI(MutableURI other) {
        this.scheme = other.scheme;
        this.ssp = other.ssp;
        this.userInfo = other.userInfo;
        this.host = other.host;
        this.port = other.port;
        this.queryParams.addAll(other.queryParams);
        this.fragment = other.fragment;
        this.encodeTemplate = other.encodeTemplate;
        this.charset = other.charset;
    }


    /**
     * Create a new, empty builder.
     * @return the new {@code MutableURI}
     */
    public static MutableURI newInstance() {
        return new MutableURI();
    }

    /**
     * Create a builder that is initialized with the given path.
     * @param path the path to initialize with
     * @return the new {@code MutableURI}
     */
    public static MutableURI fromPath(String path) {
        MutableURI builder = new MutableURI();
        builder.path(path);
        return builder;
    }

    /**
     * Create a builder that is initialized with the given {@code URI}.
     * @param uri the URI to initialize with
     * @return the new {@code MutableURI}
     */
    public static MutableURI fromUri(URI uri) {
        MutableURI builder = new MutableURI();
        builder.uri(uri);
        return builder;
    }

    private static boolean hasLength(String s) {
        return s != null && !s.isEmpty();
    }

    private static boolean hasText(String s) {
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

    /**
     * Create a builder that is initialized with the given URI string.
     * <p><strong>Note:</strong> The presence of reserved characters can prevent
     * correct parsing of the URI string. For example if a query parameter
     * contains {@code '='} or {@code '&'} characters, the query string cannot
     * be parsed unambiguously. Such values should be substituted for URI
     * variables to enable correct parsing:
     * <pre class="code">
     * String uriString = &quot;/hotels/42?filter={value}&quot;;
     * MutableURI.fromUriString(uriString).buildAndExpand(&quot;hot&amp;cold&quot;);
     * </pre>
     * @param uri the URI string to initialize with
     * @return the new {@code MutableURI}
     */
    public static MutableURI fromUriString(String uri) {
        Objects.requireNonNull(uri, "URI must not be null");
        Matcher matcher = URI_PATTERN.matcher(uri);
        if (matcher.matches()) {
            MutableURI builder = new MutableURI();
            String scheme = matcher.group(2);
            String userInfo = matcher.group(5);
            String host = matcher.group(6);
            String port = matcher.group(8);
            String path = matcher.group(9);
            String query = matcher.group(11);
            String fragment = matcher.group(13);
            boolean opaque = false;
            if (scheme != null && !scheme.isEmpty()) {
                String rest = uri.substring(scheme.length());
                if (!rest.startsWith(":/")) {
                    opaque = true;
                }
            }
            builder.scheme(scheme);
            if (opaque) {
                String ssp = uri.substring(scheme.length()).substring(1);
                if (hasLength(fragment)) {
                    ssp = ssp.substring(0, ssp.length() - (fragment.length() + 1));
                }
                builder.schemeSpecificPart(ssp);
            }
            else {
                builder.userInfo(userInfo);
                builder.host(host);
                if (hasLength(port)) {
                    builder.port(port);
                }
                builder.path(path);
                builder.query(query);
            }
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
     * Create a URI components builder from the given HTTP URL String.
     * <p><strong>Note:</strong> The presence of reserved characters can prevent
     * correct parsing of the URI string. For example if a query parameter
     * contains {@code '='} or {@code '&'} characters, the query string cannot
     * be parsed unambiguously. Such values should be substituted for URI
     * variables to enable correct parsing:
     * <pre class="code">
     * String urlString = &quot;https://example.com/hotels/42?filter={value}&quot;;
     * MutableURI.fromHttpUrl(urlString).buildAndExpand(&quot;hot&amp;cold&quot;);
     * </pre>
     * @param httpUrl the source URI
     * @return the URI components of the URI
     */
    public static MutableURI fromHttpUrl(String httpUrl) {
        Objects.requireNonNull(httpUrl, "HTTP URL must not be null");
        Matcher matcher = HTTP_URL_PATTERN.matcher(httpUrl);
        if (matcher.matches()) {
            MutableURI builder = new MutableURI();
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
    public MutableURI uri(URI uri) {
        Objects.requireNonNull(uri, "URI must not be null");
        this.scheme = uri.getScheme();
        if (uri.isOpaque()) {
            resetHierarchicalComponents();
        }
        else {
            if (uri.getRawUserInfo() != null) {
                this.userInfo = uri.getRawUserInfo();
            }
            if (uri.getHost() != null) {
                this.host = uri.getHost();
            }
            if (uri.getPort() != -1) {
                this.port = String.valueOf(uri.getPort());
            }
            if (hasLength(uri.getRawPath())) {
                this.pathBuilder = new CompositePathComponentBuilder();
                this.pathBuilder.addPath(uri.getRawPath());
            }
            if (hasLength(uri.getRawQuery())) {
                this.queryParams.clear();
                query(uri.getRawQuery());
            }
            resetSchemeSpecificPart();
        }
        if (uri.getRawFragment() != null) {
            this.fragment = uri.getRawFragment();
        }
        return this;
    }

    /**
     * Set or append individual URI components of this builder from the values
     * of the given {@link UriComponents} instance.
     * <p>For the semantics of each component (i.e. set vs append) check the
     * builder methods on this class. For example {@link #host(String)} sets
     * while {@link #path(String)} appends.
     * @param uriComponents the UriComponents to copy from
     * @return this MutableURI
     */
    public MutableURI uriComponents(UriComponents uriComponents) {
        Objects.requireNonNull(uriComponents, "UriComponents must not be null");
        uriComponents.copyToMutableURI(this);
        return this;
    }

    /**
     * Set the URI scheme. The given scheme may contain URI template variables,
     * and may also be {@code null} to clear the scheme of this builder.
     * @param scheme the URI scheme
     * @return this MutableURI
     */
    @Override
    public MutableURI scheme(@Nullable String scheme) {
        this.scheme = scheme;
        return this;
    }

    /**
     * Set the URI scheme-specific-part. When invoked, this method overwrites
     * {@linkplain #userInfo(String) user-info}, {@linkplain #host(String) host},
     * {@linkplain #port(int) port}, {@linkplain #path(String) path}, and
     * {@link #query(String) query}.
     * @param ssp the URI scheme-specific-part, may contain URI template parameters
     * @return this MutableURI
     */
    public MutableURI schemeSpecificPart(String ssp) {
        this.ssp = ssp;
        resetHierarchicalComponents();
        return this;
    }

    /**
     * Set the URI user info. The given user info may contain URI template variables,
     * and may also be {@code null} to clear the user info of this builder.
     * @param userInfo the URI user info
     * @return this MutableURI
     */
    @Override
    public MutableURI userInfo(@Nullable String userInfo) {
        this.userInfo = userInfo;
        resetSchemeSpecificPart();
        return this;
    }

    /**
     * Set the URI host. The given host may contain URI template variables,
     * and may also be {@code null} to clear the host of this builder.
     * @param host the URI host
     * @return this MutableURI
     */
    @Override
    public MutableURI host(@Nullable String host) {
        this.host = host;
        resetSchemeSpecificPart();
        return this;
    }

    /**
     * Set the URI port. Passing {@code -1} will clear the port of this builder.
     * @param port the URI port
     * @return this MutableURI
     */
    @Override
    public MutableURI port(int port) {
        Assert.isTrue(port >= -1, "Port must be >= -1");
        this.port = String.valueOf(port);
        resetSchemeSpecificPart();
        return this;
    }

    /**
     * Set the URI port. Use this method only when the port needs to be
     * parameterized with a URI variable. Otherwise use {@link #port(int)}.
     * Passing {@code null} will clear the port of this builder.
     * @param port the URI port
     * @return this MutableURI
     */
    @Override
    public MutableURI port(@Nullable String port) {
        this.port = port;
        resetSchemeSpecificPart();
        return this;
    }

    /**
     * Append the given path to the existing path of this builder.
     * The given path may contain URI template variables.
     * @param path the URI path
     * @return this MutableURI
     */
    @Override
    public MutableURI path(String path) {
        this.pathBuilder.addPath(path);
        resetSchemeSpecificPart();
        return this;
    }

    /**
     * Append path segments to the existing path. Each path segment may contain
     * URI template variables and should not contain any slashes.
     * Use {@code path("/")} subsequently to ensure a trailing slash.
     * @param pathSegments the URI path segments
     * @return this MutableURI
     */
    @Override
    public MutableURI pathSegment(String... pathSegments) throws IllegalArgumentException {
        this.pathBuilder.addPathSegments(pathSegments);
        resetSchemeSpecificPart();
        return this;
    }

    /**
     * Set the path of this builder overriding all existing path and path segment values.
     * @param path the URI path (a {@code null} value results in an empty path)
     * @return this MutableURI
     */
    @Override
    public MutableURI replacePath(@Nullable String path) {
        this.pathBuilder = new CompositePathComponentBuilder();
        if (path != null) {
            this.pathBuilder.addPath(path);
        }
        resetSchemeSpecificPart();
        return this;
    }

    /**
     * Append the given query to the existing query of this builder.
     * The given query may contain URI template variables.
     * <p><strong>Note:</strong> The presence of reserved characters can prevent
     * correct parsing of the URI string. For example if a query parameter
     * contains {@code '='} or {@code '&'} characters, the query string cannot
     * be parsed unambiguously. Such values should be substituted for URI
     * variables to enable correct parsing:
     * <pre class="code">
     * MutableURI.fromUriString(&quot;/hotels/42&quot;)
     * 	.query(&quot;filter={value}&quot;)
     * 	.buildAndExpand(&quot;hot&amp;cold&quot;);
     * </pre>
     * @param query the query string
     * @return this MutableURI
     */
    @Override
    public MutableURI query(@Nullable String query) {
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
        resetSchemeSpecificPart();
        return this;
    }

    /**
     * Set the query of this builder overriding all existing query parameters.
     * @param query the query string; a {@code null} value removes all query parameters.
     * @return this MutableURI
     */
    @Override
    public MutableURI replaceQuery(@Nullable String query) {
        this.queryParams.clear();
        if (query != null) {
            query(query);
        }
        resetSchemeSpecificPart();
        return this;
    }

    /**
     * Append the given query parameter to the existing query parameters. The
     * given name or any of the values may contain URI template variables. If no
     * values are given, the resulting URI will contain the query parameter name
     * only (i.e. {@code ?foo} instead of {@code ?foo=bar}).
     * @param name the query parameter name
     * @param values the query parameter values
     * @return this MutableURI
     * @see #queryParam(String, Collection)
     */
    @Override
    public MutableURI queryParam(String name, Object... values) {
        Objects.requireNonNull(name, "Name must not be null");
        if (!ObjectUtils.isEmpty(values)) {
            for (Object value : values) {
                String valueAsString = (value != null ? value.toString() : null);
                this.queryParams.add(name, valueAsString);
            }
        }
        else {
            this.queryParams.add(name, null);
        }
        resetSchemeSpecificPart();
        return this;
    }

    /**
     * Append the given query parameter to the existing query parameters. The
     * given name or any of the values may contain URI template variables. If no
     * values are given, the resulting URI will contain the query parameter name
     * only (i.e. {@code ?foo} instead of {@code ?foo=bar}).
     * @param name the query parameter name
     * @param values the query parameter values
     * @return this MutableURI
     * @since 5.2.0
     * @see #queryParam(String, Object...)
     */
    @Override
    public MutableURI queryParam(String name, @Nullable Collection<?> values) {
        return queryParam(name, values != null ? values.toArray() : EMPTY_VALUES);
    }

    /**
     * Add the given query parameters.
     * @param params the params
     * @return this MutableURI
     * @since 4.0
     */
    @Override
    public MutableURI queryParams(@Nullable MultiValueMap<String, String> params) {
        if (params != null) {
            this.queryParams.addAll(params);
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
    @Override
    public MutableURI replaceQueryParam(String name, Object... values) {
        Objects.requireNonNull(name, "Name must not be null");
        this.queryParams.remove(name);
        if (!ObjectUtils.isEmpty(values)) {
            queryParam(name, values);
        }
        resetSchemeSpecificPart();
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
    @Override
    public MutableURI replaceQueryParam(String name, @Nullable Collection<?> values) {
        return replaceQueryParam(name, values != null ? values.toArray() : EMPTY_VALUES);
    }

    /**
     * Set the query parameter values overriding all existing query values.
     * @param params the query parameter name
     * @return this MutableURI
     * @since 4.2
     */
    @Override
    public MutableURI replaceQueryParams(@Nullable MultiValueMap<String, String> params) {
        this.queryParams.clear();
        if (params != null) {
            this.queryParams.putAll(params);
        }
        return this;
    }

    /**
     * Set the URI fragment. The given fragment may contain URI template variables,
     * and may also be {@code null} to clear the fragment of this builder.
     * @param fragment the URI fragment
     * @return this MutableURI
     */
    public MutableURI fragment(String fragment) {
        if (fragment != null) {
            Assert.hasLength(fragment, "Fragment must not be empty");
            this.fragment = fragment;
        }
        else {
            this.fragment = null;
        }
        return this;
    }


    private void resetHierarchicalComponents() {
        this.userInfo = null;
        this.host = null;
        this.port = null;
        this.pathBuilder = new CompositePathComponentBuilder();
        this.queryParams.clear();
    }

}
