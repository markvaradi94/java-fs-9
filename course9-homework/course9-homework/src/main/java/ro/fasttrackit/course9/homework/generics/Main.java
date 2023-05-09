package ro.fasttrackit.course9.homework.generics;

import java.io.File;

public class Main {
    public static void main(String[] args) {
        Box<String> stringBox = new Box<String>("string");
        Box<Integer> intBox = new Box<Integer>(1);

        Pair<Integer, File> filePair = new Pair<>(10, new File("test.txt"));
        Pair<Integer, String> stringPair = new Pair<>(5, "hello");
        Pair<String, Long> longPair = new Pair<>("hello", 42L);

        processBox(stringBox);
        processPair(filePair);

        System.out.println(stringPair.transform(Pair::getValue));
        System.out.println(Pair.sum(5, 2.5));
    }

    private static <V> V getValue(Pair<String, V> pair) {
        return pair.getValue();
    }

    private static void processBox(Box<String> box) {
        String value = box.getValue();
    }

    private static void processPair(Pair<Integer, File> pair) {
        Integer key = pair.getKey();
        File value = pair.getValue();
    }
}
