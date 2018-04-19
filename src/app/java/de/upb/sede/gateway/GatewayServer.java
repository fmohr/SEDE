package de.upb.sede.gateway;

import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.List;

import com.sun.net.httpserver.HttpServer;

import de.upb.sede.composition.FMCompositionParser;
import de.upb.sede.composition.gc.ExecutorCoordinator;
import de.upb.sede.composition.gc.GraphConstruction;
import de.upb.sede.composition.gc.ResolveInfo;
import de.upb.sede.composition.graphs.nodes.InstructionNode;
import de.upb.sede.config.ClassesConfig;
import de.upb.sede.interfaces.Gateway;
import de.upb.sede.requests.ExecutorRegistration;
import de.upb.sede.requests.Request;
import de.upb.sede.requests.resolve.GatewayResolution;
import de.upb.sede.webinterfaces.SunHttpHandler;
import de.upb.sede.webinterfaces.server.BasicServerResponse;

public class GatewayServer implements Gateway{
	
	private final ExecutorCoordinator execCoordinator = new ExecutorCoordinator();
	private final ClassesConfig classesConfig;
	
	public GatewayServer(String... classConfigFilePaths){
		classesConfig = new ClassesConfig(classConfigFilePaths);
	}
	

	public BasicServerResponse getExecRegisterHandle() {
		return new ExecutorRegistrationHandler(execCoordinator, classesConfig);
	}

	public BasicServerResponse getResolveCompositionHandle() {
		return new ResolveCompositionHandler(execCoordinator, classesConfig);
	}
	
	public GraphConstruction buildGraph(String fmComposition, ResolveInfo resolveInfo) {
		List<String> fmInstructions = FMCompositionParser.separateInstructions(fmComposition);
		List<InstructionNode> instNodes = new ArrayList<>();
		for(String instruction : fmInstructions) {
			instNodes.add(FMCompositionParser.parseInstruction(instruction));
		}
		GraphConstruction graphConstruction = new GraphConstruction(resolveInfo, instNodes);
		
		return graphConstruction;
	}
	

    public static void main(String[] args) throws Exception {
        HttpServer server = HttpServer.create(new InetSocketAddress(8000), 0);
        GatewayServer gateWayServer = new GatewayServer();
        
        server.createContext("/register", new SunHttpHandler(gateWayServer::getExecRegisterHandle));
        server.createContext("/resolve", new SunHttpHandler(gateWayServer::getResolveCompositionHandle));
        server.setExecutor(null); // creates a default executor
        server.start();
    }

	@Override
	public GatewayResolution resolve(Request resolveRequest) {
		return null;
	}

	@Override
	public boolean register(ExecutorRegistration registry) {
		return false;
	}
    

}
