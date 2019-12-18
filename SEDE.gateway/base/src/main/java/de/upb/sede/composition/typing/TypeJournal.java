package de.upb.sede.composition.typing;

import java.util.ArrayList;
import java.util.List;

public class TypeJournal {

    private List<TypeJournalPage> pages = new ArrayList<>();

    /**
     * The journal size.
     * @return journal size
     */
    public long size() {
        return pages.size();
    }

    /**
     * Returns the i'th typing context in this journal.
     * Creates a new page and all the preceding pages if the index is larger than the current journal size.
     *
     * The i'th typing context describes the type state *before* the i'th instruction is executed.
     * @param index index of the requested page. index of the next instruction to be executed.
     * @return The new type journal page
     */
    public TypeJournalPage getPageAt(long index) {
        if(index < 0) {
            throw new IllegalArgumentException("Index is negative: " + index);
        }
        if(index == 0 && pages.isEmpty()) {
            return createFirstPage();
        }

        if(pages.size() <= index) {
            return newPageAt(index);
        } else {
            return pages.get((int) index);
        }
    }

    public void injectFirstPage(TypeJournalPage firstPage) {
        if(!pages.isEmpty()) {
            throw new IllegalStateException("Injecting a first page into a non empty journal.");
        }
        pages.add(firstPage);
    }

    public TypeJournalPage getPageAfterInst(long instructionIndex) {
        return getPageAt(instructionIndex + 1);
    }

    /**
     * Creates the first page in the journal.
     * @return The first type journal page.
     */
    private TypeJournalPage createFirstPage() {
        TypeJournalPage firstPage = new TypeJournalPage();
        pages.add(firstPage);
        return firstPage;
    }

    /**
     * Creates a new page at the given index.
     * If there are pages missing before the index, they are recursevly created.
     * @param index index of the new page.
     * @return new journal page.
     */
    private TypeJournalPage newPageAt(long index) {
        if(index < pages.size()) {
            throw new IllegalArgumentException(String.format("Page %d already exists.", index));
        }
        TypeJournalPage prevPage = getPageAt(index - 1);
        TypeJournalPage newPage = new TypeJournalPage(prevPage);
        if(index != pages.size()) {
            throw new RuntimeException(String.format("Implementation error: the number of pages in list doesnt match the current page index: pages = %d != %d = index", pages.size(), index));
        }
        pages.add(newPage);
        return newPage;
    }

    public TypeJournalPage getLastPage() {
        return getPageAt(pages.size());
    }
}
