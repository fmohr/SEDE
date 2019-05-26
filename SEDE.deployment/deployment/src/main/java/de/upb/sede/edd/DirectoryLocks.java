package de.upb.sede.edd;

import de.upb.sede.util.UncheckedException;

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
            throw new IllegalStateException("The directory lock is being locked twice by the same thread: " + dir);
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

    private static class DirectoryLock implements AutoCloseable {

        private final ReentrantLock vmLock;

        private final File file;

        private FileChannel channel;

        private FileLock fileLock;

        public DirectoryLock(ReentrantLock vmLock, LockableDir dir) {
            this.vmLock = vmLock;
            file = new File(dir.toFile(), ".edd.lock");
            if(!file.exists()) {
                try {
                    file.createNewFile();
                } catch (IOException e) {
                    throw new RuntimeException("Cannot create lock file: " + file.toString());
                }
            }
        }

        private boolean tryLock() {
            boolean locked = vmLock.tryLock();
            if(!locked) {
                return false;
            }
            try {
                channel = new RandomAccessFile(file, "rw").getChannel();
                fileLock = channel.tryLock();
                if(fileLock == null) {
                    vmLock.unlock();
                    return false;
                }
            } catch (IOException e) {
                vmLock.unlock();
                throw UncheckedException.throwAsUncheckedException(e);
            }
            return true;
        }

        private void lock() {
            try {
                vmLock.lockInterruptibly();
                channel = new RandomAccessFile(file, "rw").getChannel();
                fileLock = channel.lock();
                if(fileLock == null)
                    throw new RuntimeException("Cannot lock file " + file);
            } catch (Exception e) {
                if(vmLock.isHeldByCurrentThread())
                    vmLock.unlock();
                throw UncheckedException.throwAsUncheckedException(e);
            }
        }

        private boolean isHeldByCurrentThread() {
            return vmLock.isHeldByCurrentThread();
        }

        @Override
        public void close() throws Exception {
            try {
                fileLock.close();
                channel.close();
            } finally {
                vmLock.unlock();
            }
        }
    }
}
