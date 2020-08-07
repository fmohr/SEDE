package ai.services.execution;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * WorkerProfile that are in one or more task groups.
 */
public class GroupsListWorkerProfile implements WorkerProfile {

    private final List<String> groups;

    public GroupsListWorkerProfile(List<String> groups) {
        if(Objects.requireNonNull(groups).isEmpty()) {
            throw new IllegalArgumentException("Worker profile group list is empty.");
        }
        this.groups = groups;
    }

    public GroupsListWorkerProfile(String group) {
        this.groups = Collections.singletonList(Objects.requireNonNull(group));
    }

    @Override
    public boolean isInGroup(String groupName) {
        return groups.contains(groupName);
    }

}
