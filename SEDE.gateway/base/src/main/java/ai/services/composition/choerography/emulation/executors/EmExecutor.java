package ai.services.composition.choerography.emulation.executors;

import ai.services.composition.choerography.emulation.EmulationException;
import ai.services.composition.orchestration.emulated.EmulatedOp;
import ai.services.exec.IExecutorHandle;

public interface EmExecutor {

    void execute(EmulatedOp emulatedOp) throws EmulationException;

    IExecutorHandle getExecutorHandle();

}
