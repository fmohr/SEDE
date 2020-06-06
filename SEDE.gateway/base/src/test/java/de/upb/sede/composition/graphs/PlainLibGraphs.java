package de.upb.sede.composition.graphs;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import de.upb.sede.IQualifiable;
import de.upb.sede.ISDLAssembly;
import de.upb.sede.SDLReader;
import de.upb.sede.beta.IExecutorRegistration;
import de.upb.sede.composition.FieldType;
import de.upb.sede.composition.types.ServiceInstanceType;
import de.upb.sede.exec.ExecutorCapabilities;
import de.upb.sede.exec.ExecutorContactInfo;
import de.upb.sede.exec.ExecutorHandle;
import de.upb.sede.gateway.ExecutorSupplyCoordinator;
import de.upb.sede.gateway.SimpleGatewayImpl;
import de.upb.sede.interfaces.IGateway;
import de.upb.sede.requests.resolve.ResolvePolicy;
import de.upb.sede.requests.resolve.beta.IChoreography;
import de.upb.sede.requests.resolve.beta.MutableResolvePolicy;
import de.upb.sede.requests.resolve.beta.MutableResolveRequest;
import de.upb.sede.util.SDLUtil;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.upb.sede.core.SEDEObject;
import de.upb.sede.core.ServiceInstanceField;
import de.upb.sede.core.ServiceInstanceHandle;
import de.upb.sede.requests.ExecutorRegistration;
import de.upb.sede.requests.resolve.InputFields;
import de.upb.sede.requests.resolve.ResolveRequest;
import de.upb.sede.util.FileUtil;

public class PlainLibGraphs {

    private static ObjectWriter mapper = new ObjectMapper().writerWithDefaultPrettyPrinter();

    private static ISDLAssembly plainlibServices;

	private static IGateway gateway;

	private static final Logger logger = LoggerFactory.getLogger(PlainLibGraphs.class);


	@BeforeClass
	public static void setupGateway() {
	    plainlibServices = getSDLAssembly();
		gateway = new SimpleGatewayImpl(plainlibServices,
		    new ExecutorSupplyCoordinator()
        );

	}
	@Test
	public void simplegraph() throws IOException {
		String composition = "b = plainlib.package1.b.B::__construct({0,1});" +
				"erg = b::calc({3})";
		int execNr = 1;
		MutableResolveRequest rr = createRR(composition);
		resolve_and_store(rr, execNr);
	}

	@Test
	public void simplegraph2() throws IOException {
		String composition = "state1 = b::__str__();" +
				"b::set_b({-1});" +
				"state2 = b::__str__();";
        MutableResolveRequest rr = createRR(composition);
        ServiceInstanceHandle instanceHandle = new ServiceInstanceHandle(getClientRegistration().getExecutorHandle().getQualifier(), "plainlib.package1.b.B", "$INSTANCE_ID b");
        rr.putInitialServices("b", instanceHandle);
        rr.addInitialContext(FieldType.builder()
                .fieldname("b")
                .type(ServiceInstanceType.builder()
                .qualifier("plainlib.package1.b.B")
                .build())
            .build());

		int execNr = 2;
		resolve_and_store(rr, execNr);
	}

	public static void resolve_and_store(MutableResolveRequest rr, int execNr) throws IOException {
        ((MutableResolvePolicy) rr.getResolvePolicy())
            .setIsDotGraphRequested(true);

		IChoreography choreography = gateway.resolve(rr);
		String pathPrefix= "testrsc/exec-requests/plainlib/e_" + execNr;
		FileUtil.writeStringToFile(pathPrefix + ".svg", choreography.getDotSVG());
        mapper.writeValue(new File(pathPrefix + ".json"), choreography.getCompositionGraph());
	}

	private static MutableResolveRequest createRR(String composition) {
        MutableResolveRequest rr = MutableResolveRequest.create();
        rr.setClientExecutorRegistration(getClientRegistration());
        rr.setComposition(composition);
        rr.setResolvePolicy(MutableResolvePolicy.create());
        return rr;
	}


    private static ISDLAssembly getSDLAssembly() {
        SDLReader reader = new SDLReader();
        reader.read(FileUtil.readResourceAsString("descs/plainlib.servicedesc.groovy"), "plainlib.servicedesc");
        ISDLAssembly sdlAssembly = reader.getSDLAssembly();
        return sdlAssembly;
    }


	private static IExecutorRegistration getClientRegistration() {

		Map<String, Object> contactInfo = new HashMap<>();
		contactInfo.put("id", "python_executor");
        List<String> supportedServices = SDLUtil.getAllServices(plainlibServices)
            .stream()
            .map(IQualifiable::getQualifier)
            .collect(Collectors.toList());
		IExecutorRegistration registration1 = de.upb.sede.beta.ExecutorRegistration.builder()
            .executorHandle(ExecutorHandle.builder()
                .capabilities(ExecutorCapabilities.builder()
                    .addAllFeatures(supportedServices)
                .build())
                .contactInfo(ExecutorContactInfo.builder()
                    .qualifier("python_executor")
                    .hostAddress("localhost")
                .build())
            .build())
            .build();
		return registration1;
	}

}
