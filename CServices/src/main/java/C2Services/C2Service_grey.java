package C2Services;

import C2Data.C2Image;
import C2Data.C2NativeInterface;
import C2Data.C2Params;
import C2Data.C2Resource;
import C2Plugins.Plugin;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.Set;


public class C2Service_grey extends Plugin {

    private static File serviceFile;
    private static List<File> linkedFiles;

    static {
        serviceFile = new File(C2NativeInterface.getPluginDir() + "libservice_grey.so");
        linkedFiles = new ArrayList<File>();
        linkedFiles.add(new File(C2NativeInterface.getPluginDir() + "libservice_grey_cpu.so"));
        linkedFiles.add(new File(C2NativeInterface.getPluginDir() + "libservice_grey_scpu.so"));
    }

    public C2Service_grey() {
        super(serviceFile, linkedFiles);

        printMethod("__construct");
    }

    protected List<Double> getParamList() {
        List<Double> paramList      = new ArrayList<Double>();

        return paramList;
    }

    public C2Image processImage(C2Resource resource, C2Image sourceImage) {
        printMethod("processImage", resource.getResourceString());

        switch (resource.getResourceString()) {
            case "j":
            case "s":
            case "c":
                break;
            case "g":
            case "f":
            default:
                throw new Error("Resource '" + resource.getResourceString() + "' not supported by service.");
        }

        List<C2Image> input_images  = new ArrayList<C2Image>();
        input_images.add(sourceImage);

        List<C2Image> output_images = null;

        if (resource.getResourceString() != "j") {
            output_images = (ArrayList<C2Image>) process(resource.getResourceChar(), getParamList(), input_images);
        } else {
            output_images = (ArrayList<C2Image>) rgb2grey(input_images);
        }

        return output_images.get(0);
    }

    private List<C2Image> rgb2grey(List<C2Image> inputImages) {
        C2Image inputImage  = inputImages.get(0);

        int lrows           = inputImage.getRows();
        int lcolumns        = inputImage.getColumns();

        short[] pixelsIn    = inputImage.getPixels();
        short[] pixelsOut   = new short[inputImage.getRows() * inputImage.getColumns() * 4];

        int pos = 0;
        for (int i = 0; i < lrows; ++i) {
            for (int j = 0; j < lcolumns; ++j) {
                pos = (i * lcolumns + j) * 4;

                int blue    = pixelsIn[pos + 0] & 0xffff;
                int green   = pixelsIn[pos + 1] & 0xffff;
                int red     = pixelsIn[pos + 2] & 0xffff;

                int value   = blue + green + red;

                // blue
                pixelsOut[pos + 0] = (short)(value/3);
                // green
                pixelsOut[pos + 1] = pixelsOut[pos + 0];
                // red
                pixelsOut[pos + 2] = pixelsOut[pos + 0];
                // alpha
                pixelsOut[pos + 3] = 0;
            }
        }

        C2Image outputImage = new C2Image(pixelsOut, inputImage.getRows(), inputImage.getColumns());

        List<C2Image> outputImages = new ArrayList<C2Image>();
        outputImages.add(outputImage);

        return outputImages;
    }
}
