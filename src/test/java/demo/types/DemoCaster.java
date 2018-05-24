package demo.types;


import de.upb.sede.core.SEDEObject;
import de.upb.sede.util.Streams;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;

public class DemoCaster {

	public SEDEObject cfs_Arr_NummerList(InputStream is) throws ParseException {
		JSONParser parser = new JSONParser();
		String content = Streams.InReadString(is);
		JSONArray jsonArray = (JSONArray) parser.parse(content);
		NummerList nummerList = new NummerList(jsonArray);
		SEDEObject obj = new SEDEObject(NummerList.class.getName(), nummerList);
		return obj;
	}

	public void cts_NummerList_Arr(OutputStream os, SEDEObject field) throws IOException {
		JSONArray jsonArray = new JSONArray();
		jsonArray.addAll((NummerList)field.getObject());
		OutputStreamWriter writer = new OutputStreamWriter(os);
		jsonArray.writeJSONString(writer);
		writer.flush();
	}

	public SEDEObject cfs_Arr_Punkt(InputStream is) throws ParseException {
		JSONParser parser = new JSONParser();
		String content = Streams.InReadString(is);
		JSONArray jsonArray = (JSONArray) parser.parse(content);
		if(jsonArray.size() < 2) {
			throw new RuntimeException("Punkt needs at least 2 list entries.");
		}
		double x = ((Number)jsonArray.get(0)).doubleValue();
		double y = ((Number)jsonArray.get(1)).doubleValue();
		Punkt punkt = new Punkt(x, y);
		SEDEObject sedeObject = new SEDEObject(Punkt.class.getName(), punkt);
		return sedeObject;
	}
	public void cts_Punkt_Arr(OutputStream os, SEDEObject field) throws IOException {
		JSONArray jsonArray = new JSONArray();
		jsonArray.add(((Punkt)field.getObject()).x);
		jsonArray.add(((Punkt)field.getObject()).y);
		OutputStreamWriter writer = new OutputStreamWriter(os);
		jsonArray.writeJSONString(writer);
		writer.flush();
	}


}
