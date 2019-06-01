package de.upb.sede.config;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import de.upb.sede.util.Kneadable;
import de.upb.sede.util.KneadableObject;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class DeploymentMethod {

    private final String method;

    private final List<Kneadable> steps;


    public DeploymentMethod(String method, List<Kneadable> steps) {
        this.method = method;
        this.steps = steps;
    }

    @JsonCreator
    public static DeploymentMethod create(
            @JsonProperty("method") String method,
            @JsonProperty("steps") List<Map> steps) {
        return new DeploymentMethod(method, steps.stream().map(KneadableObject::new).collect(Collectors.toList()));
    }

    public String getMethod() {
        return method;
    }

    public List<Kneadable> getSteps() {
        return steps;
    }
}
