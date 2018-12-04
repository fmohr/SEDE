package C2Services;

import C2Data.C2Image;
import C2Data.C2Params;
import C2Data.C2Resource;

import java.util.Map;
import java.util.Set;

public class C2Service_JAVA_grey extends C2Service_grey {
    private C2Resource mResource;
    public C2Service_JAVA_grey() {
        super();

        mResource = new C2Resource("java");
    }

    public void setOptions(C2Params params){

        double height = 0;
        double width = 0;

        Map<String, Double> paramMap    = params.getParams();
        Set<String> paramKeys           = paramMap.keySet();

        for (String key : paramKeys) {
            double paramValue1 = paramMap.get("height");
            height = paramValue1;
            double paramValue2 = paramMap.get("width");
            width = paramValue2;
        }

        System.out.println("Height:" + height );
        System.out.println("Width:" + width );

    }

    public C2Image processImage(C2Image sourceImage) {
        return super.processImage(mResource, sourceImage);
    }
}
