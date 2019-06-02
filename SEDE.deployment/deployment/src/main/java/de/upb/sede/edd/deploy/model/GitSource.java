package de.upb.sede.edd.deploy.model;

public class GitSource implements DeploymentSource {

    private String url;

    private String branch;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

}
