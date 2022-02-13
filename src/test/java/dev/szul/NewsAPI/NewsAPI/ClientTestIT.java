package dev.szul.NewsAPI.NewsAPI;

import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.ServerSocket;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

@WireMockTest
class ClientTestIT {

    private static final String NEWS_API_WIREMOCK_PATH = "/v2/top-headlines";
    private static final String APPLICATION_JSON = "application/json";
    private static int freePort;
    private static final String urlPrefix = String.format("http://localhost:%s", freePort);
    private static final String NEWS_API_ERROR_RESPONSE = """
            {
                "status": "error",
                "code": "apiKeyMissing",
                "message": "Your API key is missing. Append this to the URL with the apiKey param, or use the x-api-key HTTP header."
            }""";
    private static final String NEWS_API_CORRECT_RESPONSE = """
            {
             "status": "ok",
             "totalResults": 3,
             "articles": [
                 {
                     "source": {
                         "id": "foo",
                         "name": "FOO"
                     },
                         "author": "Foo Foo",
                         "title": "Foo Foo Foo",
                         "description": "Foo Foo Foo Foo",
                 },
                                  {
                     "source": {
                         "id": "bar",
                         "name": "BAR"
                     },
                         "author": "Bar Bar",
                         "title": "Bar Bar Bar Bar",
                         "description": "Bar Bar Bar Bar",
                 },
             -
             ]
             }""";

    static {
        try {
            final var server = new ServerSocket(0);
            freePort = server.getLocalPort();
            server.close();
        } catch (IOException e) {
            // empty
        }
    }

    @Test
    void buildUrl() {
    }

    @Test
    void buildRequest() {
    }

    @Test
    void get_ShouldReturnArticlesObject() {
        stubFor(get(urlPathMatching(NEWS_API_WIREMOCK_PATH + "/.*"))
                .willReturn(ok()
                        .withHeader("Content-Type", APPLICATION_JSON)
                        .withBody(
                                "{\"data\":\"some data\"}"
                        )));

        final var client = new Client("API_KEY_123456789", urlPrefix + NEWS_API_WIREMOCK_PATH);
        final var responseObject = client.get();
        System.out.println("response = " + responseObject.toString());

        assertTrue("{\"data\":\"some data\"}".equals(responseObject.toString()));

        verify(getRequestedFor(urlPathEqualTo("/some/data"))
                .withHeader("accept", equalTo("application/json"))
        );
    }
}
