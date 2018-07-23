package demo;

import Catalano.Imaging.FastBitmap;
import de.upb.sede.casters.FastBitmapCaster;
import de.upb.sede.client.CoreClientHttpServer;
import de.upb.sede.config.ClassesConfig;
import de.upb.sede.config.OnthologicalTypeConfig;
import de.upb.sede.core.ObjectDataField;
import de.upb.sede.core.SEDEObject;
import de.upb.sede.config.ExecutorConfiguration;
import de.upb.sede.exec.Executor;
import de.upb.sede.exec.ExecutorHttpServer;
import de.upb.sede.gateway.GatewayHttpServer;
import de.upb.sede.requests.Result;
import de.upb.sede.requests.RunRequest;
import de.upb.sede.requests.resolve.ResolvePolicy;
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

	static FastBitmap frog;

	static String executor1Address = WebUtil.HostIpAddress();
	static int executorPort = 9000;
	static ExecutorHttpServer executor1;

	static GatewayHttpServer gateway;

	@BeforeClass
	public static void startClient() {

		gateway = new GatewayHttpServer(gatewayPort, getTestClassConfig(), getTestTypeConfig());
		ExecutorConfigurationCreator creator = new ExecutorConfigurationCreator();
		creator.withExecutorId("Client");
		ExecutorConfiguration configuration = ExecutorConfiguration.parseJSON(creator.toString());
		coreClient = new CoreClientHttpServer(configuration, clientAddress, clientPort, gatewayAddress, gatewayPort);
		/*
			Disable if you will have an executor register to the gateway:
		 */
//		coreClient.getClientExecutor().getExecutorConfiguration().getSupportedServices().addAll(
//				Arrays.asList("Catalano.Imaging.Filters.Crop",
//						"Catalano.Imaging.Filters.Resize",
//						"Catalano.Imaging.sede.CropFrom0")
//		);
		/*
			Disable if you dont want to have a local executor do the imaging:
		 */
		creator = new ExecutorConfigurationCreator();
		creator.withExecutorId("executor");
		executor1 = new ExecutorHttpServer(ExecutorConfiguration.parseJSON(creator.toString()), executor1Address, executorPort);
		executor1.getExecutorConfiguration().getSupportedServices().addAll(
				Arrays.asList("Catalano.Imaging.Filters.Crop",
						"Catalano.Imaging.Filters.Resize",
						"Catalano.Imaging.sede.CropFrom0")
		);
		gateway.register(executor1.registration());
		/*
			Disabled if you dont have dot installed.
		 */
		coreClient.writeDotGraphToDir("testrsc/images");

	}



	@AfterClass
	public static void shutdown() {
		gateway.shutdown();
		executor1.shutdown();
		coreClient.getClientExecutor().shutdown();
	}

	@BeforeClass
	public static void loadImages() {
		 frog = new FastBitmap("testrsc/images/red-eyed.jpg");
	}
	@AfterClass
	public static  void shutdownClient() {
		coreClient.getClientExecutor().shutdown();
	}

	@Test
	public void testImageProcessing1() {
		String composition =
				"s1 = Catalano.Imaging.Filters.Crop::__construct({i1=5, i2=5, i3=300, i4=300});\n" +
				"s1::ApplyInPlace({i1=imageIn});\n" +
				"s2 = Catalano.Imaging.Filters.Resize::__construct({i1=200, i2=200});\n" +
				"imageOut = s2::applyInPlace({i1=imageIn});";

		ResolvePolicy policy = new ResolvePolicy();
		policy.setServicePolicy("None");
		policy.setReturnFieldnames(Arrays.asList("imageOut"));

		SEDEObject inputObject_fb1 = new ObjectDataField(FastBitmap.class.getName(), frog);

		Map<String, SEDEObject> inputs = new HashMap<>();
		inputs.put("imageIn", inputObject_fb1);

		JOptionPane.showMessageDialog(null, frog.toIcon(), "Original image", JOptionPane.PLAIN_MESSAGE);

		RunRequest runRequest = new RunRequest("processing1", composition, policy, inputs);

		Map<String, Result> resultMap = coreClient.blockingRun(runRequest);
		Result result = resultMap.get("imageOut");
		if(result == null || result.hasFailed()) {
			Assert.fail("Result missing...");
		}
		/*
			Cast it to bitmap:
		 */
		FastBitmap processedImage = (FastBitmap) result.castResultData(
				FastBitmap.class.getName(), FastBitmapCaster.class).getDataField();
		JOptionPane.showMessageDialog(null, processedImage.toIcon(), "Result", JOptionPane.PLAIN_MESSAGE);
	}

	@Test
	public void testImageProcessing2() {
		/*
			Tests fixed parameters:
		 */
		String composition =
				"s1  = Catalano.Imaging.sede.CropFrom0::__construct({100,100});\n" +
				"imageOut = s1::ApplyInPlace({i1=imageIn});";

		ResolvePolicy policy = new ResolvePolicy();
		policy.setServicePolicy("None");
		policy.setReturnFieldnames(Arrays.asList("imageOut"));

		SEDEObject inputObject_fb1 = new ObjectDataField(FastBitmap.class.getName(), frog);

		Map<String, SEDEObject> inputs = new HashMap<>();
		inputs.put("imageIn", inputObject_fb1);

		JOptionPane.showMessageDialog(null, frog.toIcon(), "Original image", JOptionPane.PLAIN_MESSAGE);

		RunRequest runRequest = new RunRequest("processing2", composition, policy, inputs);

		Map<String, Result> resultMap = coreClient.blockingRun(runRequest);
		Result result = resultMap.get("imageOut");
		if(result == null || result.hasFailed()) {
			Assert.fail("Result missing...");
		}
		/*
			Cast it to bitmap:
		 */
		FastBitmap processedImage = (FastBitmap) result.castResultData(
				FastBitmap.class.getName(), FastBitmapCaster.class).getDataField();
		JOptionPane.showMessageDialog(null, processedImage.toIcon(), "Result", JOptionPane.PLAIN_MESSAGE);
	}


	private static ClassesConfig getTestClassConfig() {
		return new ClassesConfig("testrsc/config/imaging-classconf.json");
	}

	private static OnthologicalTypeConfig getTestTypeConfig() {
		return new OnthologicalTypeConfig("testrsc/config/imaging-typeconf.json");
	}
}
