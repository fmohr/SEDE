package ai.services;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.util.*;

@SuppressWarnings({"unchecked", "rawtypes"})
public class BuiltinCaster {

    public List cfs_List(InputStream is) throws ParseException, IOException {
        JSONParser parser = new JSONParser();
        Reader jsonReader = new InputStreamReader(is);
        JSONArray jsonArray = (JSONArray) parser.parse(jsonReader);
        List list = new ArrayList(jsonArray);
        return list;
    }

    public void cts_List(OutputStream os, List list) throws IOException {
        Objects.requireNonNull(list);
        OutputStreamWriter writer = new OutputStreamWriter(os);
        JSONArray.writeJSONString(list, writer);
        writer.flush();
    }

    public Map cfs_Dict(InputStream is) throws ParseException, IOException {
        JSONParser parser = new JSONParser();
        Reader jsonReader = new InputStreamReader(is);
        JSONObject jsonObject = (JSONObject) parser.parse(jsonReader);
        Map map = new HashMap(jsonObject);
        return map;
    }

    public void cts_Dict(OutputStream os, Map map) throws IOException {
        Objects.requireNonNull(map);
        OutputStreamWriter writer = new OutputStreamWriter(os);
        JSONObject.writeJSONString(map, writer);
        writer.flush();
    }
}

