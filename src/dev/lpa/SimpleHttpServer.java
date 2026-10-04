package dev.lpa;

import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;

public class SimpleHttpServer {
    private static long visitorCounter = 0;
    public static void main(String[] args) {
        try {
            HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);
            server.createContext("/", exchange -> {
                String requestMethod = exchange.getRequestMethod();
                System.out.println("Request Method: "+ requestMethod);
                String data = new String(exchange.getRequestBody().readAllBytes());
                System.out.println("Request Data: "+ data);
                if (requestMethod.equals("POST")) {
                    visitorCounter++;
                }

                String response = """
                        <html>
                        <body>
                        <h1>This is my HTTP server... it ain't much</h1>
                        <p>What can I say, I trying to make a web server using IntelliJ!</p>
                        <p>Number of visitors who signed up = %d</p>
                        <form method="post">
                        <input type="submit" value="Submit">
                        </form>
                        </body>
                        </html>
                        """.formatted(visitorCounter);
                var bytes = response.getBytes();
                exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, bytes.length);
                exchange.getResponseBody().write(bytes);
                exchange.getResponseBody().close();
            });
            server.start();
            System.out.println("Server Started on port 8080");
        } catch (IOException e){
            throw new RuntimeException(e);
        }
    }
}
