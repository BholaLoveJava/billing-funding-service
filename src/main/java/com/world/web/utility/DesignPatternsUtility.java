package com.world.web.utility;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class DesignPatternsUtility {

    public static void main(String[] args) {

        System.out.println(getName(2).toUpperCase());
        //System.out.println(getName(0).toUpperCase()); // will throw NullPointerException

        /*Now introduced Optional */
        Optional<String> result = getNameOptional(2);
        result.ifPresentOrElse(
                name -> System.out.println(name.toUpperCase()),
                () -> System.out.println("Not Found")
        );

        /*Optional will handle the null scenarios gracefully */
        Optional<String> resultNull = getNameOptional(0);
        resultNull.ifPresentOrElse(
                name -> System.out.println(name.toUpperCase()),
                () -> System.out.println("Not Found"));

        /* Strategy Design Pattern */
        List<Integer> intList = List.of(12, 34, 45, 28, 32, 80, 89);
        /*Number Sum*/
        int numSum = getSumOfNumbers(intList, exp -> true);
        System.out.println("Number sum :: " + numSum);

        /*Even Number Sum*/
        int evenSum = getSumOfNumbers(intList, DesignPatternsUtility::isEven);
        System.out.println("Even number sum :: " + evenSum);

        /*Odd Number Sum*/
        int oddSum = getSumOfNumbers(intList, DesignPatternsUtility::isOdd);
        System.out.println("Odd number sum :: " + oddSum);

       /*
        * Concept of deferred evaluation
        * Two ways to pass data to function
        * 1. Applicative Order
        * 2. Normal Order
        */
        System.out.println("concept of deferred evaluation");
        operateApplicativeOrder(compute(20));

        /*Normal Order Evaluation */
        System.out.println("Normal Order Evaluation");
        operateNormalOrder(() -> compute(20));

    }

    public static String getName(long id) {
        if (id == 0) {
            return null;
        }
        return "some name";
    }

    public static Optional<String> getNameOptional(long id) {
        if (id == 0) {
            return Optional.empty();
        }
        return Optional.of("some name");
    }

    /**
     * Strategies for Even number
     * @param num as input parameter
     * @return boolean
     */
    public static boolean isEven(int num) {
        return num % 2 == 0;
    }

    /**
     * Strategies for Odd number
     * @param num as input parameter
     * @return boolean
     */
    public static boolean isOdd(int num) {
        return num % 2 == 1;
    }

    public static int getSumOfNumbers(List<Integer> intList, Predicate<Integer> selector) {
        return intList.stream()
                .filter(selector)
                .mapToInt(data -> data)
                .reduce(0, Integer::sum);
    }

    public static int compute(int num) {
        System.out.println("Slow Operation");
        return num * 100;
    }

    public static void operateNormalOrder(Supplier<Integer> supplier) {
        if(Math.random() > 0.5) {
            System.out.println("Use the value :: "+supplier.get());
        } else {
            System.out.println("Continue without using the value");
        }
    }

    public static void operateApplicativeOrder(int value) {
       if(Math.random() > 0.5) {
           System.out.println("Use the value :: "+value);
       } else {
           System.out.println("Continue without using the value");
       }
    }
}

/*
 * Return a reference, if the value will always exists.
 * Return an Optional<T>, if the value may or may not exist.
 * Don't use Optional<T> for fields.
 * Don't use Optional<T> for method parameters.
 */