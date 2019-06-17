package de.upb.sede.edd.deploy.model;

import de.upb.sede.util.Validatable;

import java.util.ArrayList;
import java.util.List;

public class GradleProcess implements Validatable {

    private List<String> tasks = new ArrayList<>();

    public List<String> getTasks() {
        return tasks;
    }

    @Override
    public void validate() throws RuntimeException {
        if(tasks == null || tasks.isEmpty()) {
            throw new IllegalStateException("no gradle tasks were provided.");
        }
    }
}
