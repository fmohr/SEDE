package de.upb.sede.casters;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import C2Data.C2Image;

public class C2ImageCaster {

    public List<C2Image> cfs_C2ImageList(InputStream is) throws Exception {

        ArrayList<C2Image> result = new ArrayList<C2Image>();

        JSONParser parser = new JSONParser();
        Reader reader = new InputStreamReader(is);
        JSONArray arr = (JSONArray) parser.parse(reader);


        for (Iterator iter=arr.iterator();iter.hasNext();) {

            Object next = iter.next();
            if(next instanceof JSONObject) {

                JSONObject jsonobject = (JSONObject) next;

                int rows = ((Number) jsonobject.get("rows")).intValue();
                int columns = ((Number) jsonobject.get("columns")).intValue();
                List<Number> numberList = (List<Number>) jsonobject.get("pixels");
                short[] pixels = new short[numberList.size()];
                for (int i = 0; i < pixels.length; i++) {
                    pixels[i] = numberList.get(i).shortValue();
                }
                C2Image image = new C2Image(pixels, rows, columns);

                result.add(image);
            }
        }

        return result;
    }

    public void cts_C2ImageList(OutputStream os, List<C2Image> images) throws IOException {
        JSONArray result = new JSONArray();

        int index =0;
        for(C2Image image:images) {

            JSONObject entry = new JSONObject();
            entry.put("rows", image.getRows());
            entry.put("columns", image.getColumns());
            entry.put("pixel", image.getPixels());

            result.add(index++, entry);
        }

        OutputStreamWriter writer = new OutputStreamWriter(os);
        result.writeJSONString(writer);
        writer.flush();

    }

}
