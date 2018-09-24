package Catalano.Imaging.wrappers;

import Catalano.Imaging.FastBitmap;

import java.util.List;

public class Resize {
	private Catalano.Imaging.Filters.Resize resizer;
	public Resize(int a, int b, String algorithm){
		resizer = new Catalano.Imaging.Filters.Resize(a, b,
				Catalano.Imaging.Filters.Resize.Algorithm.valueOf(algorithm));
	}

	public void applyInPlace(FastBitmap fb) {
		resizer.applyInPlace(fb);
	}

	public void applyToList(List<FastBitmap> fbList) {
		for(FastBitmap fb : fbList) {
			applyInPlace(fb);
		}
	}
}
