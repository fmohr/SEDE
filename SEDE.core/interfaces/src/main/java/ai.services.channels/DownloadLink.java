package ai.services.channels;


import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public interface DownloadLink extends AutoCloseable{

    InputStream getStream() throws IOException;

    byte[] getBytes() throws IOException;

}
