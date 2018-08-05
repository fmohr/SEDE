package Catalano.Imaging.Filters;

public class GrayScaleFactory {

	public static Grayscale withMethodname(String methodname) {
		return new Grayscale(Grayscale.Algorithm.valueOf(methodname));
	}


	public static Grayscale withRGB(double r, double g, double b) {
		return new Grayscale(r, g, b);
	}

}
