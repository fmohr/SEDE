package C2Plugins;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ServicePython extends Service {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3078778476955503437L;
	private volatile Map<String, ServiceInstancePython> instances = new HashMap<>();

	public ServicePython(File serviceFile, List<File> linkedFiles, String namespace) {
		super(serviceFile, linkedFiles);
	}
	
	@Override
	public String newInstance(String name, Map<String, Object> params) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void serializeInstance(String key) {
		// TODO Auto-generated method stub

	}

	@Override
	public void startSerializedInstance(String key) {
		// TODO Auto-generated method stub

	}

	@Override
	public void killInstance(String key) {
		// TODO Auto-generated method stub

	}

	@Override
	public ServiceInstancePython getInstance(String key) {
		return instances.get(key);
	}
}
