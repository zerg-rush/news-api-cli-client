package dev.szul.NewsAPI.helpers;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static dev.szul.NewsAPI.helpers.Helpers.fileExists;
import static dev.szul.NewsAPI.helpers.Helpers.isSelectionInRange;
import static org.junit.jupiter.api.Assertions.*;

class HelpersTest {

    @Test
    void isSelectionInRange_ShouldResultFalseWhenGotNegative() {
        final var options = 5;
        final var selection = -2;

        final var expected = false;

        final var actual= isSelectionInRange(options, selection);
        assertEquals(expected, actual);
    }

    @Test
    void isSelectionInRange_ShouldResultFalseWhenGotZero() {
        final var options = 5;
        final var selection = 0;

        final var expected = false;

        final var actual= isSelectionInRange(options, selection);
        assertEquals(expected, actual);
    }

    @Test
    void isSelectionInRange_ShouldResultTrueWhenGotInRange() {
        final var options = 5;
        final var selection = 2;

        final var expected = true;

        final var actual= isSelectionInRange(options, selection);
        assertEquals(expected, actual);
    }

    @Test
    void isSelectionInRange_ShouldResultTrueWhenGotMaxValue() {
        final var options = 5;
        final var selection = 4;

        final var expected = true;

        final var actual= isSelectionInRange(options, selection);
        assertEquals(expected, actual);
    }

    @Test
    void isSelectionInRange_ShouldResultFalseWhenGotRightAboveRange() {
        final var options = 5;
        final var selection = 6;

        final var expected = false;

        final var actual= isSelectionInRange(options, selection);
        assertEquals(expected, actual);
    }

    @Test
    void isSelectionInRange_ShouldResultFalseWhenGotAboveRange() {
        final var options = 5;
        final var selection = 10;

        final var expected = false;

        final var actual= isSelectionInRange(options, selection);
        assertEquals(expected, actual);
    }

    @Test
    void fileExists_ShouldResultTrueWhenFileExists(@TempDir Path tempDir) throws IOException {
        final var dummyFile = tempDir.resolve("foo.txt");
        final var expected = true;

        Files.writeString(dummyFile, "bar");

        final var actual= fileExists(dummyFile.toString());
        assertEquals(expected, actual);
    }

    @Test
    void fileExists_ShouldResultFalseWhenFileDoesNotExist(@TempDir Path tempDir) {
        final var dummyFile = tempDir.resolve("foo.txt");
        final var expected = false;

        final var actual= fileExists(dummyFile.toString());
        assertEquals(expected, actual);
    }

    @Test
    void exitWithStatusCodeAndMessage() {
    }
}
