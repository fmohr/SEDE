package de.upb.sede.edd;

import de.upb.sede.edd.deploy.EDDSource;
import de.upb.sede.util.NullableCache;
import de.upb.sede.util.Uncheck;
import de.upb.sede.util.OptionalField;
import org.apache.commons.io.FileUtils;
import org.eclipse.jgit.api.*;
import org.eclipse.jgit.internal.storage.file.FileRepository;
import org.eclipse.jgit.lib.*;
import org.eclipse.jgit.util.FS;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.Optional;

public class GitRepository implements EDDSource {
    private final static Logger logger = LoggerFactory.getLogger(GitRepository.class);
    private String remoteURL;
    private OptionalField<String> remoteBranch;

    private File localRepoDir;
    private File gitDir;

    private NullableCache<Repository> repository = new NullableCache<>();

    public GitRepository(String remoteURL, String remoteBranch, File localRepoDir) {
        this(remoteURL, localRepoDir);
        this.remoteBranch = OptionalField.ofNullable(remoteBranch);
    }

    public GitRepository(String remoteURL, File localRepoDir) {
        this.remoteURL = remoteURL;
        this.remoteBranch = OptionalField.empty();
        this.localRepoDir = localRepoDir;
        this.gitDir = new File(localRepoDir, ".git");
    }


    private Repository getLocalRepo() {
        if(repository.isNull()) {
           repository.set( Uncheck.call(() -> new FileRepository(gitDir)));
        }
        return repository.get();
    }

    /**
     * See: https://www.eclipse.org/lists/jgit-dev/msg01892.html
     */
    public boolean isValidLocalRepository() {
        if (RepositoryCache.FileKey.isGitRepository(gitDir, FS.DETECTED)) {
            for (Ref ref : Uncheck.call(getLocalRepo().getRefDatabase()::getRefs)) {
                if (ref.getObjectId() == null)
                    continue;
                return true;
            }
            return false;
        } else {
            return false;
        }
    }

    public void initialize() {
        if(logger.isDebugEnabled())
            logger.debug("Initializing repository {} branch {} into directory {}", getRemoteURL(), getRemoteBranch().orElse("DEFAULT"), getLocalRepoDir());
        if(isValidLocalRepository()) {
            throw new IllegalStateException("Local repository is already initialized.");
        }
        CloneCommand cloning = Git.cloneRepository()
            .setURI(remoteURL)
            .setDirectory(localRepoDir)
//            .setBare(true) // TODO make a bare repository
            ;
        remoteBranch.ifPresent(cloning::setBranch);

        try(Git git = Uncheck.call(cloning)) {
            Uncheck.call(git.fetch().setCheckFetchedObjects(true));
        }
    }

    public void resetHard() {
        if(isValidLocalRepository()) {
            try(Git git = Git.wrap(getLocalRepo())) {
                git.reset().setMode(ResetCommand.ResetType.HARD);
            }
        }
    }

    public void pull() {
        if(logger.isDebugEnabled())
            logger.debug("Pulling new files into repository {} branch {} into directory {}", getRemoteURL(), getRemoteBranch().orElse("DEFAULT"), getLocalRepoDir());
        try(Git git = Git.wrap(getLocalRepo())) {
            PullCommand cmd = git.pull();
            cmd.setRemoteBranchName(
                remoteBranch
                .orElseGet(() -> Uncheck.call(git.getRepository()::getFullBranch)));
            cmd.setRemote(remoteURL);
            Uncheck.call(cmd);
        }
    }

    public void clear() {
        if(localRepoDir.exists()) {
            Uncheck.<Void>call(() -> {FileUtils.deleteDirectory(localRepoDir); return null;});
        }
    }

    public boolean matches() {
        if(!isValidLocalRepository()) {
            return false;
        }
        // TODO check remote url match
        if(remoteBranch.isAbsent() || Uncheck.call(getLocalRepo()::getFullBranch).equals(remoteBranch.get())) {
            return true;
        } else {
            return false;
        }
    }

    private boolean update() {
        resetHard();
        pull();
        return true; // TODO check if local branch is up-to-date with the remote version
    }

    public String getRemoteURL() {
        return remoteURL;
    }

    public Optional<String> getRemoteBranch() {
        return remoteBranch.opt();
    }

    public File getLocalRepoDir() {
        return localRepoDir;
    }

    @Override
    public boolean retrieve(boolean update) {
        if(logger.isDebugEnabled())
            logger.debug("Retrieve repository {} branch {} into directory {}", getRemoteURL(), getRemoteBranch().orElse("DEFAULT"), getLocalRepoDir());
        if(update) {
            if (matches()) {
                boolean newCommits = update();
                return newCommits;
            } else {
                clear();
                initialize();
                return true;
            }
        } else if(!isValidLocalRepository()){
            clear();
            initialize();
            return true;
        } else {
            return false;
        }
    }
}
