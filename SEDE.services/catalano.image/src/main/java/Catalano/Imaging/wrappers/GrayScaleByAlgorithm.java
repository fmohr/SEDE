package Catalano.Imaging.wrappers;

import Catalano.Imaging.FastBitmap;
import Catalano.Imaging.Filters.Grayscale;

import java.util.List;

public class GrayScaleByAlgorithm {
	private Catalano.Imaging.Filters.Grayscale grayscale;

	public GrayScaleByAlgorithm(String algorithm) {
		grayscale = new Catalano.Imaging.Filters.Grayscale(Grayscale.Algorithm.valueOf(algorithm));
	}

	public void applyInPlace(FastBitmap fb) {
		grayscale.applyInPlace(fb);
	}

	public void applyToList(List<FastBitmap> fbList) {
		for(FastBitmap fb : fbList) {
			applyInPlace(fb);
		}
	}
}
