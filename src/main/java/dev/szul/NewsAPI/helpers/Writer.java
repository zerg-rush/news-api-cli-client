package dev.szul.NewsAPI.helpers;

import dev.szul.NewsAPI.NewsAPI.Articles;
import dev.szul.NewsAPI.config.Config;
import dev.szul.NewsAPI.config.FeatureToggles;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

import static dev.szul.NewsAPI.config.FeatureToggles.FEATURE_TOGGLE_DISPLAY_HEADER;
import static dev.szul.NewsAPI.config.FeatureToggles.FEATURE_TOGGLE_SAVE_WITH_HEADER;

public class Writer {

    private final Articles articles;
    private String outputFilename = "";
    private final boolean diskEnabled;
    private final boolean screenEnabled;

    public Writer(InputOutput io, Articles articles) {
        final var outputMode = io.askUserToSelectOption(
                "What do you want to do with these articles?",
                new String[]{"save to file", "display on screen", "both"}, 3);

        diskEnabled = outputMode != 2;
        screenEnabled = outputMode != 1;

        if (diskEnabled) {
            outputFilename = io.askUserForString("Enter result file name", Config.DEFAULT_OUTPUT_FILENAME);
        }
        this.articles = articles;
    }

    public void write() {
        if (diskEnabled) {
            try {
                if (FeatureToggles.FEATURE_TOGGLE_APPEND_INSTEAD_OF_OVERWRITE) {
                    Files.writeString(Path.of(outputFilename),
                            (FEATURE_TOGGLE_SAVE_WITH_HEADER ?
                                    "title:description:author"
                                    :
                                    "") +
                                    articles.toString(),
                            StandardOpenOption.CREATE, StandardOpenOption.APPEND);
                } else {
                    Files.writeString(Path.of(outputFilename), articles.toString(),
                            StandardOpenOption.CREATE);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if (screenEnabled) {
            if (FEATURE_TOGGLE_DISPLAY_HEADER) {
                System.out.println("title:description:author");
            }
            System.out.println(articles.toString());
        }
    }

}
