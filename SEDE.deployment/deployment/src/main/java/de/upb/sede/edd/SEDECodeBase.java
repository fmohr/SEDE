package de.upb.sede.edd;

import de.upb.sede.edd.deploy.DeploymentContext;
import de.upb.sede.edd.deploy.DeploymentException;
import de.upb.sede.edd.deploy.EDDSource;
import de.upb.sede.edd.deploy.GradleProjectBuild;
import de.upb.sede.edd.process.DefaultProcessHandle;
import de.upb.sede.edd.process.DefaultProcessHandleBuilder;
import de.upb.sede.edd.process.ProcessResult;
import de.upb.sede.util.Uncheck;

import java.io.File;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.concurrent.Executor;

public class SEDECodeBase {

    private final static SystemSettingLookup CODE_BASE_DIR = new SystemSettingLookup(
        "sede",
        "edd.sede.codebase.localDir",
        "SEDE_CODE_BASE_LOCAL_DIR");

    private final static SystemSettingLookup REPOSITORY_BRANCH = new SystemSettingLookup(
        "dev-edd",
        "edd.sede.repository.branch",
        "SEDE_REPOSITORY_BRANCH");

    private final static SystemSettingLookup REPOSITORY_URL = new SystemSettingLookup(
        "https://github.com/fmohr/SEDE.git",
        "edd.sede.repository.url",
        "SEDE_REPOSITORY_URL");



    private final static SystemSettingLookup PROXY_REPOSITORY_BRANCH = new SystemSettingLookup(
        "master",
        "edd.sedeProxy.repository.branch",
        "SEDEPROXY_REPOSITORY_BRANCH");

    private final static SystemSettingLookup PROXY_REPOSITORY_URL = new SystemSettingLookup(
        "https://github.com/aminfa/SEDE.proxy.git",
        "edd.sedeProxy.repository.url",
        "SEDEPROXY_REPOSITORY_URL");

    private final LockableDir localRepoDir;

    private final GitRepository sedeCoreRepository;
    private final GitRepository sedeProxyRepository;

    public SEDECodeBase(LockableDir eddHome) {
        this.localRepoDir = eddHome.getChild(CODE_BASE_DIR.lookup());

        this.sedeCoreRepository =
            new GitRepository(REPOSITORY_URL.lookup(), REPOSITORY_BRANCH.lookup(), localRepoDir.getChild("core").toFile());
        this.sedeProxyRepository =
            new GitRepository(PROXY_REPOSITORY_URL.lookup(), PROXY_REPOSITORY_BRANCH.lookup(), localRepoDir.getChild("proxy").toFile());
    }

    public GitRepository getSedeCoreRepository() {
        return sedeCoreRepository;
    }

    public GitRepository getSedeProxyRepository() {
        return sedeProxyRepository;
    }


    public File getSedeCoreDirectory() {
        return sedeCoreRepository.getLocalRepoDir();
    }

    public File getSedeProxyDirectory() {
        return sedeProxyRepository.getLocalRepoDir();
    }

    public synchronized boolean retrieve( boolean update) {
        return Uncheck.callEach(
            () -> sedeCoreRepository.retrieve(update),
            () -> sedeProxyRepository.retrieve(update))
            .contains(true); //  at least one repository was updated
    }

    public synchronized void buildSEDE(Executor executor) {
        DefaultProcessHandleBuilder processBuilder = new DefaultProcessHandleBuilder(executor);
        processBuilder.setDisplayName("Build SEDE");

        File workingDir = sedeCoreRepository.getLocalRepoDir();
        processBuilder.setWorkingDir(workingDir);

        String gradleExecPath = GradleProjectBuild.findGradleExecutable(workingDir);
        processBuilder.setExecutable(gradleExecPath);
        processBuilder.setArgs(Arrays.asList("jarjar"));

        DefaultProcessHandle handle = processBuilder.build().start();
        ProcessResult result = handle.waitForFinish();
        result.rethrowFailure();
        result.assertNormalExitValue();
    }

}
