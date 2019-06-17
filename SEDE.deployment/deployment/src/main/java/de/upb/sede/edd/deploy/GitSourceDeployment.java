package de.upb.sede.edd.deploy;

import de.upb.sede.edd.GitRepository;
import de.upb.sede.edd.deploy.model.GitSource;
import de.upb.sede.util.Kneadable;

import java.io.File;
import java.util.Objects;

public class GitSourceDeployment extends AbstractSourceDeployment implements EDDSource {

    private GitSource gitSource;

    public static final String TYPE = "git-repo";

    public GitSourceDeployment(String displayName, File sourceHome, Kneadable spec) {
        super(displayName, sourceHome, spec);
        gitSource = Objects.requireNonNull(getSourceDefinition().knead(GitSource.class));
    }

    @Override
    protected boolean retrieveSource(boolean forcedUpdates) throws Exception {
        GitRepository gitRepository;
        if(gitSource.getBranch().isPresent()) {
            gitRepository = new GitRepository(gitSource.getUrl(), gitSource.getBranch().get(), getSourceHome());
        } else {
            gitRepository = new GitRepository(gitSource.getUrl(), getSourceHome());
        }
        return gitRepository.retrieve(forcedUpdates);
    }

}
