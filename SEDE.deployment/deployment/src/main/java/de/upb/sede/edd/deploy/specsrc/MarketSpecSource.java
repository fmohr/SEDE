package de.upb.sede.edd.deploy.specsrc;

import de.upb.sede.edd.deploy.model.DeploymentSpecificationRegistry;
import de.upb.sede.util.Cache;
import de.upb.sede.util.Uncheck;
import de.upb.sede.util.UnmodifiableURI;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class MarketSpecSource extends SpecSourceFromURI implements Cache<DeploymentSpecificationRegistry>, SpecSource {

    private final static Logger logger = LoggerFactory.getLogger(MarketSpecSource.class);

    public MarketSpecSource(UnmodifiableURI specURI) {
        super(specURI);
    }

    protected DeploymentSpecificationRegistry fetch() {
        OkHttpClient client = new OkHttpClient();
        String getRequestURL = getSpecUri().mod().replacePath("get-conf/deployments").buildString();
        Request request = new Request.Builder()
            .url(getRequestURL)
            .get()
            .build();
        logger.info("Fetching the deployment specification from: {}", getRequestURL);

        Response response;
        try {
            response = client.newCall(request).execute();
            return DeploymentSpecificationRegistry.fromString(response.body().string());
        } catch (IOException e) {
            logger.error("Error trying to fetch deployment specifications from: {}", getRequestURL, e);
            throw Uncheck.throwAsUncheckedException(e);
        }

    }
}
