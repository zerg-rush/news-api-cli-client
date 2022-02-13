package dev.szul.NewsAPI.helpers;

import dev.szul.NewsAPI.config.Config;
import dev.szul.NewsAPI.config.FeatureToggles;
import dev.szul.NewsAPI.config.NewsAPI;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

public class ApiKey {

    private final InputOutput io;

    private String apiKeyValue = "";

    public ApiKey(InputOutput io) {
        this.io = io;
        if (!NewsAPI.API_KEY.isEmpty()) {
            apiKeyValue = NewsAPI.API_KEY;
        } else {
            if (Helpers.fileExists(Config.API_KEY_FILENAME)) {
                readApiKeyFromFile();
            } else {
                askUserForApiKey();
            }
        }
    }

    public String getApiKeyValue() {
        return apiKeyValue;
    }

    private void readApiKeyFromFile() {
        try {
            final var content = Files.readString(Path.of(Config.API_KEY_FILENAME));
            if (content.length() == 32) {
                apiKeyValue = content;
            } else {
                Helpers.exitWithStatusCodeAndMessage(3, "");
            }
        } catch (Exception e) {
            Helpers.exitWithStatusCodeAndMessage(2, "");
        }
    }

    private void askUserForApiKey() {
        String response;
        do {
            response = io.askUserForString("Please enter your API key (32 characters) to access NewsAPI service",
                    "");
        } while (response.length() == 32);
        apiKeyValue = response;
        saveApiKeyToFile();
    }

    private void saveApiKeyToFile() {
        if (FeatureToggles.FEATURE_TOGGLE_PROPOSE_TO_SAVE_ENTERED_API_KEY) {
            if (io.askUserForYesOrNo("Do you want to save this API key for later usage", true))
                try {
                    Files.writeString(Path.of(Config.API_KEY_FILENAME), apiKeyValue,
                            StandardOpenOption.CREATE);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
        }
    }

}
