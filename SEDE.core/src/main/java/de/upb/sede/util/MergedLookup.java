package de.upb.sede.util;

import java.util.*;
import java.util.function.Predicate;

public class MergedLookup<T> {

    private List<T> list = new ArrayList<>();

    public MergedLookup() {

    }

    public MergedLookup(List<T> list) {
        this.list = new ArrayList<>(list);
    }

    public List<T> getList() {
        return list;
    }

    public synchronized void setList(List<T> list) {
        this.list = Objects.requireNonNull(list);
    }

    public synchronized Optional<T> firstMatching(Predicate<T> match) {
        return list.stream().filter(match).findFirst();
    }

    public synchronized Iterable<T> allMatching(Predicate<T> match) {
        return () -> {
            List<T> localCopy = new ArrayList<>(list);
            Iterator<T> it = localCopy.iterator();
            return new Iterator<T>() {
                T next = null;
                @Override
                public boolean hasNext() {
                    if(next != null) {
                        return true;
                    }
                    while(it.hasNext()) {
                        T next = it.next();
                        if(match.test(next)) {
                            this.next = next;
                            return true;
                        }
                    }
                    return false;
                }

                @Override
                public T next() {
                    if(hasNext()) {
                        T next = this.next;
                        this.next = null;
                        return next;
                    } else {
                        throw new NoSuchElementException();
                    }
                }
            };
        };
    }
}
