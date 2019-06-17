package de.upb.sede.edd.process;


import de.upb.sede.util.FileUtil;

import javax.annotation.Nullable;
import java.io.File;
import java.util.*;
import java.util.stream.Collectors;

/**
 */
public class ClassPath implements Iterable<String> {
    private final List<String> files;

    private final String displayName;

    public ClassPath() {
        this("file collection", null);
    }

    public ClassPath(Collection<File> files) {
        this("file collection", files);
    }

    public ClassPath(File[] files) {
        this("file collection", Arrays.asList(files));
    }

    public ClassPath(String displayName) {
        this(displayName, null);
    }

    public ClassPath(String displayName, @Nullable Collection<File> files) {
        this.displayName = displayName;
        this.files = new ArrayList<>();
        if (files != null) {
            this.files(files);
        }
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setFiles(Iterable<File> path) {
        files.clear();
        for(File f : path) {
            files.add(f.toString());
        }
    }

    public void setFiles(File... paths) {
        files.clear();
        files(paths);
    }

    public void files(File... paths) {
        Arrays.stream(paths).map(File::toString).forEach(files::add);
    }


    public void files(Collection<File> paths) {
        paths.stream().map(File::toString).forEach(files::add);
    }

    public void wildcardDirs(List<File> paths) {
        Optional<File> file = paths.stream().filter(File::isFile).findAny();
        if(file.isPresent()) {
            throw new IllegalArgumentException(getDisplayName() + " can only create a wildcard directory of non file files:" + file.toString());
        }
        paths.stream().map(f -> FileUtil.canonicalize(f).toString() + "/*").forEach(files::add);
    }

    public void wildcardDirs(File... paths) {
        wildcardDirs(Arrays.asList(paths));
    }

    public void setWildcardDirs(List<File> paths) {
        files.clear();
        wildcardDirs(paths);
    }

    public void setWildcardDirs(File... paths) {
        files.clear();
        wildcardDirs(paths);
    }

    @Override
    public String toString() {
        return getDisplayName();
    }

    public String getSingleFile() throws IllegalStateException {
        Iterator<String> iterator = iterator();
        if (!iterator.hasNext()) {
            throw new IllegalStateException(String.format("Expected %s to contain exactly one file, however, it contains no files.", getDisplayName()));
        }
        String singleFile = iterator.next();
        if (iterator.hasNext()) {
            throw new IllegalStateException(String.format("Expected %s to contain exactly one file, however, it contains more than one file.", getDisplayName()));
        }
        return singleFile;
    }

    @Override
    public Iterator<String> iterator() {
        return getFiles().iterator();
    }

    public String getAsPath() {
        return String.join(File.pathSeparator, getFiles());
    }

    public boolean contains(File file) {
        return getFiles().contains(file);
    }

    public boolean isEmpty() {
        return getFiles().isEmpty();
    }


    public List<String> getFiles() {
        return files;
    }

}
