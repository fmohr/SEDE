package ai.services.channels;

import de.upb.sede.core.ServiceInstanceHandle;

import java.io.*;

public class StdFSServiceStorageChannel implements ServiceStorageChannel {

    private final String serviceStoreLocation;

    public StdFSServiceStorageChannel(String serviceStoreLocation) {
        this.serviceStoreLocation = serviceStoreLocation;
    }

    @Override
    public DownloadLink loadService(ServiceInstanceHandle serviceInstanceHandle) {
        String serviceInstanceFilePath = pathFor(serviceInstanceHandle);
        return new FileLoad(serviceInstanceFilePath);
    }

    @Override
    public UploadLink storeService(ServiceInstanceHandle serviceInstanceHandle) {
        String serviceInstanceFilePath = pathFor(serviceInstanceHandle);
        return new FileStorage(serviceInstanceFilePath);
    }

    private static class FileLoad implements DownloadLink {

        private final String path;

        private InputStream fileInputStream;

        FileLoad(String path) {
            this.path = path;
        }

        @Override
        public InputStream getStream() throws IOException {
            fileInputStream = new BufferedInputStream(new FileInputStream(path));
            return fileInputStream;
        }

        public byte[] getBytes() throws IOException {
            return getStream().readAllBytes();
        }

        @Override
        public void close() throws Exception {
            if(fileInputStream == null) {
                throw new IllegalStateException("Service file was not opened: " + path);
            }
            fileInputStream.close();
        }
    }

    private static class FileStorage implements UploadLink {

        private final String path;

        private byte[] payloadAsByte;

        private BufferedOutputStream fileOutputStream;

        FileStorage(String path) {
            this.path = path;
        }

        @Override
        public OutputStream getPayloadStream() throws IOException {
            File serviceFile = new File(path);
            serviceFile.getParentFile().mkdirs();
            fileOutputStream = new BufferedOutputStream(new FileOutputStream(path));
            return fileOutputStream;
        }

        @Override
        public void setPayload(byte[] payload) {
            payloadAsByte = payload;
        }

        @Override
        public void close() throws Exception {
            if(fileOutputStream != null) {
                fileOutputStream.close();
            } else if(payloadAsByte != null){
                getPayloadStream().write(payloadAsByte);
                close();
            } else {
                throw new IllegalStateException("Payload was not supplied.");
            }
        }
    }



    public String pathFor(ServiceInstanceHandle serviceInstanceHandle) {
        String serviceClassPath = serviceInstanceHandle.getClasspath();
        String instanceId = serviceInstanceHandle.getId();
        return pathFor(serviceStoreLocation, serviceClassPath, instanceId);
    }

    /**
     * Returns the path of storage for the requested instance.
     *
     * @param serviceClasspath
     *            class path of the service
     * @param instanceid
     *            id of the instance to get the path for.
     * @return Path of the requested instance.
     */
    public static String pathFor(String servicesPath, String serviceClasspath, String instanceid) {
        int max = 200;
        /*
         * maximum number of characters that is allowed service classpath to be. the
         * first few characters are cut in order to get under the max amount.
         */
        if (serviceClasspath.length() > max) {
            serviceClasspath = serviceClasspath.substring(serviceClasspath.length() - max);
        }
        return servicesPath + "/" + serviceClasspath + "/" + instanceid;
    }
}
