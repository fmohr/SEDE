package ai.services.util;

import com.google.common.collect.Multimap;

import java.util.Optional;

public interface ResourceIdentifier {

    Optional<String> getScheme();

    Optional<String> getUserInfo();

    Optional<String> getHost();

    Optional<Integer> getPort();

    Optional<String> getPath();

    Multimap<String, String> getQueryParams();

    Optional<String> getFragment();

}
