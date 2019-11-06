package de.upb.sede.composition.graphs.typing;

import javax.annotation.Nullable;

public class TypeJournalPage implements TypingContext {

    @Nullable
    private TypeJournalPage prevPage;

    public TypeJournalPage(@Nullable TypeJournalPage prevPage) {
        this.prevPage = prevPage;
    }

    public TypeJournalPage() {
    }




}
