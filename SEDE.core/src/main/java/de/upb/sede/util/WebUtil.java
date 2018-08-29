package de.upb.sede.util;

import java.io.IOException;
import java.net.Inet4Address;
import java.net.ServerSocket;
import java.net.UnknownHostException;

public class WebUtil {

	public static String HostIpAddress(){
		try {
			return Inet4Address.getLocalHost().getHostAddress();
		} catch (UnknownHostException e) {
			throw new RuntimeException(e);
		}
	}

	public static int nextFreePort(int startFrom) {
		int port;
		if(startFrom < 1) {
			port = 1;
		} else if(startFrom > Short.MAX_VALUE) {
			port = Short.MAX_VALUE;
		} else {
			port = startFrom;
		}
		/*
		 * try to open a server socket with each port.
		 * If no exception is thrown we know the port is free.
		 */
		while(port < Short.MAX_VALUE) {

			try {
				new ServerSocket(port).close(); // close it immediately
			} catch (IOException ex) {
				port++;
				continue; // try next port
			}
			return port;
		}
		// if the program gets here, no port in the range was found
		throw new RuntimeException("no free port found");
	}
}
