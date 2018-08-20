package de.upb.sede.casters;

import Catalano.Imaging.FastBitmap;
import Catalano.Imaging.Tools.ImageHistogram;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ImageHistogramCaster {
	/**
	 * Casts from the semantic representation 'Arr' which is a list of numbers, to ImageHistogram object.
	 * The data in form of semantic type 'Arr' is taken from the provided inputstream.
	 */
	public ImageHistogram cfs_ImageHistogram(InputStream is) throws ParseException, IOException {
		JSONParser parser = new JSONParser();
		Reader reader = new InputStreamReader(is);
		List<Number> arr = (JSONArray) parser.parse(reader);
		int[] values = new int[arr.size()];
		for (int i = 0; i < values.length; i++) {
			values[i] = arr.get(i).intValue();
		}
		return new ImageHistogram(values);
	}

	/**
	 * Casts an ImageHistogram object to the semantic representation 'Arr' which is a list of numbers and writes the data into the provided stream.
	 */
	public void cts_ImageHistogram(OutputStream os, ImageHistogram ih) throws IOException {
		/*
		 * copy values to a list:
		 */
		int[] values = ih.getValues();
		List<Integer>arr =new ArrayList<>(values.length);
		for (int i = 0; i < values.length; i++) {
			arr.add(values[i]);
		}

		/*
		 * write list to out:
		 */
		OutputStreamWriter writer = new OutputStreamWriter(os);
		JSONArray.writeJSONString(arr, writer);
		writer.flush();
	}
}
