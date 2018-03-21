package de.upb.sede.requests;

import java.util.HashMap;
import java.util.Map;

/**
 * All instances of request should be immutable.
 */
public class Request {
    

    private final static String UNDEFINED_REQUESTID = "NO_RID";
    private final String requestId;

    private final static String UNDEFINED_CLIENTHOST = "NO_CLIENTHOST";
    private final String clientHost;

    private final static String UNDEFINED_COMPOSITION = "NO_COMPOSITION";
    private final String composition;

    private final static String UNDEFINED_COMPOSITIONGRAPH = "NO_COMPOSITIONGRAPH";
    private final String compositionGraph;
    
    private final static Map<String,Object> UNDEFINED_VARIABLES = new HashMap<>();
    private final Map<String, Object> variables;

   

    public String getComposition(){
        return composition;
    }
    
    public Map<String, Object> getVariables(){
        return variables;
    }

    public Request() {
        this.requestId = UNDEFINED_REQUESTID;
        this.clientHost = UNDEFINED_CLIENTHOST;
        this.composition = UNDEFINED_COMPOSITION;
        this.compositionGraph = UNDEFINED_COMPOSITIONGRAPH;
        this.variables = UNDEFINED_VARIABLES;
    }

    private Request(String requestId, String clientHost, String composition, String compositionGraph,  Map<String, Object> variables){
        this.requestId = requestId;
        this.clientHost = clientHost;
        this.compositionGraph = compositionGraph;
        this.composition = composition;
        this.variables = variables;
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
	public String[] getClientHostSet() {
        assert containsClinetHosts(); // TODO this assertion might be superfluous and cause bugs.
		return clientHostSet.toArray(new String[clientHostSet.size()]);
	}
}