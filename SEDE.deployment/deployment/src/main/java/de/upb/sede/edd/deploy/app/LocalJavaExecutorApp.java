package de.upb.sede.edd.deploy.app;

import de.upb.sede.edd.EDDHome;
import de.upb.sede.edd.deploy.target.JExecutorTarget;
import de.upb.sede.exec.Executor;
import de.upb.sede.util.LazyAccessCache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class LocalJavaExecutorApp extends App {

    private final static Logger logger = LoggerFactory.getLogger(LocalJavaExecutorApp.class);

    private JExecutorTarget javaExecutor;

    private ExecutorService executorService = Executors.newCachedThreadPool();

    LocalJavaExecutorApp(final EDDHome home) {
        String id = "jExecutor" + Long.toHexString(UUID.randomUUID().getLeastSignificantBits());
        File executorDir = home.getAppDir(id).toFile();
        logger.info("Creating java executor target: " + id);
        javaExecutor =  new JExecutorTarget(id + " Java Executor Target ",
            id, executorService, home.getSEDECodeBase(), executorDir, home.getServiceDir().toFile(), home.getPortDir());
    }


    public JExecutorTarget getJavaExecutor(String spec) {
        return getJavaExecutor();
    }

    public JExecutorTarget getJavaExecutor() {
        return javaExecutor;
    }

    public synchronized void start() {
        javaExecutor.start();
    }

}
