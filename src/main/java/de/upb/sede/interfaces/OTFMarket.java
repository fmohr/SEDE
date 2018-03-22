package de.upb.sede.interfaces;

import de.upb.sede.requests.GSMRegistration;

public interface OTFMarket {
    public void register(GSMRegistration registry);
    public String getServices();
    public String getGatewayhost();
    public String getGatewayhostFor(String Service);
}