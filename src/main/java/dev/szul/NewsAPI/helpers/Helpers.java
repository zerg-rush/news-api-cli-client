package dev.szul.NewsAPI.helpers;

import java.io.File;

import static dev.szul.NewsAPI.config.Config.ERROR_EXIT_CODES_DESCRIPTIONS;

public class Helpers {

    public static boolean isSelectionInRange(int options, int selection) {
        return selection >= 1 && selection <= options;
    }

    public static boolean fileExists(String filename) {
        final var file = new File(filename);
        return file.exists();
    }

    public static void exitWithStatusCodeAndMessage(int exitCode, String extensionMessage) {
        final var message = ERROR_EXIT_CODES_DESCRIPTIONS.get(exitCode);

        if (exitCode != 0 || !extensionMessage.isEmpty()) {
            System.out.println(message);
        }
        if (!extensionMessage.isEmpty()) {
            System.out.println(System.lineSeparator() + extensionMessage);
        }
        System.exit(exitCode);
    }

}
