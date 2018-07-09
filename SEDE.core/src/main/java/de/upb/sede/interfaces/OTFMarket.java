package de.upb.sede.interfaces;

import de.upb.sede.requests.ExecutorRegistration;

public interface OTFMarket {
	public void register(ExecutorRegistration registry);

	public String getServices();

	public String getGatewayhost();

	public String getGatewayhostFor(String Service);
}