package C2Data;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.*;

public class C2ParamsCaster {
    public C2Params cfs_C2Params(InputStream is) throws Exception {
        JSONParser parser = new JSONParser();
        Reader reader = new InputStreamReader(is);
        JSONObject jsonobject = (JSONObject) parser.parse(reader);

        double paramValue= (double)jsonobject.get("ParamValue");

        C2Params params = new C2Params(paramValue);

        return params;
    }

    public void cts_C2Params(OutputStream os, C2Params paramValue) throws IOException {
        JSONObject entry = new JSONObject();
        entry.put("ParamValue", paramValue.getmParam());

        OutputStreamWriter writer = new OutputStreamWriter(os);
        entry.writeJSONString(writer);
        writer.flush();
    }
}
