package de.upb.sede.edd.deploy.group;

import de.upb.sede.edd.EDDHome;
import de.upb.sede.edd.LockableDir;
import de.upb.sede.edd.SEDECodeBase;
import de.upb.sede.edd.deploy.group.transaction.GroupComponents;
import de.upb.sede.edd.deploy.target.JExecutorTarget;
import de.upb.sede.util.Cache;
import de.upb.sede.util.LazyAccessCache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.UUID;
import java.util.concurrent.Executor;

public class SingleInstanceGroupComponents implements GroupComponents {

    private final static Logger logger = LoggerFactory.getLogger(SingleInstanceGroupComponents.class);


    private LazyAccessCache<JExecutorTarget> javaExecutor;

    SingleInstanceGroupComponents(final String groupName, final Executor executor, final EDDHome home) {
        javaExecutor = new LazyAccessCache<JExecutorTarget>(() -> {
            String id = groupName + "jExecutor" + Long.toHexString(UUID.randomUUID().getLeastSignificantBits());
            File executorDir = home.getGroupDir(groupName).getChild("jExecutor").toFile();
            logger.info("Creating java executor target: " + id);
            return new JExecutorTarget(groupName + " Java Executor Target ",
                id, executor, home.getSEDECodeBase(), executorDir, home.getServiceDir().toFile(), home.getPortDir());
        });

    }

    public JExecutorTarget getJavaExecutor(String spec) {
        return getJavaExecutor();
    }

    public JExecutorTarget getJavaExecutor() {
        return javaExecutor.get();
    }

    public boolean hasJavaExecutor() {
        return javaExecutor.isSet();
    }

    public void reset() {
        javaExecutor.unset();
    }
}
