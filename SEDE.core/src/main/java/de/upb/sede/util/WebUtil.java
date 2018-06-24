package de.upb.sede.util;

import java.net.Inet4Address;
import java.net.UnknownHostException;

public class WebUtil {

	public static String HostIpAddress(){
		try {
			return Inet4Address.getLocalHost().getHostAddress();
		} catch (UnknownHostException e) {
			throw new RuntimeException(e);
		}
	}
}
