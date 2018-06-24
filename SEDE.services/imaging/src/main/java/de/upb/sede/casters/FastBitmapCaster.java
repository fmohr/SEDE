package de.upb.sede.casters;

import Catalano.Imaging.FastBitmap;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.util.Iterator;
import java.util.List;

/**
 * Provides casting methods for: FastBitmap <-> Arr
 */
public class FastBitmapCaster {

	/**
	 * Casts from the semantic representation 'Arr' which is a list of numbers, to FastBitMap object.
	 * The data in form of semantic type 'Arr' is taken from the provided inputstream.
	 */
	public FastBitmap cfs_Arr_FastBitmap(InputStream is) throws ParseException, IOException {
		JSONParser parser = new JSONParser();
		Reader reader = new InputStreamReader(is);
		List<Number> arr = (JSONArray) parser.parse(reader);
		int width = arr.get(0).intValue();
		int type = arr.get(1).intValue();

		if (type == 0) {
			return new FastBitmap(getGrayscaleImageFromVector(arr, width));
		}
		else if (type == 1)
			return new FastBitmap(getRGBImageFromVector(arr, width));

		throw new UnsupportedOperationException("Bitmap type needs to be either 0 or 1. Was: " + type);
	}

	/**
	 * Casts a FastBitMap object to the semantic representation 'Arr' which is a list of numbers and writes the data into the provided stream.
	 */
	public void cts_FastBitmap_Arr(OutputStream os, FastBitmap fb) throws IOException {
		JSONArray arr = new JSONArray();
		int[][][] image = fb.toMatrixRGBAsInt();
		arr.add(new Double(fb.getWidth()));
		arr.add(1.0); // code for rgb images
		for (int i = 0; i < image.length; i++) {
			for (int j = 0; j < image[i].length; j++) {
				for (int k = 0; k < image[i][j].length; k++) {
					arr.add(new Double(image[i][j][k]));
				}
			}
		}
		OutputStreamWriter writer = new OutputStreamWriter(os);
		arr.writeJSONString(writer);
		writer.flush();
	}


	private static int[][][] getRGBImageFromVector(List<Number> arr, int width) {
		int height = (int) ((arr.size() - 2) * 1f / width / 3);
		int[][][] image = new int[(int) height][width][3];
		int row = 0;
		int column = 0;
		int color = 0;
		Iterator<Number> it = arr.iterator();
		it.next();
		it.next();
		while (it.hasNext()) {
			int val = it.next().intValue();
			image[row][column][color++] = val;

			/* switch to next column/row if color/col has reached maximum respectively */
			if (color == 3) {
				color = 0;
				column++;
			}
			if (column == width) {
				column = 0;
				row++;
			}
		}
		return image;
	}

	private static int[][] getGrayscaleImageFromVector(List<Number> arr, int width) {
		int[][] image = new int[(arr.size() - 2) / width][width];
		int row = 0;
		int col = 0;
		Iterator<Number> it = arr.iterator();
		it.next();
		it.next();
		while (it.hasNext()) {
			int val = it.next().intValue();
			image[row][col++] = val;

			/* switch to next row if col has reached width */
			if (col == width) {
				col = 0;
				row++;
			}
		}
		return image;
	}
}
