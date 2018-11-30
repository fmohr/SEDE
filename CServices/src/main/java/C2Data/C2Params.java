package C2Data;

import java.util.ArrayList;
import java.util.List;

public class C2Params {

    private List<Double> mParams = new ArrayList<Double>();

    public C2Params(List<Double> paramValues) { mParams = paramValues; }

    public List<Double> getmParams() { return mParams; }

    public double getParamValue(int i){return mParams.get(i);}

    public int getSize(){return mParams.size();}

}