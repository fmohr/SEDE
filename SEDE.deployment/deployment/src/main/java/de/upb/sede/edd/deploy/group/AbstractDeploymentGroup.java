package de.upb.sede.edd.deploy.group;

import de.upb.sede.edd.LockableDir;

import java.util.Objects;

public abstract class AbstractDeploymentGroup implements DeploymentGroup {

    private String groupName;

    private String displayName;

    private LockableDir groupDir;

    public AbstractDeploymentGroup(String groupName, LockableDir groupDir) {
        this.groupName = Objects.requireNonNull(groupName);
        this.displayName = "Group " + groupName;
        this.groupDir = Objects.requireNonNull(groupDir);
    }


    public String getGroupName() {
        return groupName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public LockableDir getGroupDir() {
        return groupDir;
    }

}
