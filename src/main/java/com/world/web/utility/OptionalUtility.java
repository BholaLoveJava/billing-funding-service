package com.world.web.utility;

import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class OptionalUtility {

    public static void main(String[] args) {
        /*
         * The Optional class is a final class with private constructor. So, the only way you have to create an instance of it is by calling one of its factory methods.
         * There are three of them.
         * You can create an empty optional by calling Optional.empty().
         * You can wrap a non-null element by calling Optional.of() with this element as an argument.
         * Passing a null reference to this method is not allowed. You will get a NullPointerException in this case.
         * You can wrap any element by calling Optional.ofNullable() with this element as an argument.
         * You may pass a null reference to this method. In this case, you will get an empty optional.
         *
         * orElseThrow() is the preferred pattern since Java SE 10. It does the same as the get() method, but its name does not leave any
         *               doubt that it can throw a NoSuchElementException.
         * orElseThrow(Supplier exceptionSupplier): does the same as the previous method. It uses the supplier you pass as an argument to
         *            create the exception that it throws.
         *
         * You can also try to get the content of an optional object and provide an object that will be returned in case the optional is empty.
         * orElse(T returnedObject): returns the argument if called on an empty optional.
         * orElseGet(Supplier<T> supplier): does the same as the previous one, without having to build the returned object,
         * in case building this object proves to be expensive. Indeed, the provided supplier is invoked only if needed.
         * Lastly, you can create another optional if this optional is empty.
         * or(Supplier<Optional> supplier): returns this unmodified optional if it is not empty and calls the provided supplier if it is.
         * This supplier creates another optional that is returned by this method.
         */
        //record Author class
        record Author(String name) implements Comparable<Author> {

            /**
             * @param author the object to be compared.
             * @return int
             */
            @Override
            public int compareTo(Author author) {
                return this.name.compareTo(author.name);
            }
        }

        //record Article class
        record Article(String title, int inceptionYear, List<Author> authors) {
        }

        //record PairOfAuthors class
        record PairOfAuthors(Author first, Author second) {
            public static Optional<PairOfAuthors> of(Author first, Author second) {
                if (first.compareTo(second) > 0) {
                    return Optional.of(new PairOfAuthors(first, second));
                } else {
                    return Optional.empty();
                }
            }
        }

        Author maria = new Author("Maria");
        Author james = new Author("James");
        Author patricia = new Author("Patricia");
        Author michael = new Author("Michael");

        Article a0 = new Article("About As You Like It", 2015, List.of(maria));
        Article a1 = new Article("About King John", 2015, List.of(james));
        Article a2 = new Article("About The Winter's Tale", 2016, List.of(patricia));
        Article a3 = new Article("About Richard II", 2017, List.of(michael));
        Article a4 = new Article("About Richard III", 2019, List.of(maria, patricia));
        Article a5 = new Article("About Henry VIII", 20219, List.of(patricia, michael));
        Article a6 = new Article("About Romeo and Juliet", 2020, List.of(maria, patricia, james));
        Article a7 = new Article("About Macbeth", 2021, List.of(maria, james, michael));
        Article a8 = new Article("About Hamlet", 2021, List.of(patricia, james, michael));
        Article a9 = new Article("About King Lear", 2022, List.of(maria, james, patricia, michael));
        Collection<Article> articles = List.of(a0, a1, a2, a3, a4, a5, a6, a7, a8, a9);

        //BiFunction which accepts Article and Author as input and returns output as Stream<PairOfAuthors>
        BiFunction<Article, Author, Stream<PairOfAuthors>> buildPairOfAuthors =
                (article, firstAuthor) -> article.authors()
                                                 .stream()
                        .flatMap(secondAuthor -> PairOfAuthors.of(firstAuthor, secondAuthor).stream());

        //Function returns Stream<PairOfAuthors> by invoking buildPairOfAuthors
        Function<Article, Stream<PairOfAuthors>> toPairOfAuthors =
                (article) -> article.authors()
                                    .stream()
                                    .flatMap(firstAuthor -> buildPairOfAuthors.apply(article, firstAuthor));

        //Grouping the Articles based on toPairOfAuthors, using Function.identity() and counting()
        Map<PairOfAuthors, Long> numberOfAuthorsTogether =
                articles.stream()
                        .flatMap(toPairOfAuthors)
                        .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

        //Function to extract Max Map.Entry value from the Map object
        Function<Map<PairOfAuthors, Long>, Map.Entry<PairOfAuthors, Long>> mapMaxExtractor =
                mapAuthorsPair -> mapAuthorsPair.entrySet()
                        .stream()
                        .max(Map.Entry.comparingByValue())
                        .orElseThrow();

        //Invoking MaxExtractor to get the Max Map.Entry data
        Map.Entry<PairOfAuthors, Long> pairResult = mapMaxExtractor.apply(numberOfAuthorsTogether);

        System.out.println("The Authors that published the most together are ");
        System.out.println("1st Approach Result :: "+pairResult);

        //Another way to get the same result with better code
        Map.Entry<PairOfAuthors, Long> mapPairResult =
        articles.stream()
                .flatMap(toPairOfAuthors)
                .collect(
                        Collectors.collectingAndThen(
                        //groupingBy first argument
                        Collectors.groupingBy(Function.identity(), Collectors.counting()),
                        //finisher second argument
                        mapPairOfAuthor ->
                                mapPairOfAuthor.entrySet()
                                        .stream()
                                        .max(Map.Entry.comparingByValue())
                                        .orElseThrow()
                ));
        System.out.println("The Authors that published the most together are ");
        System.out.println("2nd Approach Result :: "+mapPairResult);

        //Other way to get same result with better and optimized code
        Collector<PairOfAuthors, ?, Map.Entry<PairOfAuthors, Long>> mapPairOfAuthorCollection =
                Collectors.collectingAndThen(
                        Collectors.groupingBy(Function.identity(), Collectors.counting()),
                        mapPairOfAuthors -> mapPairOfAuthors.entrySet()
                                .stream()
                                .max(Map.Entry.comparingByValue())
                                .orElseThrow()
                );
        //Use this supplier, combiner and finisher in a single stream
        Map.Entry<PairOfAuthors, Long> mapPairAuthorResult =
                articles.stream()
                        .flatMap(toPairOfAuthors)
                        .collect(mapPairOfAuthorCollection);
        System.out.println("The Authors that published the most together are ");
        System.out.println("3rd Approach Result :: "+mapPairAuthorResult);

        /*
         * Thanks to the flatMapping() collector, you can write this code with a single collector by merging the intermediate flatMap() and the terminal collector.
         */
        Map.Entry<PairOfAuthors, Long> flatMapPairAuthorResult =
                articles.stream().collect(
                        Collectors.flatMapping(
                                toPairOfAuthors,
                                mapPairOfAuthorCollection
                        )
                );
        System.out.println("The Authors that published the most together are ");
        System.out.println("4th Approach Result :: "+flatMapPairAuthorResult);

        /*
         * Finding the two authors that published the most, per year, is just a matter of passing this
         * flatMapping() collector as a downstream collector to the right groupingBy().
         */
        /*To fix NoSuchElementException, we have introduced Optional and remove the orElseThrow() */
        Collector<PairOfAuthors, ?, Optional<Map.Entry<PairOfAuthors, Long>>>  optionalMapPairOfAuthor =
        Collectors.collectingAndThen(
                Collectors.groupingBy(Function.identity(), Collectors.counting()),
                mapPairOfAuthor ->
                        mapPairOfAuthor.entrySet()
                                .stream()
                                .max(Map.Entry.comparingByValue())
        );

        Collector<Article, ?, Optional<Map.Entry<PairOfAuthors, Long>>> flatMappingCollector =
                Collectors.flatMapping(
                        toPairOfAuthors,
                        optionalMapPairOfAuthor
                );

        Map<Integer, Optional<Map.Entry<PairOfAuthors, Long>>> mostPublishedAuthor =
                articles.stream()
                        .collect(Collectors.groupingBy(Article::inceptionYear, flatMappingCollector));
        System.out.println("Two Author that Published the most per year");
        System.out.println("Result :: "+mostPublishedAuthor);

        /*
         * having a map in which values are empty optionals is useless and maybe costly. It is an anti-pattern.
         * If the optional is empty, this call returns an empty optional. The mapping function is then ignored.
         * The next call to stream() then returns an empty stream, which will be removed from the main stream because we are in a flatMap() call.
         */
        Map<Integer, Map.Entry<PairOfAuthors, Long>> mostPublishedAuthorResult =
                articles.stream()
                        .collect(Collectors.groupingBy(Article::inceptionYear, flatMappingCollector))
                        .entrySet()
                        .stream()
                        .flatMap(entry ->
                                entry.getValue()
                                        .map(value -> Map.entry(entry.getKey(), value))
                                        .stream())
                        .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        System.out.println("Two Author that Published the most per year");
        System.out.println("Result :: "+mostPublishedAuthorResult);

        /*
         * Three important points of the Stream API and the optionals.
         * The Optional.map() method that returns an empty optional if it is called on an empty optional.
         * The Optional.stream() method that opens a stream on the content of an optional.
         * If the optional is empty, then the returned stream is also empty. It allows you to move from the optional space to the stream space seamlessly.
         * The Stream.flatMap() method that opens the streams built from the optionals, silently removing the empty streams.
         */

        Map<Integer, Optional<String>> integerOptionalMap =
                Map.of(1, Optional.empty(),
                        2, Optional.of("Two"),
                        3, Optional.empty(),
                        4, Optional.of("Four"));
        /*filter out the Optional empty value data from the map */
        Map<Integer, String> finalMapResult =
        integerOptionalMap.entrySet()
                .stream()
                .flatMap(entry -> entry.getValue()
                        .map(value -> Map.entry(entry.getKey(), value))
                        .stream()
                )
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        System.out.println("Removed Optional Value form the Map");
        System.out.println("Result :: "+finalMapResult);

        /*
         * Some Rules to Use Optionals Properly
         * Rule #1 Never use null for an optional variable or returned value.
         * Rule #2 Never call orElseThrow() or get() unless you are sure the optional is not empty.
         * Rule #3 Prefer alternatives to ifPresent(), orElseThrow(), or get().
         * Rule #4 Do not create an optional to avoid testing for the nullity of a reference.
         * Rule #5 Do not use optional in fields, method parameters, collections, and maps.
         * Rule #6 Do not use identity-sensitive operations on an optional object, such as reference equality, identity hash code, and synchronization.
         * Rule #7 Do not forget that optional objects are not serializable.
         */
    }
}

/*
 * https://dev.java/learn/api/streams/optionals/
 * https://docs.spring.io/spring-boot/reference/testing/spring-boot-applications.html
 * https://github.com/kehrlann/spring-boot-testing
 * https://javadoc.io/doc/org.mockito/mockito-core/latest/org.mockito/org/mockito/Mockito.html
 * https://assertj.github.io/doc/
 */