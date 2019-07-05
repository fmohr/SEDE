package de.upb.sede.edd.deploy.model;

import de.upb.sede.util.OptionalField;
import de.upb.sede.util.Validatable;

import java.util.Optional;

public class GitSource extends DeploymentSource implements Validatable {

    private String url;

    private OptionalField<String> branch = OptionalField.empty();

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Optional<String> getBranch() {
        return branch.opt();
    }

    public void setBranch(String branch) {
        this.branch = OptionalField.ofNullable(branch);
    }

    @Override
    public void validate() throws RuntimeException {
        if(url == null) {
            throw new IllegalStateException("Git source does not specify the url of the remote repository.");
        }
    }
}
