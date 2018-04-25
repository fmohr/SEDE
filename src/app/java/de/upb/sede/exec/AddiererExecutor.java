package de.upb.sede.exec;

import java.util.Arrays;

import de.upb.sede.requests.ExecutorRegistration;

public class AddiererExecutor {
	public static void main(String[] args) {
		String gatewayHost = "http://localhost:6060";
		String host = "addierer Server";
		
		ExecutorRegistration execRegistration = new ExecutorRegistration(host, Arrays.asList("java"), Arrays.asList("demo.math.Addierer"));
		
		String registrationAnswer = HttpRegistration.registerToGateway(gatewayHost, execRegistration);
		
		System.out.println("Gateway responded to registration with: " + registrationAnswer);
	}
}
