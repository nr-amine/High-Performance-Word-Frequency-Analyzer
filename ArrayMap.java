/* Amine NOUAR : Je déclare qu'il s'agit de mon propre travail et que ce travail
a été entièrement écrit par un être humain. */
import java.util.ArrayList;
import java.util.Arrays;

public class ArrayMap <K, V> {
    private int size;
    private MapEntry<K, V>[] values;
    private static final int DEFAULT_CAPACITY = 10;
    private static final int CAP_MULTIPLIER = 2;
    @SuppressWarnings("unchecked")
    public ArrayMap(int capacity) {
        this.size = 0;
        values = (MapEntry<K, V>[]) new MapEntry[capacity];
    }
    public ArrayMap() {
        this(DEFAULT_CAPACITY);
    }
    public ArrayMap(ArrayMap<K, V> other) {
        this.size = other.size;
        for (int i = 0; i < other.values.length; i++) {
            this.values[i] = other.values[i];
        }
    }
    public int size() {
        return size;
    }
    public boolean isEmpty() {
        return size == 0;
    }
    /*method to make resizing more readable*/
    public void resize() {
        @SuppressWarnings("unchecked")
        MapEntry<K, V>[] newValues = (MapEntry<K, V>[]) new MapEntry[values.length * CAP_MULTIPLIER];
        for (int i = 0; i < values.length; i++) {
            newValues[i] = values[i];
        }
        values = newValues;
    }
    public V get(Object key) {
        for (int i = 0; i < size; i++) {
            if (values[i].key().equals(key)) {
                return values[i].value();
            }
        }
        return null;
    }
    public V put(K key, V value) {
        if (size == values.length) {
            resize();
        }
        for (MapEntry<K, V> entry : values) {
            if (entry != null && entry.key().equals(key)) {
                return entry.setValue(value);
            }
        }
        values[size] = new MapEntry<>(key, value);
        size++;
        return null;
    }
    public V remove(K key) {
        for (int i=0; i < size; i++) {
            if(values[i].key().equals(key)) {
                V res = values[i].value();
                values[i] = values[size - 1];
                values[size - 1] = null;
                size--;
                return res;
            }
        }
        return null;
    }
    @SuppressWarnings("unchecked")
    public <T> T returnKey(int i) 
    {
        if (i < size) {
            return (T) values[i].key();
            }
        else {
            return null;
            }
        }
    /*used setAll and copyOf (stackoverflow directed me to
    reflect.Array but chose not to go with it) */
    @SuppressWarnings("unchecked")
    public <T> T[] arrayOfKeys(T[] a) {
        if (size <= a.length) {
            Arrays.setAll(a, i -> returnKey(i));
            if (size < a.length) {
                a[size] = null; 
            }
            return a;
        }
        else {
            T[] temp = (T[]) Arrays.copyOf(a, size);
            Arrays.setAll(temp, i -> (T) values[i].key());
            return temp;
        }
    }
    public ArrayList<K> listOfKeys() {
        ArrayList<K> keys = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            keys.add(values[i].key());
        }
        return keys;
    } 
}
