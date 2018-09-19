package C2Services;

import C2Data.C2Image;
import C2Plugins.Plugin;
import C2Plugins.ServiceInstancePlugin;

import java.io.File;
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
        serviceFile = new File("service_grey/service_grey.so");
        linkedFiles = new ArrayList<File>();
        linkedFiles.add(new File("service_grey/service_grey_cpu.so"));
    }

    public C2service_grey() {
        super(serviceFile, linkedFiles);
    }


    public List<C2Image> process(List<C2Image> sourceImages) {

        System.out.println("---------------------- HELLO from service_grey ------------------");

        //TODO set and use search path for libraries: java.library.path
        //TODO load shared objects only once
        // System.load("/sede/codebase/ServiceCodeProvider/c2imaging/service_node/bin/libpluginbridge.so");

        File serviceFile = new File("/home/deffel/coding/sfb901_demonstrator/service_plugins/build/libservice_grey.so");

        ArrayList<File> linkedFiles = new ArrayList<File>();
        linkedFiles.add(new File("/home/deffel/coding/sfb901_demonstrator/service_plugins/build/libservice_grey_cpu.so"));

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

        params.put("images", sourceImages);

        Object result = serviceInstancePlugin.invokeOp("process", params);

        List<C2Image> results = new ArrayList<C2Image>();

        if (result instanceof List) {

            for (Object o : (List) result) {

                if (o instanceof C2Image) {

                    results.add((C2Image) o);
                }
            }

        }
        return results;

    }

}






