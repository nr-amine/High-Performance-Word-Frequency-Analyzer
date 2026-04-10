/* Amine NOUAR : Je déclare qu'il s'agit de mon propre travail et que ce travail
a été entièrement écrit par un être humain. */

import java.util.ArrayList;
import java.util.Arrays;

public class SortedArrayMap<K extends Comparable<K>, V> {
    private int size;
    protected MapEntry<K, V>[] values;
    private static final int DEFAULT_CAPACITY = 10;
    private static final int CAP_MULTIPLIER = 2;
    @SuppressWarnings("unchecked")
    public SortedArrayMap(int capacity) {
        this.size = 0;
        values = (MapEntry<K, V>[]) new MapEntry[capacity];
    }
    public SortedArrayMap() {
        this(DEFAULT_CAPACITY);
    }
    @SuppressWarnings("unchecked")
    public SortedArrayMap(SortedArrayMap<K, V> other) {
        this.size = other.size;
        this.values = (MapEntry<K, V>[]) new MapEntry[other.values.length];
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
    public void resize() {
        @SuppressWarnings("unchecked")
        MapEntry<K, V>[] newValues = (MapEntry<K, V>[]) new MapEntry[values.length * CAP_MULTIPLIER];
        for (int i = 0; i < values.length; i++) {
            newValues[i] = values[i];
        }
        values = newValues;
    }


    /* -----The findIndex method is adapted directly from the Arrays.binarySearch method-------*/
    
    private int findIndex(K key) {
        int low = 0;
        int high = size - 1;
        while (low <= high) {
            int mid = (low + high) >>> 1;
            int cmp = values[mid].key().compareTo(key);
            if (cmp < 0) {
                low = mid + 1;
            } else if (cmp > 0) {
                high = mid - 1;
            } else {
                return mid;
            }
        }
        return -(low + 1);
    }

    public V get(Object key) {
        @SuppressWarnings("unchecked")
        K k = (K) key;
        int i = findIndex(k);
        if (i >= 0) {
            return values[i].value();
        }
        return null;
    }
    /*  Version sans System.arraycopy et avec binarySearch
    public V put(K key, V value) {
        if (size == values.length) {
            resize();
        }
        K k = (K) key;
        MapEntry<K, V> cbl = new MapEntry<>(k, null);
        int i = Arrays.binarySearch(values, 0, size, cbl, (e1, e2) -> e1.key().compareTo(e2.key()));
        if (i >= 0) {
            V temp = values[i].value();
            values[i] = new MapEntry<>(key, value);
            return temp;
        } else {
            i = -(i + 1);
            for (int j = size; j > i; j--) {
                values[j] = values[j - 1];
            }
            values[i] = new MapEntry<>(key, value);
            size++;
            return null;
        }
    }
        */
    public V put(K key, V value) {
        if (size == values.length) {
            resize();
        }
        int i = findIndex(key);
        if (i >= 0) {
            V temp = values[i].value();
            values[i] = new MapEntry<>(key, value);
            return temp;
        } else {
            i = -i - 1; /*got around to 
                        reading the docs which led me to the "useful index" binarySearch 
                        returns if the element is not found*/
            System.arraycopy(values, i, values, i + 1, size - i);
            values[i] = new MapEntry<>(key, value);
            size++;
            return null;
        }
    }
    public V remove(K key) {
        K k = (K) key;
        int i = findIndex(k);
        if (i >= 0) {
            V temp = values[i].value();
            System.arraycopy(values, i + 1, values, i, size - i - 1);
            values[size - 1] = null;
            size--;
            return temp;
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