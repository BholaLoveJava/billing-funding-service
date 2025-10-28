package com.world.web.utility;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.function.BinaryOperator;
import java.util.function.IntSupplier;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class StreamUtility {

    private static final Integer high = 1000;
    private static final Integer low = 400;

    public static void main(String[] args) {
        multiMapMethod();
        streamsMultiMapExample();

        System.out.println("\n");
        collectorsTeeingExample();
        processOptionalListData();
    }

    private static void multiMapMethod() {
        var  result =   IntStream.rangeClosed(1, 10)
                .mapMulti((value, ic) -> {
                         ic.accept(value);
                });
        result.forEach(System.out::println);
    }

    private static void streamsMultiMapExample() {
        Predicate<Integer> evenPredicate = num -> num % 2 == 0;
        Predicate<Integer> oddPredicate =  num -> num % 2 != 0;

        /*filter based on even predicate*/
        var evenList =  IntStream.range(1, 10).boxed().filter(evenPredicate).toList();
         printResult(evenList, "even list result data");

         /*filter based on odd predicate*/
        var oddList = IntStream.range(1, 10).boxed().filter(oddPredicate).toList();
        printResult(oddList, "odd list result data");

        /*sort data using Comparator*/
        var sortedResult = IntStream.rangeClosed(1, 10).boxed()
                .sorted(Comparator.comparingInt(Integer::intValue))
                .toList();
        printResult(sortedResult, "Sorted integer data result");

       /*max data using max and comparator*/
       var maxResult = IntStream.rangeClosed(1, 10)
                                .boxed()
                                .max(Comparator.comparingInt(Integer::intValue));
       System.out.println("Max result data :: "+maxResult);

       /*min data using min and comparator*/
       var minResult = IntStream.rangeClosed(1, 10)
                                 .boxed()
                                 .min(Comparator.comparingInt(Integer::intValue));
       System.out.println("Min result data :: "+minResult);

       /*sum, using reduce terminal operator */
        var sumResult = IntStream.rangeClosed(1, 10)
                                 .boxed()
                                 .reduce(0, Integer::sum);
        System.out.println("Sum using reduce function :: "+sumResult);
        var evenSumResult = IntStream.rangeClosed(1, 10)
                                      .boxed()
                                      .filter(evenPredicate)
                                      .reduce(0, Integer::sum);
        System.out.println("Even sum using filter and reduce function :: "+evenSumResult);

        /* */
        var result = IntStream.rangeClosed(1, 10)
                              .boxed()
                              .reduce(BinaryOperator.maxBy(Comparator.comparingInt(Integer::intValue)));
        System.out.println("Max result by BinaryOperator :: "+result);

        /* Stream allMatch and noneMatch method examples*/
        var allMatchResult = IntStream.rangeClosed(1, 10)
                                      .boxed()
                                      .allMatch(data -> data > 0);
        System.out.println("All Match Result :: "+allMatchResult);

        var noneMatchResult = IntStream.rangeClosed(1, 10)
                                       .boxed()
                                       .noneMatch(data -> data < 0);
        System.out.println("None Match Result :: "+noneMatchResult);

        /*Stream findFirst and firstAny Terminal method examples */
        System.out.println("findFirst Demo");
        IntStream.rangeClosed(1, 10)
                         .boxed()
                         .findFirst()
                         .ifPresent(System.out::println);

        System.out.println("findAny Demo");
        IntStream.rangeClosed(1, 10)
                .boxed()
                .findAny()
                .ifPresentOrElse(System.out::println, null);

        /*dropWhile method examples */
        System.out.println("Drop-While Test");
       IntStream.rangeClosed(1, 10)
                .boxed()
                .dropWhile(evenPredicate)
                .peek(System.out::println)
                .findFirst()
                .ifPresent(System.out::println);

        /*stream limit method examples*/
        var limitResult = IntStream.rangeClosed(1, 10)
                                   .boxed()
                                   .filter(oddPredicate)
                                   .limit(1);
        System.out.println("Limit method result :: "+limitResult);

        /*Concat two IntStream source examples */
       var streamSource1 = IntStream.rangeClosed(1, 10);
       var streamSource2 = IntStream.rangeClosed(20, 30);

       var mergedIntStreamResult = IntStream.concat(streamSource1, streamSource2).boxed().toList();
       printResult(mergedIntStreamResult, "Merged IntStream Result");

       /* IntStream mapToObj method example*/
       var  mapToObjResult = IntStream.rangeClosed(1, 10)
               .mapToObj(value -> {
                      return value + 10;
               }).toList();
       printResult(mapToObjResult, "MapToObj Result");

       var toArrayResult = IntStream.rangeClosed(1, 10).boxed().toArray();
       printArrayData(toArrayResult, "toArray method result");

       /*IntStream generate method example */
        System.out.println("IntStream generate supplier example");
        IntStream.generate(new IntSupplier() {
            @Override
            public int getAsInt() {
                return getRandomNumber();
            }
        }).boxed()
          .limit(5)
          .forEach(System.out::println);

        /*IntStream Collectors.groupingBy() examples */
        System.out.println("IntStream Collectors.groupingBy Result");
        IntStream.rangeClosed(1, 10)
                .boxed()
                .collect(Collectors.groupingBy(data -> data > 2))
                .forEach((key,value) ->System.out.println(key +""+value));

        /*IntStream forEachOrdered Examples */
        System.out.println("IntStream forEachOrdered Result");
        IntStream.rangeClosed(1, 10)
                .boxed()
                .filter(oddPredicate)
                .skip(1)
                .limit(1)
                .peek(System.out::println)
                .forEachOrdered(System.out::println);
    }

    public static int getRandomNumber() {
        return new Random(1000).nextInt();
    }

    private static void printArrayData(Object[] array, String description) {
        System.out.println(description);
        Stream.of(array).forEach(System.out::println);
    }

    private static void printResult(List<Integer> data, String description) {
        System.out.println(description);
        Stream.of(data).forEach(System.out::println);
    }

    /**
     * Java 12 Features Collectors.teeing()
     */
    public static void collectorsTeeingExample() {
       List<Integer> listData =  List.of(1,2,3,4,5,6);
       String result = listData.stream().collect(Collectors.teeing(
               Collectors.summingInt(Integer::intValue),
               Collectors.counting(),
               (sum, count) -> "Sum :: "+sum+" ,Count :: "+count));
       System.out.println("Result :: " + result);
    }

    /**
     * Java 9 Features
     */
    public static void processOptionalListData() {
        List<Optional<String>>  optionals = List.of(Optional.of("A"), Optional.empty(), Optional.of("B"));
        List<String> listData =
                optionals.stream()
                         .filter(Predicate.not(Optional::isEmpty))
                         .flatMap(Optional::stream)
                         .toList();
        listData.forEach(System.out::println);
    }
}
