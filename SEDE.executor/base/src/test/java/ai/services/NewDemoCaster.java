package ai.services;


import demo.types.NummerList;
import demo.types.Punkt;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;

public class NewDemoCaster {

    public static NummerList cfs_NummerList(InputStream is) throws ParseException, IOException {
        JSONParser parser = new JSONParser();
        Reader jsonReader = new InputStreamReader(is);
        JSONArray jsonArray = (JSONArray) parser.parse(jsonReader);
        NummerList nummerList = new NummerList(jsonArray);
        return nummerList;
    }

    public static void cts_NummerList(OutputStream os, NummerList nl) throws IOException {
        JSONArray jsonArray = new JSONArray();
        jsonArray.addAll(nl);
        OutputStreamWriter writer = new OutputStreamWriter(os);
        jsonArray.writeJSONString(writer);
        writer.flush();
    }

    public static byte[] cts_NummerList(NummerList nl) throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        cts_NummerList(outputStream, nl);
        return outputStream.toByteArray();
    }

    public static Punkt cfs_Punkt(InputStream is) throws ParseException, IOException {
        JSONParser parser = new JSONParser();
        Reader jsonReader = new InputStreamReader(is);
        JSONArray jsonArray = (JSONArray) parser.parse(jsonReader);
        if(jsonArray.size() < 2) {
            throw new RuntimeException("Punkt needs at least 2 list entries.");
        }
        double x = ((Number)jsonArray.get(0)).doubleValue();
        double y = ((Number)jsonArray.get(1)).doubleValue();
        Punkt punkt = new Punkt(x, y);
        return punkt;
    }
    public static void cts_Punkt(OutputStream os, Punkt p) throws IOException {
        JSONArray jsonArray = new JSONArray();
        jsonArray.add(p.x);
        jsonArray.add(p.y);
        OutputStreamWriter writer = new OutputStreamWriter(os);
        jsonArray.writeJSONString(writer);
        writer.flush();
    }
    public static byte[] cts_Punkt(Punkt p) throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        cts_Punkt(outputStream, p);
        return outputStream.toByteArray();
    }


}
