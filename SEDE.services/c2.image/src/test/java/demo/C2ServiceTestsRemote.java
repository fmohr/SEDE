package demo;

import C2Data.*;
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

import javax.swing.*;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

/*
	Test class to run a standalone SEDE executor.
 */
public class C2ServiceTestsRemote {

	static CoreClient coreClient;

	static String clientAddress = "131.234.58.31";
	// static String clientAddress = WebUtil.HostIpAddress();
	static int clientPort = 9001;

	static String gatewayAddress = "sfb-k8node-1.cs.uni-paderborn.de";
	static int gatewayPort = 30370;

	// C2 image type.
	static C2NativeInterface ni;
	static C2ImageManager im;
	static C2Image lenna;
	static C2Image lenna_mod;

	private static ClassesConfig getTestClassConfig() {
		ClassesConfig classesConfig = new ClassesConfig();
		classesConfig.appendConfigFromJsonStrings(
				FileUtil.readResourceAsString("config/c2imaging-classconf.json"),
				FileUtil.readResourceAsString("config/builtin-classconf.json"));
		return classesConfig;
	}

	private static OnthologicalTypeConfig getTestTypeConfig() {
		OnthologicalTypeConfig typeConfig = new OnthologicalTypeConfig();
		typeConfig.appendConfigFromJsonStrings(
				FileUtil.readResourceAsString("config/c2imaging-typeconf.json")
				, FileUtil.readResourceAsString("config/builtin-typeconf.json"));
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
		// gateway = new GatewayHttpServer(gatewayPort, getTestClassConfig(), getTestTypeConfig());
		ExecutorConfigurationCreator creator = new ExecutorConfigurationCreator();
		creator.withExecutorId("Client");
		ExecutorConfiguration configuration = ExecutorConfiguration.parseJSON(creator.toString());
		coreClient = HttpCoreClient.createNew(configuration, clientAddress, clientPort, gatewayAddress, gatewayPort);

		System.out.println(clientAddress.toString());

//		List<String> services = getTestExecConfig();
//
//		creator = new ExecutorConfigurationCreator();
//		creator.withExecutorId("executor");
//		executor1 = new ExecutorHttpServer(ExecutorConfiguration.parseJSON(creator.toString()), executor1Address, executorPort);
//		executor1.getBasisExecutor().getExecutorConfiguration().getSupportedServices().addAll(services);
//		//System.out.println(executor1.getBasisExecutor().getExecutorConfiguration().toJsonString());
//		gateway.getBasis().register(executor1.getBasisExecutor().registration());
		/*
			Disabled if you dont have dot installed.
		 */
		coreClient.writeDotGraphToDir("testrsc");

	}

	@BeforeClass
	public static void loadBefore() {
		// Load native libraries
		ni = C2NativeInterface.getInstance();

		// Load C2 image.
		List<String> file_src = new ArrayList<String>();
		file_src.add(FileUtil.getPathOfResource("images/lenna.tiff"));

		// Location of result image.
		List<String> file_dest = new ArrayList<String>();
		file_dest.add(ni.getSedeRootDir() + "/result.jpg");

		im = new C2ImageManager(file_src, file_dest);
		im.environmentUp();
		im.readImages();
		lenna = im.getSourceImages().get(0);

		assert lenna != null;
	}

	@AfterClass
	public static void shutdown() {
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

	@Test
    public void testGreyGaussSobelExplicitResources() throws Exception {

		String composition =
				"s1 = C2Services.C2Service_grey_SCPU::__construct();\n" +
						"imageInter2 = s1::processImage({i1=imageIn});\n" +
						// "s2 = C2Services.C2Service_gausslowpass::__construct();\n" +
						// "imageInter2 = s2::processImage({i1=resource2, i2=imageInter1});\n" +
						"s3 = C2Services.C2Service_sobel_GPU::__construct();\n" +
						"imageOut = s3::processImage({i1=imageInter2});\n";

		C2Resource SCPU = new C2Resource("scpu");
		C2Resource CPU = new C2Resource("cpu");
		C2Resource GPU = new C2Resource("gpu");
		C2Resource FPGA = new C2Resource("fpga");
		C2Resource JAVA = new C2Resource("java");
		C2Resource OVERLAY = new C2Resource("overlay");

		SEDEObject inputObject_lenna = new ObjectDataField(C2Image.class.getName(), lenna);
		SEDEObject inputObject_res1 = new ObjectDataField(C2Resource.class.getName(), SCPU);
		SEDEObject inputObject_res2 = new ObjectDataField(C2Resource.class.getName(), SCPU);
		SEDEObject inputObject_res3 = new ObjectDataField(C2Resource.class.getName(), SCPU);

		ResolvePolicy policy = new ResolvePolicy();
		policy.setServicePolicy("None");
		policy.setReturnFieldnames(Arrays.asList("imageInter2", "imageOut"));

		Map<String, SEDEObject> inputs = new HashMap<>();
		inputs.put("imageIn", inputObject_lenna);

		RunRequest runRequest = new RunRequest("proc_cservices_2", composition, policy, inputs);

		Map<String, Result> resultMap = coreClient.blockingRun(runRequest);
		coreClient.assertErrorFreeRun(resultMap);
		// Result inter1 = resultMap.get("imageInter1");
		Result inter2 = resultMap.get("imageInter2");
		Result result = resultMap.get("imageOut");

		C2Image lenna_inter2 = inter2.castResultData(C2Image.class.getName(), C2ImageCaster.class).getDataField();
		lenna_mod = result.castResultData(C2Image.class.getName(), C2ImageCaster.class).getDataField();

		JOptionPane.showMessageDialog(null, lenna.convertToImageIcon(), "Input Image", JOptionPane.PLAIN_MESSAGE);
		JOptionPane.showMessageDialog(null, lenna_inter2.convertToImageIcon(), "Gauss Blurred Image", JOptionPane.PLAIN_MESSAGE);
		JOptionPane.showMessageDialog(null, lenna_mod.convertToImageIcon(), "Sobel Filtered Image", JOptionPane.PLAIN_MESSAGE);
	}

	@Test
	public void testGreyMedianMorph() throws InvocationTargetException, InterruptedException {

		String composition =
				"s1 = C2Services.C2Service_grey::__construct();\n" +
						"imageInter1 = s1::processImage({i1=resource1, i2=imageIn});\n" +
						"s2 = C2Services.C2Service_median::__construct();\n" +
						"s2::setOptions({i1=paramValueMedian});\n" +
						"imageInter2 = s2::processImage({i1=resource2, i2=imageInter1});\n" +
						"s3 = C2Services.C2Service_morphedgedetection::__construct();\n" +
						"s3::setOptions({i1=paramValueMorph});\n" +
						"imageOut = s3::processImage({i1=resource3, i2=imageInter2});\n";

		C2Resource SCPU	= new C2Resource("scpu");
		C2Resource CPU	= new C2Resource("cpu");
		C2Resource GPU	= new C2Resource("gpu");
		C2Resource FPGA	= new C2Resource("fpga");
		C2Resource JAVA	= new C2Resource("java");
		C2Resource OVERLAY = new C2Resource("overlay");

		Map< String,Double> paramsMedian = new HashMap< String,Double>();
		paramsMedian.put("filter_size",3.0);
		Map< String,Double> paramsMorph = new HashMap< String,Double>();
		paramsMorph.put("matrix_size",3.0);

		C2Params paramValuesMedian = new C2Params(paramsMedian);
		C2Params paramValuesMorph = new C2Params(paramsMorph);

		SEDEObject inputObject_lenna		= new ObjectDataField(C2Image.class.getName(), lenna);
		SEDEObject inputObject_res1			= new ObjectDataField(C2Resource.class.getName(), SCPU);
		SEDEObject inputObject_res2			= new ObjectDataField(C2Resource.class.getName(), CPU);
		SEDEObject inputObject_res3			= new ObjectDataField(C2Resource.class.getName(), OVERLAY);
		SEDEObject inputObject_paramMedian	= new ObjectDataField(C2Params.class.getName(), paramValuesMedian);
		SEDEObject inputObject_paramMorph	= new ObjectDataField(C2Params.class.getName(), paramValuesMorph);


		ResolvePolicy policy = new ResolvePolicy();
		policy.setServicePolicy("None");
		policy.setReturnFieldnames(Arrays.asList("imageInter1", "imageInter2", "imageOut"));

		Map<String, SEDEObject> inputs = new HashMap<>();
		inputs.put("imageIn", inputObject_lenna);
		inputs.put("resource1", inputObject_res1);
		inputs.put("resource2", inputObject_res2);
		inputs.put("resource3", inputObject_res3);
		inputs.put("paramValueMedian", inputObject_paramMedian);
		inputs.put("paramValueMorph", inputObject_paramMorph);

		RunRequest runRequest = new RunRequest("proc_cservices", composition, policy, inputs);

		Map<String, Result> resultMap = coreClient.blockingRun(runRequest);
		Result inter1 = resultMap.get("imageInter1");
		Result inter2 = resultMap.get("imageInter2");
		Result result = resultMap.get("imageOut");

		C2Image lenna_inter1 = inter1.castResultData(C2Image.class.getName(), C2ImageCaster.class).getDataField();
		C2Image lenna_inter2 = inter2.castResultData(C2Image.class.getName(), C2ImageCaster.class).getDataField();
		lenna_mod = result.castResultData(C2Image.class.getName(), C2ImageCaster.class).getDataField();

		JOptionPane.showMessageDialog(null, lenna.convertToImageIcon(), "Input Image", JOptionPane.PLAIN_MESSAGE);
		JOptionPane.showMessageDialog(null, lenna_inter1.convertToImageIcon(), "Grey Image", JOptionPane.PLAIN_MESSAGE);
		JOptionPane.showMessageDialog(null, lenna_inter2.convertToImageIcon(), "Median filtered Image", JOptionPane.PLAIN_MESSAGE);
		JOptionPane.showMessageDialog(null, lenna_mod.convertToImageIcon(), "Morph Edge Detected Image", JOptionPane.PLAIN_MESSAGE);

	}
}
