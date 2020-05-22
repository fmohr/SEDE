package de.upb.sede.composition.de.upb.sede.composition.choerography.emulation;

import de.upb.sede.composition.de.upb.sede.composition.choerography.emulation.Emulation;
import de.upb.sede.composition.graphs.nodes.INotification;

public interface ExecutorEmulator {

    Emulation.Dependency notify (INotification notification, );

    Emulation.Dependency cast();

    Emulation.Dependency invoke();

    Emulation.Dependency fetch();

    Emulation.Dependency createReport();

}
