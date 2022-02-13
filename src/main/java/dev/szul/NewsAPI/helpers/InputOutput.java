package dev.szul.NewsAPI.helpers;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.Scanner;

public class InputOutput {

    public static final String ANSI_RESET = "\u001B[0m";

    public static final String ANSI_YELLOW = "\u001B[33m";

    InputStream input;
    PrintStream output;
    Scanner scanner;

    public InputOutput(InputStream input, PrintStream output) {
        this.input = input;
        this.output = output;
        this.scanner = new Scanner(input);
    }

    public int askUserToSelectOption(String prompt, String[] options, int defaultValue) {
        var selection = defaultValue;
        var done = false;
        final var repeatMessage = "Try harder... I need number from 1 to " + options.length + System.lineSeparator();

        do {
            output.println(prompt);
            var index = 1;
            for (String line : options) {
                output.println("\t" + index + ". " +
                        line +
                        (index == defaultValue ?
                                ANSI_YELLOW + " (default)" + ANSI_RESET
                                :
                                ""));
                index++;
            }

            final var response = scanner.nextLine();
            if (response.isEmpty()) {
                selection = defaultValue;
                done = true;
            } else {
                try {
                    selection = Integer.parseInt(response);
                    done = Helpers.isSelectionInRange(options.length, selection);
                    if (!done) {
                        output.println(repeatMessage);
                    }
                } catch (NumberFormatException e) {
                    output.println(repeatMessage);
                }
            }

        } while (!done);
        output.println("selected: " + options[selection - 1] + System.lineSeparator());
        return selection;
    }

    public String askUserForString(String prompt, String defaultValue) {
        output.println(prompt +
                (defaultValue.isEmpty() ?
                        ""
                        :
                        " (default: " + ANSI_YELLOW + defaultValue + ANSI_RESET + ")") + ": ");
        var response = scanner.nextLine();
        if (response.isEmpty()) {
            response = defaultValue;
        }
        output.println("entered: " + response + System.lineSeparator());
        return response;
    }

    public boolean askUserForYesOrNo(String prompt, boolean defaultValue) {
        var selection = defaultValue;
        var done = false;
        final var repeatMessage = "Make your mind... I need simple Y(es) or N(o)" + System.lineSeparator();

        do {
            output.println(prompt +
                    " [" +
                    (defaultValue ?
                            ANSI_YELLOW + "Yes" + ANSI_RESET + "/No"
                            :
                            "Yes/" + ANSI_YELLOW + "/No" + ANSI_RESET
                    ) +
                    "]? ");

            final var response = scanner.nextLine();

            if (response.isEmpty()) {
                done = true;
            } else {
                try {
                    switch (response.toLowerCase()) {
                        case "y":
                        case "yes":
                        case "yeah":
                        case "t":
                        case "tak":
                            selection = true;
                            done = true;
                            break;
                        case "n":
                        case "no":
                        case "nie":
                            selection = false;
                            done = true;
                            break;
                        case "":
                            done = true;
                    }
                    if (!done) {
                        output.println(repeatMessage);
                    }
                } catch (NumberFormatException e) {
                    output.println(repeatMessage);
                }
            }
        } while (!done);
        output.println("selected: " +
                (selection ?
                        "Yes"
                        :
                        "No") +
                "\n");
        return selection;
    }

}
