package com.world.web.features;

import jakarta.annotation.Nonnull;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Java11Features {

    public static void main(String[] args) {
        stringMethods();
        fileMethods();
        collectionToArray();
        predicateNotMethod();
        localVariableSyntax();
    }


    /**
     * Java 11 adds a few new methods to the String class: isBlank, lines, strip, stripLeading, stripTrailing, and repeat.
     */
    public static void stringMethods() {
        String multilineString = "Baeldung helps \n \n developers \n explore Java.";
        List<String> lines = multilineString.lines()
                .filter(line -> !line.isBlank())
                .map(String::strip)
                .toList();
        System.out.println("stringMethods() Result :: " + lines);
    }

    /**
     * We can use the new readString and writeString static methods from the Files class:
     */
    public static void fileMethods() {
        try {
            String tempDir = "src/main/resources/";
            Path filePath = Files.writeString(Files.createTempFile(Path.of(tempDir), "demo",
                    ".txt"), "Sample text");
            String fileContent = Files.readString(filePath);
            System.out.println("File data :: "+fileContent);
        } catch (Exception exp) {
            System.out.println("Exception message :: " + exp.getMessage());
        }
    }

    /**
     * The java.util.Collection interface contains a new default toArray method which takes an IntFunction argument.
     */
    public static void collectionToArray() {
        List<String> sampleList = Arrays.asList("Java", "Kotlin");
        String[] sampleArray = sampleList.toArray(String[]::new);
        System.out.println("collectionToArray() String array :: "+ Arrays.toString(sampleArray));
    }

    /**
     * A static not method has been added to the Predicate interface.
     * We can use it to negate an existing predicate, much like the negate method:
     */
    public static void predicateNotMethod() {
        List<String> sampleList = Arrays.asList("Java", "\n \n", "Kotlin", " ");
        List<String> withoutBlanks =
                sampleList.stream()
                          .filter(Predicate.not(String::isBlank))
                          .toList();
        System.out.println("predicateNotMethod() Result :: "+withoutBlanks);
    }

    /**
     * Support for using the local variable syntax (var keyword) in lambda parameters was added in Java 11.
     */
    public static void localVariableSyntax() {
        List<String> sampleList = Arrays.asList("Java", "Kotlin");
        String resultString = sampleList.stream()
                .map((@Nonnull var x) -> x.toUpperCase())
                .collect(Collectors.joining(", "));
        System.out.println("localVariableSyntax() Result :: "+resultString);
    }

    /**
     * The new HTTP client from the java.net.http package was introduced in Java 9. It has now become a standard feature in Java 11.
     * The new HTTP API improves overall performance and provides support for both HTTP/1.1 and HTTP/2:
     */
    public static void httpClient() throws IOException, InterruptedException {
        HttpClient httpClient = HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_2)
                .connectTimeout(Duration.ofSeconds(20))
                .build();
        HttpRequest httpRequest = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create("http://localhost:" + 8080))
                .build();
        HttpResponse<String> httpResponse = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
        System.out.println("API response data :: "+httpResponse.body());
    }

}

/*
 *
 * Performance Enhancements
 * A No-Op Garbage Collector
 * A new garbage collector called Epsilon is available for use in Java 11 as an experimental feature.
 * It’s called a No-Op (no operations) because it allocates memory but does not actually collect any garbage.
 * Thus, Epsilon is applicable for simulating out of memory errors.
 * Obviously Epsilon won’t be suitable for a typical production Java application; however, there are a
 * few specific use-cases where it could be useful:
   1.Performance testing
   2.Memory pressure testing
   3.VM interface testing and
   4.Extremely short-lived jobs
   In order to enable it, use the -XX:+UnlockExperimentalVMOptions -XX:+UseEpsilonGC flag
 *
 * Flight Recorder
 * Java Flight Recorder (JFR) is now open-source in Open JDK, whereas it used to be a commercial product in Oracle JDK.
 * JFR is a profiling tool that we can use to gather diagnostics and profiling data from a running Java application.
 * To start a 120 seconds JFR recording, we can use the following parameter:
   > -XX:StartFlightRecording=duration=120s,settings=profile,filename=java-demo-app.jfr
 * We can use JFR in production since its performance overhead is usually below 1%.
 * Once the time elapses, we can access the recorded data saved in a JFR file; however, in order to analyze and visualize the data,
 * we need to make use of another tool called JDK Mission Control (JMC).
 *
 * Java EE & CORBA
 * Java 9 already deprecated selected Java EE and CORBA modules. In release 11, it has now completely removed:
 * Java API for XML-Based Web Services (java.xml.ws)
 * Java Architecture for XML Binding (java.xml.bind)
 * JavaBeans Activation Framework (java.activation)
 * Common Annotations (java.xml.ws.annotation)
 * Common Object Request Broker Architecture (java.corba)
 * JavaTransaction API (java.transaction)
 * JMC
 * JDK Mission Control (JMC) is no longer included in the JDK. A standalone version of JMC is now available as a separate download
 */