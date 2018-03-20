
package de.upb.sede.requests;

import java.util.Objects;
import java.util.Set;
import java.util.TreeSet;

/**
 * Immutable
 */
public class ExecRequest extends Request {

    private final ExecEnvironment environment;

    private final static String Undefined_RequestId = "NO_RID";
    private final String requestId;

    private final static String Undefined_ClientId = "NO_CID";
    private final String clientId;

    private final Set<String> clientHostSet;

    public ExecRequest(ExecEnvironment environment) {
        this.environment = Objects.requireNonNull(environment);
        this.requestId = Undefined_RequestId;
        this.clientId = Undefined_ClientId;
        this.clientHostSet = new TreeSet<>();
    }

    private ExecRequest(ExecEnvironment environment, String requestId, String clientId, Set<String> clientHostSet){
        this.environment = Objects.requireNonNull(environment);
        this.requestId = requestId;
        this.clientId = clientId;
        this.clientHostSet = clientHostSet;
    }

    /*
        With methods
    */

    public ExecRequest withExecEnvironment(ExecEnvironment environment) {
        return new ExecRequest(Objects.requireNonNull(environment), 
            requestId, clientId, clientHostSet);
    }

    public ExecRequest withRequestId(String requestId) {
        return new ExecRequest(environment, 
            Objects.requireNonNull(requestId),
            clientId, clientHostSet);
    }

    public ExecRequest withClientId(String clientId) {
        return new ExecRequest(environment, requestId, 
            Objects.requireNonNull(clientId), 
            clientHostSet);
    }

    public ExecRequest withClientHostSet(String[] clientHostSet){
        Objects.requireNonNull(clientHostSet);

        // create a new set and fill it with the given hosts
        TreeSet<String> newSet = new TreeSet<>();
        for(String host : clientHostSet){
            newSet.add(host);
        }
        return new ExecRequest(environment, 
            requestId, clientId, 
            newSet);
    }

    public ExecRequest withAddedClientHost(String clientHost) {
        // add host to the present set
        Set<String> newSet = new TreeSet<>(this.clientHostSet);
        newSet.add(Objects.requireNonNull(clientHost));
        
        return new ExecRequest(environment, 
            requestId, clientId, 
            newSet);
    }

    /*
        has methods
    */

    public boolean hasExecEnvironment(){
        return true; // is always set.
    }

    public boolean hasRequestId(){
        return this.requestId != Undefined_RequestId;
    }

    public boolean hasClientId(){
        return this.clientId != Undefined_ClientId;
    }

    public boolean containsClinetHosts(){
        return ! this.clientHostSet.isEmpty();
    }

    /*
        get methods
    */

    public ExecEnvironment getExecEnvironment() {
        assert hasExecEnvironment();
        return environment;
    }

	@Override
	public String getRequestId() {
        assert hasRequestId();
		return requestId;
	}

	@Override
	public String getClientId() {
        assert hasClientId();
		return clientId;
	}

	@Override
	public String[] getClientHostSet() {
        assert containsClinetHosts(); // TODO this assertion might be superfluous and cause bugs.
		return clientHostSet.toArray(new String[clientHostSet.size()]);
	}
}