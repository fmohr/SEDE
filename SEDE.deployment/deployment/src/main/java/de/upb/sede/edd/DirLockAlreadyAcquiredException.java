package de.upb.sede.edd;

public class DirLockAlreadyAcquiredException extends Exception {

    private LockableDir dir;

    DirLockAlreadyAcquiredException(LockableDir dir) {
        this.dir = dir;
    }

    public LockableDir getDir() {
        return dir;
    }

    public void setDir(LockableDir dir) {
        this.dir = dir;
    }
}
