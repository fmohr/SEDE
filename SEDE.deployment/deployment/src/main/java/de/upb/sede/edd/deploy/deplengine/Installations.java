package de.upb.sede.edd.deploy.deplengine;

import de.upb.sede.edd.DirLockAlreadyAcquiredException;
import de.upb.sede.edd.LockableDir;
import de.upb.sede.edd.deploy.*;
import de.upb.sede.edd.deploy.model.DeploymentSpecification;
import de.upb.sede.edd.deploy.model.DeploymentSpecificationRegistry;
import de.upb.sede.util.LazyAccessCache;
import de.upb.sede.util.MutableOptionalField;
import de.upb.sede.util.Uncheck;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

public class Installations {

    private static final Logger logger = LoggerFactory.getLogger(Installations.class);

    private final List<DeploymentSpecification> deployments;

    private final String displayName;

    private LazyAccessCache<ExecutorService> executorService = new LazyAccessCache<>(Executors::newCachedThreadPool);

    private DeploymentSpecificationRegistry deplSpecRegistry;

    private LockableDir installDir;

    private MutableOptionalField<DeploymentContext> deploymentContext = MutableOptionalField.empty();

    private boolean update = false;

    public Installations(String displayName,
                         LockableDir installDir,
                         DeploymentSpecificationRegistry specRegistry,
                         Set<DeploymentSpecification> deploymentSet,
                         boolean update) {
        this.displayName = displayName;
        this.installDir = installDir;
        this.deplSpecRegistry = specRegistry;
        this.deployments = new ArrayList<>(deploymentSet);
        this.update = update;
    }



    public String getDisplayName() {
        return displayName;
    }

    public LockableDir getInstallDir() {
        return installDir;
    }

//    public void commitSetGatewayAddress(SetGatewayAddressTransaction transaction) {
//        boolean wait = true;
//        try (AutoCloseable groupLock = getInstallDir().lockDir(!wait)){
//            gatewayAddress.setNullable(transaction.getGateway().orElse(null));
////            if(gatewayAddress.isPresent() &&  componentInstances.access().hasJavaExecutor()) {
////                componentInstances.access().getJavaExecutor().registerToGateway(gatewayAddress.get());
////            }
//        } catch (DeploymentException ex) {
//            throw ex;
//        } catch ( DirLockAlreadyAcquiredException e) {
//            throw new DeploymentException(getDisplayName() + " another deployment transaction is in process.");
//        } catch (Exception e) {
//            throw new DeploymentException(e);
//        }
//    }

    public void start() {
        Runnable deploymentRun = () -> {
            try (AutoCloseable groupLock = getInstallDir().lockDir(false)){
                deploy();
            } catch (DeploymentException ex) {
                throw ex;
            } catch ( DirLockAlreadyAcquiredException e) {
                throw new DeploymentException(getDisplayName() + " another deployment transaction is in process.");
            } catch (Exception e) {
                throw new DeploymentException(e);
            }
        };
        Future<?> deploymentSubmission = executorService.get().submit(deploymentRun);
        try {
            // wait for deployment to finish..
            deploymentSubmission.get();
        } catch (InterruptedException e) {
            // interruption.
            logger.warn("Deployment was interrupted: ", e);
            stop();
        } catch (ExecutionException e) {
            throw Uncheck.throwAsUncheckedException(e);
        }
    }

    public void stop() {
        executorService.access().shutdownNow();
        executorService.unset();
    }


    private void deploy() {
        DeploymentWorkflowSettings deploymentWorkflowSettings = new DeploymentWorkflowSettings();
        deploymentWorkflowSettings.setUpdate(update);
        this.deploy(deploymentWorkflowSettings);
    }

    private void deploy(DeploymentWorkflowSettings settings) {
        DeploymentContext context = new DeploymentContext(settings, executorService.access());
        try {
            deploymentContext.set(context);
            deploy(context);
        } catch (Throwable throwable) {
            throw new DeploymentException(getDisplayName() + " error while deploying services.", throwable);
        } finally {
            context.getExecutor().shutdownNow();
        }
    }


    public Optional<DeploymentContext> lastDeployment() {
        return deploymentContext.opt();
    }


    private void deploy(DeploymentContext context) {
        DeploymentWorkflow workflow = DeploymentWorkflow.createWorkflowFromSpecs(context, deplSpecRegistry, deployments);
        workflow.deploy(installDir);
    }
}
