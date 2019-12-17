package de.upb.sede.composition.typing;

import de.upb.sede.composition.IFieldType;
import de.upb.sede.composition.FieldType;
import de.upb.sede.composition.IInstTCResult;
import de.upb.sede.composition.InstTCResult;
import de.upb.sede.composition.graphs.types.TypeClass;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TypeJournalPage implements TypeContext {

    private final TypeContext readOnlyPrevPage;

    private final Map<String, TypeClass> typeMap = new HashMap<>();

    TypeJournalPage(TypeJournalPage readOnlyPrevPage) {
        this.readOnlyPrevPage = readOnlyPrevPage;
    }

    TypeJournalPage() {
        readOnlyPrevPage = new EmptyJournalPage();
    }

    TypeJournalPage(List<IFieldType> initialContext) {
        this();
        initialContext.forEach(
            journalPage -> typeMap.put(journalPage.getFieldname(), journalPage.getType())
        );
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

    private void addPage(List<IInstTCResult> pages) {
        if(readOnlyPrevPage instanceof TypeJournalPage) {
           ((TypeJournalPage) readOnlyPrevPage).addPage(pages);
        } else if(!(readOnlyPrevPage instanceof EmptyJournalPage)) {
            throw new RuntimeException("Code error: new journal page class added");
        }
        List<IFieldType> fieldTypes = new ArrayList<>();
        typeMap.entrySet().stream().map(typeContextEntry ->
            FieldType.builder()
                .fieldname(typeContextEntry.getKey())
                .type(typeContextEntry.getValue())
                .build())
            .forEach(fieldTypes::add);
        IInstTCResult page = InstTCResult.builder().fieldTypes(fieldTypes).build();
        pages.add(page);
    }

    public List<IInstTCResult> extractModel() {
        List<IInstTCResult> pageModelList = new ArrayList<>();
        addPage(pageModelList);
        return pageModelList;
    }

    public IInstTCResult collapseModel() {
        IInstTCResult prevModel;
        if(readOnlyPrevPage instanceof TypeJournalPage) {
            prevModel = ((TypeJournalPage) readOnlyPrevPage).collapseModel();
        } else if(!(readOnlyPrevPage instanceof EmptyJournalPage)) {
            throw new RuntimeException("Code error: new journal page class added");
        } else {
            prevModel = InstTCResult.builder().build();
        }
        InstTCResult.Builder modelBuilder = InstTCResult.builder();
        prevModel.getFieldTypes().stream()
            .filter(ft -> !typeMap.containsKey(ft.getFieldname()))
            .forEach(modelBuilder::addFieldTypes);

        typeMap.entrySet().stream()
            .map(typeContextEntry ->
                FieldType.builder()
                    .fieldname(typeContextEntry.getKey())
                    .type(typeContextEntry.getValue())
                    .build())
            .forEach(modelBuilder::addFieldTypes);

        return modelBuilder.build();
    }

}
