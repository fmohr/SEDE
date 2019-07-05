package de.upb.sede.edd;

import de.upb.sede.util.Uncheck;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantLock;

public class DirectoryLocks {

    private final static Map<String, ReentrantLock> JVM_PATH_LOCKS = new ConcurrentHashMap<>();

    public DirectoryLocks() {
    }

    private static ReentrantLock getDirectoryLock(LockableDir dir) {
        return JVM_PATH_LOCKS.computeIfAbsent(dir.toString(), (String directory) -> new ReentrantLock());
    }

    public static void resetLocks() {
        JVM_PATH_LOCKS.clear();
    }

    public static AutoCloseable lockDir(LockableDir dir) {
        DirectoryLock directoryLock = new DirectoryLock(getDirectoryLock(dir), dir);
        if(directoryLock.isHeldByCurrentThread()) {
//            throw new IllegalStateException("The directory lock is being locked twice by the same thread: " + dir);
            return directoryLock;
        }
        directoryLock.lock(); // this statement blocks until the lock has been acquired.
        if(directoryLock.vmLock == null || directoryLock.fileLock == null) {
            throw new NullPointerException("Directory locks aren't correctly initialized.");
        }
        return directoryLock; // close this, to release the locks.
    }

    public static AutoCloseable tryLockDir(LockableDir dir) throws DirLockAlreadyAcquiredException {
        DirectoryLock directoryLock = new DirectoryLock(getDirectoryLock(dir), dir);
        if(directoryLock.isHeldByCurrentThread()) {
            throw new IllegalStateException("The directory lock is being locked twice by the same thread: " + dir);
        }
        boolean acquired = directoryLock.tryLock(); // this statement returns immediately.
        if(!acquired) {
            throw new DirLockAlreadyAcquiredException(dir);
        }
        if(directoryLock.vmLock == null || directoryLock.fileLock == null) {
            throw new NullPointerException("Directory locks aren't correctly initialized.");
        }
        return directoryLock; // close this, to release the locks.
    }

    private static File toLock(LockableDir dir) {
        String dirName = dir.toFile().getName();
        return new File(dir.toFile().getParent(), "." + dirName + ".lock");
    }

    private static class DirectoryLock implements AutoCloseable {

        private final ReentrantLock vmLock;

        private final File lockFile;

        private FileChannel channel;

        private FileLock fileLock;

        public DirectoryLock(ReentrantLock vmLock, LockableDir dir) {
            this.vmLock = vmLock;
            lockFile = toLock(dir);
            if(!lockFile.exists()) {
                try {
                    lockFile.createNewFile();
                } catch (IOException e) {
                    throw new RuntimeException("Cannot create lock lockFile: " + lockFile.toString());
                }
            }
        }

        private boolean tryLock() {
            boolean locked = vmLock.tryLock();
            if(!locked) {
                return false;
            }
            try {
                channel = new RandomAccessFile(lockFile, "rw").getChannel();
                fileLock = channel.tryLock();
                if(fileLock == null) {
                    vmLock.unlock();
                    return false;
                }
            } catch (IOException e) {
                vmLock.unlock();
                throw Uncheck.throwAsUncheckedException(e);
            }
            return true;
        }

        private void lock() {
            try {
                vmLock.lockInterruptibly();
                channel = new RandomAccessFile(lockFile, "rw").getChannel();
                fileLock = channel.lock();
                if(fileLock == null)
                    throw new RuntimeException("Cannot lock lockFile " + lockFile);
            } catch (Exception e) {
                if(vmLock.isHeldByCurrentThread())
                    vmLock.unlock();
                throw Uncheck.throwAsUncheckedException(e);
            }
        }

        private boolean isHeldByCurrentThread() {
            return vmLock.isHeldByCurrentThread();
        }

        @Override
        public void close() throws Exception {
            try {
                if(fileLock != null) {
                    fileLock.close();
                    channel.close();
                }
            } finally {
                if(vmLock.isLocked()) {
                    vmLock.unlock();
                }
            }
        }
    }
}
