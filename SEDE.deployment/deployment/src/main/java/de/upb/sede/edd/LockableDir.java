package de.upb.sede.edd;

import java.io.File;

public interface LockableDir {

    void clear();

    /**
     * URL encodes the given string.
     * @param childDir
     * @return
     */
    LockableDir getChild(String childDir);

    AutoCloseable lockDir(boolean tryLock) throws DirLockAlreadyAcquiredException;

    File toFile();

    /**
     * URL encodes the given file name.
     */
    File subFile(String fileName);

}
