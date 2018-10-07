package demo;

import Catalano.Imaging.FastBitmap;
import Catalano.Imaging.Tools.ImageHistogram;
import de.upb.sede.casters.C2ImageCaster;
import de.upb.sede.casters.FastBitmapCaster;
import de.upb.sede.casters.ImageHistogramCaster;
import de.upb.sede.client.CoreClient;
import de.upb.sede.client.HttpCoreClient;
import de.upb.sede.config.ClassesConfig;
import de.upb.sede.config.OnthologicalTypeConfig;
import de.upb.sede.core.ObjectDataField;
import de.upb.sede.core.SEDEObject;
import de.upb.sede.config.ExecutorConfiguration;
import de.upb.sede.core.SemanticStreamer;
import de.upb.sede.exec.ExecutorHttpServer;
import de.upb.sede.gateway.GatewayHttpServer;
import de.upb.sede.procedure.CastTypeProcedure;
import de.upb.sede.requests.Result;
import de.upb.sede.requests.RunRequest;
import de.upb.sede.requests.resolve.ResolvePolicy;
import de.upb.sede.util.ExecutorConfigurationCreator;
import de.upb.sede.util.FileUtil;
import de.upb.sede.util.WebUtil;
import C2Data.C2Image;
import C2Data.C2ImageManager;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class ImagingTests {

	static CoreClient coreClient;

	static String clientAddress = WebUtil.HostIpAddress();
	static int clientPort = 7000;

	static String gatewayAddress = "localhost";
	static int gatewayPort = 6000;

	// Catalano image type.
	static FastBitmap frog;

    // C2 image type.
    static C2ImageManager im_lenna;
    static C2Image lenna;


	static String executor1Address = WebUtil.HostIpAddress();
	static int executorPort = 9000;
	static ExecutorHttpServer executor1;

	static GatewayHttpServer gateway;

	// TODO: there is currently a bug in C2ImageManager, which breaks all tests that have GUIs. This flag controls this.
    //   if false: we can use C2Images, but no GUI (java.swing, java.awt, etc.)
    //   if true:  we cannot use C2Images, but all results are displayed in a GUI.
	static final boolean WORKAROUND___ENABLE_C2_IMAGES = true;

	@BeforeClass
	public static void startClient() {
		gateway = new GatewayHttpServer(gatewayPort, getTestClassConfig(), getTestTypeConfig());
		ExecutorConfigurationCreator creator = new ExecutorConfigurationCreator();
		creator.withExecutorId("Client");
		ExecutorConfiguration configuration = ExecutorConfiguration.parseJSON(creator.toString());
		coreClient = HttpCoreClient.createNew(configuration, clientAddress, clientPort, gatewayAddress, gatewayPort);
		/*
			Disable if you will have an executor register to the gateway:
		 */
		coreClient.getClientExecutor().getExecutorConfiguration().getSupportedServices().addAll(
				Arrays.asList("Catalano.Imaging.Filters.Crop",
						"Catalano.Imaging.Filters.Resize",
						"Catalano.Imaging.sede.CropFrom0",
						"Catalano.Imaging.Filters.CannyEdgeDetector",
						"Catalano.Imaging.Filters.CannyEdgeDetectorFactory",
						"Catalano.Imaging.Texture.BinaryPattern.LocalBinaryPattern",
						"Catalano.Imaging.Filters.GrayScale",
						"Catalano.Imaging.Filters.GrayScaleFactory",
						"Catalano.Imaging.Filters.GrayScale_Lightness",
						"Catalano.Imaging.Filters.GrayScale_Average",
						"Catalano.Imaging.Filters.GrayScale_GeometricMean",
						"Catalano.Imaging.Filters.GrayScale_Luminosity",
						"Catalano.Imaging.Filters.GrayScale_MinimumDecomposition",
						"Catalano.Imaging.Filters.GrayScale_MaximumDecomposition",
						"C2Services.C2AddTwoNumbers",
						"C2Services.C2service_grey")
		);
		/*
			Disable if you dont want to have a local executor do the imaging:
		 */
//		creator = new ExecutorConfigurationCreator();
//		creator.withExecutorId("executor");
//		executor1 = new ExecutorHttpServer(ExecutorConfiguration.parseJSON(creator.toString()), executor1Address, executorPort);
//		executor1.getBasisExecutor().getExecutorConfiguration().getSupportedServices().addAll(
//				Arrays.asList("Catalano.Imaging.Filters.Crop",
//						"Catalano.Imaging.Filters.Resize",
//						"Catalano.Imaging.sede.CropFrom0",
//						"Catalano.Imaging.Filters.CannyEdgeDetector",
//						"Catalano.Imaging.Filters.CannyEdgeDetectorFactory",
//						"Catalano.Imaging.Texture.BinaryPattern.LocalBinaryPattern",
//						"Catalano.Imaging.Filters.GrayScale",
//						"Catalano.Imaging.Filters.GrayScaleFactory",
//						"Catalano.Imaging.Filters.GrayScale_Lightness",
//						"Catalano.Imaging.Filters.GrayScale_Average",
//						"Catalano.Imaging.Filters.GrayScale_GeometricMean",
//						"Catalano.Imaging.Filters.GrayScale_Luminosity",
//						"Catalano.Imaging.Filters.GrayScale_MinimumDecomposition",
//						"Catalano.Imaging.Filters.GrayScale_MaximumDecomposition",
//						"C2Services.C2AddTwoNumbers",
//                        "C2Services.C2service_grey"
//						)
//		);
//		System.out.println(executor1.getBasisExecutor().getExecutorConfiguration().toJsonString());
//		gateway.getBasis().register(executor1.getBasisExecutor().registration());
		/*
			Disabled if you dont have dot installed.
		 */
//		coreClient.writeDotGraphToDir("testrsc");

	}



	@AfterClass
	public static void shutdown() {
		gateway.shutdown();
//		executor1.shutdown();
		coreClient.getClientExecutor().shutdown();

		if(WORKAROUND___ENABLE_C2_IMAGES)
            im_lenna.environmentDown();
	}

	@BeforeClass
	public static void loadBefore() {
		// Load Catalano image.
		frog = new FastBitmap(FileUtil.getPathOfResource("images/red-eyed.jpg"));
		assert frog != null;

		if(WORKAROUND___ENABLE_C2_IMAGES) {
			// Load libraries.
			loadC2Libraries();

            // Load C2 image.
            ArrayList<String> file_src = new ArrayList<String>();
            ArrayList<String> file_dest = new ArrayList<String>();
            file_src.add(FileUtil.getPathOfResource("images/lenna.tiff"));
			//file_src.add(FileUtil.getPathOfResource("images/red-eyed.jpg"));
            im_lenna = new C2ImageManager(file_src, file_dest);
            im_lenna.environmentUp();
            im_lenna.readImages();
            lenna = im_lenna.getSourceImages().get(0);
            assert lenna != null;
        }
	}

	/**
	 *  LD_LIBRARY_PATH=“/home/amin/SEDE/SEDE.services/c2.image/out/test/resources/shared_libs:/home/amin/SEDE/CServices/csrc/service_plugins/build:/usr/local/lib" idea
	 */

	@BeforeClass
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

		if(!WORKAROUND___ENABLE_C2_IMAGES)
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

		if(!WORKAROUND___ENABLE_C2_IMAGES)
		    JOptionPane.showMessageDialog(null, processedImage.toIcon(), "Result", JOptionPane.PLAIN_MESSAGE);
	}


	@Test
	public void testConvertMagickImage() {
        // This test only works with WORKAROUND___ENABLE_C2_IMAGES enabled!
        assert WORKAROUND___ENABLE_C2_IMAGES;

		System.out.printf("Magick/JNI load \"lenna\". %dx%d \n", lenna.getRows(), lenna.getColumns());

		// Convert to BufferedImage.
		BufferedImage buf_lenna = im_lenna.convertToBufferedImage(lenna);
		System.out.printf("Converted to BufferedImage. %dx%d \n", buf_lenna.getHeight(), buf_lenna.getWidth());

		// Convert to FastBitmap.
		FastBitmap fast_lenna = new FastBitmap(buf_lenna);
		System.out.printf("Converted to FastBitmap. %dx%d \n", fast_lenna.getHeight(), fast_lenna.getWidth());

	}

	@Test
	public void testRGBTestImage() {
		// Load C2 image.
		ArrayList<String> file_src = new ArrayList<String>();
		ArrayList<String> file_dest = new ArrayList<String>();
		file_src.add(FileUtil.getPathOfResource("images/rgb_test.png"));
		file_src.add(FileUtil.getPathOfResource("images/r_test.tiff"));
		C2ImageManager manager = new C2ImageManager(file_src, file_dest);

		manager.environmentUp();
		manager.readImages();
		C2Image rgbImg = manager.getSourceImages().get(0);
		C2Image rImg = manager.getSourceImages().get(1);

		FastBitmap rgbImgFb = new FastBitmap(FileUtil.getPathOfResource("images/rgb_test.png"));

		/*
		 * Write the pixels encoded in each representation to a file:
		 */
		StringBuilder pixelData = new StringBuilder();
		pixelData.append("rgb_test.png C2Image pixels:\n" + Arrays.toString(rgbImg.getPixels()));
		pixelData.append("\nr_test.png Fastbitmap pixels:\n" + pixelsToString(rgbImgFb));

		pixelData.append("\nr_test.tiff C2Image pixels:\n" + Arrays.toString(rImg.getPixels()));

		FileUtil.writeStringToFile("testrsc/pixel_data.txt", pixelData.toString());

		/*
		 * Cast C2Image -> semantic -> C2Image
		 */
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		SEDEObject image0 = new ObjectDataField("C2Data.C2Image", rgbImg);
		SemanticStreamer.streamObjectInto(out, image0, C2ImageCaster.class.getName(), "sem");
		byte[] semanticData = out.toByteArray();

		InputStream in = new ByteArrayInputStream(semanticData);
		SEDEObject c2Obj = SemanticStreamer.readObjectFrom(in, C2ImageCaster.class.getName(), "sem", "C2Data.C2Image");

		/*
		 * Cast C2Image -> semantic -> FastBitmap
		 */

		in = new ByteArrayInputStream(semanticData);
		SEDEObject fbObj = SemanticStreamer.readObjectFrom(in, FastBitmapCaster.class.getName(), "sem", "FastBitmap");

//		JOptionPane.showMessageDialog(null, ((FastBitmap) fbObj.getDataField()).toIcon(), "Result", JOptionPane.PLAIN_MESSAGE);
	}

	public String pixelsToString(FastBitmap fb) {
		int[] pixelArr = fb.getRGBData(); // 'getRGBData' is correct, internally its one array for both argb and rgb
		List<Integer> imageEncoding = new ArrayList<>();
		for (int index = 0, size = pixelArr.length;
			 index < size; index++) {
			int argb = pixelArr[index];
			imageEncoding.add(argb  >> 24 & 255); // alpha
			imageEncoding.add(argb  >> 16 & 255); // red
			imageEncoding.add(argb  >>  8 & 255); // green
			imageEncoding.add(argb  	  & 255); // blue
		}
		return imageEncoding.toString();
	}


    @Test
    public void testCatalanoCropWithC2Image() throws InvocationTargetException, InterruptedException {
        // This test only works with WORKAROUND___ENABLE_C2_IMAGES enabled!
	    assert WORKAROUND___ENABLE_C2_IMAGES;

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

        // Convert to BufferedImage.
        BufferedImage buf_lenna = im_lenna.convertToBufferedImage(lenna);
        // Convert to FastBitmap.
        FastBitmap fast_lenna = new FastBitmap(buf_lenna);

        System.out.printf("Running Catalano Crop with C2Image. %dx%d \n", fast_lenna.getHeight(), fast_lenna.getWidth());

        if(!WORKAROUND___ENABLE_C2_IMAGES)
            JOptionPane.showMessageDialog(null, fast_lenna.toIcon(), "Original image", JOptionPane.PLAIN_MESSAGE);

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

        if(!WORKAROUND___ENABLE_C2_IMAGES)
            JOptionPane.showMessageDialog(null, processedImage.toIcon(), "Result", JOptionPane.PLAIN_MESSAGE);

        System.out.printf("... done, result has dimensions %dx%d \n", processedImage.getHeight(), processedImage.getWidth());
    }

	@Test
	public void testC2CServicesAddTwoNumbers() throws InvocationTargetException, InterruptedException {
		// This test only works with WORKAROUND___ENABLE_C2_IMAGES enabled!
		assert WORKAROUND___ENABLE_C2_IMAGES;

		/*
			Tests fixed parameters:
		 */
		String composition =
				"s1 = C2Services.C2AddTwoNumbers::__construct({11});\n" +
						"i1 = s1::compute({22,33});";

		ResolvePolicy policy = new ResolvePolicy();

		policy.setServicePolicy("None");
		policy.setReturnFieldnames(Arrays.asList("i1"));

		Map<String, SEDEObject> inputs = new HashMap<>();

		RunRequest runRequest = new RunRequest("proc_cservices", composition, policy, inputs);

		coreClient.run(runRequest, result -> {
			System.out.println(result.getFieldname());
		});
		coreClient.join("proc_cservices", true);
	}

    @Test
    public void testC2ServicesC2service_grey() throws InvocationTargetException, InterruptedException {
		// This test only works with WORKAROUND___ENABLE_C2_IMAGES enabled!
		assert WORKAROUND___ENABLE_C2_IMAGES;

		/*
			Tests fixed parameters:
		 */
        String composition =
                "s1 = C2Services.C2service_grey::__construct();\n" +
        		"imageOut = s1::process({i1=imageIn});\n";


		SEDEObject inputObject_c2i = new ObjectDataField(C2Image.class.getName(), lenna);

        ResolvePolicy policy = new ResolvePolicy();
        policy.setServicePolicy("None");
        policy.setReturnFieldnames(Arrays.asList("imageOut"));

        Map<String, SEDEObject> inputs = new HashMap<>();
		inputs.put("imageIn", inputObject_c2i);

        RunRequest runRequest = new RunRequest("proc_cservices", composition, policy, inputs);

		Map<String, Result> resultMap = coreClient.blockingRun(runRequest);
		Result result = resultMap.get("imageOut");

		C2Image processedImage = result.castResultData(C2Image.class.getName(), C2ImageCaster.class).getDataField();
    }

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
}
