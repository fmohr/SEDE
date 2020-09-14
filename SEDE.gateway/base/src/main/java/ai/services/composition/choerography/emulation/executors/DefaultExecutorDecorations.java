package de.upb.sede.composition.choerography.emulation.executors;

import de.upb.sede.composition.choerography.emulation.EmulationException;
import de.upb.sede.composition.graphs.nodes.*;
import de.upb.sede.composition.orchestration.emulated.*;

import java.util.ArrayList;
import java.util.List;

class DefaultExecutorDecorations {

    public static GraphCreatingExecutor addDefaultDecorators(GraphCreatingExecutor base) {
        AbstractExecutorDecorator<?> decorator = new TransmissionHandshakeDecorator(base);

        decorator = new AcceptDataDecorator(decorator);
        decorator = new InstructionDecorator(decorator);
        decorator = new ParseConstDecorator(decorator);
        decorator = new ServiceLoadStoreDecorator(decorator);
        decorator = new DeleteDecorator(decorator);
        decorator = new CastDecorator(decorator);
        decorator = new MarshalDecorator(decorator);
        decorator = new FinishDecorator(decorator);
        decorator = new ClientFinishDecorator(decorator);

        return decorator;
    }

    static class TransmissionHandshakeDecorator extends AbstractExecutorDecorator<ITransmissionOp> {

        public TransmissionHandshakeDecorator(GraphCreatingExecutor delegate) {
            super(delegate, TransmissionOp.class);
        }

        @Override
        public EmulatedOp handleOperation(ITransmissionOp op) throws EmulationException {

            addNodes(
                op.getSourceReadyNtf(),
                op.getTargetReadyNtf(),
                op.getTargetReceivedNtf(),
                op.getTransmitDataNode()
            );

            addEdges(
                edge(op.getSourceReadyNtf(), op.getTransmitDataNode()),
                edge(op.getTargetReadyNtf(), op.getTransmitDataNode()),
                edge(op.getTransmitDataNode(), op.getTargetReceivedNtf())
            );


            consumeFields(op.getDFields(), op.getSourceReadyNtf());
            produceFields(op.getDFields(), op.getTargetReceivedNtf());


            if (op.getDeleteFieldNode() != null) {
                // Rerun delete field operation:
                IDeleteFieldOp deleteOp = DeleteFieldOp.builder().addAllDFields(op.getDFields()).deleteFieldNode(op.getDeleteFieldNode()).build();
                getHead().execute(deleteOp);
            }

            return TransmissionOp.builder().from(op).wasHandled(true).build();
        }

    }

    static class InstructionDecorator extends AbstractExecutorDecorator<IInstructionOp> {

        public InstructionDecorator(GraphCreatingExecutor delegate) {
            super(delegate, IInstructionOp.class);
        }

        @Override
        public EmulatedOp handleOperation(IInstructionOp op) {
            IInstructionNode instructionNode = op.getInstructionNode();
            List<String> dFields = op.getDFields();
            addNodes(instructionNode);
            consumeFields(dFields, instructionNode);
            produceFields(dFields, instructionNode);
            return InstructionOp.builder().from(op).wasHandled(true).build();
        }
    }

    static class AcceptDataDecorator extends AbstractExecutorDecorator<IAcceptOp> {

        public AcceptDataDecorator(GraphCreatingExecutor delegate) {
            super(delegate, IAcceptOp.class);
        }

        @Override
        public EmulatedOp handleOperation(IAcceptOp op) {
            addNodes(
                op.getSourceReadyNtf(),
                op.getTargetReadyNtf(),
                op.getTargetReceivedNtf(),
                op.getAcceptDataNode()
            );

            addEdges(
                edge(op.getSourceReadyNtf(), op.getAcceptDataNode()),
                edge(op.getTargetReadyNtf(), op.getAcceptDataNode()),
                edge(op.getAcceptDataNode(), op.getTargetReceivedNtf())
            );

            consumeFields(op.getDFields(), op.getTargetReadyNtf());
            produceFields(op.getDFields(), op.getTargetReceivedNtf());

            return AcceptOp.builder().from(op).wasHandled(true).build();
        }

    }

    static class ParseConstDecorator extends AbstractExecutorDecorator<IParseOp> {

        public ParseConstDecorator(GraphCreatingExecutor delegate) {
            super(delegate, IParseOp.class);
        }

        @Override
        public EmulatedOp handleOperation(IParseOp op) {
            IParseConstantNode node = op.getParseConstantNode();
            addNodes(node);
            consumeFields(op.getDFields(), node);
            produceFields(op.getDFields(), node);

            return ParseOp.builder()
                .from(op)
                .wasHandled(true)
                .build();
        }
    }

    static class ServiceLoadStoreDecorator
        extends AbstractExecutorDecorator<IServiceLoadStoreOp> {

        public ServiceLoadStoreDecorator(GraphCreatingExecutor delegate) {
            super(delegate, IServiceLoadStoreOp.class);
        }

        @Override
        public EmulatedOp handleOperation(IServiceLoadStoreOp op) {
            IServiceInstanceStorageNode node = op.getServiceInstanceStorageNode();
            addNodes(node);
            consumeFields(op.getDFields(), node);
            produceFields(op.getDFields(), node);

            return ServiceLoadStoreOp.builder()
                .from(op)
                .wasHandled(true)
                .build();
        }
    }

    static class DeleteDecorator extends AbstractExecutorDecorator<IDeleteFieldOp> {

        public DeleteDecorator(GraphCreatingExecutor delegate) {
            super(delegate, IDeleteFieldOp.class);
        }

        @Override
        public EmulatedOp handleOperation(IDeleteFieldOp op) {
            IDeleteFieldNode deleteFieldNode = op.getDeleteFieldNode();
            addNodes(deleteFieldNode);

            consumeFields(op.getDFields(), deleteFieldNode);
            produceFields(op.getDFields(), deleteFieldNode);
            return DeleteFieldOp.builder()
                .from(op)
                .wasHandled(true)
                .build();
        }
    }


    static class CastDecorator extends AbstractExecutorDecorator<ICastOp> {

        public CastDecorator(GraphCreatingExecutor delegate) {
            super(delegate, ICastOp.class);
        }

        @Override
        public EmulatedOp handleOperation(ICastOp op) {
            IMarshalNode firstMarshal = op.getFirstCast();

            addNodes(firstMarshal);
            if (op.getSecondCast() == null) {
                consumeFields(op.getDFields(), firstMarshal);
                produceFields(op.getDFields(), firstMarshal);
            } else {
                IMarshalNode secondMarshal = op.getSecondCast();
                addNodes(secondMarshal);
                addEdges(
                    edge(firstMarshal, secondMarshal)
                );
                consumeFields(op.getDFields(), firstMarshal);
                produceFields(op.getDFields(), secondMarshal);
            }

            return CastOp.builder()
                .from(op)
                .wasHandled(true)
                .build();
        }
    }

    static class MarshalDecorator extends AbstractExecutorDecorator<IMarshalOp> {

        public MarshalDecorator(GraphCreatingExecutor delegate) {
            super(delegate, IMarshalOp.class);
        }

        @Override
        public EmulatedOp handleOperation(IMarshalOp op) {
            addNodes(op.getMarshalNode());
            consumeFields(op.getDFields(), op.getMarshalNode());
            produceFields(op.getDFields(), op.getMarshalNode());

            return MarshalOp.builder()
                .from(op)
                .wasHandled(true)
                .build();
        }

    }

    static class FinishDecorator extends AbstractExecutorDecorator<IFinishOp> {

        public FinishDecorator(GraphCreatingExecutor delegate) {
            super(delegate, IFinishOp.class);
        }

        @Override
        public EmulatedOp handleOperation(IFinishOp op) {
            INotifyNode executionFinishedNtf = op.getExecutionFinishedNtf();

            addNodes(executionFinishedNtf);
            List<ExecutionGraph.GraphEdge> edges = new ArrayList<>();
            for (BaseNode lastNode : GraphTraversal.lastNodes(getBase().getGraph())) {
                if (!lastNode.equals(executionFinishedNtf))
                    edges.add(edge(lastNode, executionFinishedNtf));
            }
            addEdges(edges);

            return FinishOp.builder()
                .from(op)
                .wasHandled(true)
                .build();
        }
    }

    static class ClientFinishDecorator extends AbstractExecutorDecorator<IWaitForFinishOp> {


        public ClientFinishDecorator(GraphCreatingExecutor delegate) {
            super(delegate, IWaitForFinishOp.class);
        }

        @Override
        public EmulatedOp handleOperation(IWaitForFinishOp op) {
            INopNode nopeNode = op.getNopNode();

            addNodes(nopeNode);
            for (IWaitForNotificationNode waitNode : op.getExFinishedNtf()) {
                addNodes(waitNode);
                addEdges(edge(nopeNode, waitNode));
            }

            return WaitForFinishOp.builder()
                .from(op)
                .wasHandled(true)
                .build();
        }
    }

}
