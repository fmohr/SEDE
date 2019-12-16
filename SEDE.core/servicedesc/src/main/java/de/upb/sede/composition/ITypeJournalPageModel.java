package de.upb.sede.composition;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import de.upb.sede.SEDEModelStyle;
import de.upb.sede.composition.graphs.types.TypeClass;
import org.immutables.value.Value;

@SEDEModelStyle
@Value.Immutable
@Value.Modifiable
@JsonDeserialize(builder = TypeJournalPageModel.Builder.class)
public interface ITypeJournalPageModel {

    public String getFieldname();

    public TypeClass getType();

}



