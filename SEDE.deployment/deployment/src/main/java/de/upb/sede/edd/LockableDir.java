package de.upb.sede.edd;

import java.io.File;

public interface LockableDir {

    LockableDir getChild(String childDir);

    AutoCloseable lockDir(boolean tryLock) throws DirLockAlreadyAcquiredException;

    File toFile();

}
