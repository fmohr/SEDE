package de.upb.sede.edd.deploy;

import de.upb.sede.edd.LockableDir;
import de.upb.sede.edd.SystemSettingLookup;
import de.upb.sede.edd.deploy.model.DeploymentMethod;
import de.upb.sede.edd.deploy.model.DeploymentSourceDirAcr;
import de.upb.sede.edd.deploy.model.DeploymentSpecification;
import de.upb.sede.util.Cache;
import de.upb.sede.util.FileUtil;
import de.upb.sede.util.LazyAccessCache;
import de.upb.sede.util.OptionalField;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

public class ServiceDeployment {

    private final static Logger logger = LoggerFactory.getLogger(ServiceDeployment.class);

    private final static String DEFAULT_ACRONYM = "0";

    private final static SystemSettingLookup DEPL_METHOD_LOOKUP =
        new SystemSettingLookup("",
            "edd.deployment.defaultMethod",
            "EDD_DEPLOYMENT_DEFAULT_METHOD");

    private DeploymentContext context;

    private DeploymentSpecification specification;

    private DeploymentMethod deploymentMethod;

    private OptionalField<File> deploymentDir = OptionalField.empty();

    private OptionalField<DeploymentReportHandle> result = OptionalField.empty();

    private Cache<Map<String, File>> sourceAcronyms = new LazyAccessCache<>(this::createSourceAcronyms);

    public ServiceDeployment(DeploymentContext context, DeploymentSpecification specification) {
        this.context = context;
        this.specification = Objects.requireNonNull(specification);
        this.deploymentMethod = findMethod(specification);
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
                logger.error("Couldnt store result of deployment {}.", specification.getName(), e);
            }
        }
    }

    private Map<String, File> createSourceAcronyms() {
        Map<String, File> sourceAcrs = new HashMap<>();
        for (int i = 0; i < deploymentMethod.getSources().size(); i++) {
            String defaultSourceAcr = "" + i;
            File sourceDir = sourceDirectory(i);
            sourceAcrs.put(defaultSourceAcr, sourceDir);
            DeploymentSourceDirAcr dirAcr = deploymentMethod.getSources().get(i).knead(DeploymentSourceDirAcr.class);
            if(dirAcr.getAcr().isPresent()) {
                sourceAcrs.put(dirAcr.getAcr().get(), sourceDir);
            }
        }
        return sourceAcrs;
    }

    private File sourceDirectory(int sourceIndex) {
        return new File(deploymentDir.get(), "" + sourceIndex);
    }

    public boolean needsUpdate() {
        return context.getSettings().forcedUpdates() || !result.get().isDeployed();
    }

    public Optional<File> resolveAcronym(String acronym) {
        return Optional.ofNullable(sourceAcronyms.access().get(acronym));
    }

    private File resolvePath(AcrPath path)
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
        deploySources();
    }

    private void deploySources() {
        for (int i = 0; i < deploymentMethod.getSources().size(); i++) {
            File sourceDir = sourceDirectory(i);
//            sou
        }
    }

    public static DeploymentMethod findMethod(DeploymentSpecification specification) {
        if(specification.getMethods().size() == 0) {
            String message = String.format("Service collection %s has no deployment methods.", specification.toString());
            throw new DeploymentException(message);
        }
        String defaultMethod = DEPL_METHOD_LOOKUP.lookup();
        DeploymentMethod chosenMethod = null;
        for(DeploymentMethod method : specification.getMethods()){
            if(defaultMethod.equals(method.getMethod())) {
                chosenMethod = method;
                break;
            }
        }
        if(chosenMethod == null) {
            chosenMethod = specification.getMethods().get(0); // chose first by default
        }
        return chosenMethod;
    }


    public DeploymentSpecification getSpecification() {
        return specification;
    }

    public DeploymentMethod getDeploymentMethod() {
        return deploymentMethod;
    }
}
