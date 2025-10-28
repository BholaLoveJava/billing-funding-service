package com.world.web.utility;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Stream;

public class FunctionUtility {

    public static void main(String[] args) {

        List<String> strings = List.of("1", " ", "2", "3 ", "", "3");

        flatMapFunction(strings);
        System.out.println("mapMulti Example");
        flatMapFunctionOpt(strings);
    }

    /**
     * @param strings as input List of String
     */
    private static void flatMapFunction(List<String> strings) {
        /*
         * Map Function
         * There is one stream created for each element of the stream you need to process. Starting with Java SE 16,
         * a method has been added to the Stream API, exactly to manage this case.
         * This method is called mapMulti() and takes a BiConsumer as an argument.
         */
        Function<String, Stream<Integer>> mapperFunction = (str) -> {
            try {
                return Stream.of(Integer.parseInt(str));
            } catch (NumberFormatException ex) {
                System.out.println("Exception message :: " + ex.getMessage());
            }
            return Stream.empty();
        };

        //Processing the List of String elements using FlatMap
        List<Integer> intList = strings.stream()
                .flatMap(mapperFunction)
                .toList();
        //Printing the Result data
        intList.forEach(System.out::println);
    }

    /**
     *
     * @param strings as input List String
     */
    private static void flatMapFunctionOpt(List<String> strings) {
        /*
         * All the faulty strings have been silently removed, but this time, no other stream has been created.
         */
        List<Integer> integerList =
                strings.stream()
                        .<Integer>mapMulti((string, consumer) -> {
                            try {
                                consumer.accept(Integer.parseInt(string));
                            } catch (NumberFormatException ex) {
                                System.out.println("Exception message :: " + ex.getMessage());
                            }
                        }).toList();
        //Printing the Result data
        integerList.forEach(System.out::println);
    }
}

/*
 * https://dev.java/learn/api/streams/intermediate-operation/
 */