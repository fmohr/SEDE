package de.upb.sede.exec;

import java.util.Arrays;

import de.upb.sede.requests.ExecutorRegistration;
import de.upb.sede.webinterfaces.client.HTTPClientRequest;

public class HttpRegistration {
	
	public static String registerToGateway(String gatewayHost, ExecutorRegistration registration) {
		
		HTTPClientRequest httpRegistration = new HTTPClientRequest(gatewayHost + "/" + "register");
		
		String registrationAnswer = httpRegistration.send(registration.toJsonString());
		
		return registrationAnswer;
	}
}
