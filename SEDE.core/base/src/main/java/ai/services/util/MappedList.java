package ai.services.util;


import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class MappedList<K, V> extends AbstractMap<K, V> {

    private final List<V> innerList;

    private final Function<V, K> keyExtractor;

    private final Map<K, V> cache = new HashMap<>();

    public MappedList(List<V> innerList, Function<V, K> keyExtractor) {
        this.innerList = innerList;
        this.keyExtractor = keyExtractor;
    }

    public MappedList(Function<V, K> keyExtractor) {
        innerList = new ArrayList<>();
        this.keyExtractor = keyExtractor;
    }

    public List<V> getInnerList() {
        return innerList;
    }

    @Override
    public int size() {
        return innerList.size();
    }

    @Override
    public boolean containsValue(Object value) {
        return innerList.contains(value);
    }

    @Override
    public V get(Object key) {
        return cache.computeIfAbsent((K) key, super::get);
    }

    @Override
    public V remove(Object key) {
        return super.remove(key);
    }

    @Override
    public void clear() {
        innerList.clear();
    }

    @Override
    public Set<K> keySet() {
        return innerList.stream()
            .map(keyExtractor)
            .collect(Collectors.toSet());
    }

    @Override
    public Collection<V> values() {
        return innerList;
    }

    @Override
    public V put(K key, V value) {
        innerList.add(value);
        return value;
    }

    public V put(V value) {
        return this.put(keyExtractor.apply(value), value);
    }

    void clearCache() {
        cache.clear();
    }

    @Override
    public Set<Entry<K, V>> entrySet() {
        return new AbstractSet<Entry<K, V>>() {
            @Override
            public Iterator<Entry<K, V>> iterator() {
                final Iterator<V> listIterator = innerList.iterator();
                return new Iterator<Entry<K, V>>() {

                    @Override
                    public boolean hasNext() {
                        return listIterator.hasNext();
                    }

                    @Override
                    public Entry<K, V> next() {
                        V next = listIterator.next();
                        return new SimpleEntry<>(keyExtractor.apply(next), next);
                    }

                    @Override
                    public void remove() {
                        clearCache();
                        listIterator.remove();
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
