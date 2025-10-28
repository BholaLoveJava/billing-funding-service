package com.world.web.utility;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.function.BiFunction;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class StreamConcatUtility {
    public static void main(String[] args) {

        List<Integer> list0 = List.of(1, 2, 3);
        List<Integer> list1 = List.of(4, 5, 6);
        List<Integer> list2 = List.of(7, 8, 9);

        streamConcatMethod(list0, list1, list2);

        splIteratorsOpt();
        streamCharacterOpt();
        fileReadStream();
        reduceUsingBinaryOperator();
        reduceWithIdentity();
        collectingStreamElements();
        streamCharactersOpt();
    }

    private static void streamConcatMethod(List<Integer> list0, List<Integer> list1, List<Integer> list2) {
        //Pattern 1 - Concat
        System.out.println("Pattern 1 Concat");
        List<Integer> mergedList = Stream.concat(list0.stream(), list1.stream()).toList();
        printListData(mergedList);

        //Pattern 2 - flatMap
        System.out.println("Pattern 2 flatMap");
        List<Integer> mergedListFlatMap = Stream.of(list0.stream(), list1.stream(), list2.stream())
                .flatMap(Function.identity())
                .toList();
        printListData(mergedListFlatMap);
    }

    private static void printListData(List<Integer> list) {
        list.forEach(System.out::println);
    }

    private static void splIteratorsOpt() {
        System.out.println("Stream Utility Methods");
        Iterator<Integer> iterator = new Iterator<Integer>() {
            int index = 0;

            @Override
            public boolean hasNext() {
                return index < 10;
            }

            @Override
            public Integer next() {
                return index++;
            }
        };

        long estimatedSize = 10L;
        int characteristics = 0;

        Spliterator<Integer> spliterator = Spliterators.spliterator(iterator, estimatedSize, characteristics);
        Stream<Integer> streamInteger = StreamSupport.stream(spliterator, false);
        streamInteger.forEach(System.out::println);

        //Empty Stream
        Stream<String> emptyStream = Stream.empty();
        List<String> emptyList = emptyStream.toList();
        System.out.println("Empty List :: " + emptyList);

        Stream<String> streamSupplier = Stream.generate(() -> "Bhola");
        List<String> supplierListResult = streamSupplier.limit(5).toList();
        System.out.println("Generate Result :: " + supplierListResult);

        System.out.println("Stream Iterate");
        //seed     predicate                 next
        Stream<String> streamIterate = Stream.iterate("+", str -> str.length() < 5, str -> str + "+");
        streamIterate.toList().forEach(System.out::println);

        System.out.println("Random ints");
        Random random = new Random(314L);
        List<Integer> randomDataResult = random.ints(10, 1, 5).boxed().toList();
        randomDataResult.forEach(System.out::println);

        System.out.println("Random String with Probability");
        List<String> randomString =
                random.doubles(1_000, 0d, 1d)
                        .mapToObj(rand ->
                                rand < 0.5 ? "A" :
                                        rand < 0.8 ? "B" :
                                                rand < 0.9 ? "C" :
                                                        "D")
                        .toList();
        Map<String, Long> stringMapCount = randomString.stream()
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
        System.out.println("Map count Result :: " + stringMapCount);

    }

    private static void streamCharacterOpt() {
        String sentence = "Hello Bhola";
        //Java 11 chars and mapToObj Character::toString
        List<String> stringList = sentence.chars()
                .mapToObj(Character::toString)
                .toList();
        stringList.forEach(System.out::println);

        //Java 10
        List<String> stringListOld = sentence.chars()
                .map(codePoint -> (char) codePoint)
                .mapToObj(Objects::toString)
                .toList();
        stringListOld.forEach(System.out::println);

        String statement = "For there is good news yet to hear and fine things to be seen";
        Pattern pattern = Pattern.compile(" ");
        //No Array creation , so less overhead
        //It is better memory-wise, and in some cases, CPU-wise.
        List<String> patternStringList = pattern.splitAsStream(statement).toList();
        patternStringList.forEach(System.out::println);
    }

    /**
     * The try-with-resources pattern will call the close() method of your stream,
     * which will in turn properly close the text file you have parsed.
     */
    private static void fileReadStream() {
        Path filePath = Path.of("src/main/resources/testRead.txt");
        try(Stream<String> lines = Files.lines(filePath)){
          long count = lines.filter(str -> str.contains("Bhola"))
                            .count();
          System.out.println("Count result :: "+count);
        } catch(IOException ex){
            System.out.println("Exception message :: "+ex.getMessage());
        }
    }

    /**
     * Get sum result using BinaryOperator
     * @param listInt as int list
     * @param binaryOperator as input parameter of type BinaryOperator
     * @return int
     */
    private static int reduce(List<Integer> listInt, BinaryOperator<Integer> binaryOperator) {
        int sum = listInt.get(0);
        for(int i = 1; i < listInt.size(); i++) {
            sum = binaryOperator.apply(sum, listInt.get(i));
        }
        return sum;
    }

    /**
     * Get max result using BinaryOperator
     * @param listInt as int list
     * @param maxBinaryOperator as input parameter of type BinaryOperator
     * @return int
     */
    private static int max(List<Integer> listInt, BinaryOperator<Integer> maxBinaryOperator) {
        int max = listInt.get(0);
        for(int i = 1; i< listInt.size(); i++) {
            max = maxBinaryOperator.apply(max, listInt.get(i));
        }
        return max;
    }

    /**
     * you can indeed compute a reduction by just providing a binary operator that operates on only two elements.
     * This is how the reduce() method works in the Stream API.
     * In some cases it may be very easy: SUM, MIN, MAX, are well-known associative operators. In some other cases, it may be much harder.
     * A way to check for that property can be just to run your binary operator on random data and verify if you always get the same result.
     * If you do not, then you know your binary operator is not associative.
     * If you do, then, unfortunately, you cannot conclude reliably.
     */
    private static void reduceUsingBinaryOperator() {
       List<Integer> listInt =  List.of(3,6,2,1);
       System.out.println("Sum using Binary Operator");
       BinaryOperator<Integer> binaryOperator = Integer::sum;
       System.out.println("Sum result :: "+reduce(listInt, binaryOperator));

       System.out.println("Max using Binary Operator");
       BinaryOperator<Integer> maxBinaryOperator = (a, b) -> a > b ? a : b;
       System.out.println("Max Result :: "+max(listInt, maxBinaryOperator));

       System.out.println("Parallel Stream reduce simulation");
       int reduce1 =  reduce(listInt.subList(0,2), binaryOperator);
       int reduce2 =  reduce(listInt.subList(2,4), binaryOperator);
       int sumResult = binaryOperator.apply(reduce1, reduce2);
       System.out.println("Reduce sum result :: "+sumResult);
    }

    /**
     * Reduce using Identity Operator
     */
    private static void reduceWithIdentity() {
        List<Integer> listInt = List.of(3,6,2,1);
        BinaryOperator<Integer> sumBinaryOperator = Integer::sum;
        int result = 0; //identity as 0
        for(int data: listInt) {
            result = sumBinaryOperator.apply(result, data);
        }
        System.out.println("Sum  result :: "+result);

        System.out.println("Fusion Reduction and Mapping");
        Stream<String> strings = Stream.of("One", "Two", "Three", "Four");
        BinaryOperator<Integer> combiner = Integer::sum;
        Function<String, Integer> mapper = String::length;
        BiFunction<Integer, String, Integer> accumulator = (partialReduction, element) -> partialReduction + mapper.apply(element);
        int sumResult = strings.reduce(0, accumulator, combiner);
        System.out.println("Sum result :: "+sumResult);
    }

    /**
     *
     */
    private static void collectingStreamElements() {
        Stream<Integer> integerStream = IntStream.range(0, 20).boxed();
        /*
         * Collecting in Plain ArrayList
         * An instance of ArrayList is built on an internal array that has a fixed size. This array can become full.
         * In that case, the ArrayList implementation detects it and copies it into a larger array.
         * This mechanism is transparent for the client, but it comes with an overhead: copying this array takes some time.
         */
        System.out.println("Collecting in Plain ArrayList");
        List<String> listString = integerStream.map(data -> Integer.toString(data))
                .collect(Collectors.toCollection(() -> new ArrayList<>(20)));
        listString.forEach(System.out::println);

        /*
         * Starting with Java SE 16, there is a better way to collect your data in an immutable list, which can be more efficient on some cases.
         * Collecting in Immutable List
         */
        System.out.println("Collecting in Immutable List");
        Stream<Integer> integersStream = IntStream.range(0, 20).boxed();
        List<String> listData = integersStream.map(data -> Integer.toString(data))
                .toList();
        listData.forEach(System.out::println);

        /*
         * Collecting in an Array
         * If you need to build an array of strings of characters, then the implementation of this function is String[]::new.
         */
        System.out.println("Collecting in an Array");
         Stream<String> stringStream = Stream.of("one","two","three","four","five","six","seven","eight");
         String[] stringArray =
                 stringStream.filter(data -> data.length() > 3)
                 .map(String::toUpperCase)
                 .toArray(String[]::new);
         for(String str : stringArray){
             System.out.println(str);
         }

         /*
          * Some of them require the processing of all the data consumed by your stream.
          * This is the case of the COUNT, MAX, MIN, AVERAGE operations, as well as the forEach(), toList(), or toArray() method calls.
          *
          * Short-Circuiting the Processing of a Stream
          * The findFirst() or findAny() methods will stop processing your data as soon as an element is found, no matter how many elements are left to be processed.
          * The same goes for anyMatch(), allMatch(), and noneMatch():
          * they may interrupt the processing of the stream with a result without having to consume all the elements your source can produce.
          * These three methods take a predicate as an argument.
          * anyMatch(predicate): returns true if one element of the stream is found, that matches the given predicate.
          * allMatch(predicate): returns true if all the elements of the stream match the predicate.
          * noneMatch(predicate): returns true if none of the elements match the predicate.
          * Returning false for anyMatch() also needs to process all the elements of the stream.
          * Returning true for allMatch() and noneMatch() also needs to process all the elements of the stream.
          * These methods are called short-circuiting methods in the Stream API because they can produce a result without
          * having to process all the elements of your stream.
          */
        Collection<String> stringCollection = List.of("one","two", "three","four","five","six","seven","eight","nine","ten");
        boolean noBlankStr =  stringCollection.stream().allMatch(Predicate.not(String::isEmpty));
        boolean lengthStr =  stringCollection.stream().anyMatch(str -> str.length() == 4);
        boolean lengthGtStr = stringCollection.stream().noneMatch(str -> str.length() == 11);
        System.out.println("allMatch result :: "+noBlankStr);
        System.out.println("anyMatch result :: "+lengthStr);
        System.out.println("noneMatch result :: "+lengthGtStr);
    }

    /**
     *
     */
    private static void streamCharactersOpt() {
        Predicate<Stream<?>> isOrdered = stream -> ((stream.spliterator().characteristics() & Spliterator.ORDERED) != 0);
        Stream<Integer> integerStream = Stream.of(1,2,3,4,5);
        boolean orderedResult  = isOrdered.test(integerStream);
        System.out.println("Ordered result :: "+orderedResult);
        /*
         * Keeping a stream ORDERED may be costly in some cases, for instance when you are using parallel streams.
         */
    }
}

/*
 *The reason why it is better to use the flatMap() way is that concat() creates intermediates streams during the concatenation.
 * When you use Stream.concat(), a new stream is created to concatenate your two streams.
 * If you need to concatenate three streams, you will end up creating a first stream to handle the first concatenation,
 * and a second one for the second concatenation.
 * So each concatenation requires a stream that will be thrown away at the end of the operation.
 * With the flatmap pattern, you just create a single stream to hold all your streams and do the flatmap.
 * The overhead is much lower.
 *
 * If the size of the source of the two streams you are concatenating is known,
 * then the size of the resulting stream is known too. In fact, it is simply the sum of the two concatenated streams.
 * Using flatmap on a stream may create an unknown number of elements to be processed in the resulting stream.
 * The Stream API loses track of the number of elements that will be processed in the resulting stream.
 *
 * https://dev.java/learn/api/streams/characteristics/
 */