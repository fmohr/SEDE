package de.upb.sede.composition.graphs.typing;

import de.upb.sede.composition.graphs.types.IValueType;

import javax.annotation.Nullable;

public class TypeJournalPage implements TypingContext {

    @Nullable
    private TypeJournalPage prevPage;

    public TypeJournalPage(@Nullable TypeJournalPage prevPage) {
        this.prevPage = prevPage;
    }

    public TypeJournalPage() {
    }


    @Override
    public FieldType getFieldType(String fieldname) {
        return null;
    }

    @Override
    public void setFieldType(String fieldname, FieldType valueType) {

    }
}
