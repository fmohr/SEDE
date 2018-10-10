package C2Services;

import C2Data.C2Image;
import C2Plugins.Plugin;
import C2Plugins.ServiceInstancePlugin;

import java.io.File;
import java.util.Iterator;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static java.lang.Math.max;
import static java.lang.Math.min;

public class C2service_grey extends Plugin {

    private int result = 0;

    public int compute(int a, int b) {
        result += a + b;

        System.out.println("C2service_grey::compute: " + result);

        return result;
    }

    /**
     *
     */
    private static final long serialVersionUID = -7471727695546943682L;

    private static File serviceFile;
    private static List<File> linkedFiles;

    static {
        serviceFile = new File("/home/lnabanita/SEDE/CServices/csrc/service_plugins/build/libservice_grey.so");
        linkedFiles = new ArrayList<File>();
        linkedFiles.add(new File("/home/lnabanita/SEDE/CServices/csrc/service_plugins/build/libservice_grey_cpu.so"));
    }

    public C2service_grey() {
        super(serviceFile, linkedFiles);
    }
/*
    public List<C2Image> process(List<C2Image> sourceImages) {
        System.out.println("sg::processList");
        List<C2Image> result = new ArrayList<C2Image>();
        for(C2Image sourceImage: sourceImages) {

            result.add(this.process(sourceImage));

        }

        return result;
    }
*/

    public C2Image process(C2Image sourceImage) {

        C2Image pixpack_target = new C2Image(sourceImage.getPixels(), sourceImage.getRows(), sourceImage.getColumns());

        int pos = 0, i, j;

        for (i = 0; i < sourceImage.getRows(); i++) {
            for (j = 0; j < sourceImage.getColumns() - 4; j += 4) {

                pos = i * sourceImage.getColumns() + j;

                pixpack_target.getPixels()[pos + 0] = (short) ((sourceImage.getPixels()[pos + 0] + sourceImage.getPixels()[pos + 1] + sourceImage.getPixels()[pos + 2]) / 3);
                pixpack_target.getPixels()[pos + 1] = (short) ((sourceImage.getPixels()[pos + 0] + sourceImage.getPixels()[pos + 1] + sourceImage.getPixels()[pos + 2]) / 3);
                pixpack_target.getPixels()[pos + 2] = (short) ((sourceImage.getPixels()[pos + 0] + sourceImage.getPixels()[pos + 1] + sourceImage.getPixels()[pos + 2]) / 3);
                pixpack_target.getPixels()[pos + 3] = 0;

            }

        }

        return pixpack_target;
    }




// public C2Image process(C2Image sourceImage) {
//        System.out.println("sg::process");
//        //TODO set and use search path for libraries: java.library.path
//        //TODO load shared objects only once
//        // System.load("/sede/codebase/ServiceCodeProvider/c2imaging/service_node/bin/libpluginbridge.so");
//
//
//        System.load(serviceFile.getAbsolutePath());
//
//        for (File linkedFile : linkedFiles) {
//            System.load(linkedFile.getAbsolutePath());
//        }
//
//        Plugin plugin = new Plugin(serviceFile, linkedFiles);
//
//        ServiceInstancePlugin serviceInstancePlugin = new ServiceInstancePlugin(plugin);
//
//        HashMap<String, Object> params = new HashMap<String, Object>();
//
//        params.put("resource", 'c'); // c=cpu, g=gpu, f=fpga, s=scpu
//
//        List<Double> parameters = new ArrayList<Double>();
//        //parameters.add(1d);
//        params.put("params", parameters);// TODO see void get_service_params in e.g. service_dim2.c
//
//        params.put("images", new ArrayList<C2Image>().add(sourceImage));
//
//        Object result = serviceInstancePlugin.invokeOp("process", params);
//
//        C2Image image = null;
//
//        if (result instanceof List) {
//
//            Iterator<C2Image> iter = ((List) result).iterator();
//
//            if(iter.hasNext()) {
//
//                image = iter.next();
//            }
//
//        }
//        return image;
        //}


/*
    public C2Image process(C2Image sourceImage) {


        int[] cmap = new int[sourceImage.getPixels().length];

        C2Image pixpack_target = new C2Image(sourceImage.getPixels(), sourceImage.getRows(), sourceImage.getColumns());

        short[] shrt_pixel = sourceImage.getPixels();

        int[] int_pixel = new int[sourceImage.getPixels().length];

        for (int k = 1; k < int_pixel.length; ++k) {
            int_pixel[k] = cmap[shrt_pixel[k]];
        }

        System.out.println("---------------------- INT pixel ------------------");
        System.out.println(int_pixel[0]);
        System.out.println(int_pixel[1]);
        System.out.println(int_pixel[2]);
        System.out.println(int_pixel[3]);

        System.out.println(cmap.length);
        System.out.println(sourceImage.getPixels().length);


        System.out.println("---------------------- SHORT pixel ------------------");
        System.out.println(sourceImage.getPixels()[0]);
        System.out.println(sourceImage.getPixels()[1]);
        System.out.println(sourceImage.getPixels()[2]);
        System.out.println(sourceImage.getPixels()[3]);


        int pos=0, i, j;

        for (i = 0; i < sourceImage.getRows(); i++) {
            for (j = 0; j < sourceImage.getColumns() - 4; j += 4) {

                pixpack_target.getPixels()[pos + 0] = (short) ((sourceImage.getPixels()[pos + 0] + sourceImage.getPixels()[pos + 1] + sourceImage.getPixels()[pos + 2]) / 3);
                pixpack_target.getPixels()[pos + 1] = (short) ((sourceImage.getPixels()[pos + 0] + sourceImage.getPixels()[pos + 1] + sourceImage.getPixels()[pos + 2]) / 3);
                pixpack_target.getPixels()[pos + 2] = (short) ((sourceImage.getPixels()[pos + 0] + sourceImage.getPixels()[pos + 1] + sourceImage.getPixels()[pos + 2]) / 3);
                pixpack_target.getPixels()[pos + 3] = 0;
            }

        }
        return pixpack_target;
    }
        /*

        int pos, i, j;

        for (i = 0; i < sourceImage.getRows(); i++) {
            for (j = 0; j < sourceImage.getColumns()-4; j+=4) {
                pos = i * sourceImage.getColumns() + j;

//                sourceImage.getPixels()[pos].red = (pixpack_source[pos].red + pixpack_source[pos].green + pixpack_source[pos].blue) / 3;
//                pixpack_target[pos].green = (pixpack_source[pos].red + pixpack_source[pos].green + pixpack_source[pos].blue) / 3;
//                pixpack_target[pos].blue = (pixpack_source[pos].red + pixpack_source[pos].green + pixpack_source[pos].blue) / 3;
//                pixpack_target[pos].opacity	= 0;



//                pixpack_target.getPixels()[pos + 0] = (short)((sourceImage.getPixels()[pos + 0] + sourceImage.getPixels()[pos + 1] + sourceImage.getPixels()[pos + 2]) / 3);
//                pixpack_target.getPixels()[pos + 1] = (short)((sourceImage.getPixels()[pos + 0] + sourceImage.getPixels()[pos + 1] + sourceImage.getPixels()[pos + 2]) / 3);
//                pixpack_target.getPixels()[pos + 2] = (short)((sourceImage.getPixels()[pos + 0] + sourceImage.getPixels()[pos + 1] + sourceImage.getPixels()[pos + 2]) / 3);
//                pixpack_target.getPixels()[pos + 3]	= 0;

                int new_grey1 = (sourceImage.getPixels()[pos + 0] + sourceImage.getPixels()[pos + 1] + sourceImage.getPixels()[pos + 2]) / 3;

                Integer aai = new Integer(new_grey1);

                short new_grey = 0;

                // short new_grey = Short(new_grey1).;

               // short final_value = (short)min(max(new_grey1, Short.MIN_VALUE), Short.MAX_VALUE);


                if(new_grey1 < 0) {
                    new_grey = 32767;
                } else {
                    new_grey = aai.shortValue();
                }


                pixpack_target.getPixels()[pos + 0] = new_grey;
                pixpack_target.getPixels()[pos + 1] = new_grey;
                pixpack_target.getPixels()[pos + 2] = new_grey;
                pixpack_target.getPixels()[pos + 3]	= 0;

            }

        }

        System.out.println(pixpack_target.getPixels()[511]);
        System.out.println(pixpack_target.getPixels()[512]);
        System.out.println(pixpack_target.getPixels()[513]);
        System.out.println(pixpack_target.getPixels()[514]);

        pixpack_target.getPixels()[0] = 0;
        pixpack_target.getPixels()[1] = 1;
        pixpack_target.getPixels()[2] = 2;
        pixpack_target.getPixels()[3]	= 3;

      return pixpack_target;

      */


    public void ApplyInPlace(C2Image sourceImage) {

        System.out.println("---------------------- HELLO from ApplyInPlace service_grey ------------------");

        //TODO set and use search path for libraries: java.library.path
        //TODO load shared objects only once
        System.load("./SEDE.services/c2.image/src/test/resources/shared_libs/libpluginbridge.so");


        System.load(serviceFile.getAbsolutePath());

        for (File linkedFile : linkedFiles) {
            System.load(linkedFile.getAbsolutePath());
        }

        Plugin plugin = new Plugin(serviceFile, linkedFiles);

        ServiceInstancePlugin serviceInstancePlugin = new ServiceInstancePlugin(plugin);

        HashMap<String, Object> params = new HashMap<String, Object>();

        params.put("resource", 'c'); // c=cpu, g=gpu, f=fpga, s=scpu

        List<Double> parameters = new ArrayList<Double>();
        //parameters.add(1d);
        params.put("params", parameters);// TODO see void get_service_params in e.g. service_dim2.c

        List<C2Image> images = new ArrayList<C2Image>();
        images.add(sourceImage);
        params.put("images", images);

        Object result = serviceInstancePlugin.invokeOp("ApplyInPlace", params);

        C2Image image = null;

        if (result instanceof List) {

            Iterator<C2Image> iter = ((List) result).iterator();

            if(iter.hasNext()) {

                image = iter.next();
            }

        }
        System.out.println("---------------------- BYE from ApplyInPlace service_grey ------------------");

    }
}
