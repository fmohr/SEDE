package de.upb.sede.spec;

import de.upb.sede.util.Validatable;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class DeploymentSpecification
    implements Predicate<String>, Validatable {

    private String name;

    private List<String> alias = new ArrayList<>();

    private List<String> services = new ArrayList<>();

    public void validate() {
        if(name == null || name.isEmpty()) {
            throw new IllegalStateException("Deployment spec name is empty.");
        }
    }

    @Override
    public String toString() {
        return "DeploymentSpecification{"
             + name +
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


    public void setName(String name) {
        this.name = name;
    }

    public void setAlias(List<String> alias) {
        this.alias = alias == null ? new ArrayList<>() : alias;
    }

    public void setServices(List<String> services) {
        this.services = services == null ? new ArrayList<>() : services;
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
