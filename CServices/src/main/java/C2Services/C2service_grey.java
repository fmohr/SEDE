package C2Services;

import C2Data.C2Image;
import C2Plugins.Plugin;
import C2Plugins.ServiceInstancePlugin;

import java.io.File;
import java.util.Iterator;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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
        serviceFile = new File("/home/aloesch/projects/SEDE/CServices/cbuild/libservice_grey.so");
        linkedFiles = new ArrayList<File>();
        linkedFiles.add(new File("/home/aloesch/projects/SEDE/CServices/cbuild/libservice_grey_cpu.so"));
    }

    public C2service_grey() {
        super(serviceFile, linkedFiles);
    }

    public List<C2Image> process(List<C2Image> sourceImages) {
        System.out.println("sg::processList");
        List<C2Image> result = new ArrayList<C2Image>();
        for(C2Image sourceImage: sourceImages) {

            result.add(this.process(sourceImage));

        }

        return result;
    }

    public C2Image process(C2Image sourceImage) {
        System.out.println("sg::process");
        //TODO set and use search path for libraries: java.library.path
        //TODO load shared objects only once
        System.load("/sede/codebase/ServiceCodeProvider/c2imaging/service_node/bin/libpluginbridge.so");


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

        params.put("images", new ArrayList<C2Image>().add(sourceImage));

        Object result = serviceInstancePlugin.invokeOp("process", params);

        C2Image image = null;

        if (result instanceof List) {

            Iterator<C2Image> iter = ((List) result).iterator();

            if(iter.hasNext()) {

                image = iter.next();
            }

        }
        return image;

    }
}
