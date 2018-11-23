package C2Services;

import C2Data.C2Image;
import C2Data.C2NativeInterface;
import C2Data.C2Resource;
import C2Plugins.Plugin;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class C2Service_sobel extends Plugin {

    private static File serviceFile;
    private static List<File> linkedFiles;

    static {
        serviceFile = new File(C2NativeInterface.getPluginDir() + "libservice_sobel.so");
        linkedFiles = new ArrayList<File>();
        linkedFiles.add(new File(C2NativeInterface.getPluginDir() + "libservice_sobel_cpu.so"));
        linkedFiles.add(new File(C2NativeInterface.getPluginDir() + "libservice_sobel_scpu.so"));
    }

    public C2Service_sobel() {
        super(serviceFile, linkedFiles);

        System.out.println("C2Service_sobel::__construct();");
    }

    public C2Image processImage(C2Resource resource, C2Image sourceImage) {
        System.out.println("C2Service_sobel::processImage()@" + resource.getResourceString() + ";");

        switch (resource.getResourceString()) {
            case "s":
            case "c":
                break;
            case "g":
            case "f":
            default:
                throw new Error("Resource '" + resource.getResourceString() + "' not supported by service.");
        }

        List<Double> params         = new ArrayList<Double>();
        List<C2Image> input_images  = new ArrayList<C2Image>();
        input_images.add(sourceImage);

        List<C2Image> output_images = (ArrayList<C2Image>)process(resource.getResourceChar(), params, input_images);

        return output_images.get(0);
    }
}
