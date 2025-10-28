package com.world.web.utility;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.stream.Stream;

public class HttpStreamUtility {

    public static void main(String[] args) {

        try {
            //The URI of the File
            URI uri = URI.create("https://www.gutenberg.org/files/98/98-0.txt");

            //Code to open create an Http Request
            HttpClient httpClient = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder(uri).build();

            //The sending of Request
            HttpResponse<Stream<String>> response = httpClient.send(request, HttpResponse.BodyHandlers.ofLines());

            List<String> lines;
            try (Stream<String> stream = response.body()) {
                lines = stream
                        .dropWhile(line -> !line.equals("A TALE OF TWO CITIES"))
                        .takeWhile(line -> !line.equals("*** END OF THE PROJECT GUTENBERG EBOOK A TALE OF TWO CITIES ***"))
                        .toList();
            }
            System.out.println("Line size :: " + lines.size());
        } catch (InterruptedException | IOException ex) {
            System.out.println("Exception message :: " + ex.getMessage());
        }
    }
}

/*
 * The HTTP Client API gives you several body handlers. The one you need to consume the body as a stream is the one
 * created by the factory method HttpResponse.BodyHandlers.ofLines(). This way of consuming the body of the
 * response is very memory efficient.
 * If you write your stream carefully, the body of the response will never be stored in memory.
 */