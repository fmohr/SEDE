package ai.services;

import java.util.Collections;
import java.util.List;

class ConstQualifier implements QualifierDefinition {

    private final String qualifier;

    private final String simpleName;

    ConstQualifier(String qualifier) {
        this(qualifier, qualifier);
    }

    ConstQualifier(String qualifier, String simpleName) {
        this.qualifier = qualifier;
        this.simpleName = simpleName;
    }

    @Override
    public String getQualifier() {
        return qualifier;
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<String> getMetaTags() {
        return Collections.EMPTY_LIST;
    }

    @Override
    public String getSimpleName() {
        return simpleName;
    }
}
