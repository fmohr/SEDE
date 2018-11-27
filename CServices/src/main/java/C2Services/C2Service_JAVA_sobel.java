package C2Services;

import C2Data.C2Image;
import C2Data.C2Resource;

public class C2Service_JAVA_sobel extends C2Service_grey {
    private C2Resource mResource;
    public C2Service_JAVA_sobel() {
        super();

        mResource = new C2Resource("java");
    }

    public C2Image processImage(C2Image sourceImage) {
        return super.processImage(mResource, sourceImage);
    }
}
