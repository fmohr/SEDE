package de.upb.sede.edd.runtime;

import de.upb.sede.edd.EDD;
import de.upb.sede.edd.EDDHome;
import de.upb.sede.edd.deploy.specsrc.SpecSource;
import de.upb.sede.edd.deploy.target.JExecutorTarget;
import de.upb.sede.util.ExpiringCache;
import de.upb.sede.util.NullableCache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.TimerTask;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ExecutorRuntime {

    private final static Logger logger = LoggerFactory.getLogger(ExecutorRuntime.class);

    private ExecutorService executorService = Executors.newCachedThreadPool();

    private SpecSource source;

    private JExecutorTarget target;

    private ExpiringCache<TimerTask> timeToLive = new ExpiringCache<>(5, TimeUnit.MINUTES, new NullableCache<>());

    public ExecutorRuntime(SpecSource source, JExecutorTarget target) {
        this.source = source;
        this.target = target;
    }

    public ExecutorRuntime(final EDD edd, SpecSource source) {
        EDDHome home = edd.getHome();
        this.source = source;
        String id = "jExecutor" + Long.toHexString(UUID.randomUUID().getLeastSignificantBits());
        File executorDir = home.getAppDir(id).toFile();
        logger.info("Creating java executor target: " + id);
        target =  new JExecutorTarget(
            id + " Java Executor Target ", id,
            executorService,
            home.getSEDECodeBase(), executorDir, home.getServiceDir().toFile(), home.getPortDir(),
            edd.getInfo().getMachineAddress().orElseThrow(() -> new IllegalStateException("Cannot create executor if no host address was set.")));
    }

    public ExecutorService getExecutorService() {
        return executorService;
    }

    public SpecSource getSource() {
        return source;
    }

    public JExecutorTarget getTarget() {
        return target;
    }

    public ExpiringCache<TimerTask> getTimeToLive() {
        return timeToLive;
    }
}
