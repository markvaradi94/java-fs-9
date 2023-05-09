package ro.fasttrackit.course9.homework.generics;

public class Pair<K, V> {
    private final K key;
    private final V value;

    public Pair(K key, V value) {
        this.key = key;
        this.value = value;
    }

    public K getKey() {
        return key;
    }

    public V getValue() {
        return value;
    }

    public <A extends UsePair<V>> V transform(A in) {
        return in.transform(this);
    }

    public static <A extends Number, B extends Number> Number sum(A a, B b) {
        return a.intValue() + b.intValue();
    }
}
