package de.upb.sede.client;

import org.aeonbits.owner.Config;
import org.aeonbits.owner.Mutable;

/**
 * SEDEClient.properties: <br><br>
 *
 * GATEWAY_ADDRESS - e.g. localhost:100 or 10.0.0.1 <br>
 * GATEWAY_PORT - Optional - integer from 1 to 65536 - if given, it is appended to GATEWAY_ADDRESS <br>
 * CLIENT_PORT - port used to open the client executor http server. Must be free. <br>
 * CLIENT_PUBLIC_ADDRESS - Optional - if given, it is shared with other executors instead of 127.0.0.1:CLIENT_PORT. Use to define the network wide address of this host. Is overridden by 'EXECUTOR_ADDRESS' environment variable.<br>
 * CLIENT_PUBLIC_PORT - Optional - if given, it is appended to CLIENT_PUBLIC_ADDRESS <br>
 * PROXY_EXECUTOR_ADDRESS - Optional - if given, the client is set up to be reached by the proxy reachable from this address. <br>
 * SERVICE_CONFIG_FILES - Optional - list of config files in the java class-path, e.g. ["config/serviceA.classconf.json", "config/serviceA.typeconf.json"] - if given and a local client is being created, these files will be read from the classload resources and are fed to the gateway. <br>
 * CLIENT_SERVICES - Optional - list of service names, e,g. : ["a.ClassA", "b.ClassB"] - if given, the client will support the local execution of the listed services. Beware that the listed classes or their libraries need to be in the class path or else if requested execution may fail at runtime. <br>
 * <br><br>
 * Note: If GATEWAY_ADDRESS or CLIENT_PORT are not defined, SERVICE_CONFIG_FILES and CLIENT_SERVICES become mandatory properties as a gateway and client with local execution will be created.
 *
 */
public interface ClientProperties  extends Config {

	String gatewayAddressKey = "de.upb.sede.client.gatewayAddress";
	String gatewayPortKey = "de.upb.sede.client.gatewayPort";
	String clientIdPrefixKey = "de.upb.sede.client.clientIdPrefix";
	String clientPortKey = "de.upb.sede.client.clientPort";
	String clientPublicAddressKey = "de.upb.sede.client.clientPublicAddress";
	String clientPublicPortKey = "de.upb.sede.client.clientPublicPort";
	String proxyExecutorAddressKey = "de.upb.sede.client.proxyExecutorAddress";
	String serviceConfigFilesKey = "de.upb.sede.client.serviceConfigFiles";
	String clientServicesKey = "de.upb.sede.client.clientServices";

	@Key(gatewayAddressKey)
	String gatewayAddress();

	@Key(gatewayPortKey)
	Integer gatewayPort();

	@Key(clientIdPrefixKey)
	@DefaultValue("CLIENT-")
	String clientIdPrefix();

	@Key(clientPortKey)
	@DefaultValue("7000")
	Integer clientPort();

	@Key(clientPublicAddressKey)
	String clientPublicAddress();

	@Key(clientPublicPortKey)
	Integer clientPublicPort();

	@Key(proxyExecutorAddressKey)
	String proxyExecutorAddressKey();

	@Key(serviceConfigFilesKey)
	String serviceConfigFiles();

	@Key(clientServicesKey)
	String clientServices();

}
