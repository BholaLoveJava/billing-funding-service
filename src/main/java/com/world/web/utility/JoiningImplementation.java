package com.world.web.utility;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;

class Joining implements Collector<String, StringBuffer, String> {

    /**
     * supplier function
     * @return Supplier
     */
    @Override
    public Supplier<StringBuffer> supplier() {
        return StringBuffer::new;
    }

    /**
     * accumulator function
     * @return BiConsumer
     */
    @Override
    public BiConsumer<StringBuffer, String> accumulator() {
        return StringBuffer::append;
    }

    /**
     * combiner function
     * @return BinaryOperator
     */
    @Override
    public BinaryOperator<StringBuffer> combiner() {
        return StringBuffer::append;
    }

    /**
     * finisher function
     * @return Function
     */
    @Override
    public Function<StringBuffer, String> finisher() {
        return StringBuffer::toString;
    }

    /**
     * characteristics function
     * @return Set
     */
    @Override
    public Set<Characteristics> characteristics() {
        return Set.of();
    }
}

public class JoiningImplementation {

    public static void main(String[] args) {
        Collection<String> stringCollection = List.of("two", "three", "four", "five", "six", "seven", "eight", "nine", "ten");
        String strJoinerResult = stringCollection.stream().collect(new Joining());
        System.out.println("Result :: " + strJoinerResult);
    }
}
