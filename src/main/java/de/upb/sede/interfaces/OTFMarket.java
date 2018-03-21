package de.upb.sede.interfaces;

import de.upb.sede.requests.Registration;

public interface OTFMarket {
    public void register(Registration registry);
    public String getServices();
    public String getGatewayhost();
    public String getGatewayhostFor(String Service);
}