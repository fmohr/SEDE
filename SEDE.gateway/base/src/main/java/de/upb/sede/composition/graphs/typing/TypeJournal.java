package de.upb.sede.composition.graphs.typing;

import de.upb.sede.gateway.engine.TypeContext;

import java.util.ArrayList;
import java.util.List;

public class TypeJournal {

    private List<TypeJournalPage> pages = new ArrayList<>();

    /**
     * returns the i'th typing context in this journal.
     * The i'th typing context describes the type state *before* the i'th instruction is executed.
     * @param index
     * @return
     */
    public TypingContext getPageAt(long index) {
        if(index == 0 && pages.isEmpty()) {
            return createFirstPage();
        }

        if(pages.size() < index) {
            TypeJournalPage prevPage = getPrevPage(index - 1);

        }
    }

    /**
     * Creates the first page in the journal.
     * @return The first type journal page.
     */
    private TypingContext createFirstPage() {
        TypeJournalPage firstPage = new TypeJournalPage();
        pages.add(firstPage);
        return firstPage;
    }

    private TypeJournalPage getPrevPage(long index) {
        long prevIndex = index - 1;
        if(pages.size() == prevIndex) {

        }
    }
}
