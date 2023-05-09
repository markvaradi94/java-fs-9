package ro.fasttrackit.course9.homework.generics;

public interface UsePair<V> {
    V transform(Pair<?, V> pair);
}
