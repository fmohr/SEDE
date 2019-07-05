package de.upb.sede.util;

import de.upb.sede.util.Cache;
import de.upb.sede.util.FileUtil;
import de.upb.sede.util.SettableCache;

import java.io.File;
import java.util.function.Function;

public class FileCache<T> implements SettableCache<T> {

    private final File file;

    private final Function<String, T> decoder;

    private final Function<T, String> encoder;

    public FileCache(File dir, String fileName, Function<String, T> decoder, Function<T, String> encoder) {
        this(new File(dir, fileName), decoder, encoder);

    }
    public FileCache(File file, Function<String, T> decoder, Function<T, String> encoder) {
        this.file = file;
        this.decoder = decoder;
        this.encoder = encoder;
    }

    public boolean clear() {
        if(file.isFile())
            return file.delete();
        return true;
    }

    @Override
    public T access() {
        if(file.isFile()) {
            return decoder.apply(FileUtil.readFileAsString(file.getPath()));
        } else {
            return decoder.apply(null);
        }
//        throw new IllegalStateException(file.getPath()  + " does not exist.");
    }


    @Override
    public boolean set(T t) {
        boolean wasCleared = clear();
        if(!wasCleared) {
            return false;
        }
        FileUtil.writeStringToFile(file.getPath(),
            encoder.apply(t));
        return true;
    }

    public File getFile() {
        return file;
    }
}
