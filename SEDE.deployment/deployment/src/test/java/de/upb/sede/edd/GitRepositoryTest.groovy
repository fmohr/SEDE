package de.upb.sede.edd

import de.upb.sede.util.Uncheck
import org.eclipse.jgit.api.Git
import org.eclipse.jgit.lib.AnyObjectId
import org.eclipse.jgit.lib.Ref
import spock.lang.Specification

import java.util.concurrent.Executors

class GitRepositoryTest extends Specification {

    void setupClass() {
        TestHome.clear()
    }



    def "test repo" () {
        given:
        def home = new TestHome();
        def repoDir = home.getChild("testRepo");
        def gitRepo = new GitRepository("https://github.com/github/testrepo.git", repoDir.toFile())
        expect:
        ! gitRepo.isValidLocalRepository()
        when:
        def lock = repoDir.lockDir(false)
        gitRepo.retrieve(false)
        lock.close()
        then:
        gitRepo.isValidLocalRepository()
        new File(repoDir.toFile(), "test").exists()
        new File(repoDir.toFile(), "test/alias.c").exists()

        when:
        gitRepo = new GitRepository(
            "https://github.com/github/testrepo.git", "topic/green", repoDir.toFile())
        gitRepo.retrieve(true)
        def validRepo = gitRepo.isValidLocalRepository()
        then:
        validRepo
        new File(repoDir.toFile(), "README.md").exists()
    }

    def "test sede repo" () {
        def sede = new SEDECodeBase(new TestHome())
        sede.retrieve(true)
        expect:
        sede.sedeCoreDirectory.exists()
        new File(sede.sedeCoreDirectory, "README.md").exists()
    }

    def "test remote branch" () {
        def remoteURL = "https://github.com/github/testrepo.git"
        Ref head = Uncheck.call(Git.lsRemoteRepository().setRemote(remoteURL).&callAsMap).get("HEAD")
        if (head != null) {
            if(head.isSymbolic()) {
                Ref b = head.getTarget();
                println b.name
            } else {
                AnyObjectId id = head.getObjectId();
                println id.name
            }
        }
        expect:
        true
    }
}
