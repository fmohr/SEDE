package de.upb.sede.edd.deploy;

import de.upb.sede.edd.process.DefaultProcessHandle;
import de.upb.sede.edd.process.DefaultProcessHandleBuilder;
import de.upb.sede.edd.process.ProcessResult;
import de.upb.sede.util.Kneadable;

public abstract class AbstractProcessBuild implements EDDBuild {

    private String displayName;

    private Kneadable buildDef;


    public AbstractProcessBuild(String displayName, Kneadable buildDef) {
        this.displayName  = displayName;
        this.buildDef = buildDef;
    }

    public Kneadable getBuildDef() {
        return buildDef;
    }

    public String getDisplayName() {
        return displayName;
    }

    @Override
    public final void build(ServiceDeployment currentDeployment) {
        try {
            DefaultProcessHandleBuilder processBuilder = new DefaultProcessHandleBuilder(currentDeployment.getExecutor());
            processBuilder.setDisplayName(displayName);
            processBuilder.setStandardOutput(currentDeployment.getOutputStream());
            processBuilder.setErrorOutput(currentDeployment.getErrOut());

            configureProcess(processBuilder, currentDeployment);
            DefaultProcessHandle handle = processBuilder.build().start();
            ProcessResult result = handle.waitForFinish();
            result.rethrowFailure();
            result.assertNormalExitValue();
        } catch (DeploymentException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new DeploymentException(displayName + " runtime error.", ex);
        }
    }

    protected abstract void configureProcess(DefaultProcessHandleBuilder builder, ServiceDeployment currentDeployment);
}
