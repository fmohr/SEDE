package de.upb.sede.edd.deploy.group.transaction;

public class CreateGroupTransaction {

    private String groupName = "default";

    private boolean localGroup = true;


    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public boolean isLocalGroup() {
        return localGroup;
    }

    public void setLocalGroup(boolean localGroup) {
        this.localGroup = localGroup;
    }
}
