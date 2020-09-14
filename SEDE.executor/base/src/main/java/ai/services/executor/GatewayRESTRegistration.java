package ai.services.executor;

import ai.services.executor.local.LocalExecutorRegistry;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.upb.sede.beta.IExecutorRegistration;
import de.upb.sede.util.ModifiableURI;
import de.upb.sede.util.Streams;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.entity.ContentType;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

import static ai.services.channels.StdRESTPaths.GW_REGISTER;

public class GatewayRESTRegistration implements ExecutorRegistrationController {

    private static final Logger logger = LoggerFactory.getLogger(GatewayRESTRegistration.class);

    private static final ObjectMapper MAPPER = new ObjectMapper();

    private final LocalExecutorRegistry localRegistry;

    public GatewayRESTRegistration(LocalExecutorRegistry localRegistry) {
        this.localRegistry = localRegistry;
    }

    public GatewayRESTRegistration() {
        localRegistry = LocalExecutorRegistry.INSTANCE;
    }

    @Override
    public void register(Executor executor) {
        if(localRegistry != null)
            localRegistry.put(Objects.requireNonNull(executor.getConfiguration().getExecutorId()),
                executor);
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
