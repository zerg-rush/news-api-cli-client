package dev.szul.NewsAPI.helpers;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

class InputOutputTest {

    private InputOutput setupIo(String inputData) {
        ByteArrayInputStream inputStream = new ByteArrayInputStream(inputData.getBytes());

        ByteArrayOutputStream outputData = new ByteArrayOutputStream();
        PrintStream outputStream = new PrintStream(outputData);

        return new InputOutput(inputStream, outputStream);
    }

    @Test
    void askUserToSelectOption_ShouldResultDefaultValueWhenGotEnter() {
        final var prompt = "What do you want to test today?";
        final var options = new String[]{"foo", "bar", "both"};
        final var defaultValue = 2;
        final var entry = "\n";

        final var expected = defaultValue;

        var ioTest = setupIo(entry);

        final var actual= ioTest.askUserToSelectOption(prompt,options, defaultValue);
        assertEquals(expected, actual);
    }

    @Test
    void askUserToSelectOption_ShouldResultNumberItGot() {
        final var prompt = "What do you want to test today?";
        final var options = new String[]{"foo", "bar", "both"};
        final var defaultValue = 2;
        final var entry = 3;

        final var expected = entry;

        var ioTest = setupIo(String.valueOf(entry));

        final var actual= ioTest.askUserToSelectOption(prompt,options, defaultValue);
        assertEquals(expected, actual);
    }

    @Test
    void askUserForString_ShouldResultDefaultValueWhenGotEnter() {
        final var prompt = "What do you think about JUnit 5?";
        final var defaultValue = "Well, it's complicated...";
        final var entry = "\n";

        final var expected = defaultValue;

        var ioTest = setupIo(entry);

        final var actual= ioTest.askUserForString(prompt, defaultValue);
        assertEquals(expected, actual);
    }

    @Test
    void askUserForString_ShouldResultWithUserEntry() {
        final var prompt = "What do you think about JUnit 5?";
        final var defaultValue = "Well, it's complicated...";
        final var entry = "It is so great!!!!!!!!!!!!!!! ;)";

        final var expected = entry;

        var ioTest = setupIo(entry);

        final var actual= ioTest.askUserForString(prompt, defaultValue);
        assertEquals(expected, actual);
    }

    @Test
    void askUserForYesOrNo_ShouldResultDefaultValueTrueWhenGotEnter() {
        final var prompt = "Do you really think that Foo is better than Bar?";
        final var defaultValue = true;
        final var entry = "\n";

        final var expected = defaultValue;

        var ioTest = setupIo(entry);

        final var actual= ioTest.askUserForYesOrNo(prompt, defaultValue);
        assertEquals(expected, actual);
    }

    @Test
    void askUserForYesOrNo_ShouldResultDefaultValueFalseWhenGotEnter() {
        final var prompt = "Do you really think that Foo is better than Bar?";
        final var defaultValue = false;
        final var entry = "\n";

        final var expected = defaultValue;

        var ioTest = setupIo(entry);

        final var actual= ioTest.askUserForYesOrNo(prompt, defaultValue);
        assertEquals(expected, actual);
    }

    @Test
    void askUserForYesOrNo_ShouldResultTrueWhenGotYes() {
        final var prompt = "Do you really think that Foo is better than Bar?";
        final var defaultValue = true;
        final var entry = "yEs";

        final var expected = true;

        var ioTest = setupIo(entry);

        final var actual= ioTest.askUserForYesOrNo(prompt, defaultValue);
        assertEquals(expected, actual);
    }

    @Test
    void askUserForYesOrNo_ShouldResultFalseWhenGotNo() {
        final var prompt = "Do you really think that Foo is better than Bar?";
        final var defaultValue = false;
        final var entry = "nO";

        final var expected = false;

        var ioTest = setupIo(entry);

        final var actual= ioTest.askUserForYesOrNo(prompt, defaultValue);
        assertEquals(expected, actual);
    }

}
