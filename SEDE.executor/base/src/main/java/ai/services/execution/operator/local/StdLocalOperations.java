package ai.services.execution.operator.local;

import ai.services.channels.ChannelService;
import ai.services.execution.operator.MainTaskFinisherOp;
import ai.services.execution.operator.OpCollection;
import ai.services.execution.operator.ServiceInstanceFactory;
import ai.services.execution.operator.TaskOperator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class StdLocalOperations {

    private final ServiceInstanceFactory serviceInstanceFactory;

    private final ChannelService channelService;

    public StdLocalOperations(ServiceInstanceFactory serviceInstanceFactory, ChannelService channelService) {
        this.serviceInstanceFactory = serviceInstanceFactory;
        this.channelService = channelService;
    }

    private void addAllStdLocalOperators(List<TaskOperator> list) {
        list.addAll(Arrays.asList(
           new AcceptDataOp(),
           new DataMarshalOp(),
           new DeleteFieldOp(),
           new InstructionOp(serviceInstanceFactory),
           new ParseConstantOp(),
           new SendNtfOp(channelService),
           new ServiceStorageOp(channelService),
           new TransmitDataOp(channelService),
           new WaitForNtfOp()
        ));
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
