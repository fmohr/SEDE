package de.upb.sede.edd;

import de.upb.sede.util.FileUtil;
import de.upb.sede.util.Uncheck;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class DefaultLockableDir implements LockableDir {

    private final File directoryFile;

    public static String urlEncode(String text) {
        try {
            return URLEncoder.encode(text, StandardCharsets.UTF_8.toString());
        } catch (UnsupportedEncodingException e) {
            throw Uncheck.throwAsUncheckedException(e);
        }
    }

    public DefaultLockableDir(String path) {
        this(new File(path));
    }

    public DefaultLockableDir(File file) {
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
    public void clear() {
        try(AutoCloseable lockDir = lockDir(false)) {
            FileUtils.cleanDirectory(toFile());
        } catch (Exception e) {
            throw Uncheck.throwAsUncheckedException(e);
        }
    }

    @Override
    public LockableDir getChild(String childDir) {
        File childFile = new File(directoryFile, urlEncode(childDir));
        if(childFile.getParentFile().equals(directoryFile)) {
            return new DefaultLockableDir(childFile);
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

    @Override
    public File subFile(String fileName) {
        return new File(toFile(), urlEncode(fileName));
    }
}