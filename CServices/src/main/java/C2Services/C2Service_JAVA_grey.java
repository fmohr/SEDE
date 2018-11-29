package C2Services;

import C2Data.C2Image;
import C2Data.C2Params;
import C2Data.C2Resource;

public class C2Service_JAVA_grey extends C2Service_grey {
    private C2Resource mResource;
    public C2Service_JAVA_grey() {
        super();

        mResource = new C2Resource("java");
    }

    public C2Image processImage(C2Image sourceImage, C2Params paramValue) {
        return super.processImage(mResource, sourceImage, paramValue);
    }
}
