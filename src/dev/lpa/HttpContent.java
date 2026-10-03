
package dev.lpa;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;

public class HttpContent {
    public static void main(String[] args) {
        try {
            URL exampleUrl = URI.create("https://www.example.com").toURL();
            URL todosUrl = URI.create("https://jsonplaceholder.typicode.com/todos?id=5").toURL();
            printContents(todosUrl.openStream());
            System.out.println("---------Next website---------");
            printContents(exampleUrl.openStream());
            System.out.println("---------Next website---------");
            URLConnection urlConnection = exampleUrl.openConnection();
            urlConnection.getHeaderFields()
                    .entrySet()
                    .forEach(System.out::println);
            System.out.println(urlConnection.getHeaderField("Cache-Control"));
            urlConnection.connect();
            printContents(urlConnection.getInputStream());
            System.out.println("---------Next website---------");
            System.out.println("----------------NEW SECTION----------------");
            URL url = new URL("https://www.example.com/");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("User-Agent", "Chrome");
            connection.setRequestProperty("Accept", "application/json, text/html");
            connection.setReadTimeout(30000);

            int responseCode = connection.getResponseCode();
            System.out.printf("Response code: %d%n", responseCode);
            if (responseCode != HttpURLConnection.HTTP_OK) {
                System.err.println("Error reading webpage: " +url);
                return;
            }
            printContents(connection.getInputStream());

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void printContents(InputStream is) {
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(is, StandardCharsets.UTF_8))) {
            String line;
            while ((line = reader.readLine()) != null) {
                // put each tag on its own line (lines with no '<', like JSON, pass through as is)
                for (String piece : line.split("(?=<)")) {
                    printWrapped(piece, 80);
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading content: " + e.getMessage());
        }
    }

    private static void printWrapped(String text, int width) {
        // short or blank lines print untouched, indentation intact
        if (text.length() <= width) {
            System.out.println(text);
            return;
        }
        StringBuilder line = new StringBuilder();
        for (String word : text.trim().split("\\s+")) {
            // hard-split words longer than the width
            while (word.length() > width) {
                if (line.length() > 0) {
                    System.out.println(line);
                    line.setLength(0);
                }
                System.out.println(word.substring(0, width));
                word = word.substring(width);
            }
            // start a new line if the word won't fit
            int needed = line.length() == 0 ? word.length() : line.length() + 1 + word.length();
            if (needed > width) {
                System.out.println(line);
                line.setLength(0);
            }
            if (line.length() > 0) line.append(' ');
            line.append(word);
        }
        if (line.length() > 0) System.out.println(line);
    }
}