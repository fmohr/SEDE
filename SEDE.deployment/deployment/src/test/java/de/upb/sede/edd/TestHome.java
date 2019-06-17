package de.upb.sede.edd;

import de.upb.sede.util.SystemPropsUtil;
import de.upb.sede.util.Uncheck;
import org.apache.commons.io.FileUtils;

public class TestHome extends DefaultLockableDir {

    public TestHome() {
        super(SystemPropsUtil.getWorkingDir() + "/testhome");
    }

    public EDDHome toEDDHome() {
        return new EDDHome(toFile());
    }

    @Override
    public AutoCloseable lockDir(boolean tryLock) throws DirLockAlreadyAcquiredException {
        throw new UnsupportedOperationException();
    }

    public static void clear() {
        System.out.println("Clearing test home..");
        DirectoryLocks.resetLocks();
        Uncheck.<Void>call(() -> {
            FileUtils.deleteDirectory(new TestHome().toFile()); return null;});
    }

}
