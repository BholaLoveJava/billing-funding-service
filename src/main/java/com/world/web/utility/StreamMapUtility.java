package com.world.web.utility;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

public class StreamMapUtility {

    public static void main(String[] args) {

        /*Create names List*/
        List<String> names = Arrays.asList("Bhola","Rohit","Sumit","Nitin");

        /*Iterate and map the name based on filter*/
        Predicate<String> nameFilter = "Nitin"::equals;
        String result = names.stream()
                .filter(nameFilter)
                .map(String::toUpperCase)
                .findFirst()
                .orElseGet(() -> "default");
        System.out.println("Result data :: "+result);

        /*Using other approach to reproduce the NullPointerException*/
        try {
            Optional<String> optionalResult =
                    names.stream()
                            .filter(nameFilter)
                            .map(StreamMapUtility::mapperFunction)
                            .findFirst();
            System.out.println("Result data ::" + optionalResult.orElse("not found"));
        } catch(NullPointerException ex) {
            ex.printStackTrace();
            System.out.println("Exception message :: "+ex.getMessage());
        }
        /*Using other approach to fix this NullPointerException*/
        Optional<Optional<String>> optionalResultFix = names.stream()
                                      .filter(nameFilter)
                                      .map(StreamMapUtility::mapperFunctionOptional)
                                      .findFirst();
        /*Will throw NoSuchElementException, is we use s.get() inside println */
        optionalResultFix.ifPresent(s -> System.out.println("Result data :: " + s));
    }

    /**
     * Method Explicitly returns null, to reproduce NullPointerException
     * @param input as String input
     * @return null
     */
    public static String mapperFunction(String input) {
        return null;
    }

    public static Optional<String> mapperFunctionOptional(String input) {
        return Optional.empty();
    }
}
