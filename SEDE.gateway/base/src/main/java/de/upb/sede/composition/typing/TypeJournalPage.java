package de.upb.sede.composition.typing;

import de.upb.sede.composition.IFieldType;
import de.upb.sede.composition.FieldType;
import de.upb.sede.composition.types.TypeClass;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TypeJournalPage {

    private final TypeJournalPage prevPage;

    private final Map<String, TypeClass> typeMap = new HashMap<>();

    TypeJournalPage(TypeJournalPage prevPage) {
        this.prevPage = prevPage;
    }

    TypeJournalPage() {
        prevPage = null;
    }

    TypeJournalPage(List<IFieldType> initialContext) {
        this();
        initialContext.forEach(
            journalPage -> typeMap.put(journalPage.getFieldname(), journalPage.getType())
        );
    }

    private boolean isFirstPage() {
        return prevPage == null;
    }

    private TypeJournalPage getPrevPage() {
        if(isFirstPage()) {
            throw new IllegalStateException("Code error: already first page.");
        }
        return prevPage;
    }

    @Nullable
    public TypeClass getFieldType(String fieldname) {
        if(typeMap.containsKey(fieldname)) {
            return typeMap.get(fieldname);
        } else if(isFirstPage()) {
            return null;
        } else {
            return getPrevPage().getFieldType(fieldname);
        }
    }

    public void setFieldType(String fieldname, TypeClass valueType) {
        typeMap.put(fieldname, valueType);
    }

    private static IFieldType toFieldType(Map.Entry<String, TypeClass> entry) {
        return FieldType.builder()
            .fieldname(entry.getKey())
            .type(entry.getValue())
            .build();
    }

    public void extractInto(List<IFieldType> context) {
        typeMap.entrySet().stream()
            .map(TypeJournalPage::toFieldType)
            .forEach(context::add);
    }

    public void copyInto(TypeJournalPage otherPage) {
        typeMap.forEach(otherPage::setFieldType);
    }
}
