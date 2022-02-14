package dev.szul.NewsAPI.NewsAPI;

import com.ginsberg.junit.exit.ExpectSystemExitWithStatus;
import com.github.tomakehurst.wiremock.WireMockServer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.options;
import static net.javacrumbs.jsonunit.assertj.JsonAssertions.assertThatJson;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ClientTestIT {

    private static final String NEWS_API_WIREMOCK_PATH = "/v2/top-headlines";
    private static final String NEWS_API_WIREMOCK_PARAMS_TEMPLATE = "?country=%s&category=%s&apiKey=%s";
    private static final String NEWS_API_ERROR_RESPONSE_WRONG_KEY = """
            {
                "status": "error",
                "code": "apiKeyMissing",
                "message": "Your API key is missing. Append this to the URL with the apiKey param, or use the x-api-key HTTP header."
            }""";
    private static final String NEWS_API_VALID_RESPONSE = """
            {
                 "status": "ok",
                 "totalResults": 2,
                 "articles": [
                     {
                         "source": {
                             "id": "foo",
                             "name": "FOO"
                         },
                             "author": "Foo Foo",
                             "title": "Foo Foo Foo",
                             "description": "Foo Foo Foo Foo"
                     },
                     {
                         "source": {
                             "id": "bar",
                             "name": "BAR"
                         },
                             "author": "Bar Bar",
                             "title": "Bar Bar Bar Bar",
                             "description": "Bar Bar Bar Bar"
                     }]
             }""";

    private WireMockServer wireMockServer;

    @BeforeEach
    void configureSystemUnderTest() {
        this.wireMockServer = new WireMockServer(options()
                .dynamicPort()
        );
        this.wireMockServer.start();
        configureFor("localhost", this.wireMockServer.port());
    }

    @AfterEach
    void stopWireMockServer() {
        this.wireMockServer.stop();
    }

    private String buildApiMethodUrl(String country, String category, String apiKeyValue) {
        return String.format("http://localhost:%d" + NEWS_API_WIREMOCK_PATH + NEWS_API_WIREMOCK_PARAMS_TEMPLATE,
                this.wireMockServer.port(), country, category, apiKeyValue);
    }

    @Test
    @DisplayName("Should fail with respective exit code if server rejected API Key")
    @ExpectSystemExitWithStatus(7)
    void get_ShouldFailWithRespectiveCodeWhenServerRejectedApiKey() {
        givenThat(
                get(urlPathEqualTo(NEWS_API_WIREMOCK_PATH))
                        .withQueryParam("country", equalTo("pl"))
                        .withQueryParam("category", equalTo("business"))
                        .withQueryParam("apiKey", equalTo("WRONG_API_KEY"))
                        .willReturn(okJson(NEWS_API_ERROR_RESPONSE_WRONG_KEY))
        );

        final var expected = NEWS_API_ERROR_RESPONSE_WRONG_KEY;

        String apiMethodUrl = buildApiMethodUrl("pl", "business", "WRONG_API_KEY");

        final var client = new Client("", apiMethodUrl);

        final var responseObject = client.get();
        final var actual = responseObject.toString();

        assertTrue(actual.equals(expected));
    }

    @Test
    @DisplayName("Should return extracted JSON array of articles from server response")
    void get_ShouldReturnExtractedArticlesFromServerResponse() {
        givenThat(
                get(urlPathEqualTo(NEWS_API_WIREMOCK_PATH))
                        .withQueryParam("country", equalTo("pl"))
                        .withQueryParam("category", equalTo("business"))
                        .withQueryParam("apiKey", equalTo("RIGHT_API_KEY"))
                        .willReturn(okJson(NEWS_API_VALID_RESPONSE))
        );

        final var expected = NEWS_API_VALID_RESPONSE;

        String apiMethodUrl = buildApiMethodUrl("pl", "business", "RIGHT_API_KEY");

        final var client = new Client("", apiMethodUrl);

        final var responseObject = client.get();
        final var actual = responseObject.toString();

        assertThatJson(actual).isEqualTo(expected);
    }

}
