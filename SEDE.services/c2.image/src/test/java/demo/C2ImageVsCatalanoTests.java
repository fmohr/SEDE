package demo;

import C2Data.C2Image;
import C2Data.C2ImageManager;
import Catalano.Imaging.FastBitmap;
import de.upb.sede.util.FileUtil;
import org.junit.BeforeClass;
import org.junit.Test;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class C2ImageVsCatalanoTests {

    // Catalano image type.
    static FastBitmap frog;

    // C2 image type.
    static C2ImageManager im_c2frog;
    static C2Image c2frog;

    // TODO: there is currently a bug in C2ImageManager, which breaks all tests that have GUIs. This flag controls this.
    //   if false: we can use C2Images, but no GUI (java.swing, java.awt, etc.)
    //   if true:  we cannot use C2Images, but all results are displayed in a GUI.
    static final boolean WORKAROUND___ENABLE_C2_IMAGES = true;

    @BeforeClass
    public static void loadBefore() {
        // Load Catalano image.
        frog = new FastBitmap(FileUtil.getPathOfResource("images/red-eyed.jpg"));
        assert frog != null;

        if(WORKAROUND___ENABLE_C2_IMAGES) {
            // Load libraries.
            loadC2Libraries();

            // Load C2 image.
            ArrayList<String> file_src = new ArrayList<String>();
            ArrayList<String> file_dest = new ArrayList<String>();
            file_src.add(FileUtil.getPathOfResource("images/red-eyed-small.jpg"));
            //file_src.add(FileUtil.getPathOfResource("images/red-eyed.jpg"));
            im_c2frog = new C2ImageManager(file_src, file_dest);
            im_c2frog.environmentUp();
            im_c2frog.readImages();
            c2frog = im_c2frog.getSourceImages().get(0);
            assert c2frog != null;
        }
    }

    public static void loadC2Libraries() {
        try {
            // Load image library bridge. The bridge comes from the demonstrator in
            //   sfb901_demonstrator/service_caller
            System.load(FileUtil.getPathOfResource("shared_libs/libimagemagick.so"));

            // Load service plugin bridge. The bridge comes from the demonstrator in
            //   sfb901_demonstrator/service_node
            System.load(FileUtil.getPathOfResource("shared_libs/libpluginbridge.so"));

            // TODO: this needs a clean solution. Currently done via LD_LIBRARY_PATH.
            // Add path to *.so of the service plugins to library path.
            // System.setProperty("java.library.path", System.getProperty("java.library.path")+":/home/deffel/coding/SEDE/CServices/cbuild");
        } catch (UnsatisfiedLinkError error) {
            System.out.println(error.getMessage());
        }
    }

    @Test
    public void testConvertMagickImage() {
        // This test only works with WORKAROUND___ENABLE_C2_IMAGES enabled!
        assert WORKAROUND___ENABLE_C2_IMAGES;

        System.out.printf("Magick/JNI load \"lenna\". %dx%d \n", c2frog.getRows(), c2frog.getColumns());

        // Convert to BufferedImage.
        BufferedImage buf_lenna = im_c2frog.convertToBufferedImage(c2frog);
        System.out.printf("Converted to BufferedImage. %dx%d \n", buf_lenna.getHeight(), buf_lenna.getWidth());

        // Convert to FastBitmap.
        FastBitmap fast_lenna = new FastBitmap(buf_lenna);
        System.out.printf("Converted to FastBitmap. %dx%d \n", fast_lenna.getHeight(), fast_lenna.getWidth());
    }

    private void printImage(FastBitmap frog_fb) {
        for(int row = 0; row < frog_fb.getHeight(); row++) {
            for(int col = 0; col < frog_fb.getWidth(); col++) {
                int[] pixel = frog_fb.getARGB(row, col);

                System.out.printf("(%d, %d, %d, %d) ", pixel[0], pixel[1], pixel[2], pixel[3]);
            }
            System.out.println();
        }
        System.out.println();
        System.out.println();
    }

    private void printImage(BufferedImage frog_fb) {
        for(int row = 0; row < frog_fb.getHeight(); row++) {
            for(int col = 0; col < frog_fb.getWidth(); col++) {
                Color c = new Color(frog_fb.getRGB(row, col), true);
                System.out.printf("(%d, %d, %d, %d) ", c.getAlpha(), c.getRed(), c.getGreen(), c.getBlue());
            }
            System.out.println();
        }
        System.out.println();
        System.out.println();
    }

    /**
     * Check how is images are laid out between the two libraries.
     */
    @Test
    public void testImageDiff() {
        // This test only works with WORKAROUND___ENABLE_C2_IMAGES enabled!
        assert WORKAROUND___ENABLE_C2_IMAGES;

        // Load lenna with Catalano image.
        FastBitmap frog_catalano = new FastBitmap(FileUtil.getPathOfResource("images/red-eyed-small.jpg"));
        assert frog_catalano != null;

        System.out.printf("FastBitmap frog_catalano \n");
        printImage(frog_catalano);

        BufferedImage buf_frog_catalano = frog_catalano.toBufferedImage();
        assert buf_frog_catalano != null;

        System.out.printf("BufferedImage frog_catalano \n");
        printImage(buf_frog_catalano);


        // Convert to BufferedImage.
        BufferedImage c2frog_buf = im_c2frog.convertToBufferedImage(c2frog);

        assert c2frog_buf != null;

        System.out.printf("BufferedImage buf_lenna \n");
        printImage(c2frog_buf);

        // Compare
        if(!buf_frog_catalano.equals(c2frog_buf)) {
            System.out.printf("BufferedImage differs \n");

            int totalDiffs = 0;
            for(int row = 0; row < c2frog_buf.getHeight(); row++) {
                for(int col = 0; col < c2frog_buf.getWidth(); col++) {
                    Color c_catalano = new Color(buf_frog_catalano.getRGB(row, col), true);
                    Color c_c2 = new Color(c2frog_buf.getRGB(row, col), true);

                    if(!c_catalano.equals(c_c2)) {
                        System.out.printf("  diff at (%d, %d): (%d, %d, %d, %d) vs (%d, %d, %d, %d) \n", row, col
                                , c_catalano.getAlpha(), c_catalano.getRed(), c_catalano.getGreen(), c_catalano.getBlue()
                                , c_c2.getAlpha(), c_c2.getRed(), c_c2.getGreen(), c_c2.getBlue());
                        totalDiffs++;
                    }
                }
            }

            float diffPercent = (totalDiffs*100/(c2frog_buf.getHeight()*c2frog_buf.getWidth()));
            System.out.printf(" ==> total diffs %d or %.1f percent \n", totalDiffs, diffPercent);
        }

        // Convert to FastBitmap.
        FastBitmap fast_lenna = new FastBitmap(c2frog_buf);
        System.out.printf("Converted to FastBitmap. %dx%d \n", fast_lenna.getHeight(), fast_lenna.getWidth());
    }
}
