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
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CountDownLatch;


public class AppAsync {

    static HttpRequest requestGet(final String path) throws URISyntaxException {
        return HttpRequest.newBuilder()
                .uri(new URI(path))
                .version(HttpClient.Version.HTTP_2)
                .headers("K1", "V1", "K2", "V2")
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

            CountDownLatch countDownLatch = new CountDownLatch(2);

            final String nbpPath = "http://api.nbp.pl/api/exchangerates/rates/c/usd/2016-04-04/?format=json";
            final String kmPath = "http://localhost:8080/people";

            System.out.println("------------------ 1 --------------------");
            CompletableFuture<HttpResponse<String>> response1 = HttpClient
                    .newBuilder()
                    .proxy(ProxySelector.getDefault())
                    .build()
                    .sendAsync(requestGet(nbpPath), HttpResponse.BodyHandlers.ofString());

            response1.thenAccept(res -> {
                System.out.println(res.body());
                System.out.println(res.headers());
                System.out.println(res.statusCode());

                Gson gson = new GsonBuilder().setPrettyPrinting().create();
                ExchangeRate exchangeRate = gson.fromJson(res.body(), ExchangeRate.class);
                System.out.println(exchangeRate);
                countDownLatch.countDown();
            });

            System.out.println("------------------ 2 --------------------");

            Person person = Person.builder().name("PAWEL").age(20).build();
            CompletableFuture<HttpResponse<String>> response2 = HttpClient
                    .newBuilder()
                    .proxy(ProxySelector.getDefault())
                    .build()
                    .sendAsync(requestPost(kmPath, person), HttpResponse.BodyHandlers.ofString());
            response2.thenAccept(res  -> {
                System.out.println(res.body());
                System.out.println(res.headers());
                System.out.println(res.statusCode());

                Gson gson2 = new GsonBuilder().setPrettyPrinting().create();
                Person person2 = gson2.fromJson(res.body(), Person.class);
                System.out.println(person2);
                countDownLatch.countDown();
            });

            System.out.println("------------------ 3 --------------------");

            countDownLatch.await();
        } catch (Exception e) {
            System.out.println("EX: " + e.getMessage());
        }

    }
}
