package de.upb.sede.edd

import de.upb.sede.util.FileUtil
import org.junit.Rule
import org.junit.contrib.java.lang.system.EnvironmentVariables
import org.junit.rules.TemporaryFolder
import spock.lang.Specification

import java.util.concurrent.Semaphore

class LockableDirImplTests extends Specification {

    @Rule
    public final EnvironmentVariables envs = new EnvironmentVariables()

    @Rule
    public TemporaryFolder testHome = new TemporaryFolder();

    def "test EDDHome::lockDir"() {
        when:
        def home = EDDHome.lookupHome()
        then:
        home == new File(System.getProperty("user.home") + "/.edd").getAbsoluteFile()
        when:
        envs.set(EDDHome.EDD_USER_HOME_ENV_KEY, "custom_folder")
        home = EDDHome.lookupHome()
        then:
        home == new File("custom_folder")
        when:
        System.properties[EDDHome.EDD_USER_HOME_PROPERTY_KEY] = "another_custom_folder"
        home = EDDHome.lookupHome()
        then:
        home == new File("another_custom_folder")

        when: "temporary test folder"
        System.properties[EDDHome.EDD_USER_HOME_PROPERTY_KEY] = testHome.getRoot().absolutePath
        home = EDDHome.lookupHome()
        then:
        home == testHome.getRoot()
    }

    def "test dir creation" () {
        given:
        System.properties[EDDHome.EDD_USER_HOME_PROPERTY_KEY] = testHome.getRoot().absolutePath
        when:
        def eddhome = new EDDHome()
        def subDir = eddhome.getChild("childDir")
        then:
        eddhome.toFile() == FileUtil.canonicalize(testHome.getRoot())
        subDir.toFile() == FileUtil.canonicalize(new File(testHome.getRoot(), "childDir"))
        eddhome.toFile().exists()
        subDir.toFile().exists()

        when:
        eddhome.getChild("a/b")
        then:
//        childAb.toFile().getParentFile() == testHome.getRoot()
        thrown(IllegalArgumentException)
    }

    def "test dir locking" () {
        given:
        System.properties[EDDHome.EDD_USER_HOME_PROPERTY_KEY] = testHome.getRoot().absolutePath
        when:
        def eddhome = new EDDHome()
        eddhome.lockDir(false)
        then:
        thrown(UnsupportedOperationException)

        when: "locking again from the same thread"
        def subFolder = eddhome.getChild("child1")
        def lock = subFolder.lockDir(false)
        subFolder.lockDir(true) // lock again
        then:
        thrown(IllegalStateException)
        when:
        subFolder.lockDir(false) // lock again
        then:
        thrown(IllegalStateException)

        when: "locking from a different thread"
        def exceptionThrown = false
        Thread thread = new Thread( {
            try {
                subFolder.lockDir(true)
            } catch(DirLockAlreadyAcquiredException) {
                exceptionThrown = true
            }
        })
        thread.start()
        thread.join(100)
        then:
//        thrown(DirLockAlreadyAcquiredException)
        exceptionThrown
        ! thread.isAlive()

        when:
        exceptionThrown = false
        def anotherThread = new Thread( {
            try {
                subFolder.lockDir(false)
            }catch(InterruptedException) {
                exceptionThrown = true
            }
        })
        anotherThread.start()
        anotherThread.join(100)
        then:
        anotherThread.isAlive()
        !exceptionThrown

        when:
        anotherThread.interrupt()
        anotherThread.join(2000)
        def finishFlag = new Semaphore(0)
        def yetAnotherThread = new Thread( {
            def lock2 = subFolder.lockDir(false)
            finishFlag.acquire()
            lock2.close()
        })
        yetAnotherThread.start()
        then:
//        anotherThread.isInterrupted()
        !anotherThread.isAlive()
        exceptionThrown
        yetAnotherThread.isAlive()

        when:
        lock.close()
        subFolder.lockDir(true)
        then:
        yetAnotherThread.isAlive()
        thrown(DirLockAlreadyAcquiredException)

        when:
        finishFlag.release()
        yetAnotherThread.join(100)
        then:
        ! yetAnotherThread.isAlive()
    }


}
