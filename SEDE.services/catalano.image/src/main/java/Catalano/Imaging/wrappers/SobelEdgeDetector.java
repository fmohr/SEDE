package Catalano.Imaging.wrappers;

import Catalano.Imaging.FastBitmap;

import java.util.List;

public class SobelEdgeDetector {
	private Catalano.Imaging.Filters.SobelEdgeDetector sobelEdgeDetector;
	public SobelEdgeDetector(){
		sobelEdgeDetector = new Catalano.Imaging.Filters.SobelEdgeDetector();
	}

	public void applyInPlace(FastBitmap fb) {
		sobelEdgeDetector.applyInPlace(fb);
	}

	public void applyInPlace(List<FastBitmap> fbList) {
		for(FastBitmap fb : fbList) {
			applyInPlace(fb);
		}
	}
}
