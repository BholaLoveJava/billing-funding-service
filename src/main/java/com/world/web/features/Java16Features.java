package com.world.web.features;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

record Book(int id, String title, String author, String isbn) {}

/**
 * The sealed keyword is used in conjunction with the permits keyword to determine exactly which classes are
 * allowed to implement this interface. In our example, this is Monkey and Snake.
 * All inheriting classes of a sealed class must be marked with one of the following:
 * sealed – meaning they must define what classes are permitted to inherit from it using the permits keyword.
 * final – preventing any further subclasses
 * non-sealed – allowing any class to be able to inherit from it.
 * The Java language recognizes sealed, non-sealed, and permits as contextual keywords (similar to abstract and extends)
 * Restrict the ability to create local classes that are subclasses of a sealed class
 * (similar to the inability to create anonymous classes of sealed classes).
 * Stricter checks when casting sealed classes and classes derived from sealed classes
 */

sealed interface JungleAnimal permits Monkey, Snake  {}

final class Monkey implements JungleAnimal {}

non-sealed class Snake implements JungleAnimal {}

public class Java16Features {

    public static void main(String[] args) {
        dayPeriodSupport();
        streamToList();
        recordsFeature();
        patternMatchingForInstanceOf();
    }

    /**
     * Day Period Support
     * A new addition to the DateTimeFormatter is the period-of-day symbol “B“, which provides an alternative to the am/pm format:
     */
    public static void dayPeriodSupport() {
        LocalTime date = LocalTime.parse("15:25:08.690791");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("h B");
        String formattedDate = date.format(formatter);
        System.out.println("Formatted time :: "+formattedDate);
    }

    /**
     * Add Stream.toList Method (JDK-8180352)
     * The aim is to reduce the boilerplate with some commonly used Stream collectors,
     * such as Collectors.toList and Collectors.toSet:
     */
    public static void streamToList() {
        List<String> integersAsString = Arrays.asList("1", "2", "3");
        List<Integer> listData = integersAsString.stream().map(Integer::parseInt).toList();
        System.out.println("List data :: "+listData);
    }

    /**
     * Records were introduced in Java 14. Java 16 brings some incremental changes.
     * Records are similar to enums in the fact that they are a restricted form of class.
     * Defining a record is a concise way of defining an immutable data holding object
     */
    public static void recordsFeature() {
        Book book = new Book(123, "Java Complete Reference", "James Gosling", "1235-dhfdsj-eyruw");
        System.out.println(book);
        System.out.println(book.hashCode());
        System.out.println(book.id()+":"+book.title()+":"+book.author()+":"+book.isbn());
    }

    /**
     * Pattern Matching for instanceof
     * Pattern matching for the instanceof keyword has been added as of Java 16.
     */
    public static void patternMatchingForInstanceOf() {
        Object obj = "TEST";
        /*Instead of purely focusing on the logic needed for the application, this code must first check the instance of obj,
         then cast the object to a String and assign it to a new variable str. */
        if (obj instanceof String str) {
            str = str.toUpperCase();
            System.out.println("String :: "+str);
        }
    }
}

/*
*
* Sealed classes, first introduced in Java 15, provide a mechanism to determine which sub-classes are allowed to extend or
* implement a parent class or interface.
* Invoke Default Methods From Proxy Instances
*/