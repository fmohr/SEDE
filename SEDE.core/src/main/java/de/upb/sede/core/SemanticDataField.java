package de.upb.sede.core;

import de.upb.sede.util.Streams;
import org.json.simple.JSONObject;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

/**
 * A SEDEObject that holds semantic data.
 * Semantic data consists of unstructured data which first needs to be casted to real data types.
 * Semantic data can be casted to other types of SEDEObject using the SemanticStreamer class.
 */
public class SemanticDataField extends SEDEObject{

	private InputStream semanticData;

	private boolean persistent;

	/**
	 * Creates a semantic data field. This is the default and only constructor offered.
	 * If the data is only available as byte[] wrap it inside a {@link ByteArrayInputStream}.
	 * @param type type of the semantic data. (Meta information about the structure of the bytes in the input stream)
	 * @param inputStream input stream of bytes which delivers the semantic data.
	 * @param persistent flag that indicates that the input stream is bounded to a resource that is not persistent.
	 *                   persistent input streams are usually cached in memory.
	 *                   non-persistent input streams are those whose needs to be processed immediately or else the data may become unavailable, e.g. input streams of the body an http request.
	 *                   Use non-persistent if you want to free up the resources bounded to the input stream.
	 */
	public SemanticDataField(String type, InputStream inputStream, boolean persistent) {
		super(type);
		this.semanticData = inputStream;
		this.persistent = persistent;
	}


	@Override
	public InputStream getDataField() {
		return semanticData;
	}

	/**
	 *
	 * @return True if the input stream is persistent and can be left untouched for a while.
	 */
	public boolean isPersistent() {
		return this.persistent;
	}

	public boolean isSemantic() {
		return true;
	}

	@Override
	public JSONObject toJson() {
		JSONObject jsonObject = super.toJson();
		byte[] byteArr = (byte[]) Streams.InReadByteArr(getDataField());
		String stringData = new String(byteArr);
		jsonObject.put("data", stringData);
		return jsonObject;
	}

	@Override
	public void fromJson(Map<String, Object> jsonData) {
		super.fromJson(jsonData);
		Object data = jsonData.get("data");
		String stringData = (String) data;
		this.semanticData = new ByteArrayInputStream(stringData.getBytes());
		this.persistent = true;
	}

	/**
	 * @return String representation of this Sede object.
	 */
	public String toString() {
		return super.toString() + getDataField().getClass().getSimpleName() + (isPersistent()? " persistent" : " non-persistent");
	}

}
