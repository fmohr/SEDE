package de.upb.sede.composition.typing;

import de.upb.sede.composition.TypeJournalPageModel;
import de.upb.sede.composition.graphs.types.TypeClass;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TypeJournalPage implements FieldTypeResolution {

    private final FieldTypeResolution readOnlyPrevPage;

    private final Map<String, TypeClass> typeMap = new HashMap<>();

    public TypeJournalPage(TypeJournalPage readOnlyPrevPage) {
        this.readOnlyPrevPage = readOnlyPrevPage;
    }

    public TypeJournalPage() {
        readOnlyPrevPage = new EmptyJournalPage();
    }

    @Override
    @Nullable
    public TypeClass getFieldType(String fieldname) {
        if(typeMap.containsKey(fieldname)) {
            return typeMap.get(fieldname);
        } else {
            return readOnlyPrevPage.getFieldType(fieldname);
        }
    }

    @Override
    public void setFieldType(String fieldname, TypeClass valueType) {
        typeMap.put(fieldname, valueType);
    }

    private void fillList(List<TypeJournalPageModel> pages) {
        if(readOnlyPrevPage instanceof TypeJournalPage) {
           ((TypeJournalPage) readOnlyPrevPage).fillList(pages);
        } else if(!(readOnlyPrevPage instanceof EmptyJournalPage)) {
            throw new RuntimeException("Code error: new journal page class added");
        }
        typeMap.entrySet().stream().map(typeContextEntry ->
            TypeJournalPageModel.builder()
                .fieldname(typeContextEntry.getKey())
                .type(typeContextEntry.getValue())
                .build())
            .forEach(pages::add);
    }

    public List<TypeJournalPageModel> getModel() {
        List<TypeJournalPageModel> pageModelList = new ArrayList<>();
        fillList(pageModelList);
        return pageModelList;
    }
}
