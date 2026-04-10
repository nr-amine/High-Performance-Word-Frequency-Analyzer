/* Amine NOUAR : Je déclare qu'il s'agit de mon propre travail et que ce travail
a été entièrement écrit par un être humain. */

public class MapEntry<K, V> {
    private final K key;
    private V value;
    
    public MapEntry(K key, V value) {
        this.key = key;
        this.value = value;
    }
    public K key() {
        return key;
    }
    public V value() {
        return value;
    }
    public V setValue(V newValue) {
        V res = value;
        this.value = newValue;
        return res;
    }
    @Override
    public boolean equals(Object o) {
        if (!(o instanceof MapEntry)) {
            return false;
        }
        else {
            MapEntry<?, ?> obj = (MapEntry<?, ?>) o;
            return value.equals(obj.value()) && key.equals(obj.key());
        }
    }
    @Override
    public int hashCode() {
        return 13*key.hashCode() + 7*value.hashCode();
    }
    @Override
    public String toString() {
        return key + "=" + value;
    }
}
