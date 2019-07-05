package de.upb.sede.edd.deploy.model;

import de.upb.sede.util.DynTypeField;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

public class DeploymentSpecification implements Predicate<String> {
    private String name; // primary key
    private String target;

    private List<String> alias = new ArrayList<>();
    private List<String> services = new ArrayList<>();
    private DeploymentMethod method = null;

    private List<DynTypeField> output = new ArrayList<>();

    public boolean validate() {
        return name != null  && target != null;
    }

    @Override
    public String toString() {
        return "DeploymentSpecification{" +
            "name='" + name + '\'' +
            ", target='" + target + '\'' +
            '}';
    }

    public String getName() {
        return name;
    }

    public List<String> getAlias() {
        return alias;
    }

    public List<String> getServices() {
        return services;
    }

    public String getTarget() {
        return target;
    }

    public DeploymentMethod getMethod() {
        return method;
    }

    public Optional<DeploymentMethod> getMethodOptional() {
        return Optional.ofNullable(method);
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAlias(List<String> alias) {
        this.alias = alias == null ? new ArrayList<>() : alias;
    }

    public void setServices(List<String> services) {
        this.services = services == null ? new ArrayList<>() : services;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public void setMethod(DeploymentMethod method) {
        this.method = method;
    }


    public List<DynTypeField> getOutput() {
        return output;
    }

    public void setOutput(List<DynTypeField> output) {
        this.output = output;
    }

    @Override
    public boolean test(String s) {
        if(s== null) {
            return false;
        } else if(getName().equals(s)){
            return true;
        } else {
            return getAlias().contains(s);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DeploymentSpecification that = (DeploymentSpecification) o;
        if (!name.equals(that.name)) return false;
        return true;
    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        return result;
    }
}
