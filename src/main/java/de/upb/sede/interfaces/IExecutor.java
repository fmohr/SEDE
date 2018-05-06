package de.upb.sede.interfaces;

import de.upb.sede.requests.DataPutRequest;
import de.upb.sede.requests.ExecRequest;
import de.upb.sede.requests.Request;

public interface IExecutor {
	public void put(DataPutRequest dataPutRequest);

	public void exec(ExecRequest execRequest);

	public void loadServices(Object Services);
}