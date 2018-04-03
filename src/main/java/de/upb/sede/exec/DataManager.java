package de.upb.sede.exec;

public class DataManager {
	DataFileManager fileManager;
	String requestID;

	public DataManager(String requestID) {
		this.requestID = requestID;
	}
}
