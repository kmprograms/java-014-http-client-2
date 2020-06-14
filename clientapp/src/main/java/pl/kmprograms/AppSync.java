package pl.kmprograms;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import pl.kmprograms.model.ExchangeRate;
import pl.kmprograms.model.Person;

import java.io.IOException;
import java.net.ProxySelector;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

public class AppSync {

    static HttpRequest requestGet(final String path) throws URISyntaxException {

        return HttpRequest.newBuilder()
                .uri(new URI(path))
                .version(HttpClient.Version.HTTP_2)
                .timeout(Duration.ofSeconds(10)) // HttpTimeoutException
                .GET()
                .build();
    }

    static <T> HttpRequest requestPost(final String path, T body) throws URISyntaxException, IOException {

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String json = gson.toJson(body);

        return HttpRequest.newBuilder()
                .uri(new URI(path))
                .version(HttpClient.Version.HTTP_2)
                .header("content-type", "application/json;charset=UTF-8")
                .timeout(Duration.ofSeconds(10)) // HttpTimeoutException
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .build();
    }

    public static void main(String[] args) {
        try {

            final String nbpPath = "http://api.nbp.pl/api/exchangerates/rates/c/usd/2016-04-04/?format=json";
            final String kmPath = "http://localhost:8080/people";

            System.out.println("------------------ 1 --------------------");
            HttpResponse<String> response1 = HttpClient
                    .newBuilder()
                    .proxy(ProxySelector.getDefault())
                    .build()
                    .send(requestGet(nbpPath), HttpResponse.BodyHandlers.ofString());

            System.out.println(response1.body());
            System.out.println(response1.headers());
            System.out.println(response1.statusCode());

            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            ExchangeRate exchangeRate = gson.fromJson(response1.body(), ExchangeRate.class);
            System.out.println(exchangeRate);

            System.out.println("------------------ 2 --------------------");

            Person person = Person.builder().name("PAWEL").age(20).build();
            HttpResponse<String> response2 = HttpClient
                    .newBuilder()
                    .proxy(ProxySelector.getDefault())
                    .build()
                    .send(requestPost(kmPath, person), HttpResponse.BodyHandlers.ofString());
            System.out.println(response2.body());
            System.out.println(response2.headers());
            System.out.println(response2.statusCode());

            Gson gson2 = new GsonBuilder().setPrettyPrinting().create();
            Person person2 = gson2.fromJson(response2.body(), Person.class);
            System.out.println(person2);

            System.out.println("------------------ 3 --------------------");

        } catch (Exception e) {
            System.out.println("EX: " + e.getMessage());
        }
    }
}
