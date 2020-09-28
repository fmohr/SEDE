package ai.services;

import ai.services.channels.ChannelService;
import ai.services.interfaces.IGateway;
import ai.services.requests.resolve.beta.IChoreography;
import ai.services.requests.resolve.beta.IResolveRequest;

import java.util.concurrent.ConcurrentHashMap;

public class CoreClient extends ConcurrentHashMap<String, ExecutionController> {

    private final IGateway gateway;

    private final ChannelService channelService;

    public CoreClient(IGateway gateway, ChannelService channelService) {
        this.gateway = gateway;
        this.channelService = channelService;
    }

    public ExecutionController boot(IResolveRequest resolveRequest) {
        IChoreography choreography = gateway.resolve(resolveRequest);
        ExecutionController frontEnd = new ExecutionController(channelService, resolveRequest, choreography);
        String executionId = frontEnd.getExecutionId();
        ExecutionController frontEnd1 = computeIfAbsent(executionId, id -> frontEnd);
        if(frontEnd != frontEnd1) {
            throw new IllegalStateException(String.format("An execution with id '%s' is already defined.", executionId));
        }
        return frontEnd1;
    }
}
