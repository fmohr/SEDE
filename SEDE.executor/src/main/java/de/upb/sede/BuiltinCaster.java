package de.upb.sede;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BuiltinCaster {

	public List cfs_List(InputStream is) throws ParseException, IOException {
		JSONParser parser = new JSONParser();
		Reader jsonReader = new InputStreamReader(is);
		JSONArray jsonArray = (JSONArray) parser.parse(jsonReader);
		List list = new ArrayList(jsonArray);
		return list;
	}

	public void cts_List(OutputStream os, List list) throws IOException {
		JSONArray jsonArray = new JSONArray();
		jsonArray.addAll(list);
		OutputStreamWriter writer = new OutputStreamWriter(os);
		jsonArray.writeJSONString(writer);
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
		JSONObject jsonObject = new JSONObject();
		jsonObject.putAll(map);
		OutputStreamWriter writer = new OutputStreamWriter(os);
		jsonObject.writeJSONString(writer);
		writer.flush();
	}
}
