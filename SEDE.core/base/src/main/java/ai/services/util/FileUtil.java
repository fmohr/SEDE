package ai.services.util;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;

import ai.services.webinterfaces.client.WriteFileRequest;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Defines methods to access files reading and writing their content. Use this
 * class when you don't want to bother with checked IO-exceptions.
 *
 * @author aminfaez
 *
 */
public class FileUtil {

	private static Logger logger = LoggerFactory.getLogger(FileUtil.class);


    /**
     * The Unix separator character.
     */
    private static final char UNIX_SEPARATOR = '/';

    /**
     * The Windows separator character.
     */
    private static final char WINDOWS_SEPARATOR = '\\';

    /**
     * The system separator character.
     */
    private static final char SYSTEM_SEPARATOR = File.separatorChar;

    /**
     * The separator character that is the opposite of the system separator.
     */
    private static final char OTHER_SEPARATOR;
    static {
        if (SYSTEM_SEPARATOR == WINDOWS_SEPARATOR) {
            OTHER_SEPARATOR = UNIX_SEPARATOR;
        } else {
            OTHER_SEPARATOR = WINDOWS_SEPARATOR;
        }
    }


    /**
	 * Returns the content of the file located at the given file path as a string.
	 *
	 * @param filePath
	 *            path to the file to be read from
	 * @return Content of the file as a string
	 *
	 * @throws UncheckedIOException
	 *             if an I/O error occurs reading from the file
	 */
	public static String readFileAsString(String filePath) {
		byte[] encoded;
		try {
			encoded = Files.readAllBytes(Paths.get(Objects.requireNonNull(filePath)));
			String fileContent = new String(encoded, Charset.defaultCharset());
			return fileContent.replaceAll("\r\n", "\n");
		} catch (IOException e) {
			throw new UncheckedIOException(e);
		}
	}

	private static File getResourceFile(String resourceFilePath) {
		File resourceFile;
		ClassLoader classLoader = FileUtil.class.getClassLoader();
		try{
			resourceFile = new File(classLoader.getResource(resourceFilePath).getFile());
		} catch(NullPointerException ex) {
			throw new RuntimeException("Resource not found: " + resourceFilePath);
		}
		return resourceFile;
	}

	public static String getPathOfResource(String resourceFilePath) {
		String path = getResourceFile(resourceFilePath).getPath();
		if(resourceFilePath.endsWith("/") && !path.endsWith("/")) {
			return path + "/";
		} else {
			return path;
		}
	}

	public static String readResourceAsString(String resourceFilePath) {
		//Get file from resources folder
		ClassLoader classLoader = FileUtil.class.getClassLoader();
		try (InputStream inputStream = classLoader.getResourceAsStream(resourceFilePath)) {
		    Objects.requireNonNull(inputStream, "Could not open stream to resource file: " + resourceFilePath);
			return Streams.InReadString(inputStream);
		} catch (IOException e) {
			throw new UncheckedIOException(e);
		}
	}

	/**
	 * Write the given content to the given file. Creates the directory and the file
	 * if it doesn't exist. Deletes (overwrites) the old content if the file already
	 * exists
	 *
	 * Uses WriteFileRequest.
	 *
	 * @param filePath
	 *            path to the file to be written into
	 * @param fileContent
	 *            content that will be written to the file
	 * @return true, if the writing was successful
	 *
	 * @throws UncheckedIOException
	 *             if an I/O error occurs writing to the file
	 */
	public static boolean writeStringToFile(String filePath, String fileContent) {
		WriteFileRequest writeFile = new WriteFileRequest(filePath, "success");
		return writeFile.send(fileContent).equals("success");
	}

	/**
	 * Creates a list of all file names in the given directory that match the given
	 * regex. The list will not contain sub-directories even if their name match the
	 * given regex.
	 *
	 * @param dirPath
	 *            path to the Directory that is looked into
	 * @param regex
	 *            regular expression used as a filter over the files. If null all
	 *            files in the directory are returned.
	 * @return list of all files
	 */
	public static List<String> listAllFilesInDir(String dirPath, String regex) {

		File directory = new File(Objects.requireNonNull(dirPath));
		if (!directory.isDirectory()) {
			throw new RuntimeException("The given path \"" + dirPath + "\" is not a directory.");
		}

		/*
		 * prepare the regular expression filter:
		 */
		Pattern filterPattern;
		if (regex != null) {
			filterPattern = Pattern.compile(regex);
		} else {
			filterPattern = Pattern.compile("(.*?)"); // match all
		}

		/*
		 * iterate over files in the given directory:
		 */
		List<String> listedFiles = new ArrayList<>();
		File[] files = directory.listFiles();
		for (File file : files) {
			if (filterPattern.matcher(file.getName()).matches() && file.isFile()) {
				listedFiles.add(file.getName());
			}
		}
		return listedFiles;
	}

	public static String getDirPath(String dir) {
		return dir.endsWith("/")? dir.substring(0, dir.length()-1) : dir;
	}




	protected static void mkDir(File file) {
		if (!file.exists()) {
			logger.info("Creating directory: \"" + file.getAbsolutePath() + "\"");
			file.mkdir();
		}
	}

	protected static String getFileExtension(String filename) throws IOException {
		try {
			return filename.substring(filename.lastIndexOf("."));
		} catch (IndexOutOfBoundsException e) {
			logger.debug("File: \"" + filename + "\" does not have a file extension.", e);
			return "";
		}
	}

    /**
     * Makes sure the output file is writable by creating its parent directories if necessary and checking access rights.
     *
     * Throws an IllegalArgumentException if:
     *  - Output file or its parent directories doesn't exist and cannot be created.
     *  - Output file exists but isn't writable. (access rights)
     *
     * @param outputFile file which will be prepared to write to
     *
     * @exception IllegalArgumentException if the output file isn't writable..
     */
    public static void prepareOutputFile(File outputFile) throws IllegalArgumentException {
        if(!outputFile.exists()) {
            /*
             * Output file doesnt exist. make sure its parent directory exists.
             */
            File outputDir = outputFile.getAbsoluteFile().getParentFile();
            if(outputDir == null) {
                throw new IllegalArgumentException("Output file doesn't exist and has no parent directory: " + outputFile );
            }
            if(!outputDir.exists()) {
                boolean success = outputDir.mkdirs();
                if(!success) {
                    throw new IllegalArgumentException("Cannot create the output directory: " + outputDir.getPath());
                }

            }
        } else if(!outputFile.canWrite()) {
            throw new IllegalArgumentException("Cannot write onto " + outputFile);
        }
    }

    public void save(byte[] data, String filePath) {
		try {
			File destination = new File(filePath);
			if (destination.exists())
				System.out.println("Overwriting file: " + destination.getName());
			BufferedOutputStream output = new BufferedOutputStream(new FileOutputStream(destination));
			output.write(data, 0, data.length);
			output.close();
			System.out.println(">>> recv file " + filePath + " (" + (data.length >> 10) + " kiB)");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

    /**
     * Returns a representation of the file path with an alternate extension.  If the file path has no extension,
     * then the provided extension is simply concatenated.  If the file path has an extension, the extension is
     * stripped and replaced with the provided extension.
     *
     * e.g. with a provided extension of ".bar"
     * foo -> foo.bar
     * foo.baz -> foo.bar
     *
     * @param filePath the file path to transform
     * @param extension the extension to use in the transformed path
     * @return the transformed path
     */
    public static String withExtension(String filePath, String extension) {
        if (filePath.toLowerCase().endsWith(extension)) {
            return filePath;
        }
        return removeExtension(filePath) + extension;
    }

    /**
     * Removes the extension (if any) from the file path.  If the file path has no extension, then it returns the same string.
     *
     * @param filePath
     * @return the file path without an extension
     */
    public static String removeExtension(String filePath) {
        int fileNameStart = Math.max(filePath.lastIndexOf('/'), filePath.lastIndexOf('\\'));
        int extensionPos = filePath.lastIndexOf('.');

        if (extensionPos > fileNameStart) {
            return filePath.substring(0, extensionPos);
        }
        return filePath;
    }

    /**
     * Canonicalizes the given file.
     */
    public static File canonicalize(File src) {
        try {
            return src.getCanonicalFile();
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    /**
     * Normalizes the given file, removing redundant segments like /../. If normalization
     * tries to step beyond the file system root, the root is returned.
     */
    public static File normalize(File src) {
        String path = src.getAbsolutePath();
        String normalizedPath = FilenameUtils.normalize(path);
        if (normalizedPath != null) {
            return new File(normalizedPath);
        }
        File root = src;
        File parent = root.getParentFile();
        while (parent != null) {
            root = root.getParentFile();
            parent = root.getParentFile();
        }
        return root;
    }

    public static File prepareTestOutputDir(String outputDirPath, boolean clear) {
        File buildFolder = new File("build");
        File testOutFolder = new File(buildFolder, "test-out");
        File outputFolder = new File(testOutFolder, outputDirPath);
        boolean mkdirs = outputFolder.mkdirs();
        if(!mkdirs && !outputFolder.isDirectory()) {
            throw new IllegalStateException("Coudln't create output folder: " + outputFolder.getAbsolutePath());
        }
        if(clear) {
            clearDirectory(outputFolder);
        }
        return outputFolder;
    }

    private static void clearDirectory(File directory) {
        assert directory.isDirectory();
        for (File file : directory.listFiles()) {
            if(file.isDirectory()) {
                clearDirectory(file);
            }
            boolean deleted = file.delete();
            if(!deleted) {
                throw new IllegalArgumentException("Couldn't delete file: " + file.getAbsolutePath());
            }
        }
    }

}
