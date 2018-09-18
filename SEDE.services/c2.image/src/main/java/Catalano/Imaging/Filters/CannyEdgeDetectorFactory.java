package Catalano.Imaging.Filters;

public class CannyEdgeDetectorFactory {
	public static CannyEdgeDetector withDefaults() {
		return new CannyEdgeDetector();
	}

	public static CannyEdgeDetector withThresholds(int low, int high) {
		return new CannyEdgeDetector(low, high);
	}

	public static CannyEdgeDetector withThresholds_Sigma(int low, int high, double sigma) {
		return new CannyEdgeDetector(low, high, sigma);
	}

	public static CannyEdgeDetector withThresholds_Sigma_Size(int low, int high, double sigma, int size) {
		return new CannyEdgeDetector(low, high, sigma, size);
	}
}
