package de.upb.sede.composition.choerography.emulation.executors;

import de.upb.sede.composition.choerography.emulation.EmulationException;
import de.upb.sede.composition.orchestration.emulated.EmulatedOp;
import de.upb.sede.exec.IExecutorHandle;

public interface EmExecutor {

    void execute(EmulatedOp emulatedOp) throws EmulationException;

    IExecutorHandle getExecutorHandle();

}
