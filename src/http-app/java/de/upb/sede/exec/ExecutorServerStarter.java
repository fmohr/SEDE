package de.upb.sede.exec;

import de.upb.sede.util.Streams;
import de.upb.sede.webinterfaces.server.HTTPServerResponse;
import de.upb.sede.webinterfaces.server.ServerCommandListener;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.*;
import java.util.function.Function;

public class ExecutorServerStarter {
	private final static Logger logger = LogManager.getLogger();
	private final ServerCommandListener commandListener;
	private final ExecutorHttpServer executor;


	private ExecutorServerStarter(String configPath, String serverHostAddress, int serverPort) throws InterruptedException {
		executor = new ExecutorHttpServer(configPath, serverHostAddress, serverPort);

		commandListener = new ServerCommandListener(executor);
		commandListener.addCommandHandle("register-to", new RegisterToGatewat());
		commandListener.listenEndlessly();


	}

	public static void main(String[] args) throws InterruptedException {
		System.out.println(Arrays.toString(args));
		if(args == null || args.length != 3) {
			throw new RuntimeException("Need 3 input arguments.");
		}
		String configPath = args[0];
		String serverHostAddress = args[1];
		int serverPort = Integer.parseInt(args[2]);

		logger.info("Starting executor with: \nconfig:{}\nip address:{}\nport:{}",
				configPath, serverHostAddress, serverPort);

		new ExecutorServerStarter(configPath, serverHostAddress, serverPort);
	}

	class RegisterToGatewat implements Function<List<String>, String> {

		@Override
		public String apply(List<String> inputs) {
			if(inputs == null || inputs.isEmpty()) {
				return "Provide the address of the gateway as the first parameter..";
			}
			String gatewayAddress = inputs.get(0);
			logger.info("Received command to register to gateway with address: {}.", gatewayAddress);
			try{
				executor.registerToGateway(gatewayAddress);
				return "Successfully registered to " + gatewayAddress;
			} catch(Exception ex) {
				return "Registration to " + gatewayAddress + " failed.\nError: " + Streams.errToString(ex);
			}
		}
	}

}
