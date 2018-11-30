package C2Data;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class C2ParamsCaster {
    public C2Params cfs_C2Params(InputStream is) throws Exception {
        JSONParser parser = new JSONParser();
        Reader reader = new InputStreamReader(is);
        JSONObject jsonobject = (JSONObject) parser.parse(reader);
        List<Double> jsonarray = (JSONArray) jsonobject.get("paramValues");
        List<Double> paramValues = new ArrayList<Double>(jsonarray.size());
        for (int i = 0; i < jsonarray.size(); i++) {
            paramValues.add(jsonarray.get(i).doubleValue());
        }

        C2Params params = new C2Params(paramValues);
        return params;
    }

    public void cts_C2Params(OutputStream os, C2Params paramValue) throws IOException {
        JSONObject entry = new JSONObject();
        JSONArray paramValues = new JSONArray();
        for (int i = 0; i < paramValue.getSize(); i++) {
            double p = paramValue.getParamValue(i);
            paramValues.add(p);
        }
        entry.put("paramValues", paramValues);
        OutputStreamWriter writer = new OutputStreamWriter(os);
        entry.writeJSONString(writer);
        writer.flush();
    }
}
