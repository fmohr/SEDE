package ai.services.channels;

import ai.services.composition.IDeployRequest;
import ai.services.composition.INtfInstance;
import ai.services.exec.IExecutionError;
import ai.services.util.*;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import ai.services.composition.graphs.nodes.ICompositionGraph;
import ai.services.exec.IExecutorContactInfo;


import com.fasterxml.jackson.databind.type.ArrayType;
import org.apache.http.HttpEntity;
import org.apache.http.HttpRequest;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.InputStreamEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.slf4j.Logger;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static ai.services.channels.StdRESTPaths.*;
import static org.slf4j.LoggerFactory.getLogger;

public class StdRESTExecutorCommChannel implements ExecutorCommChannel, Closeable {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    private static final JavaType JAVA_TYPE_ERROR_LIST = OBJECT_MAPPER.getTypeFactory().constructArrayType(IExecutionError.class);

    private static final Logger logger = getLogger(StdRESTExecutorCommChannel.class);

    private static final Logger loggerInterrupt = getLogger(StdRESTExecutorCommChannel.class.getName() + ".interrupt");
    private static final Logger loggerPing = getLogger(StdRESTExecutorCommChannel.class.getName() + ".ping");
    private static final Logger loggerNtf = getLogger(StdRESTExecutorCommChannel.class.getName() + ".ntf");
    private static final Logger loggerData = getLogger(StdRESTExecutorCommChannel.class.getName() + ".data");
    private static final Logger loggerDeploy = getLogger(StdRESTExecutorCommChannel.class.getName() + ".exec");

    private static final String CONNECTION_TIMEOUT = new SystemSettingLookup("4",
        "ai.services.channels.StdRESTExecutorCommChannel.connectionTimeout",
        "STD_CONNECTION_TIMEOUT").lookup();

    private final UnmodifiableURI baseURL;

    private final PoolingHttpClientConnectionManager connectionManager;

    private final CloseableHttpClient httpClient;

    public StdRESTExecutorCommChannel(IExecutorContactInfo contactInfo) {
        ModifiableURI uri = ModifiableURI.fromHttpUrl(contactInfo.getURL());
        baseURL = uri.unmodifiableCopy();
        int timeout = (int) (Float.parseFloat(CONNECTION_TIMEOUT) * 1000);
        RequestConfig config = RequestConfig.custom()
            .setConnectTimeout(timeout)
            .setConnectionRequestTimeout(timeout)
            .setSocketTimeout(timeout)
            .build();
        connectionManager = new PoolingHttpClientConnectionManager();
        httpClient =
            HttpClients.custom()
                .setDefaultRequestConfig(config)
                .setConnectionManager(connectionManager)
                .build();
    }

    public Optional<Long> testConnectivity() {
        ModifiableURI pingURL = baseURL.mod();
        pingURL.path(EX_PING);
        final HttpGet request = new HttpGet(pingURL.buildURI());
        long timeStarted = System.currentTimeMillis();
        try(CloseableHttpResponse response = httpClient.execute(request)) {
            StatusLine statusLine = response.getStatusLine();
            if(statusCodeIsAOk(statusLine.getStatusCode())) {
                long roundTripTime = System.currentTimeMillis() - timeStarted;
                loggerPing.debug("Ping successful, URL: {}, RTT: {} ms", pingURL.buildString(), roundTripTime);
                return Optional.of(roundTripTime);
            }
            throw statusError(statusLine);
        } catch (ClientProtocolException e) {
            loggerPing.warn("There was a problem with the request: {}", pingURL.buildString(), e);
            return Optional.empty();
        } catch (IOException e) {
            loggerPing.warn("Ping did not succeed: {}", pingURL.buildString(), e);
            return Optional.empty();
        }
    }

    @Override
    public void interrupt(String executionId) {
        try {
            post(EX_INTERRUPT, executionId);
        } catch (IOException e) {
            loggerInterrupt.error("Error trying to interrupt execution {}", executionId, e);
        }
    }

    @Override
    public void remove(String executionId) {
        try {
            post(EX_REMOVE, executionId);
        } catch (IOException e) {
            logger.error("Error trying to remove execution {}", executionId, e);
        }
    }

    @Override
    public void joinExecution(String executionId) throws InterruptedException {
        try {
            get(EX_JOIN, executionId);
        } catch (IOException e) {
            // TODO inspect the message and make sure its an interruption problem.
            throw new InterruptedException(e.getMessage());
        }
    }

    @Override
    public List<IExecutionError> getErrors(String executionId) {
        ModifiableURI url = baseURL.mod();
        url.path(EX_ERRORS);
        addExecutionIdQueryParam(url, executionId);
        final HttpGet request = new HttpGet(url.buildURI());
        try(CloseableHttpResponse response = httpClient.execute(request)) {
            StatusLine statusLine = response.getStatusLine();
            if(statusCodeIsAOk(statusLine.getStatusCode())) {
                logger.debug("Fetching errors was successful, URL: {}", url.buildString());
                return parseErrors(response.getEntity().getContent());
            } else {
                throw statusError(statusLine);
            }
        } catch (ClientProtocolException e) {
            logger.warn("There was a problem with the request: {}", url, e);
        } catch (IOException e) {
            logger.error("Error trying to fetch execution errors for execution id: {}", executionId, e);
        }
        return Collections.emptyList();
    }

    private List<IExecutionError> parseErrors(InputStream content) throws IOException {
        try {
            return OBJECT_MAPPER.readValue(content, JAVA_TYPE_ERROR_LIST);
        } catch(ClassCastException ex) {
            throw new IOException("Couldn't parse response body as an execution error list", ex);
        }
    }

    @Override
    public void startExecution(String executionId) {
        try {
            post(EX_START, executionId);
        } catch (IOException e) {
            throw new RuntimeException("Error trying to set execution to be started: ", e);
        }
    }

    private void post(String path, String executionId) throws IOException {
        ModifiableURI url = baseURL.mod();
        url.path(path);
        addExecutionIdQueryParam(url, executionId);
        final HttpPost request = new HttpPost(url.buildURI());
        makeRequest(request, url.toString());
    }

    private void get(String path, String executionId) throws IOException {
        ModifiableURI url = baseURL.mod();
        url.path(path);
        addExecutionIdQueryParam(url, executionId);
        final HttpGet request = new HttpGet(url.buildURI());
        makeRequest(request, url.toString());
    }

    private void makeRequest(HttpUriRequest request, String url) throws IOException {
        try(CloseableHttpResponse response = httpClient.execute(request)) {
            StatusLine statusLine = response.getStatusLine();
            if(statusCodeIsAOk(statusLine.getStatusCode())) {
                logger.debug("Request was successful, URL: {}", url);
            } else {
                throw statusError(statusLine);
            }
        } catch (ClientProtocolException e) {
            logger.warn("There was a problem with the request: {}", url, e);
        }
    }

    @Override
    public void pushNotification(INtfInstance ntfRequest) throws PushNotificationException {
        String executionId = ntfRequest.executionId();
        String notification = ntfRequest.getQualifier();

        ModifiableURI pushNtfUrl = baseURL.mod();
        pushNtfUrl.path(EX_NOTIFICATION);
        addExecutionIdQueryParam(pushNtfUrl, executionId);
        final HttpPost request = new HttpPost(pushNtfUrl.buildURI());
        request.setEntity(serializePayload(ntfRequest));
        try(CloseableHttpResponse response = httpClient.execute(request)) {
            StatusLine statusLine = response.getStatusLine();
            if(statusCodeIsAOk(statusLine.getStatusCode())) {
                loggerNtf.debug("Notification push was successful, URL: {}",
                    pushNtfUrl.buildString());
            } else {
                throw statusError(statusLine);
            }
        } catch (ClientProtocolException e) {
            loggerNtf.warn("There was a problem with the request: {}",
                pushNtfUrl.buildString(), e);
            throw new PushNotificationException(e);
        } catch (IOException e) {
            loggerNtf.warn("Error while pushing notification, url: {}, notification: {}",
                pushNtfUrl.buildString(), notification, e);
            throw new PushNotificationException(e);
        }
    }

    public DataChannel dataChannel(String executionId) {
        return new DataChannel() {

            @Override
            public UploadLink getUploadLink(final String fieldname, final String semantictype) {

                return new UploadLink() {

                    private ExtendedByteArrayOutputStream outputStream;
                    private byte[] payload;

                    @Override
                    public void close() throws Exception {
                        final ModifiableURI dataUploadUrl = baseURL.mod();
                        dataUploadUrl.path(EX_SETFIELD);
                        addExecutionIdQueryParam(dataUploadUrl, executionId);
                        dataUploadUrl.queryParam(EX_PARAM_FIELDNAME, fieldname);
                        dataUploadUrl.queryParam(EX_PARAM_SEMANTICTYPE, semantictype);

                        final HttpPost request = new HttpPost(dataUploadUrl.buildURI());
                        request.setEntity(payload());
                        try(CloseableHttpResponse response = httpClient.execute(request)) {
                            StatusLine statusLine = response.getStatusLine();
                            if(statusCodeIsAOk(statusLine.getStatusCode())) {
                                loggerData.debug("Data upload was successful, URL: {}", dataUploadUrl.buildString());
                            } else {
                                throw statusError(statusLine);
                            }
                        }
                    }

                    private HttpEntity payload() {
                        HttpEntity httpEntity;
                        if(payload != null) {
                            httpEntity = new ByteArrayEntity(payload);
                        } else if(outputStream != null) {
                            httpEntity = new InputStreamEntity(outputStream.toInputStream());
                        } else {
                            throw new IllegalStateException("No Data payload was provided.");
                        }
                        return httpEntity;
                    }

                    @Override
                    public OutputStream getPayloadStream() {
                        outputStream = new ExtendedByteArrayOutputStream();
                        return outputStream;
                    }

                    @Override
                    public void setPayload(byte[] payload) {
                        this.payload = payload;
                    }
                };

            }

            @Override
            public DownloadLink getDownloadLink(String fieldname) {
                return new DownloadLink() {

                    boolean requested = false;

                    InputStream downloadStream;

                    private HttpGet createHttpRequest() {
                        final ModifiableURI dataUploadUrl = baseURL.mod();
                        dataUploadUrl.path(EX_GETFIELD);
                        addExecutionIdQueryParam(dataUploadUrl, executionId);
                        dataUploadUrl.queryParam(EX_PARAM_FIELDNAME, fieldname);
                        final HttpGet request = new HttpGet(dataUploadUrl.buildURI());
                        return request;
                    }

                    private void request() {
                        if(requested) {
                            throw new IllegalStateException("Already requested.");
                        }
                        requested = true;
                    }

                    @Override
                    public InputStream getStream() throws IOException {
                        request();
                        HttpGet httpRequest = createHttpRequest();
                        try(CloseableHttpResponse response = httpClient.execute(httpRequest)) {
                            StatusLine statusLine = response.getStatusLine();
                            if(statusCodeIsAOk(statusLine.getStatusCode())) {
                                HttpEntity entity = response.getEntity();
                                downloadStream = entity.getContent();
                                return downloadStream;
                            } else {
                                throw statusError(statusLine);
                            }
                        }
                    }

                    @Override
                    public byte[] getBytes() throws IOException {
                        InputStream stream = getStream();
                        downloadStream = null;
                        return Streams.InReadByteArr(stream);
                    }

                    @Override
                    public void close() throws Exception {
                        if(!requested) {
                            throw new IllegalStateException("Not requested.");
                        }
                        if(downloadStream != null) {
                            downloadStream.close();
                        }
                    }
                };
            }
        };
    }

    @Override
    public void deployGraph(IDeployRequest deployRequest) throws GraphDeploymentException {
        String executionId = deployRequest.executionId();
        ICompositionGraph toBeDeployed = deployRequest.getCompGraph();
        ModifiableURI deployUrl = baseURL.mod();
        deployUrl.path(EX_DEPLOYGRAPH);
        addExecutionIdQueryParam(deployUrl, executionId);
        final HttpPost request = new HttpPost(deployUrl.buildURI());
        request.setEntity(serializePayload(toBeDeployed));
        try(CloseableHttpResponse response = httpClient.execute(request)) {
            StatusLine statusLine = response.getStatusLine();
            if(statusCodeIsAOk(statusLine.getStatusCode())) {
                loggerDeploy.debug("Deploy graph was successful, URL: {}", deployUrl.buildString());
            } else {
                throw statusError(statusLine);
            }
        } catch (ClientProtocolException e) {
            loggerDeploy.warn("There was a problem with the request: {}", deployUrl.buildString(), e);
            throw new GraphDeploymentException(e);
        } catch (IOException e) {
            loggerDeploy.warn("Error while deploying graph, url: {}", deployUrl.buildString(), e);
            throw new GraphDeploymentException(e);
        }
    }



    private static IOException statusError(StatusLine statusLine) {
        return new IOException("Error status code response: " + statusLine.getStatusCode() + ", reason phrase: " + statusLine.getReasonPhrase());
    }

    private static HttpEntity serializePayload(Object payload) {
        ExtendedByteArrayOutputStream outputStream = new ExtendedByteArrayOutputStream();
        try {
            OBJECT_MAPPER.writeValue(outputStream, payload);
        } catch (IOException e) {
            logger.warn("Error trying to serialise the payload: {}", payload, e);
            throw new RuntimeException("Notification not serialisable.", e);
        }
        return new InputStreamEntity(outputStream.toInputStream(), ContentType.APPLICATION_JSON);
    }

    private static void addExecutionIdQueryParam(ModifiableURI url, String executionId) {
        url.queryParam(EX_PARAM_ID, executionId);
    }

    private static boolean statusCodeIsAOk(int statusCode) {
        return statusCode >= 200 && statusCode < 300;
    }

    public void shutdownQuietly() {
        try {
            connectionManager.close();
            httpClient.close();
        } catch (IOException e) {
            logger.warn("Exception while closing excutor comm channel: {} ", this.baseURL.buildString(), e);
        }
    }

    @Override
    public void close() throws IOException {
        connectionManager.close();
        httpClient.close();
    }
}
