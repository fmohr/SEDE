package de.upb.sede.edd.deploy.group.transaction;

public class RemoveGroupTransaction {

    String groupName = "default";

    public RemoveGroupTransaction(String groupName) {
        this.groupName = groupName;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }
}
