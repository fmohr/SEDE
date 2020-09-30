package ai.services;

import ai.services.channels.ChannelService;
import ai.services.interfaces.IGateway;
import ai.services.requests.resolve.beta.IChoreography;
import ai.services.requests.resolve.beta.IResolveRequest;

public class CoreClient {

    private final IGateway gateway;

    private final ChannelService channelService;

    public CoreClient(IGateway gateway, ChannelService channelService) {
        this.gateway = gateway;
        this.channelService = channelService;
    }

    public ExecutionController boot(IResolveRequest resolveRequest) {
        IChoreography choreography = gateway.resolve(resolveRequest);
        return boot(resolveRequest, choreography);
    }

    public ExecutionController boot(IResolveRequest resolveRequest, IChoreography choreography) {
        ExecutionController frontEnd = new ExecutionController(channelService, resolveRequest, choreography);
        return frontEnd;
    }

    public ExecutionController bootAndStart(IResolveRequest resolveRequest, IChoreography choreography) {
        ExecutionController ctrl= this.boot(resolveRequest, choreography);
        try {
            ctrl.startExecution();
            return ctrl;
        } catch(Exception ex) {
            ctrl.close();
            throw ex;
        }
    }

    public ExecutionController bootAndStart(IResolveRequest resolveRequest) {
        IChoreography choreography = gateway.resolve(resolveRequest);
        return bootAndStart(resolveRequest, choreography);
    }
}
