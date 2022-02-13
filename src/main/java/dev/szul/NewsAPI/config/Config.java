package dev.szul.NewsAPI.config;

import java.util.Map;

public class Config {

    public static final String DEFAULT_OUTPUT_FILENAME = "articles.txt";
    public static final String API_KEY_FILENAME = "api.key";

    public static final Map<Integer, String> ERROR_EXIT_CODES_DESCRIPTIONS = Map.of(
    0, "OK",
    1, "Invalid hardcoded API key",
    2, "Error during reading api.key file",
    3, "Invalid API key value in api.key file",

    4, "I/O error during sending request",
    5, "Operation interrupted by user",

    6, "NewsAPI server did not respond correctly",
    7, "NewsAPI server responded with error message",
    8, "NewsAPI responded with empty articles list"
    );

}
