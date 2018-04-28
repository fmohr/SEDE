package de.upb.sede.exec;

import de.upb.sede.requests.ExecutorRegistration;
import de.upb.sede.webinterfaces.client.HTTPClientRequest;

public class HttpRegistrator {
	
	public static String registerToGateway(String gatewayHost, ExecutorRegistration registration) {
		
		HTTPClientRequest httpRegistration = new HTTPClientRequest(gatewayHost + "/" + "register");
		
		String registrationAnswer = httpRegistration.send(registration.toJsonString());
		
		return registrationAnswer;
	}
}
