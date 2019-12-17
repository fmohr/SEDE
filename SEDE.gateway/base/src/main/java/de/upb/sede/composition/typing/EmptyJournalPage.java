package de.upb.sede.composition.typing;

import de.upb.sede.composition.graphs.types.TypeClass;

import javax.annotation.Nullable;

public class EmptyJournalPage implements TypeContext {

    @Override
    @Nullable
    public TypeClass getFieldType(String fieldname) {
        return null;
    }

    @Override
    public void setFieldType(String fieldname, TypeClass valueType) {
        throw new IllegalStateException("Cannot write onto empty page.");

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
