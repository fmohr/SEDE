package de.upb.sede.composition.graphs.typing;

import de.upb.sede.composition.graphs.types.IFieldType;

import javax.annotation.Nullable;

public class EmptyJournalPage implements FieldTypeResolution {

    @Override
    @Nullable
    public IFieldType getFieldType(String fieldname) {
        return null;
    }

    @Override
    public void setFieldType(String fieldname, IFieldType valueType) {
        throw new IllegalStateException("Cannot write onto empty page");
    }

    @Override
    public String toString() {
        return "EmptyJournalPage";
    }

    public EmptyJournalPage() {
    }

    @Override
    public int hashCode() {
        return 0;
    }

    @Override
    public boolean equals(Object obj) {
        return (obj instanceof EmptyJournalPage);
    }
}
