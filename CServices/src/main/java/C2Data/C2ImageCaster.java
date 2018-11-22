package C2Data;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import C2Data.C2Image;

public class C2ImageCaster {
	public C2Image cfs_C2Image(InputStream is) throws Exception {
		JSONParser parser = new JSONParser();
		Reader reader = new InputStreamReader(is);
		JSONObject jsonobject = (JSONObject) parser.parse(reader);

		int rows = ((Number) jsonobject.get("rows")).intValue();
		int columns = ((Number) jsonobject.get("columns")).intValue();
		List<Number> jsonarray = (JSONArray) jsonobject.get("pixels");
		short[] pixels = new short[jsonarray.size()];
		for (int i = 0; i < jsonarray.size(); i++) {
			pixels[i] = jsonarray.get(i).shortValue();
		}

		C2Image image = new C2Image(pixels, rows, columns);

		return image;
	}

	public void cts_C2Image(OutputStream os, C2Image image) throws IOException {
		JSONObject entry = new JSONObject();
		entry.put("rows", image.getRows());
		entry.put("columns", image.getColumns());

		JSONArray pixels = new JSONArray();
		for (short p:image.getPixels()) {
			pixels.add(p);
		}
		entry.put("pixels", pixels);

		OutputStreamWriter writer = new OutputStreamWriter(os);
		entry.writeJSONString(writer);
		writer.flush();
	}
}
