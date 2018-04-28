package de.upb.sede.exec;

import java.util.Arrays;

import de.upb.sede.requests.ExecutorRegistration;

public class AddiererExecutor {
	public static void main(String[] args) {
		String gatewayHost = "http://localhost:6060";
		String host = "addierer Server";
		registerToGateway(gatewayHost, host);
	}
	
	public static void registerToGateway(String gatewayHost, String execHost) {
		ExecutorRegistration execRegistration = new ExecutorRegistration(execHost, Arrays.asList("java"), Arrays.asList("demo.math.Addierer"));
		
		String registrationAnswer = HttpRegistrator.registerToGateway(gatewayHost, execRegistration);
		
		System.out.println("Gateway responded to registration with: " + registrationAnswer);
	
	}
}
