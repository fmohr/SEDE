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
import java.util.List;
import java.lang.*;

public class C2ServiceTests {

	static CoreClient coreClient;

	static String clientAddress = WebUtil.HostIpAddress();
	static int clientPort = 7001;

	static String gatewayAddress = "localhost";
	static int gatewayPort = 6001;

	static String executor1Address = WebUtil.HostIpAddress();
	static int executorPort = 9004;
	static ExecutorHttpServer executor1;

	static GatewayHttpServer gateway;

	// C2 image type.
	static C2NativeInterface ni;
	static C2ImageManager im;
	static C2Image lenna;
	static C2Image lenna_mod;

	private static ClassesConfig getTestClassConfig() {
		ClassesConfig classesConfig = new ClassesConfig();
		classesConfig.appendConfigFromJsonStrings(
				FileUtil.readResourceAsString("config/imaging-classconf.json"),
				FileUtil.readResourceAsString("config/builtin-classconf.json"));
		return classesConfig;
	}

	private static OnthologicalTypeConfig getTestTypeConfig() {
		OnthologicalTypeConfig typeConfig = new OnthologicalTypeConfig();
		typeConfig.appendConfigFromJsonStrings(
				FileUtil.readResourceAsString("config/imaging-typeconf.json")
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

	@Test
    public void testGreyGaussSobel() throws InvocationTargetException, InterruptedException {

		String composition =
				"s1 = C2Services.C2Service_grey::__construct();\n" +
				"imageInter1 = s1::processImage({i1=resource1, i2=imageIn});\n" +
				"s2 = C2Services.C2Service_gausslowpass::__construct();\n" +
				"imageInter2 = s2::processImage({i1=resource2, i2=imageInter1});\n" +
                "s3 = C2Services.C2Service_sobel::__construct();\n" +
                "imageOut = s3::processImage({i1=resource3, i2=imageInter2});\n";

		C2Resource SCPU	= new C2Resource("scpu");
		C2Resource CPU	= new C2Resource("cpu");
		C2Resource GPU	= new C2Resource("gpu");
		C2Resource FPGA	= new C2Resource("fpga");
		C2Resource JAVA	= new C2Resource("java");
		C2Resource OVERLAY = new C2Resource("overlay");

		SEDEObject inputObject_lenna	= new ObjectDataField(C2Image.class.getName(), lenna);
		SEDEObject inputObject_res1		= new ObjectDataField(C2Resource.class.getName(), SCPU);
        SEDEObject inputObject_res2		= new ObjectDataField(C2Resource.class.getName(), CPU);
        SEDEObject inputObject_res3		= new ObjectDataField(C2Resource.class.getName(), CPU);

        ResolvePolicy policy = new ResolvePolicy();
        policy.setServicePolicy("None");
        policy.setReturnFieldnames(Arrays.asList("imageInter1", "imageInter2", "imageOut"));

        Map<String, SEDEObject> inputs = new HashMap<>();
		inputs.put("imageIn", inputObject_lenna);
		inputs.put("resource1", inputObject_res1);
        inputs.put("resource2", inputObject_res2);
        inputs.put("resource3", inputObject_res3);

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
        JOptionPane.showMessageDialog(null, lenna_inter2.convertToImageIcon(), "Gauss Blurred Image", JOptionPane.PLAIN_MESSAGE);
		JOptionPane.showMessageDialog(null, lenna_mod.convertToImageIcon(), "Sobel Filtered Image", JOptionPane.PLAIN_MESSAGE);
    }

	@Test
	public void testGreyGaussSobelExplicitResources() throws InvocationTargetException, InterruptedException {

		String composition =
				"s1 = C2Services.C2Service_grey_OVERLAY::__construct();\n" +
						"imageInter1 = s1::processImage({i1=imageIn});\n" +
						"s2 = C2Services.C2Service_gausslowpass_SCPU::__construct();\n" +
						"imageInter2 = s2::processImage({i1=imageInter1});\n" +
						"s3 = C2Services.C2Service_sobel_GPU::__construct();\n" +
						"imageOut = s3::processImage({i1=imageInter2});\n";

		SEDEObject inputObject_lenna	= new ObjectDataField(C2Image.class.getName(), lenna);

		ResolvePolicy policy = new ResolvePolicy();
		policy.setServicePolicy("None");
		policy.setReturnFieldnames(Arrays.asList("imageInter1", "imageInter2", "imageOut"));

		Map<String, SEDEObject> inputs = new HashMap<>();
		inputs.put("imageIn", inputObject_lenna);

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
		SEDEObject inputObject_res3			= new ObjectDataField(C2Resource.class.getName(), CPU);
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


	@Test
	public void testGreyGaussCanny() throws InvocationTargetException, InterruptedException {

		String composition =
				"s1 = C2Services.C2Service_grey::__construct();\n" +
						"imageInter1 = s1::processImage({i1=resource1, i2=imageIn});\n" +
						"s2 = C2Services.C2Service_gausslowpass::__construct();\n" +
						"imageInter2 = s2::processImage({i1=resource2, i2=imageInter1});\n" +
						"s3 = C2Services.C2Service_canny::__construct();\n" +
						"s3::setOptions({i1=paramValueCanny});\n" +
						"imageOut = s3::processImage({i1=resource3, i2=imageInter2});\n";

		C2Resource SCPU	= new C2Resource("scpu");
		C2Resource CPU	= new C2Resource("cpu");


		Map< String,Double> paramsCanny = new HashMap< String,Double>();
		paramsCanny.put("Thigh",0.7);
		paramsCanny.put("Tlow",0.3);


		C2Params paramValuesCanny = new C2Params(paramsCanny);

		SEDEObject inputObject_lenna	= new ObjectDataField(C2Image.class.getName(), lenna);
		SEDEObject inputObject_res1		= new ObjectDataField(C2Resource.class.getName(), SCPU);
		SEDEObject inputObject_res2		= new ObjectDataField(C2Resource.class.getName(), CPU);
		SEDEObject inputObject_res3		= new ObjectDataField(C2Resource.class.getName(), CPU);
		SEDEObject inputObject_paramCanny	= new ObjectDataField(C2Params.class.getName(), paramValuesCanny);

		ResolvePolicy policy = new ResolvePolicy();
		policy.setServicePolicy("None");
		policy.setReturnFieldnames(Arrays.asList("imageInter1", "imageInter2", "imageOut"));

		Map<String, SEDEObject> inputs = new HashMap<>();
		inputs.put("imageIn", inputObject_lenna);
		inputs.put("resource1", inputObject_res1);
		inputs.put("resource2", inputObject_res2);
		inputs.put("resource3", inputObject_res3);
		inputs.put("paramValueCanny", inputObject_paramCanny);

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
		JOptionPane.showMessageDialog(null, lenna_inter2.convertToImageIcon(), "Gauss Blurred Image", JOptionPane.PLAIN_MESSAGE);
		JOptionPane.showMessageDialog(null, lenna_mod.convertToImageIcon(), "Canny  Filtered Image", JOptionPane.PLAIN_MESSAGE);
	}

	@Test
	public void testDimContrastDeform() throws InvocationTargetException, InterruptedException {

		String composition =
				"s1 = C2Services.C2Service_dim::__construct();\n" +
						"imageInter1 = s1::processImage({i1=resource1, i2=imageIn});\n" +
						"s2 = C2Services.C2Service_contrast::__construct();\n" +
						"s2::setOptions({i1=paramValueContrast});\n" +
						"imageInter2 = s2::processImage({i1=resource2, i2=imageInter1});\n" +
						"s3 = C2Services.C2Service_deform::__construct();\n" +
						"s3::setOptions({i1=paramValueDeform});\n" +
						"imageOut = s3::processImage({i1=resource3, i2=imageInter2});\n";

		C2Resource SCPU	= new C2Resource("scpu");
		C2Resource CPU	= new C2Resource("cpu");
		C2Resource GPU	= new C2Resource("gpu");
		C2Resource FPGA	= new C2Resource("fpga");
		C2Resource JAVA	= new C2Resource("java");
		C2Resource OVERLAY = new C2Resource("overlay");



		Map< String,Double> paramsContrast = new HashMap< String,Double>();
		paramsContrast.put("factor",3.0);

		Map< String,Double> paramsDeform = new HashMap< String,Double>();
		paramsDeform.put("radius",3.0);
		paramsDeform.put("angle",60.0);
		paramsDeform.put("position_x",10.0);
		paramsDeform.put("position_y",10.0);
		paramsDeform.put("mode",1.0);


		C2Params paramValuesContrast = new C2Params(paramsContrast);
		C2Params paramValuesDeform = new C2Params(paramsDeform);

		SEDEObject inputObject_lenna	= new ObjectDataField(C2Image.class.getName(), lenna);
		SEDEObject inputObject_res1		= new ObjectDataField(C2Resource.class.getName(), SCPU);
		SEDEObject inputObject_res2		= new ObjectDataField(C2Resource.class.getName(), CPU);
		SEDEObject inputObject_res3		= new ObjectDataField(C2Resource.class.getName(), CPU);
		SEDEObject inputObject_paramContrast	= new ObjectDataField(C2Params.class.getName(), paramValuesContrast);
		SEDEObject inputObject_paramDeform	= new ObjectDataField(C2Params.class.getName(), paramValuesDeform);

		ResolvePolicy policy = new ResolvePolicy();
		policy.setServicePolicy("None");
		policy.setReturnFieldnames(Arrays.asList("imageInter1", "imageInter2", "imageOut"));

		Map<String, SEDEObject> inputs = new HashMap<>();
		inputs.put("imageIn", inputObject_lenna);
		inputs.put("resource1", inputObject_res1);
		inputs.put("resource2", inputObject_res2);
		inputs.put("resource3", inputObject_res3);
		inputs.put("paramValueContrast", inputObject_paramContrast);
		inputs.put("paramValueDeform", inputObject_paramDeform);

		RunRequest runRequest = new RunRequest("proc_cservices", composition, policy, inputs);

		Map<String, Result> resultMap = coreClient.blockingRun(runRequest);
		Result inter1 = resultMap.get("imageInter1");
		Result inter2 = resultMap.get("imageInter2");
		Result result = resultMap.get("imageOut");

		C2Image lenna_inter1 = inter1.castResultData(C2Image.class.getName(), C2ImageCaster.class).getDataField();
		C2Image lenna_inter2 = inter2.castResultData(C2Image.class.getName(), C2ImageCaster.class).getDataField();
		lenna_mod = result.castResultData(C2Image.class.getName(), C2ImageCaster.class).getDataField();

		JOptionPane.showMessageDialog(null, lenna.convertToImageIcon(), "Input Image", JOptionPane.PLAIN_MESSAGE);
		JOptionPane.showMessageDialog(null, lenna_inter1.convertToImageIcon(), "Dim Image", JOptionPane.PLAIN_MESSAGE);
		JOptionPane.showMessageDialog(null, lenna_inter2.convertToImageIcon(), "Contrast Image", JOptionPane.PLAIN_MESSAGE);
		JOptionPane.showMessageDialog(null, lenna_mod.convertToImageIcon(), "Deform Image", JOptionPane.PLAIN_MESSAGE);
	}

	@Test
	public void testWhitebalanceRotateNegative() throws InvocationTargetException, InterruptedException {

		String composition =
				"s1 = C2Services.C2Service_whitebalance::__construct();\n" +
						"s1::setOptions({i1=paramValueWhitebalance});\n" +
						"imageInter1 = s1::processImage({i1=resource1, i2=imageIn});\n" +
						"s2 = C2Services.C2Service_rotate::__construct();\n" +
						"s2::setOptions({i1=paramValueRotate});\n" +
						"imageInter2 = s2::processImage({i1=resource2, i2=imageInter1});\n" +
						"s3 = C2Services.C2Service_negative::__construct();\n" +
						"imageOut = s3::processImage({i1=resource3, i2=imageInter2});\n";

		C2Resource SCPU	= new C2Resource("scpu");
		C2Resource CPU	= new C2Resource("cpu");
		C2Resource GPU	= new C2Resource("gpu");
		C2Resource FPGA	= new C2Resource("fpga");
		C2Resource JAVA	= new C2Resource("java");
		C2Resource OVERLAY = new C2Resource("overlay");



		Map< String,Double> paramsWhitebalance = new HashMap< String,Double>();
		paramsWhitebalance.put("parameter",1.0);

		Map< String,Double> paramsRotate = new HashMap< String,Double>();
        paramsWhitebalance.put("degree",60.0);



		C2Params paramValuesWhitebalance = new C2Params(paramsWhitebalance);
		C2Params paramValuesRotate = new C2Params(paramsRotate);

		SEDEObject inputObject_lenna	= new ObjectDataField(C2Image.class.getName(), lenna);
		SEDEObject inputObject_res1		= new ObjectDataField(C2Resource.class.getName(), SCPU);
		SEDEObject inputObject_res2		= new ObjectDataField(C2Resource.class.getName(), CPU);
		SEDEObject inputObject_res3		= new ObjectDataField(C2Resource.class.getName(), CPU);
		SEDEObject inputObject_paramWhitebalance	= new ObjectDataField(C2Params.class.getName(), paramValuesWhitebalance);
		SEDEObject inputObject_paramRotate	= new ObjectDataField(C2Params.class.getName(), paramValuesRotate);

		ResolvePolicy policy = new ResolvePolicy();
		policy.setServicePolicy("None");
		policy.setReturnFieldnames(Arrays.asList("imageInter1", "imageInter2", "imageOut"));

		Map<String, SEDEObject> inputs = new HashMap<>();
		inputs.put("imageIn", inputObject_lenna);
		inputs.put("resource1", inputObject_res1);
		inputs.put("resource2", inputObject_res2);
		inputs.put("resource3", inputObject_res3);
		inputs.put("paramValueWhitebalance", inputObject_paramWhitebalance);
		inputs.put("paramValueRotate", inputObject_paramRotate);

		RunRequest runRequest = new RunRequest("proc_cservices", composition, policy, inputs);

		Map<String, Result> resultMap = coreClient.blockingRun(runRequest);
		Result inter1 = resultMap.get("imageInter1");
		Result inter2 = resultMap.get("imageInter2");
		Result result = resultMap.get("imageOut");

		C2Image lenna_inter1 = inter1.castResultData(C2Image.class.getName(), C2ImageCaster.class).getDataField();
		C2Image lenna_inter2 = inter2.castResultData(C2Image.class.getName(), C2ImageCaster.class).getDataField();
		lenna_mod = result.castResultData(C2Image.class.getName(), C2ImageCaster.class).getDataField();

		JOptionPane.showMessageDialog(null, lenna.convertToImageIcon(), "Input Image", JOptionPane.PLAIN_MESSAGE);
		JOptionPane.showMessageDialog(null, lenna_inter1.convertToImageIcon(), "White balanced Image", JOptionPane.PLAIN_MESSAGE);
		JOptionPane.showMessageDialog(null, lenna_inter2.convertToImageIcon(), "Rotated Image", JOptionPane.PLAIN_MESSAGE);
		JOptionPane.showMessageDialog(null, lenna_mod.convertToImageIcon(), "Negative Image", JOptionPane.PLAIN_MESSAGE);
	}

	@Test
	public void testMaxrgbReflectGrayhistoequal() throws InvocationTargetException, InterruptedException {

		String composition =
				"s1 = C2Services.C2Service_maxrgb::__construct();\n" +
						"s1::setOptions({i1=paramValueMaxrgb});\n" +
						"imageInter1 = s1::processImage({i1=resource1, i2=imageIn});\n" +
						"s2 = C2Services.C2Service_reflect::__construct();\n" +
						"s2::setOptions({i1=paramValueReflect});\n" +
						"imageInter2 = s2::processImage({i1=resource2, i2=imageInter1});\n" +
						"s3 = C2Services.C2Service_grayhistoequal::__construct();\n" +
						"imageOut = s3::processImage({i1=resource3, i2=imageInter2});\n";

		C2Resource SCPU	= new C2Resource("scpu");
		C2Resource CPU	= new C2Resource("cpu");
		C2Resource GPU	= new C2Resource("gpu");
		C2Resource FPGA	= new C2Resource("fpga");
		C2Resource JAVA	= new C2Resource("java");
		C2Resource OVERLAY = new C2Resource("overlay");



		Map< String,Double> paramsMaxrgb = new HashMap< String,Double>();
		paramsMaxrgb.put("mode",1.0);

		Map< String,Double> paramsReflect = new HashMap< String,Double>();
		paramsReflect.put("angle",60.0);



		C2Params paramValuesMaxrgb = new C2Params(paramsMaxrgb);
		C2Params paramValuesReflect = new C2Params(paramsReflect);

		SEDEObject inputObject_lenna	= new ObjectDataField(C2Image.class.getName(), lenna);
		SEDEObject inputObject_res1		= new ObjectDataField(C2Resource.class.getName(), SCPU);
		SEDEObject inputObject_res2		= new ObjectDataField(C2Resource.class.getName(), CPU);
		SEDEObject inputObject_res3		= new ObjectDataField(C2Resource.class.getName(), CPU);
		SEDEObject inputObject_paramMaxrgb	= new ObjectDataField(C2Params.class.getName(), paramValuesMaxrgb);
		SEDEObject inputObject_paramReflect	= new ObjectDataField(C2Params.class.getName(), paramValuesReflect);

		ResolvePolicy policy = new ResolvePolicy();
		policy.setServicePolicy("None");
		policy.setReturnFieldnames(Arrays.asList("imageInter1", "imageInter2", "imageOut"));

		Map<String, SEDEObject> inputs = new HashMap<>();
		inputs.put("imageIn", inputObject_lenna);
		inputs.put("resource1", inputObject_res1);
		inputs.put("resource2", inputObject_res2);
		inputs.put("resource3", inputObject_res3);
		inputs.put("paramValueMaxrgb", inputObject_paramMaxrgb);
		inputs.put("paramValueReflect", inputObject_paramReflect);

		RunRequest runRequest = new RunRequest("proc_cservices", composition, policy, inputs);

		Map<String, Result> resultMap = coreClient.blockingRun(runRequest);
		Result inter1 = resultMap.get("imageInter1");
		Result inter2 = resultMap.get("imageInter2");
		Result result = resultMap.get("imageOut");

		C2Image lenna_inter1 = inter1.castResultData(C2Image.class.getName(), C2ImageCaster.class).getDataField();
		C2Image lenna_inter2 = inter2.castResultData(C2Image.class.getName(), C2ImageCaster.class).getDataField();
		lenna_mod = result.castResultData(C2Image.class.getName(), C2ImageCaster.class).getDataField();

		JOptionPane.showMessageDialog(null, lenna.convertToImageIcon(), "Input Image", JOptionPane.PLAIN_MESSAGE);
		JOptionPane.showMessageDialog(null, lenna_inter1.convertToImageIcon(), "Max RGB Image", JOptionPane.PLAIN_MESSAGE);
		JOptionPane.showMessageDialog(null, lenna_inter2.convertToImageIcon(), "Reflected Image", JOptionPane.PLAIN_MESSAGE);
		JOptionPane.showMessageDialog(null, lenna_mod.convertToImageIcon(), "Gray Histo equalized Image", JOptionPane.PLAIN_MESSAGE);
	}

	@Test
	public void testRadialblurPixelate() throws InvocationTargetException, InterruptedException {

		String composition =
				"s1 = C2Services.C2Service_radialblur::__construct();\n" +
						"imageInter1 = s1::processImage({i1=resource1, i2=imageIn});\n" +
						"s2 = C2Services.C2Service_pixelate::__construct();\n" +
						"s2::setOptions({i1=paramValuePixelate});\n" +
						"imageOut = s2::processImage({i1=resource2, i2=imageInter1});\n";

		C2Resource SCPU	= new C2Resource("scpu");
		C2Resource CPU	= new C2Resource("cpu");

		Map< String,Double> paramsPixelate = new HashMap< String,Double>();
		paramsPixelate.put("area",5.0);

		C2Params paramValuesPixelate = new C2Params(paramsPixelate);


		SEDEObject inputObject_lenna	= new ObjectDataField(C2Image.class.getName(), lenna);
		SEDEObject inputObject_res1		= new ObjectDataField(C2Resource.class.getName(), SCPU);
		SEDEObject inputObject_res2		= new ObjectDataField(C2Resource.class.getName(), SCPU);
		SEDEObject inputObject_paramPixelate	= new ObjectDataField(C2Params.class.getName(), paramValuesPixelate);

		ResolvePolicy policy = new ResolvePolicy();
		policy.setServicePolicy("None");
		policy.setReturnFieldnames(Arrays.asList("imageInter1", "imageOut"));

		Map<String, SEDEObject> inputs = new HashMap<>();
		inputs.put("imageIn", inputObject_lenna);
		inputs.put("resource1", inputObject_res1);
		inputs.put("resource2", inputObject_res2);
		inputs.put("paramValuePixelate", inputObject_paramPixelate);

		RunRequest runRequest = new RunRequest("proc_cservices", composition, policy, inputs);

		Map<String, Result> resultMap = coreClient.blockingRun(runRequest);
		Result inter1 = resultMap.get("imageInter1");
		Result result = resultMap.get("imageOut");

		C2Image lenna_inter1 = inter1.castResultData(C2Image.class.getName(), C2ImageCaster.class).getDataField();
		lenna_mod = result.castResultData(C2Image.class.getName(), C2ImageCaster.class).getDataField();

		JOptionPane.showMessageDialog(null, lenna.convertToImageIcon(), "Input Image", JOptionPane.PLAIN_MESSAGE);
		JOptionPane.showMessageDialog(null, lenna_inter1.convertToImageIcon(), "Radial Blurred Image", JOptionPane.PLAIN_MESSAGE);
		JOptionPane.showMessageDialog(null, lenna_mod.convertToImageIcon(), "Pixelate Image", JOptionPane.PLAIN_MESSAGE);
	}

	@Test
    public void testcanny() throws InvocationTargetException, InterruptedException {

        String composition =
                "s1 = C2Services.C2Service_canny::__construct();\n" +
                        "s1::setOptions({i1=paramValueCanny});\n" +
                        "imageOut = s1::processImage({i1=resource1, i2=imageIn});\n" ;

        C2Resource SCPU = new C2Resource("scpu");
        C2Resource CPU = new C2Resource("cpu");

        double image_rows = (double)lenna.getRows();
        double image_cols = (double)lenna.getColumns();


        Map<String, Double> paramsCanny = new HashMap<String, Double>();
        paramsCanny.put("Tlow",0.1);
        paramsCanny.put("Thigh",0.3);

        C2Params paramValuesCanny = new C2Params(paramsCanny);


        SEDEObject inputObject_lenna = new ObjectDataField(C2Image.class.getName(), lenna);
        SEDEObject inputObject_res1 = new ObjectDataField(C2Resource.class.getName(), SCPU);
        SEDEObject inputObject_paramCanny = new ObjectDataField(C2Params.class.getName(), paramValuesCanny);

        ResolvePolicy policy = new ResolvePolicy();
        policy.setServicePolicy("None");
        policy.setReturnFieldnames(Arrays.asList("imageOut"));

        Map<String, SEDEObject> inputs = new HashMap<>();
        inputs.put("imageIn", inputObject_lenna);
        inputs.put("resource1", inputObject_res1);
        inputs.put("paramValueCanny", inputObject_paramCanny);

        RunRequest runRequest = new RunRequest("proc_cservices", composition, policy, inputs);

        Map<String, Result> resultMap = coreClient.blockingRun(runRequest);
        Result result = resultMap.get("imageOut");

        lenna_mod = result.castResultData(C2Image.class.getName(), C2ImageCaster.class).getDataField();

        JOptionPane.showMessageDialog(null, lenna.convertToImageIcon(), "Input Image", JOptionPane.PLAIN_MESSAGE);
        JOptionPane.showMessageDialog(null, lenna_mod.convertToImageIcon(), "Pixelate Image", JOptionPane.PLAIN_MESSAGE);
    }

    @Test
    public void testcontrast() throws InvocationTargetException, InterruptedException {

        String composition =
                "s1 = C2Services.C2Service_contrast::__construct();\n" +
                        "s1::setOptions({i1=paramValueContrast});\n" +
                        "imageOut = s1::processImage({i1=resource1, i2=imageIn});\n" ;

        C2Resource SCPU = new C2Resource("scpu");

        double image_rows = (double)lenna.getRows();
        double image_cols = (double)lenna.getColumns();


        Map< String,Double> paramsContrast = new HashMap< String,Double>();
        paramsContrast.put("factor",5.0);

        C2Params paramValuesContrast = new C2Params(paramsContrast);


        SEDEObject inputObject_lenna = new ObjectDataField(C2Image.class.getName(), lenna);
        SEDEObject inputObject_res1 = new ObjectDataField(C2Resource.class.getName(), SCPU);
        SEDEObject inputObject_paramContrast = new ObjectDataField(C2Params.class.getName(), paramValuesContrast);

        ResolvePolicy policy = new ResolvePolicy();
        policy.setServicePolicy("None");
        policy.setReturnFieldnames(Arrays.asList("imageOut"));

        Map<String, SEDEObject> inputs = new HashMap<>();
        inputs.put("imageIn", inputObject_lenna);
        inputs.put("resource1", inputObject_res1);
        inputs.put("paramValueContrast", inputObject_paramContrast);

        RunRequest runRequest = new RunRequest("proc_cservices", composition, policy, inputs);

        Map<String, Result> resultMap = coreClient.blockingRun(runRequest);
        Result result = resultMap.get("imageOut");

        lenna_mod = result.castResultData(C2Image.class.getName(), C2ImageCaster.class).getDataField();

        JOptionPane.showMessageDialog(null, lenna.convertToImageIcon(), "Input Image", JOptionPane.PLAIN_MESSAGE);
        JOptionPane.showMessageDialog(null, lenna_mod.convertToImageIcon(), "Contrast Image", JOptionPane.PLAIN_MESSAGE);
    }

    @Test
    public void testGreyContrast() throws InvocationTargetException, InterruptedException {

        String composition =
                "s1 = C2Services.C2Service_grey::__construct();\n" +
                        "imageInter1 = s1::processImage({i1=resource1, i2=imageIn});\n" +
                        "s2 = C2Services.C2Service_contrast::__construct();\n" +
                        "imageOut = s2::processImage({i1=resource2, i2=imageInter1});\n";

        C2Resource SCPU = new C2Resource("scpu");
        C2Resource CPU = new C2Resource("cpu");


        Map< String,Double> paramsContrast = new HashMap< String,Double>();
        paramsContrast.put("factor",10.0);

        C2Params paramValuesContrast = new C2Params(paramsContrast);


        SEDEObject inputObject_lenna = new ObjectDataField(C2Image.class.getName(), lenna);
        SEDEObject inputObject_res1 = new ObjectDataField(C2Resource.class.getName(), SCPU);
        SEDEObject inputObject_res2 = new ObjectDataField(C2Resource.class.getName(), CPU);
        SEDEObject inputObject_paramContrast = new ObjectDataField(C2Params.class.getName(), paramValuesContrast);

        ResolvePolicy policy = new ResolvePolicy();
        policy.setServicePolicy("None");
        policy.setReturnFieldnames(Arrays.asList("imageOut"));

        Map<String, SEDEObject> inputs = new HashMap<>();
        inputs.put("imageIn", inputObject_lenna);
        inputs.put("resource1", inputObject_res1);
        inputs.put("resource2", inputObject_res2);
        inputs.put("paramValueCanny", inputObject_paramContrast);

        RunRequest runRequest = new RunRequest("proc_cservices", composition, policy, inputs);

        Map<String, Result> resultMap = coreClient.blockingRun(runRequest);
        Result result = resultMap.get("imageOut");
        Result inter1 = resultMap.get("imageInter1");

        C2Image lenna_inter1 = inter1.castResultData(C2Image.class.getName(), C2ImageCaster.class).getDataField();
        lenna_mod = result.castResultData(C2Image.class.getName(), C2ImageCaster.class).getDataField();

        JOptionPane.showMessageDialog(null, lenna.convertToImageIcon(), "Input Image", JOptionPane.PLAIN_MESSAGE);
        JOptionPane.showMessageDialog(null, lenna_inter1.convertToImageIcon(), "Grey Image", JOptionPane.PLAIN_MESSAGE);
        JOptionPane.showMessageDialog(null, lenna_mod.convertToImageIcon(), "Contrast Image", JOptionPane.PLAIN_MESSAGE);
    }


}
