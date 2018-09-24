package Catalano.Imaging.wrappers;

import Catalano.Imaging.FastBitmap;

import java.util.List;

public class CannyEdgeDetector {
	private Catalano.Imaging.Filters.CannyEdgeDetector cannyEdgeDetector;
	public CannyEdgeDetector(int a, int b, double c, int d){
		cannyEdgeDetector = new Catalano.Imaging.Filters.CannyEdgeDetector(a, b, c, d);
	}

	public void applyInPlace(FastBitmap fb) {
		cannyEdgeDetector.applyInPlace(fb);
	}

	public void applyInPlace(List<FastBitmap> fbList) {
		for(FastBitmap fb : fbList) {
			applyInPlace(fb);
		}
	}
}
