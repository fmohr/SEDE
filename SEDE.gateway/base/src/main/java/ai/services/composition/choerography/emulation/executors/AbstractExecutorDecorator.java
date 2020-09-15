package ai.services.composition.choerography.emulation.executors;

import ai.services.composition.choerography.emulation.EmulationException;
import ai.services.composition.graphs.nodes.BaseNode;
import ai.services.composition.orchestration.emulated.EmulatedOp;
import ai.services.exec.IExecutorHandle;

import java.util.List;
import java.util.function.Predicate;

abstract class AbstractExecutorDecorator<T extends EmulatedOp> implements GraphCreatingExecutor {

    private final GraphCreatingExecutor delegate;

    private final Predicate<EmulatedOp> opPredicate;

    private AbstractExecutorDecorator<?> head;

    public AbstractExecutorDecorator(GraphCreatingExecutor delegate, Predicate<EmulatedOp> opPredicate) {
        this.delegate = delegate;
        this.opPredicate = opPredicate;
        setHead(this);
    }

    public AbstractExecutorDecorator(GraphCreatingExecutor delegate, Class<? extends T> handlesOpClass, boolean acceptsHandled) {
        this(delegate, op -> {
           if(!handlesOpClass.isInstance(op)) {
               return false;
           }
           if(!acceptsHandled && op.wasHandled()) {
               return false;
           }
           return true;
        });
    }

    public AbstractExecutorDecorator(GraphCreatingExecutor delegate, Class<? extends T> handlesOpClass) {
        this(delegate, handlesOpClass, false);
    }


    private void setHead(AbstractExecutorDecorator<?> decorator) {
        this.head = decorator;
        if(delegate instanceof AbstractExecutorDecorator) {
            ((AbstractExecutorDecorator<?>) delegate).setHead(head);
        } else if(!(delegate instanceof BaseExecutor)) {
            throw new IllegalArgumentException("Delegate " + delegate.getClass().getName() + " is of unknown type.");
        }
    }

    protected final BaseExecutor getBase() {
        if(delegate instanceof BaseExecutor) {
            return (BaseExecutor) delegate;
        } else if(delegate instanceof AbstractExecutorDecorator){
            return ((AbstractExecutorDecorator) delegate).getBase();
        } else {
            throw new IllegalStateException("Delegate " + delegate.getClass().getName() + " is of unknown type.");
        }
    }

    protected AbstractExecutorDecorator<?> getHead() {
        return head;
    }

    @Override
    public void execute(EmulatedOp emulatedOp) throws EmulationException {
        if(opPredicate.test(emulatedOp)) {
            EmulatedOp handledOperation = handleOperation((T) emulatedOp);
            delegate.execute(handledOperation);
        } else {
            delegate.execute(emulatedOp);
        }
    }

    public abstract EmulatedOp handleOperation(T op) throws EmulationException;

    @Override
    public void addNodes(List<BaseNode> nodes) {
        delegate.addNodes(nodes);
    }


    @Override
    public void addEdges(List<ExecutionGraph.GraphEdge> edges) {
        delegate.addEdges(edges);
    }

    @Override
    public void addNodes(List<BaseNode> nodes, List<ExecutionGraph.GraphEdge> edges) {
        delegate.addNodes(nodes, edges);
    }


    @Override
    public void consumeFields(List<String> toBeConsumed, List<BaseNode> consumers) {
        delegate.consumeFields(toBeConsumed, consumers);
    }


    @Override
    public void produceFields(List<String> toBeProduced, List<BaseNode> producers) {
        delegate.produceFields(toBeProduced, producers);
    }

    @Override
    public IExecutorHandle getExecutorHandle() {
        return delegate.getExecutorHandle();
    }

    protected final ExecutionGraph.GraphEdge edge(BaseNode n1, BaseNode n2) {
        return new ExecutionGraph.GraphEdge(n1, n2);
    }

    @Override
    public ExecutionGraph getGraph() {
        return getBase().getGraph();
    }

    protected final void assertNotHandled(EmulatedOp op) {
        if(op.wasHandled()) {
            throw new IllegalArgumentException("Operation " + op.toString() + " was handled already. " + this.getClass() + "  needs to be decorated after default decorators.");
        }
    }

}
