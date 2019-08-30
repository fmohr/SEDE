package de.upb.sede.exec;

import de.upb.sede.IQualifiable;
import de.upb.sede.SModelStyle;
import org.immutables.value.Value;

import java.util.List;

@SModelStyle
@Value.Immutable
interface IServiceDesc extends IQualifiable {

    List<IMethodDesc> getMethods();
}
