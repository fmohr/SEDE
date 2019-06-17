package de.upb.sede.edd.deploy.group;

import de.upb.sede.edd.EDDHome;
import de.upb.sede.edd.deploy.DeploymentException;
import de.upb.sede.edd.deploy.group.transaction.CreateGroupTransaction;
import de.upb.sede.edd.deploy.group.transaction.RemoveGroupTransaction;
import de.upb.sede.edd.deploy.model.DeploymentSpecificationRegistry;
import de.upb.sede.util.Cache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

public class GroupRepository {

    private final static Logger logger = LoggerFactory.getLogger(GroupRepository.class);

    private List<DeploymentGroup> groups = new ArrayList<>();

    private EDDHome eddHome;

    private Cache<DeploymentSpecificationRegistry> registry;

    public static final String DEFAULT_GROUP_NAME = "default";

    public GroupRepository(EDDHome eddhome, Cache<DeploymentSpecificationRegistry> registry) {
        this.eddHome = eddhome;
        this.registry = registry;
    }

    public Optional<DeploymentGroup> getGroup(String group) {
        group = tranform(group);
        logger.info("Group '{}' requested", group);
        for(DeploymentGroup deploymentGroup : groups) {
            if(deploymentGroup.getGroupName().equalsIgnoreCase(group)) {
                logger.info("Group '{}' was found.", group);
                return Optional.of(deploymentGroup);
            }
        }
        logger.info("Group '{}' was not found.", group);
        return Optional.empty();
    }

    private String tranform(String group) {
        if(group == null) {
            return DEFAULT_GROUP_NAME;
        }

        return group.toLowerCase();
    }

    public boolean createGroup(CreateGroupTransaction createGroupTransaction) {
        String groupName = tranform(createGroupTransaction.getGroupName());
        logger.info("New group creation requested. Group name = {}", groupName);
        if(getGroup(groupName).isPresent()) {
            logger.info("Cannot create a new group with name {}, as it already exists.", groupName);
            return false;
        }
        if(createGroupTransaction.isLocalGroup()) {
            logger.info("New local group '{}' created.", groupName);
            DeploymentGroup newGroup = new LocalDeploymentGroup(groupName, eddHome, registry);
            groups.add(newGroup);
            return true;
        } else {
            throw new DeploymentException("Cannot create non local groups.");
        }
    }

    public boolean deleteGroup(RemoveGroupTransaction removeGroupTransaction) {
        String groupNameToBeRemoved = tranform(removeGroupTransaction.getGroupName());
        return groups.removeIf(deploymentGroup -> deploymentGroup.getGroupName().equalsIgnoreCase(groupNameToBeRemoved));
    }

}
