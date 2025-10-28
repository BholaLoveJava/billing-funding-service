package com.world.web.utility;

import java.util.List;
import java.util.function.Predicate;

public class StreamFilterUtility {

    public static void main(String[] args) {

        List<Integer> listData = List.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 15, 20);
        Predicate<Integer> numLessThanFive = (num) -> num < 5;
        /*
         * filter -> 1,2,3,4
         * Checks all elements. Keeps those that match condition, anywhere.
         */
        System.out.println("filter example");
        listData.stream()
                .filter(numLessThanFive)
                .forEach(System.out::println);

        /* dropWhile -> 5,6,7,8,9,10,15,20
         * Drops elements from start while condition is true. Keeps the rest.
         */
        System.out.println("dropWhile example");
        listData.stream()
                .dropWhile(numLessThanFive)
                .forEach(System.out::println);

        /*
         * takeWhile -> 1,2,3,4
         * Takes elements from start while condition is true. Stops at first false.
         */
        System.out.println("takeWhile example");
        listData.stream()
                .takeWhile(numLessThanFive)
                .forEach(System.out::println);
    }
}
