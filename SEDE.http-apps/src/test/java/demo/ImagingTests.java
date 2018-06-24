package demo;

import Catalano.Imaging.FastBitmap;
import de.upb.sede.casters.FastBitmapCaster;
import de.upb.sede.client.CoreClientHttpServer;
import de.upb.sede.config.ClassesConfig;
import de.upb.sede.config.OnthologicalTypeConfig;
import de.upb.sede.core.SEDEObject;
import de.upb.sede.config.ExecutorConfiguration;
import de.upb.sede.core.SemanticStreamer;
import de.upb.sede.gateway.GatewayHttpServer;
import de.upb.sede.requests.Result;
import de.upb.sede.requests.RunRequest;
import de.upb.sede.requests.resolve.ResolvePolicy;
import de.upb.sede.requests.resolve.ResolveRequest;
import de.upb.sede.util.ExecutorConfigurationCreator;
import de.upb.sede.util.WebUtil;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.swing.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class ImagingTests {

	static CoreClientHttpServer coreClient;

	static String clientAddress = WebUtil.HostIpAddress();
	static int clientPort = 7000;

	static String gatewayAddress = "localhost";
	static int gatewayPort = 6000;


	static GatewayHttpServer gateway;

	@BeforeClass
	public static void startClient() {

		gateway = new GatewayHttpServer(gatewayPort, getTestClassConfig(), getTestTypeConfig());
		ExecutorConfigurationCreator creator = new ExecutorConfigurationCreator();
		creator.withExecutorId("Client");
		ExecutorConfiguration configuration = ExecutorConfiguration.parseJSON(creator.toString());
		coreClient = new CoreClientHttpServer(configuration, clientAddress, clientPort, gatewayAddress, gatewayPort);

	}
	@AfterClass
	public static  void shutdownClient() {
		coreClient.getClientExecutor().shutdown();
	}

	@Test
	public void testImagingProcessing1() {
		coreClient.getClientExecutor().getExecutorConfiguration().getSupportedServices().addAll(
				Arrays.asList("Catalano.Imaging.Filters.Crop", "Catalano.Imaging.Filters.Resize")
		);
		String composition =
				"s1 = Catalano.Imaging.Filters.Crop::__construct({i1=5, i2=5, i3=300, i4=300});\n" +
				"fb2 = s1::ApplyInPlace({i1=fb1});\n" +
				"s2 = Catalano.Imaging.Filters.Resize::__construct({i1=200, i2=200});\n" +
				"fb3 = s2::applyInPlace({i1=fb2});";

		ResolvePolicy policy = new ResolvePolicy();
		policy.setServicePolicy("None");
		policy.setReturnFieldnames(Arrays.asList("fb2"));

		FastBitmap fb1 = new FastBitmap("testrsc/images/red-eyed.jpg");
		SEDEObject inputObject_fb1 = new SEDEObject(FastBitmap.class.getName(), fb1);

		Map<String, SEDEObject> inputs = new HashMap<>();
		inputs.put("fb1", inputObject_fb1);

		JOptionPane.showMessageDialog(null, fb1.toIcon(), "Original image", JOptionPane.PLAIN_MESSAGE);

		RunRequest runRequest = new RunRequest(composition, policy, inputs);

		ResolveRequest resolveRequest = coreClient.runToResolve(runRequest, "imaging");
		IntegrationTest_Resolve.resolveToDot(resolveRequest, gateway, "testrsc/images/processing1");


		Map<String, Result> resultMap = coreClient.blockingRun(runRequest);
		Result result = resultMap.get("fb2");
		if(result == null || result.hasFailed()) {
			Assert.fail("Result failed.");
		}
		FastBitmap processedImage;
		if(result.getResultData().isSemantic()) {
			/*
				Cast it to bitmap:
			 */
			processedImage = (FastBitmap) SemanticStreamer.readObjectFrom(result.getResultData(),
					FastBitmapCaster.class.getName(), "Arr", FastBitmap.class.getName())
					.getObject();
		} else{
			/*
				Result already in bitmap format:
			 */
			processedImage = (FastBitmap) result.getResultData().getObject();
		}
		JOptionPane.showMessageDialog(null, processedImage.toIcon(), "Result", JOptionPane.PLAIN_MESSAGE);
	}


	private static ClassesConfig getTestClassConfig() {
		return new ClassesConfig("testrsc/config/imaging-classconf.json");
	}

	private static OnthologicalTypeConfig getTestTypeConfig() {
		return new OnthologicalTypeConfig("testrsc/config/imaging-typeconf.json");
	}
}
