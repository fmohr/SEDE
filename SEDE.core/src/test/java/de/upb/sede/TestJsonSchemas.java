package de.upb.sede;

import de.upb.sede.core.SEDEObject;
import de.upb.sede.requests.DataPutRequest;
import de.upb.sede.requests.ExecRequest;
import de.upb.sede.requests.ExecutorRegistration;
import de.upb.sede.requests.RunRequest;
import de.upb.sede.requests.resolve.GatewayResolution;
import de.upb.sede.requests.resolve.InputFields;
import de.upb.sede.requests.resolve.ResolvePolicy;
import de.upb.sede.requests.resolve.ResolveRequest;
import de.upb.sede.util.FileUtil;
import de.upb.sede.util.JsonSerializable;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.junit.Test;

import java.io.File;

public class TestJsonSchemas {

	/**
	 * Retrieves the string content of the schema-file for the specified request class.
	 */
	private String getSchemaForRequest(Class<? extends JsonSerializable> requestClass) {
		//Get file from resources folderL
		String pathToSchema = "json-schema/requests/" + requestClass.getSimpleName() + ".schema.json";
		ClassLoader classLoader = getClass().getClassLoader();
		File resourceFile;
		try{
			resourceFile = new File(classLoader.getResource(pathToSchema).getFile());
		} catch(NullPointerException  ex) {
			throw new RuntimeException("Resource not found: " + pathToSchema);
		}
		return FileUtil.readFileAsString(resourceFile.getPath());
	}

	@Test
	public void test_DataPutRequest() {
		DataPutRequest request = new DataPutRequest("exec-id-1", "field-1", new SEDEObject(SEDEObject.PrimitiveType.Number, 10));
		System.out.println(request.toJson());
		String schema = getSchemaForRequest(DataPutRequest.class);
		// TODO verify against json schema
	}
	@Test
	public void test_ExecRequest() {
		ExecRequest request = new ExecRequest("exec-id-1", "Json String of the graph");
		System.out.println(request.toJson());
		String schema = getSchemaForRequest(ExecRequest.class);
		// TODO verify against json schema
	}
	@Test
	public void test_ExecutorRegistration() {
		JSONObject contactInfo = new JSONObject();
		JSONArray cap = new JSONArray();
		cap.add("JAVA");
		cap.add("GPU");
		JSONArray supportedServices = new JSONArray();
		supportedServices.add("com.example.SomeService");
		supportedServices.add("com.example.AnotherService");
		ExecutorRegistration request = new ExecutorRegistration(contactInfo, cap, supportedServices);
		System.out.println(request.toJson());
		String schema = getSchemaForRequest(ExecutorRegistration.class);
		// TODO verify against json schema
	}
	@Test
	public void test_RunRequest() {
		JSONObject inputs = new JSONObject();
		RunRequest request = new RunRequest("exec-id-0", "a = some.Service::__construct();b = a::someMethod({1})", new ResolvePolicy(), inputs);
		System.out.println(request.toJson());
		String schema = getSchemaForRequest(RunRequest.class);
		// TODO verify against json schema
	}
	@Test
	public void test_ResolveRequest() {
		InputFields fields = new InputFields();
		fields.toJson();
		JSONObject contactInfo = new JSONObject();
		JSONArray cap = new JSONArray();
		cap.add("JAVA");
		cap.add("GPU");
		JSONArray supportedServices = new JSONArray();
		supportedServices.add("com.example.SomeService");
		supportedServices.add("com.example.AnotherService");
		ExecutorRegistration registration = new ExecutorRegistration(contactInfo, cap, supportedServices);
		ResolveRequest request = new ResolveRequest("exec-id-0", "a = some.Service::__construct();b = a::someMethod({1})", new ResolvePolicy(), fields, registration);
		System.out.println(request.toJson());
		String schema = getSchemaForRequest(ResolveRequest.class);
		// TODO verify against json schema
	}
	@Test
	public void test_ResolvePolicy() {
		ResolvePolicy resolvePolicy = new ResolvePolicy();
		System.out.println(resolvePolicy.toJson());
		String schema = getSchemaForRequest(ResolvePolicy.class);
		// TODO verify against json schema
	}
	@Test
	public void test_GatewayResolution() {
		JSONArray returnField = new JSONArray();
		returnField.add("field-1");
		returnField.add("field-2");
		GatewayResolution resolution = new GatewayResolution("Json String of composition graph", returnField);
		resolution.setDotSvg("Dot visualisation of the composition graph in SVG format.");
		System.out.println(resolution.toJson());
		String schema = getSchemaForRequest(GatewayResolution.class);
		// TODO verify against json schema
	}
}
