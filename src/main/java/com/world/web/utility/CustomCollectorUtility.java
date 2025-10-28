package com.world.web.utility;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.ObjIntConsumer;
import java.util.function.Supplier;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class CustomCollectorUtility {

    public static void main(String[] args) {
        /*
         * With the first three components, you can use the collect() method from the specialized streams of numbers.
         * The IntStream.collect() method takes three arguments:
         * an instance of Supplier, called supplier;
         * an instance of ObjIntConsumer, called accumulator;
         * an instanceof BiConsumer, called combiner.
         */

        Supplier<List<Integer>> supplier = ArrayList::new;
        ObjIntConsumer<List<Integer>> accumulator = Collection::add;
        BiConsumer<List<Integer>, List<Integer>> combiner = Collection::addAll;
        List<Integer> listResult = IntStream.rangeClosed(1, 10)
                .collect(supplier, accumulator, combiner);
        System.out.println("List Result :: " + listResult);

        //Set
        Supplier<Set<Integer>> supplierS = HashSet::new;
        ObjIntConsumer<Set<Integer>> accumulatorS = Collection::add;
        BiConsumer<Set<Integer>, Set<Integer>> combinerS = Collection::addAll;
        Set<Integer> setResult = IntStream.rangeClosed(1, 10)
                .collect(supplierS, accumulatorS, combinerS);
        System.out.println("Set Result :: " + setResult);

        //Collecting Primitive Type in StringBuffer
        Supplier<StringBuffer> supplierSB = StringBuffer::new;
        ObjIntConsumer<StringBuffer> accumulatorSB = StringBuffer::append;
        BiConsumer<StringBuffer, StringBuffer> combinerSB = StringBuffer::append;
        StringBuffer strBufferResult = IntStream.rangeClosed(1, 10)
                .collect(supplierSB, accumulatorSB, combinerSB);
        System.out.println("StringBuffer result :: " + strBufferResult);

        /*
         * The Collector API defines a fourth component precisely to handle this case, which is called a finisher.
         * A finisher is an instance of Function that takes the container in which the elements were accumulated and transforms
         * it to something else. In the case of the Collectors.joining(), this function is just the following.
         * Function<StringBuffer, String> finisher = stringBuffer -> stringBuffer.toString();
         * There are many collectors where the finisher is just the identity function.
         * This is the case for the following collectors: toList(), toSet(), groupingBy(), and toMap().
         *
         * The finisher is used to seal the intermediate container into an immutable container before returning it to your application.
         *
         */

        Collection<String> listString = List.of("two", "three", "four", "five", "six", "seven", "eight", "nine", "ten", "eleven", "twelve");
        Integer keyResult = listString.stream()
                .collect(Collectors.groupingBy(String::length, Collectors.counting()))
                .entrySet()
                .stream()
                .max(Map.Entry.comparingByValue())
                .orElseThrow()
                .getKey();
        System.out.println("Max key value :: " + keyResult);

        // Finisher example
        Function<Map<Integer, Long>, Map.Entry<Integer, Long>> finisher =
                map -> map.entrySet()
                        .stream()
                        .max(Map.Entry.comparingByValue())
                        .orElseThrow();

        Map<Integer, Long> strLenCountMap = listString.stream()
                .collect(Collectors.groupingBy(String::length, Collectors.counting()));
        Integer maxKeyResult = finisher.apply(strLenCountMap).getKey();
        System.out.println("Max key value :: " + maxKeyResult);

        /*
         * Now that you have this function, you can integrate this maximum value extraction step within the collector itself by using collectingAndThen().
         */
        Map.Entry<Integer, Long> mapMaxResult = listString.stream()
                .collect(Collectors.collectingAndThen(
                        Collectors.groupingBy(String::length, Collectors.counting()), finisher
                ));
        System.out.println("Map Max Key Value :: "+mapMaxResult.getKey());

        /*
         * Being able to do that enables the combination of more collectors to conduct more sophisticated computations on your data.
         * They take the three arguments we presented in the previous sections.
         * The supplier used to create the mutable container in which the elements of the stream are accumulated.
         * The accumulator, modeled by a bi-Consumer.
         * The combiner, also modeled by a bi-consumer, used to combine two partially filled containers, used in the case of parallel streams.
         */

        Collector<String, ?, List<String>> listCollector = Collectors.toList();
        List<String> stringList = listString.stream().collect(listCollector);
        System.out.println("Result :: "+stringList);

        Collector<String, ?, Map<Integer, Long>> mapCollector = Collectors.groupingBy(String::length, Collectors.counting());
        Map<Integer, Long> mapCollectorResult = listString.stream().collect(mapCollector);
        System.out.println("Result :: "+mapCollectorResult);
    }
}
