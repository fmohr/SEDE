package de.upb.sede.edd.deploy.target;

import de.upb.sede.edd.LockableDir;
import de.upb.sede.edd.SEDECodeBase;
import de.upb.sede.edd.deploy.DeploymentException;
import de.upb.sede.edd.process.JavaProcessHandleBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.ArrayList;
import java.util.Objects;
import java.util.concurrent.Executor;

public class JExecutorTarget extends DefaultTarget {

    private final static Logger logger = LoggerFactory.getLogger(JExecutorTarget.class);

    private JavaAppComponent executorProcess;

    private SEDECodeBaseComponent sedeCodeBase;

    private ExecutorConfigComponent executorConfig;

    private ExecutorSecretaryComponent executorSecretary;

    private TCPPortComponent tcpPortComponent;

    private String id;

    public JExecutorTarget(Executor executor, String id, SEDECodeBase sedeCodeBase, File executorDir, File serviceDir, LockableDir portsDir) {
        this(null, id, executor, sedeCodeBase, executorDir, serviceDir, portsDir);
    }

    public JExecutorTarget(String displayName, String id,
                           Executor executor,
                           SEDECodeBase sedeCodeBase,
                           File executorDir, File serviceDir, LockableDir portsDir)  {
        super(displayName, executor);
        this.id = Objects.requireNonNull(id);
        Objects.requireNonNull(executor);
        Objects.requireNonNull(sedeCodeBase);
        Objects.requireNonNull(executorDir);
        Objects.requireNonNull(serviceDir);
        if(displayName == null) {
            setDisplayName("Java Executor Target " + id);
        }
        this.executorProcess = new JavaAppComponent(getDisplayName(), getExecutor());
        this.sedeCodeBase = new SEDECodeBaseComponent(getDisplayName(), sedeCodeBase);
        this.executorConfig = new ExecutorConfigComponent(getDisplayName(), executorDir, id, serviceDir);
        executorProcess.getJavaProcessBuilder().setDisplayName("Java Executor " + id);
        this.tcpPortComponent = new TCPPortComponent(getDisplayName(), portsDir);
        this.executorSecretary = new ExecutorSecretaryComponent(getDisplayName());
    }

    public JavaAppComponent getExecutorProcess() {
        return executorProcess;
    }

    public SEDECodeBaseComponent getSedeCodeBase() {
        return sedeCodeBase;
    }

    public ExecutorConfigComponent getExecutorConfig() {
        return executorConfig;
    }

    public TCPPortComponent getTcpPortComponent() {
        return tcpPortComponent;
    }

    public ExecutorSecretaryComponent getExecutorSecretary() {
        return executorSecretary;
    }

    @Override
    public void start() {
        logger.info("{}: starting executor...", getDisplayName());
        sedeCodeBase.deploy(false, getExecutor()); // TODO how to configure sede updates?
        sedeCodeBase.getCodeBase().getSedeCoreDirectory();
        JavaProcessHandleBuilder builder = executorProcess.getJavaProcessBuilder();
        builder.getClasspath().wildcardDirs(
            new File(sedeCodeBase.getCodeBase().getSedeCoreDirectory(), "deploy/SEDE"),
            new File(sedeCodeBase.getCodeBase().getSedeCoreDirectory(), "deploy/SEDE_logging_lib"));
        builder.getClasspath().files(
            new File(sedeCodeBase.getCodeBase().getSedeCoreDirectory(), "deploy/run-executor-template"));
        builder.setMain("de.upb.sede.exec.ExecutorServerStarter");
        boolean allocatedPort = getTcpPortComponent().allocatePort();
        if(!allocatedPort || !getTcpPortComponent().getAllocatedTcpPort().isPresent()) {
            throw new DeploymentException(getDisplayName() + ": cannot allocate a tcp port for the executor server with id: " + id);
        }
        builder.args(executorConfig.getExecutorConfigFile().getAbsolutePath(), "0.0.0.0", getTcpPortComponent().getAllocatedTcpPort().get().localPort);
        builder.setWorkingDir(executorConfig.getExecutorDir());
        executorProcess.start();
        logger.info("{}: successfully started executor.", getDisplayName());
    }

    @Override
    public void stop() {
        executorProcess.stop();
        tcpPortComponent.deallocatePort();
    }

    public synchronized void reset() {
        logger.info("{}: reset was requested.", getDisplayName());
        this.executorProcess.stop();
        this.executorProcess = new JavaAppComponent(getDisplayName(), getExecutor());
    }

    public void registerToGateway(String gatewayAddress) {
        executorSecretary.registerToGateway(getExecutorConfig().getExecutorId(),
            new ArrayList<>(getExecutorConfig().getServices()),
            getTcpPortComponent().getAllocatedTcpPort().get().advertisedPort + "");
    }
}
