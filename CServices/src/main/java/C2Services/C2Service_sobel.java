package C2Services;

import C2Data.C2Image;
import C2Plugins.Plugin;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class C2Service_sobel extends Plugin {

    private static File serviceFile;
    private static List<File> linkedFiles;

    static {
        serviceFile = new File("/home/aloesch/projects/SEDE/CServices/cbuild/libservice_sobel.so");
        linkedFiles = new ArrayList<File>();
        linkedFiles.add(new File("/home/aloesch/projects/SEDE/CServices/cbuild/libservice_sobel_cpu.so"));
        linkedFiles.add(new File("/home/aloesch/projects/SEDE/CServices/cbuild/libservice_sobel_scpu.so"));
    }

    public C2Service_sobel() {
        super(serviceFile, linkedFiles);

        System.out.println("C2Service_sobel::__construct();");
    }

    public C2Image processImage(C2Image sourceImage) {
        System.out.println("C2Service_sobel::processImage();");
        //TODO set and use search path for libraries: java.library.path
        //TODO load shared objects only once
        //System.load("/sede/codebase/ServiceCodeProvider/c2imaging/service_node/bin/libpluginbridge.so");

        List<Double> params         = new ArrayList<Double>();
        List<C2Image> input_images  = new ArrayList<C2Image>();
        input_images.add(sourceImage);

        List<C2Image> output_images = (ArrayList<C2Image>)process('c', params, input_images);

        return output_images.get(0);
    }
}
