package ai.services.executor;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import ai.services.beta.IExecutorRegistration;
import ai.services.util.ModifiableURI;
import ai.services.util.Streams;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.entity.ContentType;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

import static ai.services.channels.StdRESTPaths.GW_REGISTER;

public class GatewayRESTRegistration implements ExecutorInstanceRegistrationController {

    private static final Logger logger = LoggerFactory.getLogger(GatewayRESTRegistration.class);

    private static final ObjectMapper MAPPER = new ObjectMapper();

    @Override
    public void register(Executor executor) {
        IExecutorRegistration registration = executor.registration();
        byte[] registrationBytes;
        try {
            registrationBytes = MAPPER.writeValueAsBytes(registration);
        } catch (JsonProcessingException e) {
            logger.error("Couldn't serialize executor registration: {}", registration, e);
            return;
        }
        executor.getConfiguration().getGateways().forEach(gatewayurl -> registerToGateway(gatewayurl, registrationBytes));
    }

    private void registerToGateway(String gatewayUrl, byte[] registration) {
        ModifiableURI modifiableURI;
        try {
            modifiableURI = ModifiableURI.fromHttpUrl(gatewayUrl);
        } catch (IllegalArgumentException ex) {
            logger.error("Couldn't create gateway http url from: {}", gatewayUrl, ex);
            return;
        }
        modifiableURI.path(GW_REGISTER);
        try(CloseableHttpClient httpClient = HttpClients.createMinimal()) {
            HttpPost request = new HttpPost(modifiableURI.buildURI());
            request.setEntity(new ByteArrayEntity(registration, ContentType.APPLICATION_JSON));
            try(CloseableHttpResponse response = httpClient.execute(request)) {
                logger.info("Executor http registration to gateway: {}. " +
                    "\nResponse status code: {}" +
                    "\nRespose body: {}", gatewayUrl,
                    response.getStatusLine().getStatusCode(), Streams.InReadString(response.getEntity().getContent()));
            }
        } catch (IOException e) {
            logger.error("Error registering to gateway with: {}", modifiableURI, e);
        }
    }

}
