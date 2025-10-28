package com.world.web.utility;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;

class ToList<T> implements Collector<T, List<T>, List<T>> {

    /**
     * supplier function
     * @return  Supplier
     */
    @Override
    public Supplier<List<T>> supplier() {
        return ArrayList::new;
    }

    /**
     * accumulator function
     * @return BiConsumer
     */
    @Override
    public BiConsumer<List<T>, T> accumulator() {
        return Collection::add;
    }

    /**
     * combiner function
     * @return BinaryOperator
     */
    @Override
    public BinaryOperator<List<T>> combiner() {
        return (list1, list2) -> {
            list1.addAll(list2);
            return list1;
        };
    }

    /**
     * finisher function
     * @return Function
     */
    @Override
    public Function<List<T>, List<T>> finisher() {
        return Function.identity();
    }

    /**
     * characteristics function
     * @return Set
     */
    @Override
    public Set<Characteristics> characteristics() {
        return Set.of(Characteristics.IDENTITY_FINISH);
    }
}
public class ToListImplementation {

    public static void main(String[] args) {
    Collection<String>  stringCollection = List.of("one","two","three","four","five","six","seven","eight","nine","ten");
    List<String> stringList = stringCollection.stream()
                                              .collect(new ToList<>());
    System.out.println("List result :: "+stringList);
    }
}
