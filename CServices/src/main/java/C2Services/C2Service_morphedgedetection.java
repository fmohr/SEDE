package C2Services;


import C2Data.C2Image;
import C2Data.C2NativeInterface;
import C2Data.C2Params;
import C2Data.C2Resource;
import C2Plugins.Plugin;

import java.io.File;
import java.util.*;

public class C2Service_morphedgedetection extends Plugin {

    private static File serviceFile;
    private static List<File> linkedFiles;

    static {
        serviceFile = new File(C2NativeInterface.getPluginDir() + "libservice_morphedgedetection.so");
        linkedFiles = new ArrayList<File>();
        linkedFiles.add(new File(C2NativeInterface.getPluginDir() + "libservice_morphedgedetection_cpu.so"));
        linkedFiles.add(new File(C2NativeInterface.getPluginDir() + "libservice_morphedgedetection_scpu.so"));
        //linkedFiles.add(new File(C2NativeInterface.getPluginDir() + "libservice_morphedgedetection_gpu.so"));
        //linkedFiles.add(new File(C2NativeInterface.getPluginDir() + "libservice_morphedgedetection_fpga.so"));
    }

    public C2Service_morphedgedetection() {
        super(serviceFile, linkedFiles);

        Map<String, Double> paramsDefaultMap = new HashMap<String, Double>();
        paramsDefaultMap.put("matrix_size", 3.0);
        setOptions(new C2Params(paramsDefaultMap));

        printMethod("__construct");
    }

    protected List<Double> getParamList() {
        List<Double> paramList      = new ArrayList<Double>();
        Map<String,Double> paramMap = getOptions().getParams();

        paramList.add(paramMap.get("matrix_size"));

        return paramList;
    }

    public C2Image processImage(C2Resource resource, C2Image sourceImage) {
        printMethod("processImage", resource.getResourceString());

        switch (resource.getResourceString()) {
            case "s":
            case "c":
            case "g":
            case "f":
                break;
            case "j":
            default:
                throw new Error("Resource '" + resource.getResourceString() + "' not supported by service.");
        }

        List<C2Image> input_images  = new ArrayList<C2Image>();
        input_images.add(sourceImage);

        List<C2Image> output_images = null;

        if (resource.getResourceString() != "j") {
            output_images = (ArrayList<C2Image>) process(resource.getResourceChar(), getParamList(), input_images);
        } else {
            output_images = (ArrayList<C2Image>) sobel(input_images);
        }

        return output_images.get(0);
    }

    public List<C2Image> sobel(List<C2Image> inputImages) {
        C2Image inputImage  = inputImages.get(0);

        int rows            = inputImage.getRows();
        int columns         = inputImage.getColumns();

        short[] pixelsIn    = inputImage.getPixels();
        short[] pixelsInter = new short[inputImage.getRows() * inputImage.getColumns() * 4];
        short[] pixelsOut   = new short[inputImage.getRows() * inputImage.getColumns() * 4];

        for (int i = 0; i < rows; ++i) {
            for (int j = 0; j < columns; ++j) {
                int pos     = (i * columns + j) * 4;

                int blue = pixelsIn[pos + 0] & 0xffff;
                int green = pixelsIn[pos + 1] & 0xffff;
                int red = pixelsIn[pos + 2] & 0xffff;

                int value = blue + green + red;

                pixelsInter[pos + 0] = (short) (value / 3);
                pixelsInter[pos + 1] = pixelsInter[pos + 0];
                pixelsInter[pos + 2] = pixelsInter[pos + 0];
                pixelsInter[pos + 3] = 0;
            }
        }

        for (int i = 0; i < rows; ++i) {
            for (int j = 0; j < columns; ++j) {
                int pos = i * columns + j;

                int temp_red_x, temp_red_y;

                if (i==0 || j==0 || i==rows-1 || j==columns-1) {
                    temp_red_x = 0;
                    temp_red_y = 0;
                } else {
                    temp_red_x =
                            (-1 * (pixelsInter[(pos - columns - 1) * 4] & 0xffff))
                                    + (-2 * (pixelsInter[(pos - columns    ) * 4] & 0xffff))
                                    + (-1 * (pixelsInter[(pos - columns + 1) * 4] & 0xffff))
                                    + (     (pixelsInter[(pos + columns - 1) * 4] & 0xffff))
                                    + ( 2 * (pixelsInter[(pos + columns    ) * 4] & 0xffff))
                                    + (     (pixelsInter[(pos + columns + 1) * 4] & 0xffff));

                    temp_red_y =
                            (-1 * (pixelsInter[(pos - columns - 1) * 4] & 0xffff))
                                    + (     (pixelsInter[(pos - columns + 1) * 4] & 0xffff))
                                    + (-2 * (pixelsInter[(pos           - 1) * 4] & 0xffff))
                                    + ( 2 * (pixelsInter[(pos           + 1) * 4] & 0xffff))
                                    + (-1 * (pixelsInter[(pos + columns - 1) * 4] & 0xffff))
                                    + (     (pixelsInter[(pos + columns + 1) * 4] & 0xffff));
                }

                if(true) {
                    pixelsOut[(pos * 4)] = (short) ((Math.abs(temp_red_x) > Math.abs(temp_red_y)) ? Math.abs(temp_red_x) : Math.abs(temp_red_y));
                } else {
                    pixelsOut[(pos * 4)] = (short) (Math.sqrt(temp_red_x * temp_red_x + temp_red_y * temp_red_y));
                }

                pixelsOut[(pos * 4) + 1]	= pixelsOut[(pos * 4)];
                pixelsOut[(pos * 4) + 2]	= pixelsOut[(pos * 4)];
                pixelsOut[(pos * 4) + 3]	= 0;
            }
        }

        C2Image outputImage = new C2Image(pixelsOut, inputImage.getRows(), inputImage.getColumns());

        List<C2Image> outputImages = new ArrayList<C2Image>();
        outputImages.add(outputImage);

        return outputImages;
    }
}
