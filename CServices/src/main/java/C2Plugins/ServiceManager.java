package C2Plugins;

import java.io.File;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.util.List;
import java.util.ArrayList;

/**
 * Manages all the services which are deployed on the service_node.
 */
public class ServiceManager {
	/* VARIABLES */
	String mPluginLocation;
	// TODO This should be configurable by the client.
	private static String namespace = "test";
	List<File> mFiles;
	List<Plugin> mPlugins;

	/**
	 * Defines the directory where all deployed services are located and loads the
	 * plugin_bridge which is heavily used by the JNIs later on.
	 * 
	 * @param location
	 *            Directory of the plug-ins.
	 */
	public ServiceManager(String location) {
		mPluginLocation = location;

		mPlugins = new ArrayList<Plugin>();

		System.loadLibrary("pluginbridge");
	}

	public List<Service> getServices() {
		List<Service> result = new ArrayList<>();
		result.addAll(mPlugins);
		return result;
	}

	/**
	 * Connection point for the deployment module to load a service on the server.
	 * 
	 * @param data
	 *            service file as a byte array.
	 * @param filename
	 *            Name of the service that is loaded.
	 */
	public void storeFile(byte[] data, String filename) {
		try {
			BufferedOutputStream output = new BufferedOutputStream(
					new FileOutputStream(mPluginLocation + "/" + filename));
			output.write(data, 0, data.length);
			output.close();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		System.out.println(">>> recv file " + filename + " (" + (data.length >> 10) + " kiB)");
	}

	/**
	 * Loads a deployed service.
	 * 
	 * @param serviceFilename
	 *            name of the service file.
	 * @param linkedFilenames
	 *            List of associated files. Linked files might be redudant by now
	 *            because they were used for the plugins to associate the "core"
	 *            file of a plugin with its different implementations for the
	 *            various resources.
	 */
	public void loadService(String serviceFilename, List<String> linkedFilenames) {
		File serviceFile = new File(mPluginLocation + "/" + serviceFilename);
		List<File> linkedFiles = new ArrayList<File>();
		for (int i = 0; i < linkedFilenames.size(); ++i) {
			linkedFiles.add(new File(mPluginLocation + "/" + linkedFilenames.get(i)));
		}
		String extension = null;
		try {
			extension = serviceFile.getName().substring(serviceFile.getName().lastIndexOf(".") + 1);
		} catch (ArrayIndexOutOfBoundsException e) {
			System.err.println("The demanded file: \"" + serviceFilename + "\" doesn't have an extension!");
		}
		switch (extension) {
		case "so":
			mPlugins.add(new Plugin(serviceFile, linkedFiles));
			break;
		default:
			break;
		}
	}

	/**
	 * Unloads the library from the dynamically binded libraries and deletes it
	 * including its linked files if demanded.
	 * 
	 * @param plugin
	 *            Plug-in to be unloaded.
	 * @param erasePlugin
	 *            If true the plug-in and its linked files are deleted from the
	 *            drive.
	 */
	public void unloadPlugin(Plugin plugin, boolean erasePlugin) {
		for (int i = 0; i < mPlugins.size(); ++i) {
			boolean equal = true;

			if (mPlugins.get(i).getMetaInfos().getIDs().size() == plugin.getMetaInfos().getIDs().size()) {
				for (int j = 0; j < mPlugins.get(i).getMetaInfos().getIDs().size(); ++j) {
					if (mPlugins.get(i).getMetaInfos().getIDs().get(j).equals(plugin.getMetaInfos().getIDs().get(j))) {
						equal &= true;
					} else {
						equal &= false;
					}
				}

				if (equal) {
					mPlugins.get(i).printUnload();
					mPlugins.get(i).unloadLibraries();

					if (erasePlugin) {
						eraseFiles(mPlugins.get(i));
					}

					mPlugins.remove(i);

					return;
				}
			}
		}

		System.out.println(
				"!!! cannot unload service " + plugin.getServiceFile().getName() + ". are the service ids unique?\n");
	}

	/**
	 * Erases a plug-in from the drive as well as all its files that it got as input
	 * files.
	 * 
	 * @param plugin
	 *            plug-in to be deleted including its linked files.
	 */
	private void eraseFiles(Plugin plugin) {
		for (int i = 0; i < plugin.getLinkedFiles().size(); ++i) {
			plugin.getLinkedFiles().get(i).delete();
		}
		plugin.getServiceFile().delete();
	}

	/**
	 * Returns a list of all the deployed Plugins.
	 * 
	 * @return A list of all the available/ deployed Plugins.
	 */
	public List<Plugin> getPluginList() {
		return mPlugins;
	}

	/**
	 * Returns the currently configured namespace in which service instances are
	 * being created.
	 * 
	 * @return
	 */
	public static String getNameSpace() {
		return namespace;
	}
}
