package com.world.web.utility;

import com.world.web.model.Student;

import java.util.Collection;
import java.util.Comparator;
import java.util.Map;
import java.util.List;
import java.util.stream.Collectors;

public class RecordUtility {

    public static void main(String[] args) {
        Student student = new Student("Bhola Kumar", "bhola.kumar1990@gmail.com", "New Delhi");
        System.out.println("Name :: " + student.name());
        System.out.println("Email :: " + student.email());
        System.out.println("Address :: " + student.address());

        System.out.println("Hash code :: " + student.hashCode());
        System.out.println("toString :: " + student);

        System.out.println("Record and Collectors Demo");
        // created record class and added custom Comparator
        record NumberOfLength(int length, long number) {
            static NumberOfLength fromEntry(Map.Entry<Integer, Long> entry) {
                return new NumberOfLength(entry.getKey(), entry.getValue());
            }

            static Comparator<NumberOfLength> comparingByLength() {
                return Comparator.comparing(NumberOfLength::length);
            }
        }

        //Having multiple max values for a String
        Collection<String> stringCollection = List.of("two", "three", "four", "five", "six", "seven", "eight", "nine", "ten", "eleven", "twelve");
        // Collection<String> stringCollection = List.of("one","two","three","four","five","six","seven","eight","nine","ten","eleven","twelve");
        //Iterate the String collection, and do the grouping based on String length and get the counting too
        Map<Integer, Long> mapStringCount = stringCollection.stream()
                .collect(Collectors.groupingBy(String::length, Collectors.counting()));
        System.out.println("Print the string count :: " + mapStringCount);

        //Iterate the map, and get the max occurrences of a String
        NumberOfLength maxResult = mapStringCount.entrySet()
                .stream()
                .map(NumberOfLength::fromEntry)
                .max(NumberOfLength.comparingByLength())
                .orElseThrow();
        System.out.println("Max result from map :: " + maxResult);

        //Using Collectors.mapping() intermediate operations
        Map<Long, List<Integer>> groupedResult = mapStringCount.entrySet()
                .stream()
                .map(NumberOfLength::fromEntry)
                .collect(Collectors.groupingBy(NumberOfLength::number,
                        Collectors.mapping(NumberOfLength::length, Collectors.toList())));
        System.out.println("Grouping and Mapping Result :: " + groupedResult);

        //Now fetch the multiple max values from this map object groupedResult

        Map.Entry<Long, List<Integer>>  mapMaxResult = groupedResult.entrySet()
                .stream()
                .max(Map.Entry.comparingByKey())
                .orElseThrow();
        System.out.println("Multiple Max result grouped :: "+mapMaxResult);

    }
}

/*
 * The filtering collector follows the same pattern as the mapping collector.
 * It is created with the Collectors.filtering() factory method that takes a regular predicate to filter the data and a mandatory downstream collector.
 * The same goes for the flatMapping collector, created by the Collectors.flatMapping() factory method,
 * that takes a flatMapping function (a function that returns a stream), and a mandatory downstream collector.
 */