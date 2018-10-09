package C2Services;

import C2Data.C2Image;
import C2Plugins.Plugin;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.lang.Math;

public class C2service_sobel extends Plugin {


    private static final long serialVersionUID = -7471727695546943682L;

    private static File serviceFile;
    private static List<File> linkedFiles;

    static {
        serviceFile = new File("/home/lnabanita/SEDE/CServices/csrc/service_plugins/build/libservice_sobel.so");
        linkedFiles = new ArrayList<File>();
        linkedFiles.add(new File("/home/lnabanita/SEDE/CServices/csrc/service_plugins/build/libservice_sobel_cpu.so"));
    }

    public C2service_sobel() {
        super(serviceFile, linkedFiles);
    }


    public C2Image process(C2Image sourceImage) {

        System.out.println("---------------------- SOBEL ------------------");

        C2Image pixpack_target = new C2Image(sourceImage.getPixels(), sourceImage.getRows(), sourceImage.getColumns());


        int temp_red_x = 0, temp_red_y = 0;

        int rows = sourceImage.getRows();
        int columns = sourceImage.getColumns();

        int i, j, pos = 0;

        for (i = 0; i < rows; ++i) {
            for (j = 0; j < columns; j += 4) {
                pos = i * columns + j;
                sourceImage.getPixels()[pos + i] = (short) ((sourceImage.getPixels()[pos + 0] + sourceImage.getPixels()[pos + 1] + sourceImage.getPixels()[pos + 2]) / 3);
            }
        }

        pos = 0;

        for (i = 1; i < rows - 1; ++i) {
            for (j = 1; j < columns - 1; j += 4) {
                pos = i * columns + j;
                temp_red_x = -1 * sourceImage.getPixels()[pos + 0]
                        + -2 * sourceImage.getPixels()[pos + 1]
                        + -1 * sourceImage.getPixels()[pos + 2]
                        + sourceImage.getPixels()[pos + 0]
                        + 2 * sourceImage.getPixels()[pos + 1]
                        + sourceImage.getPixels()[pos + 2];

                temp_red_y = -1 * sourceImage.getPixels()[pos + 0]
                        + sourceImage.getPixels()[pos + 1]
                        + -2 * sourceImage.getPixels()[pos + 2]
                        + 2 * sourceImage.getPixels()[pos + 0]
                        + -1 * sourceImage.getPixels()[pos + 1]
                        + sourceImage.getPixels()[pos + 2];

                System.out.println(temp_red_x);
                System.out.println(temp_red_y);


                if(true)
                    pixpack_target.getPixels()[pos+0] = (short)((Math.abs(temp_red_x) > Math.abs(temp_red_y)) ? Math.abs(temp_red_x) : Math.abs(temp_red_y));
                else
                    // don't forget to include <math.h> for sqrt() support
                    pixpack_target.getPixels()[pos+0]	= (short)(Math.sqrt(temp_red_x*temp_red_x + temp_red_y*temp_red_y));

                pixpack_target.getPixels()[pos+1]	= pixpack_target.getPixels()[pos+0];
                pixpack_target.getPixels()[pos+2]	= pixpack_target.getPixels()[pos+0];
                pixpack_target.getPixels()[pos+3]	= 0;
            }
        }
        return pixpack_target;
    }
}
