package dev.szul.NewsAPI.NewsAPI;

import dev.szul.NewsAPI.config.NewsAPI;
import dev.szul.NewsAPI.helpers.Helpers;
import org.json.JSONObject;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import static dev.szul.NewsAPI.Main.HTTP_STATUS_CODE_OK;

public class Client {

    private final String apiKeyValue;
    private String url;
    private HttpRequest request;

    public Client(String apiKeyValue, String... url) {
        this.apiKeyValue = apiKeyValue;
        if (url.length == 0) {
            buildUrl();
        } else {
            this.url = url[0];
        }
        buildRequest();
    }

    public void buildUrl() {
        url = NewsAPI.URL_TEMPLATE +
                "?country=" + NewsAPI.COUNTRY +
                "&category=" + NewsAPI.CATEGORY +
                "&apiKey=" + apiKeyValue;
    }

    public void buildRequest() {
        request = HttpRequest.newBuilder(URI.create(url))
                .GET()
                .build();
    }

    public JSONObject get() {
        final var client = HttpClient.newHttpClient();

        try {
            final var response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() == HTTP_STATUS_CODE_OK) {
                final var jsonResponse = new JSONObject(response.body());
                final var status = jsonResponse.getString("status");

                if (status.equals("ok")) {
                    return jsonResponse;
                } else {
                    final var code = jsonResponse.getString("code");
                    final var message = jsonResponse.getString("message");
                    Helpers.exitWithStatusCodeAndMessage(7,
                            "code: " + code +
                                    ", message: " + message +
                                    "response: " + response.body());
                }
            } else {
                Helpers.exitWithStatusCodeAndMessage(6,
                        "code: " + response.statusCode() + ", response: " + response.body());
            }
        } catch (java.io.IOException e) {
            Helpers.exitWithStatusCodeAndMessage(4,
                    "stack trace: " + e);
        } catch (InterruptedException e) {
            Helpers.exitWithStatusCodeAndMessage(5,
                    "stack trace: " + e);
        }
        return new JSONObject();
    }

}
