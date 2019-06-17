package de.upb.sede.edd.deploy;

import de.upb.sede.edd.LockableDir;
import de.upb.sede.edd.SystemSettingLookup;
import de.upb.sede.edd.deploy.group.transaction.GroupComponents;
import de.upb.sede.edd.deploy.model.DeploymentMethod;
import de.upb.sede.edd.deploy.model.DeploymentSourceDirAcr;
import de.upb.sede.edd.deploy.model.DeploymentSpecification;
import de.upb.sede.edd.deploy.model.output.ClassPathOutput;
import de.upb.sede.edd.deploy.model.output.OutputCollectionType;
import de.upb.sede.edd.deploy.target.JExecutorTarget;
import de.upb.sede.edd.process.ClassPath;
import de.upb.sede.util.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.*;
import java.util.concurrent.Executor;
import java.util.stream.Collectors;

public class ServiceDeployment {

    private final static Logger logger = LoggerFactory.getLogger(ServiceDeployment.class);

    private final static String DEFAULT_ACRONYM = "0";

    private final static SystemSettingLookup DEPL_METHOD_LOOKUP =
        new SystemSettingLookup("",
            "edd.deployment.defaultMethod",
            "EDD_DEPLOYMENT_DEFAULT_METHOD");

    private DeploymentContext context;

    private String displayName = "";

    private DeploymentSpecification specification;

    private DeploymentMethod deploymentMethod;

    private OptionalField<File> deploymentDir = OptionalField.empty();

    private OptionalField<DeploymentReportHandle> result = OptionalField.empty();

    private Cache<Map<String, File>> sourceAcronyms = new LazyAccessCache<>(this::createSourceAcronyms);


    public ServiceDeployment(DeploymentContext context, DeploymentSpecification specification) {
        this.context = context;
        this.specification = Objects.requireNonNull(specification);
        this.deploymentMethod = findMethod(specification);
        this.displayName = "Service deployment '" + specification.getName() +
            "' with method '" + deploymentMethod.getMethodType() + "'";
    }

    public void deploy(LockableDir deployRootDirectory) {
        deploymentDir = OptionalField.of(deployRootDirectory.toFile());
        try {
            result = OptionalField.of(DeploymentReportHandle.load(deploymentDir.get()));
        } catch (IOException e) {
            throw new DeploymentException("Cannot load deployment result from directory: " + deploymentDir.get().toString(), e);
        }
        if(needsUpdate()) {
            doDeploy();
            result.get().setDeployed(true);
            try{
                result.get().store();
            } catch (IOException e) {
                logger.error("Couldn't store result of deployment {}.", getDisplayName(), e);
            }
        }
    }

    public Executor getExecutor() {
        return context.getExecutor();
    }

    public OutputStream getOutputStream() {
        return context.getOutputStream();
    }

    public OutputStream getErrOut() {
        return context.getErrOut();
    }

    private Map<String, File> createSourceAcronyms() {
        Map<String, File> sourceAcrs = new HashMap<>();
        for (int i = 0; i < deploymentMethod.getSources().size(); i++) {
            String defaultSourceAcr = "" + i;
            String sourceDirName = deploymentMethod.getSources().get(i).knead(DeploymentSourceDirAcr.class).getDirectoryAcronym();
            File sourceDir = sourceDirectory(sourceDirName);
            sourceAcrs.put(defaultSourceAcr, sourceDir);
            DeploymentSourceDirAcr dirAcr = deploymentMethod.getSources().get(i).knead(DeploymentSourceDirAcr.class);
            if(dirAcr.getAcr().isPresent()) {
                sourceAcrs.put(dirAcr.getAcr().get(), sourceDir);
            }
        }
        return sourceAcrs;
    }

    private File sourceDirectory(String sourceDirName) {
        return new File(deploymentDir.get(), sourceDirName);
    }

    public boolean needsUpdate() {
        return context.getSettings().forcedUpdates() || !result.get().isDeployed();
    }

    public Optional<File> resolveAcronym(String acronym) {
        return Optional.ofNullable(sourceAcronyms.access().get(acronym));
    }

    public String getDisplayName() {
        return displayName;
    }

    public File resolvePath(AcrPath path)
    {
        String acronym = path.getAcr().orElse(DEFAULT_ACRONYM);
        File sourceDir;
        if(path.getRef().isPresent()) {
            ServiceDeployment reference = context.findByName(path.getRef().get());
            sourceDir = reference.resolveAcronym(acronym)
                .orElseThrow(() -> new DeploymentException(
                    "Cannot resolve acronym " + acronym +
                        " of dependency" + path.getRef().get() +
                        " of deployment " + specification.getName() + "."));
        } else {
            sourceDir = resolveAcronym(acronym)
                .orElseThrow(() -> new DeploymentException(
                    "Cannot resolve acronym " + acronym +
                        " of deployment " + specification.getName() + "."));
        }
        return new File(sourceDir, path.getPath());
    }

    private void doDeploy() {
        boolean sourceUpdates = retrieve();
        if(! sourceUpdates && result.get().isDeployed()) {
            logger.info("{} was deployed and sources had no updates.", getDisplayName());
        }
        build();

    }

    private void build() {
        logger.info("Starting building steps of {}", getDisplayName());
        for(Kneadable buildDef : deploymentMethod.getBuilds()) {
            String displayName = getDisplayName()
                + " build:\n\t\t"
                + buildDef.toString().replaceAll("\n", "\n\t\t") + "\n";
            EDDBuild build = DeploymentStepRegistry.getInstance().toBuildStep(displayName, buildDef);
            build.build(this);
        }
    }

    private boolean retrieve() {
        logger.info("Retrieving sources of {}", getDisplayName());
        boolean sourcesUpdated = false;
        for (int i = 0; i < deploymentMethod.getSources().size(); i++) {
            String sourceDirName = deploymentMethod.getSources().get(i).knead(DeploymentSourceDirAcr.class).getDirectoryAcronym();
            File sourceDir = sourceDirectory(sourceDirName);
            String displayName = getDisplayName() + " sources " + i + " into " + sourceDir;
            EDDSource source = DeploymentStepRegistry.getInstance().toSourceStep(displayName, sourceDir, deploymentMethod.getSources().get(i));
            boolean sourceUpdated = source.retrieve(context.getSettings().forcedUpdates(), this);
            sourcesUpdated = sourcesUpdated || sourceUpdated;
        }
        return sourcesUpdated;
    }

    public static DeploymentMethod findMethod(DeploymentSpecification specification) {
        if(!specification.getMethodOptional().isPresent()) {
            String message = String.format("%s has no deployment methods.", specification.toString());
            throw new DeploymentException(message);
        } else {
//            String defaultMethod = DEPL_METHOD_LOOKUP.lookup();
            DeploymentMethod chosenMethod = specification.getMethod();
            return chosenMethod;
        }
    }


    public DeploymentSpecification getSpecification() {
        return specification;
    }

    public DeploymentMethod getDeploymentMethod() {
        return deploymentMethod;
    }

    public void collectOutputs(GroupComponents gc) {
        logger.info("{}: collecting deployment outputs.", getDisplayName());
        List<KneadableField> outputs = specification.getOutput();
        // should do nothing if outputs is empty.
        for(KneadableField output : outputs) {
            Optional<String> type = output.knead(OutputCollectionType.class).getType();
            if(type.isPresent()) {
                collectOutput(gc, output, type.get());
            } else {
                logger.warn("{}: output has no type: {}", getDisplayName(), output);
            }
        }
        JExecutorTarget target = gc.getJavaExecutor(specification.getName());
        logger.debug("{}: adding services of {} to {}.", getDisplayName(), specification.getName(), target.getDisplayName());
        target.getExecutorConfig().getServices().addAll(specification.getServices());
    }

    private void collectOutput(GroupComponents gc, KneadableField output, String type) {
        if(ClassPathOutput.OUTPUT_TYPE.equals(type)) {
            ClassPathOutput classPathOutput = output.knead(ClassPathOutput.class);
            JExecutorTarget target = gc.getJavaExecutor(specification.getName());
            ClassPath cp = target.getExecutorProcess().getJavaProcessBuilder().getClasspath();
            for(AcrPath path : classPathOutput.getFiles()) {
                File cpFile = resolvePath(path);
                logger.debug("{}: adding file '{}' to the classpath of {}.", getDisplayName(), cpFile, target.getDisplayName());
                cp.files(cpFile);
            }
            for(AcrPath path : classPathOutput.getJarDirs()) {
                File wildcardDir = resolvePath(path);
                logger.debug("{}: adding jars in dir '{}' to the classpath of {}.", getDisplayName(), wildcardDir, target.getDisplayName());
                cp.wildcardDirs(wildcardDir);
            }
        } else {
            logger.warn("{}: output type was not recognized: {}", getDisplayName(), type);
        }

    }
}
