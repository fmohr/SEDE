package ai.services.channels;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.upb.sede.composition.graphs.nodes.ICompositionGraph;
import de.upb.sede.composition.graphs.nodes.INotification;
import de.upb.sede.exec.IExecutorContactInfo;
import de.upb.sede.util.ExtendedByteArrayOutputStream;
import de.upb.sede.util.ModifiableURI;
import de.upb.sede.util.SystemSettingLookup;
import de.upb.sede.util.UnmodifiableURI;


import org.apache.http.HttpEntity;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.InputStreamEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.slf4j.Logger;

import java.io.Closeable;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Optional;

import static ai.services.channels.StdRESTPaths.*;
import static org.slf4j.LoggerFactory.getLogger;

public class StdRESTExecutorCommChannel implements ExecutorCommChannel, Closeable {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

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
        ModifiableURI interruptURL = baseURL.mod();
        interruptURL.path(EX_INTERRUPT);
        addExecutionIdQueryParam(interruptURL, executionId);
        final HttpPost request = new HttpPost(interruptURL.buildURI());
        try(CloseableHttpResponse response = httpClient.execute(request)) {
            StatusLine statusLine = response.getStatusLine();
            if(statusCodeIsAOk(statusLine.getStatusCode())) {
                loggerInterrupt.debug("Interrupt successful, URL: {}", interruptURL.buildString());
            } else {
                throw statusError(statusLine);
            }
        } catch (ClientProtocolException e) {
            loggerInterrupt.warn("There was a problem with the request: {}", interruptURL.buildString(), e);
        } catch (IOException e) {
            loggerInterrupt.warn("Error while interrupting execution, url: {}", interruptURL.buildString(), e);
        }
    }


    @Override
    public void pushNotification(String executionId, INotification notification) throws PushNotificationException {
        ModifiableURI pushNtfUrl = baseURL.mod();
        pushNtfUrl.path(EX_NOTIFICATION);
        addExecutionIdQueryParam(pushNtfUrl, executionId);
        final HttpPost request = new HttpPost(pushNtfUrl.buildURI());
        request.setEntity(serializePayload(notification));
        try(CloseableHttpResponse response = httpClient.execute(request)) {
            StatusLine statusLine = response.getStatusLine();
            if(statusCodeIsAOk(statusLine.getStatusCode())) {
                loggerNtf.debug("Notification push was successful, URL: {}", pushNtfUrl.buildString());
            } else {
                throw statusError(statusLine);
            }
        } catch (ClientProtocolException e) {
            loggerNtf.warn("There was a problem with the request: {}", pushNtfUrl.buildString(), e);
            throw new PushNotificationException(e);
        } catch (IOException e) {
            loggerNtf.warn("Error while pushing notification, url: {}, notification: {}", pushNtfUrl.buildString(), notification, e);
            throw new PushNotificationException(e);
        }
    }

    public ExecutionDataChannel dataChannel(String executionId) {
        return new ExecutionDataChannel() {

            @Override
            public UploadLink getUploadLink(final String fieldname, final String semantictype) {

                return new UploadLink() {

                    private ExtendedByteArrayOutputStream outputStream;
                    private byte[] payload;

                    @Override
                    public void close() {
                        final ModifiableURI dataUploadUrl = baseURL.mod();
                        dataUploadUrl.path(EX_SETFIELD);
                        addExecutionIdQueryParam(dataUploadUrl, executionId);
                        dataUploadUrl.queryParam("fieldname", fieldname);
                        dataUploadUrl.queryParam("semantictype", semantictype);

                        final HttpPost request = new HttpPost(dataUploadUrl.buildURI());
                        request.setEntity(payload());
                        try(CloseableHttpResponse response = httpClient.execute(request)) {
                            StatusLine statusLine = response.getStatusLine();
                            if(statusCodeIsAOk(statusLine.getStatusCode())) {
                                loggerData.debug("Data upload was successful, URL: {}", dataUploadUrl.buildString());
                            } else {
                                throw statusError(statusLine);
                            }
                        } catch (ClientProtocolException e) {
                            loggerData.warn("There was a problem with the request: {}", dataUploadUrl.buildString(), e);
                            throw new PushNotificationException(e);
                        } catch (IOException e) {
                            loggerData.warn("Error while uploading data to field {} with semantic type {}, url: {}",
                                fieldname, semantictype, dataUploadUrl.buildString(), e);
                            throw new PushNotificationException(e);
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
        };
    }

    @Override
    public void deployGraph(String executionId, ICompositionGraph toBeDeployed) throws GraphDeploymentException {
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
            throw new PushNotificationException(e);
        } catch (IOException e) {
            loggerDeploy.warn("Error while deploying graph, url: {}", deployUrl.buildString(), e);
            throw new PushNotificationException(e);
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
