package de.upb.sede;

class ConstQualifier implements IQualifiable {

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
    public String getSimpleName() {
        return simpleName;
    }
}
