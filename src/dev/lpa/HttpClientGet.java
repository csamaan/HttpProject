package dev.lpa;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.stream.Stream;

public class HttpClientGet {

    public static void main(String[] args) {
        try {
            URL url = new URL("http://localhost:8080/");
            HttpClient client = HttpClient.newHttpClient();

            HttpRequest request = HttpRequest.newBuilder()
                    .GET()
                    .uri(url.toURI())
                    .header("User-Agent", "Chrome")
                    .headers("Accept", "application/json", "Accept", "text/html")
                    .timeout(Duration.ofSeconds(30))
                    .build();

            HttpResponse<Stream<String>> response = client.send(request,
            HttpResponse.BodyHandlers.ofLines());
        if (response.statusCode() != HttpURLConnection.HTTP_OK) {
            System.out.println("HTTP ERROR CODE: " + response.statusCode());
            return;
        }
        System.out.println("HTTP RESPONSE: " + response.body());
        response.body()
                .filter(s-> s.contains("<h1>"))
                .map(s->s.replaceAll("<[^>]*>", "").strip())
                .forEach(System.out::println);
        } catch (IOException e) {
            System.err.println("Client Error: " + e.getMessage());
        } catch(URISyntaxException e){
            System.err.println("Invalid URL: " + e.getMessage());
        } catch(InterruptedException e){
            System.err.println("Interrupted: " + e.getMessage());
        }
    }
}