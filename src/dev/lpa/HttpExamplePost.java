package dev.lpa;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpExamplePost {

    public static void main(String[] args) {
        try {
            URL url = new URL("http://localhost:8080/");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            // Set the HTTP request method
            connection.setRequestMethod("POST");
            connection.setRequestProperty("User-Agent", "Chrome");
            connection.setRequestProperty("Accept", "application/json, text/html");

            connection.setReadTimeout(30000); // 30-second timeout
            connection.setDoOutput(true);
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            String parameters="first=Joe&last=Smith";
            int length = parameters.getBytes().length;
            connection.setRequestProperty("Content-Length", String.valueOf(length));


            DataOutputStream output = new DataOutputStream(connection.getOutputStream());
            output.writeBytes(parameters);
            output.flush();
            output.close();
            int responseCode = connection.getResponseCode();
            System.out.println("Response code: " + responseCode);



            if (responseCode != HttpURLConnection.HTTP_OK) {
                System.out.println("Error reading web page: " + connection.getResponseMessage());
                return;
            }

            // Read the server's HTML response
            try (BufferedReader inputReader = new BufferedReader(
                    new InputStreamReader(connection.getInputStream()))) {

                String line;
                while ((line = inputReader.readLine()) != null) {
                    System.out.println(line);
                }
            }
        } catch (IOException e) {
            System.err.println("Client Error: " + e.getMessage());
        }
    }
}