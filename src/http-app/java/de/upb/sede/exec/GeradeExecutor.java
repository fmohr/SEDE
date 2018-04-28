package de.upb.sede.exec;

import java.util.Arrays;

import de.upb.sede.requests.ExecutorRegistration;

public class GeradeExecutor {
	public static void main(String[] args) {
		String gatewayHost = "http://localhost:6060";
		String host = "gerade Server";
		
		ExecutorRegistration execRegistration = new ExecutorRegistration(host, Arrays.asList("java"), Arrays.asList("demo.math.Gerade"));
		
		String registrationAnswer = HttpRegistrator.registerToGateway(gatewayHost, execRegistration);
		
		System.out.println("Gateway responded to registration with: " + registrationAnswer);
	}
}
