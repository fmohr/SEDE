package ai.services.execution.operator.local;

import ai.services.channels.ChannelService;
import ai.services.channels.ExecutionDataChannel;
import ai.services.channels.ExecutorCommChannel;
import ai.services.channels.UploadLink;
import ai.services.execution.Task;
import ai.services.execution.TaskTransition;
import ai.services.execution.operator.MainTaskOperator;
import ai.services.composition.graphs.nodes.ITransmitDataNode;
import ai.services.core.SEDEObject;
import ai.services.exec.IExecutorContactInfo;
import ai.services.util.StringUtil;


public class TransmitDataOp extends MainTaskOperator {

    private final ChannelService channelService;

    public TransmitDataOp(ChannelService channelService) {
        super(ITransmitDataNode.class);
        this.channelService = channelService;
    }

    @Override
    public TaskTransition runTask(Task t) throws Exception {
        ITransmitDataNode node = (ITransmitDataNode) t.getNode();
        IExecutorContactInfo contactInfo = node.getContactInfo();
        String fieldname = node.getFieldName();
        SEDEObject fieldValue = t.getFieldContext().getFieldValue(fieldname);
        if(!fieldValue.isSemantic()) {
            throw new IllegalStateException(StringUtil.unexpectedTypeMsg("SemanticDataField", fieldValue));
        }
        ExecutorCommChannel executorCommChannel = channelService.interExecutorCommChannel(contactInfo);
        ExecutionDataChannel executionDataChannel = executorCommChannel.dataChannel(t.getFieldContext().contextIdentifier());
        try(UploadLink uploadLink = executionDataChannel.getUploadLink(fieldname, fieldValue.getType())) {
            uploadLink.setPayload(fieldValue.getDataField());
            return mainTaskPerformed(t);
        }
    }
}