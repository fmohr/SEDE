package ai.services.execution.operator.local;

import ai.services.channels.ChannelService;
import ai.services.execution.operator.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class StdLocalOperations {

    private static final Logger logger = LoggerFactory.getLogger("ai.services.execution.operator.local");

    private final ServiceInstanceFactory serviceInstanceFactory;

    private final ChannelService channelService;

    public StdLocalOperations(ServiceInstanceFactory serviceInstanceFactory, ChannelService channelService) {
        this.serviceInstanceFactory = serviceInstanceFactory;
        this.channelService = channelService;
    }

    private void addAllStdLocalOperators(List<TaskOperator> list) {
        List<TaskOperator> mainTaskOperators = Arrays.asList(
            new AcceptDataOp(),
            new DataMarshalOp(),
            new DeleteFieldOp(),
            new InstructionOp(serviceInstanceFactory),
            new ParseConstantOp(),
            new SendNtfOp(channelService),
            new ServiceStorageOp(channelService),
            new TransmitDataOp(channelService),
            new WaitForNtfOp(),
            new NopOp()
        );
        if(logger.isDebugEnabled()) {
            mainTaskOperators = mainTaskOperators.stream().map(op -> new TaskLoggerOperator(op)).collect(Collectors.toList());
        }
        list.addAll(mainTaskOperators);
    }

    private void addMainTaskFinisherOperator(List<TaskOperator> list) {
        list.add(new MainTaskFinisherOp());
    }


    public TaskOperator allStdLocalOperators() {
        List<TaskOperator> operators = new ArrayList<>();
        addAllStdLocalOperators(operators);
        addMainTaskFinisherOperator(operators);
        return new OpCollection(operators);
    }

}
