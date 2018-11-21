package demo;

import de.upb.sede.client.CoreClient;
import de.upb.sede.client.HttpCoreClient;
import de.upb.sede.config.ClassesConfig;
import de.upb.sede.config.ExecutorConfiguration;
import de.upb.sede.config.OnthologicalTypeConfig;
import de.upb.sede.core.ObjectDataField;
import de.upb.sede.core.SEDEObject;
import de.upb.sede.exec.ExecutorHttpServer;
import de.upb.sede.gateway.GatewayHttpServer;
import de.upb.sede.requests.Result;
import de.upb.sede.requests.RunRequest;
import de.upb.sede.requests.resolve.ResolvePolicy;
import de.upb.sede.util.ExecutorConfigurationCreator;
import de.upb.sede.util.FileUtil;
import de.upb.sede.util.WebUtil;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import C2Data.C2Image;
import C2Data.C2ImageManager;
import de.upb.sede.casters.C2ImageCaster;

import javax.swing.*;
import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.List;

public class C2ServiceTests {

	static CoreClient coreClient;

	static String clientAddress = WebUtil.HostIpAddress();
	static int clientPort = 7001;

	static String gatewayAddress = "localhost";
	static int gatewayPort = 6001;

	static String executor1Address = WebUtil.HostIpAddress();
	static int executorPort = 9001;
	static ExecutorHttpServer executor1;

	static GatewayHttpServer gateway;

	// C2 image type.
	static C2ImageManager im;
	static C2Image lenna;
	static C2Image lenna_mod;

	private static ClassesConfig getTestClassConfig() {
		ClassesConfig classesConfig = new ClassesConfig();
		classesConfig.appendConfigFromJsonStrings(FileUtil.readResourceAsString("config/imaging-classconf.json"));
		return classesConfig;
	}

	private static OnthologicalTypeConfig getTestTypeConfig() {
		OnthologicalTypeConfig typeConfig = new OnthologicalTypeConfig();
		typeConfig.appendConfigFromJsonStrings(FileUtil.readResourceAsString("config/imaging-typeconf.json"));
		return typeConfig;
	}

	private static List<String> getTestExecConfig() {
		String exec_conf = FileUtil.readResourceAsString("config/executor-conf.json");

		JSONParser parser = new JSONParser();
		JSONObject jsonobject = null;
		try {
			jsonobject = (JSONObject) parser.parse(exec_conf);
		} catch (ParseException e) {
			System.err.println("Cannot parse executor-conf.json!");
		}
		List<String> jsonarray = (JSONArray) jsonobject.get("services");

		return jsonarray;
	}

	@BeforeClass
	public static void startClient() {
		gateway = new GatewayHttpServer(gatewayPort, getTestClassConfig(), getTestTypeConfig());
		ExecutorConfigurationCreator creator = new ExecutorConfigurationCreator();
		creator.withExecutorId("Client");
		ExecutorConfiguration configuration = ExecutorConfiguration.parseJSON(creator.toString());
		coreClient = HttpCoreClient.createNew(configuration, clientAddress, clientPort, gatewayAddress, gatewayPort);

		List<String> services = getTestExecConfig();

		creator = new ExecutorConfigurationCreator();
		creator.withExecutorId("executor");
		executor1 = new ExecutorHttpServer(ExecutorConfiguration.parseJSON(creator.toString()), executor1Address, executorPort);
		executor1.getBasisExecutor().getExecutorConfiguration().getSupportedServices().addAll(services);
		//System.out.println(executor1.getBasisExecutor().getExecutorConfiguration().toJsonString());
		gateway.getBasis().register(executor1.getBasisExecutor().registration());
		/*
			Disabled if you dont have dot installed.
		 */
		coreClient.writeDotGraphToDir("testrsc");

	}

	@BeforeClass
	public static void loadBefore() {
		// Load libraries.
		loadC2Libraries();

		// Load C2 image.
		List<String> file_src = new ArrayList<String>();
		List<String> file_dest = new ArrayList<String>();
		file_src.add(FileUtil.getPathOfResource("images/lenna.tiff"));

		// Location of result image.
		file_dest.add("/home/aloesch/test.jpg");

		im = new C2ImageManager(file_src, file_dest);
		im.environmentUp();
		im.readImages();
		lenna = im.getSourceImages().get(0);

		assert lenna != null;
	}

	@AfterClass
	public static void shutdown() {
		gateway.shutdown();
		executor1.shutdown();
		coreClient.getClientExecutor().shutdown();

		List<C2Image> image_list = new ArrayList<C2Image>();
		image_list.add(lenna_mod);

		im.setTargetImages(image_list);
		try {
			im.writeImages();
		} catch (Exception e) {
			System.out.println("Couldn't store image on HDD!");
		}

		im.environmentDown();
	}

	public static void loadC2Libraries() {
		try {
			// Load image library bridge. The bridge comes from the demonstrator in
			//   sfb901_demonstrator/service_caller
			System.load(FileUtil.getPathOfResource("shared_libs/libimagemagick.so"));

			// Load service plugin bridge. The bridge comes from the demonstrator in
			//   sfb901_demonstrator/service_node
			System.load(FileUtil.getPathOfResource("shared_libs/libpluginbridge.so"));

			// TODO: this needs a clean solution. Currently done via LD_LIBRARY_PATH.
			// Add path to *.so of the service plugins to library path.
			// System.setProperty("java.library.path", System.getProperty("java.library.path")+":/home/deffel/coding/SEDE/CServices/cbuild");
		} catch (UnsatisfiedLinkError error) {
			System.out.println(error.getMessage());
		}
	}

    @Test
    public void testGreySobel() throws InvocationTargetException, InterruptedException {
		String composition =
				"s1 = C2Services.C2Service_grey::__construct();\n" +
				"i2 = s1::processImage({i1=imageIn});\n" +
				"s2 = C2Services.C2Service_sobel::__construct();\n" +
				"imageOut = s2::processImage({i2});\n";

		JOptionPane.showMessageDialog(null, lenna.convertToImageIcon(), "Input", JOptionPane.PLAIN_MESSAGE);

		SEDEObject inputObject_c2i = new ObjectDataField(C2Image.class.getName(), lenna);

        ResolvePolicy policy = new ResolvePolicy();
        policy.setServicePolicy("None");
        policy.setReturnFieldnames(Arrays.asList("imageOut"));

        Map<String, SEDEObject> inputs = new HashMap<>();
		inputs.put("imageIn", inputObject_c2i);

        RunRequest runRequest = new RunRequest("proc_cservices", composition, policy, inputs);

		Map<String, Result> resultMap = coreClient.blockingRun(runRequest);
		Result result = resultMap.get("imageOut");

		lenna_mod = result.castResultData(C2Image.class.getName(), C2ImageCaster.class).getDataField();

		JOptionPane.showMessageDialog(null, lenna_mod.convertToImageIcon(), "Result", JOptionPane.PLAIN_MESSAGE);
    }
}
