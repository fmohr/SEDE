package Catalano.Imaging.wrappers;

import Catalano.Imaging.FastBitmap;

import java.util.List;

public class Crop {
	private Catalano.Imaging.Filters.Crop cropper;
	public Crop(int a, int b, int c, int d){
		cropper = new Catalano.Imaging.Filters.Crop(a, b, c, d);
	}

	public void applyInPlace(FastBitmap fb) {
		cropper.ApplyInPlace(fb);
	}

	public void applyToList(List<FastBitmap> fbList) {
		for(FastBitmap fb : fbList) {
			applyInPlace(fb);
		}
	}
}
