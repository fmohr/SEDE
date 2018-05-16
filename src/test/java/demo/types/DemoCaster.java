package demo.types;


import de.upb.sede.core.SEDEObject;
import de.upb.sede.exec.SemanticStreamer;

import java.io.*;

public class DemoCaster {

	public SEDEObject cfs_Arr_NummerList(InputStream is)  {
		return SemanticStreamer.deserialize(is, NummerList.class.getName());
	}
	public void cts_NummerList_Arr(OutputStream os, SEDEObject field) {
		SemanticStreamer.serialize(os, (Serializable) field.getObject());
	}

	public SEDEObject cfs_Arr_Punkt(InputStream is) {
		return SemanticStreamer.deserialize(is, NummerList.class.getName());

	}
	public void cts_Punkt_Arr(OutputStream os, SEDEObject field) {
		SemanticStreamer.serialize(os, (Serializable) field.getObject());
	}


}
