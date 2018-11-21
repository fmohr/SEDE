package C2Plugins;

import java.io.File;
import java.util.List;
import java.util.Map;

public abstract class Service {

	/**
	 * 
	 */
	//private static final long serialVersionUID = -6016176195313769529L;

	File serviceFile;
	List<File> linkedFiles;
	ServiceMetaInformation metaInfos = null;

	public Service(File serviceFile, List<File> linkedFiles) {
		this.serviceFile = serviceFile;
		this.linkedFiles = linkedFiles;
	}

	public void setMetaInfos(ServiceMetaInformation metaInfos) {
		this.metaInfos = metaInfos;
	}

	public ServiceMetaInformation getMetaInfosCopy() {
		return metaInfos.deepCopy();
	}

	public ServiceMetaInformation getMetaInfos() {
		return metaInfos;
	}
}
