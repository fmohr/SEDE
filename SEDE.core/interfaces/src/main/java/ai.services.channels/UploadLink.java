package ai.services.channels;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;

public interface UploadLink extends AutoCloseable {

    OutputStream getPayloadStream() throws IOException;

    void setPayload(byte[] payload);

}
