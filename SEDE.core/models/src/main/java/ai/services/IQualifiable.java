package ai.services;

public interface IQualifiable {

    String getQualifier();

    static IQualifiable of(String qualifier) {
        return new ConstQualifier(qualifier);
    }

}
