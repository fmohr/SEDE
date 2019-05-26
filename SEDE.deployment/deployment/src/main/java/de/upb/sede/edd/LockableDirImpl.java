package de.upb.sede.edd;

import de.upb.sede.util.FileUtil;

import java.io.File;

public class LockableDirImpl implements LockableDir {

    private final File directoryFile;

    public LockableDirImpl(String absolutePath) {
        this(new File(absolutePath));
    }

    public LockableDirImpl(File file) {
        this.directoryFile = FileUtil.canonicalize(file);
        if(! directoryFile.isAbsolute()) {
            throw new IllegalArgumentException("File or path is not absolute:" + directoryFile.toString());
        }
        if(directoryFile.isFile()) {
            throw new IllegalArgumentException("File or path is not a directory: " + directoryFile.toString());
        }
        if(! directoryFile.exists()) {
            boolean created = directoryFile.mkdirs();
            if(!created)
                throw new IllegalArgumentException("Could not create directory: " + directoryFile.toString());
        }
    }

    @Override
    public LockableDir getChild(String childDir) {
        File childFile = new File(directoryFile, childDir);
        if(childFile.getParentFile().equals(directoryFile)) {
            return new LockableDirImpl(childFile);
        } else {
            throw new IllegalArgumentException("Cannot create a *direct* sub folder called '" + childDir + "' for: " + this);
        }
    }

    @Override
    public AutoCloseable lockDir(boolean tryLock) throws DirLockAlreadyAcquiredException {
        if(! tryLock) {
            return DirectoryLocks.lockDir(this);
        } else {
            return DirectoryLocks.tryLockDir(this);
        }
    }


    public String toString() {
        return toFile().getAbsolutePath();
    }

    @Override
    public File toFile() {
        return directoryFile;
    }
}
