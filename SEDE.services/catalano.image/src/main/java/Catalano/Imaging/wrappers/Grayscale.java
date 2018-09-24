package Catalano.Imaging.wrappers;


import Catalano.Imaging.FastBitmap;

import java.util.List;

public class Grayscale {
	private Catalano.Imaging.Filters.Grayscale grayscale;

	public Grayscale(double r, double g, double b) {
		grayscale = new Catalano.Imaging.Filters.Grayscale(r, g, b);
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
