package ai.services.composition.graphs;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import ai.services.beta.ExecutorRegistration;
import ai.services.composition.FieldType;
import ai.services.composition.types.ServiceInstanceType;
import ai.services.exec.ExecutorCapabilities;
import ai.services.exec.ExecutorContactInfo;
import ai.services.exec.ExecutorHandle;
import ai.services.requests.resolve.beta.MutableResolvePolicy;
import ai.services.requests.resolve.beta.MutableResolveRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import ai.services.IQualifiable;
import ai.services.ISDLAssembly;
import ai.services.SDLReader;
import ai.services.beta.IExecutorRegistration;
import ai.services.gateway.ExecutorArbiter;
import ai.services.gateway.StdGatewayImpl;
import ai.services.interfaces.IGateway;
import ai.services.requests.resolve.beta.IChoreography;
import ai.services.util.SDLUtil;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ai.services.core.ServiceInstanceHandle;
import ai.services.util.FileUtil;

public class PlainLibGraphs {

    private static ObjectWriter mapper = new ObjectMapper().writerWithDefaultPrettyPrinter();

    private static ISDLAssembly plainlibServices;

	private static IGateway gateway;

	private static final Logger logger = LoggerFactory.getLogger(PlainLibGraphs.class);


	@BeforeClass
	public static void setupGateway() {
	    plainlibServices = getSDLAssembly();
		gateway = new StdGatewayImpl(plainlibServices,
		    new ExecutorArbiter()
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
                .typeQualifier("plainlib.package1.b.B")
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
        List<String> supportedServices = SDLUtil.getAllServices(plainlibServices)
            .stream()
            .map(IQualifiable::getQualifier)
            .collect(Collectors.toList());
		IExecutorRegistration registration1 = ExecutorRegistration.builder()
            .executorHandle(ExecutorHandle.builder()
                .capabilities(ExecutorCapabilities.builder()
                    .addAllServices(supportedServices)
                .build())
                .contactInfo(ExecutorContactInfo.builder()
                    .qualifier("python_executor")
                    .uRL("localhost")
                .build())
            .build())
            .build();
		return registration1;
	}

}
