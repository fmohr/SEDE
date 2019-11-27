package de.upb.sede.composition.graphs.typing;

import de.upb.sede.composition.graphs.types.IFieldType;
import de.upb.sede.exec.IMethodDesc;

import javax.annotation.Nullable;
import java.util.Map;

public class TypeJournalPage implements FieldTypeResolution {

    private final FieldTypeResolution readOnlyPrevPage;

    private IMethodDesc methodDesc;

    private Map<String, IFieldType> typeMap;


    public TypeJournalPage(TypeJournalPage readOnlyPrevPage) {
        this.readOnlyPrevPage = readOnlyPrevPage;
    }

    public TypeJournalPage() {
        readOnlyPrevPage = new EmptyJournalPage();
    }

    @Override
    @Nullable
    public IFieldType getFieldType(String fieldname) {
        if(typeMap.containsKey(fieldname)) {
            return typeMap.get(fieldname);
        } else {
            return readOnlyPrevPage.getFieldType(fieldname);
        }
    }

    @Override
    public void setFieldType(String fieldname, IFieldType valueType) {
        typeMap.put(fieldname, valueType);
    }

}
