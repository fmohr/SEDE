package de.upb.sede.edd.deploy.target;

import de.upb.sede.edd.LockableDir;
import de.upb.sede.edd.SEDECodeBase;
import de.upb.sede.edd.deploy.DeploymentException;
import de.upb.sede.edd.deploy.target.components.*;
import de.upb.sede.edd.process.JavaProcessHandleBuilder;
import de.upb.sede.requests.ExecutorRegistration;
import de.upb.sede.util.URIMod;
import de.upb.sede.util.WebUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.*;
import java.util.concurrent.Executor;

public class JExecutorTarget extends DefaultTarget implements ExecutorTarget {

    private static final Logger logger = LoggerFactory.getLogger(JExecutorTarget.class);

    private JavaAppComponent executorProcess;

    private SEDECodeBaseComponent sedeCodeBase;

    private ExecutorConfigComponent executorConfig;

    private HostAddressInfoComponent addressInfo;

    private TCPPortComponent tcpPortComponent;

    private String id;

    public JExecutorTarget(Executor executor, String id, SEDECodeBase sedeCodeBase, File executorDir, File serviceDir, LockableDir portsDir, String address) {
        this(null, id, executor, sedeCodeBase, executorDir, serviceDir, portsDir, address);
    }

    public JExecutorTarget(String displayName,
                           String id,
                           Executor executor,
                           SEDECodeBase sedeCodeBase,
                           File executorDir,
                           File serviceDir,
                           LockableDir portsDir,
                           String address)  {
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
        this.addressInfo = new HostAddressInfoComponent(getDisplayName(), address);
    }

    public JavaAppComponent getExecutorProcess() {
        return executorProcess;
    }

    public SEDECodeBaseComponent getSedeCodeBase() {
        return sedeCodeBase;
    }

    @Override
    public List<String> getServiceCollections() {
        return new ArrayList<>(executorConfig.getServices());
    }



    private Map<String, Object> contactInfo(String id, String address) {
        Map<String, Object> contactInfo = new HashMap<>();
        contactInfo.put("id", id);
        contactInfo.put("host-address", address);
        return contactInfo;
    }

    @Override
    public ExecutorRegistration getRegistration() {
        String address = URIMod.setPort(
            this.addressInfo.getAddress(),
            tcpPortComponent.getAllocatedTcpPort().orElseThrow(() -> new IllegalStateException("No port was allocated yet.")).advertisedPort);

        ExecutorRegistration registration = new ExecutorRegistration(contactInfo(id, address),
            Collections.emptyList(),
            new ArrayList<>(executorConfig.getServices()));
        return registration;
    }

    public ExecutorConfigComponent getExecutorConfig() {
        return executorConfig;
    }

    @Override
    public TCPPortComponent getPort() {
        return tcpPortComponent;
    }

    @Override
    public HostAddressInfoComponent getHostAddress() {
        return addressInfo;
    }

    public TCPPortComponent getTcpPortComponent() {
        return tcpPortComponent;
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

    @Override
    public JavaAppComponent getApplication() {
        return executorProcess;
    }


//    public void registerToGateway(String id, List<String> services, String port) {
//        if(gatewayAddress.isAbsent()) {
//            logger.warn("{}: cannot register executor. No gateway defined.", getDisplayName());
//            return;
//        }
//        logger.info("{}: registering to gateway: {}", getDisplayName(), gatewayAddress.get());
//        String address = WebUtil.HostPublicIpAddress() + ":" + port;
//
//        ExecutorRegistration registration = new ExecutorRegistration(contactInfo(id, address), Collections.emptyList(), services);
//
//        // TODO registration
//
//        HttpURLConnectionClientRequest httpRegistration = new HttpURLConnectionClientRequest(gatewayAddress.get() + "/register");
//
//        String registrationAnswer = httpRegistration.send(registration.toJsonString());
//        if (!registrationAnswer.isEmpty()) {
//            throw new RuntimeException("Registration to gateway \"" + gatewayAddress.get()
//                + "\" failed with non empty return message:\n" + registrationAnswer);
//        }
//        logger.debug("{} registered to gateway: {}", getDisplayName(), getGatewayAddress().get());
//    }
}
