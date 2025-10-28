package com.world.web.utility;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ParallelStreamUtility {

    public static void main(String[] args) {

        Set<String> threadNames =
        IntStream.range(0, 10)
                .parallel()
                .mapToObj(data -> Thread.currentThread().getName())
                .collect(Collectors.toSet());
        System.out.println("Set threads data");
        threadNames.forEach(System.out::println);

       /*
        * If you process your stream sequentially, all the elements are processed in the thread that runs your method.
        * If you process the same stream in parallel, the elements are processed by a thread from the Common Fork/Join pool.
        * Any access to a non-concurrent, external element may lead to race conditions and data inconsistency.
        * It is an anti-pattern for a stream to mutate a state external to this stream.
        */
        System.out.println("External element may lead to Race Conditions and Data inconsistency");
        List<Integer> integerList = new ArrayList<>();
        IntStream.rangeClosed(1, 1000)
                .parallel()
                .forEach(integerList::add);
        System.out.println("List size :: "+integerList.size());

        /*
         * Encounter Order
         * There are cases where the order in which the data is processed is significant in the Stream API. This is the case for the following methods.
         * limit(n): limits the processing to the n first elements of this stream.
         * skip(n): skips the processing for the n first elements of this stream.
         * findFirst(): find the first element of the stream.
         * These three methods need to remember in what order the elements of the stream are processed and need to count the elements to produce a correct result.
         * There are called stateful operations, because they need to carry an internal state to work.
         * Having such stateful operations leads to overheads in parallel streams.
         * For instance, limit() needs an internal counter to work correctly.
         * In parallel, this internal counter is shared among different threads.
         * Sharing a mutable state between threads is costly and should be avoided.
         */

        /*
         * Rule #1 Do not optimize because it's fun; optimize because you have requirements and you do not meet them.
         * Rule #2 Choose your source of data with caution.
         * Rule #3 Do not modify external state, and do not share mutable state.
         * Rule #4 Do not guess; measure the performance of your code.
         */
    }
}
