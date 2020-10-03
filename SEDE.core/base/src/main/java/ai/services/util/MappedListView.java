package ai.services.util;


import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class MappedListView<K, V, U> extends AbstractMap<K, V> {

    private final Collection<U> innerList;

    private final Function<U, K> keyExtractor;
    private final Function<U, V> valueExtractor;

    private final Map<K, V> cache = new HashMap<>();

    public MappedListView(Collection<U> innerList, Function<U, K> keyExtractor) {
        this.innerList = innerList;
        this.keyExtractor = keyExtractor;
        this.valueExtractor = u -> (V)u;
    }

    public MappedListView(Collection<U> innerList, Function<U, K> keyExtractor, Function<U, V> valueExtractor) {
        this.innerList = innerList;
        this.keyExtractor = keyExtractor;
        this.valueExtractor = valueExtractor;
    }

    public Collection<U> getInnerList() {
        return innerList;
    }

    @Override
    public V get(Object key) {
        return cache.computeIfAbsent((K) key, super::get);
    }

    public void flushCache() {
        cache.clear();
    }

    @Override
    public Set<Entry<K, V>> entrySet() {
        return new AbstractSet<Entry<K, V>>() {
            @Override
            public Iterator<Entry<K, V>> iterator() {
                final Iterator<U> listIterator = innerList.iterator();
                return new Iterator<Entry<K, V>>() {

                    @Override
                    public boolean hasNext() {
                        return listIterator.hasNext();
                    }

                    @Override
                    public Entry<K, V> next() {
                        U next = listIterator.next();
                        return new SimpleEntry<>(keyExtractor.apply(next), valueExtractor.apply(next));
                    }
                };
            }

            @Override
            public int size() {
                return innerList.size();
            }
        };
    }
}
