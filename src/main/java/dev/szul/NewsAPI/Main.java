package dev.szul.NewsAPI;

import dev.szul.NewsAPI.NewsAPI.Articles;
import dev.szul.NewsAPI.NewsAPI.Client;
import dev.szul.NewsAPI.helpers.InputOutput;
import dev.szul.NewsAPI.helpers.Writer;
import dev.szul.NewsAPI.helpers.ApiKey;

public class Main {

    public static int HTTP_STATUS_CODE_OK = 200;

    public static void main(String[] args) {
        System.out.println("NewsAPI cli demo client made as a recruitment task v1.0 alpha" + System.lineSeparator());

        final var io = new InputOutput(System.in, System.out);

        final var articles = new Articles();
        final var apiKey = new ApiKey(io);
        final var writer = new Writer(io, articles);
        final var Client = new Client(apiKey.getApiKeyValue());

        articles.load(Client.get());
        writer.write();
    }

}
