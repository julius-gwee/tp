package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.address.commons.exceptions.DataLoadingException;

public class JsonSearchHistoryStorageTest {

    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonSearchHistoryStorageTest");

    @TempDir
    public Path testFolder;

    @Test
    public void readSearchHistory_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> readSearchHistory(null));
    }

    @Test
    public void readSearchHistory_missingFile_emptyResult() throws Exception {
        assertFalse(readSearchHistory(Paths.get("NonExistentFile.json")).isPresent());
    }

    @Test
    public void readSearchHistory_notJsonFormat_exceptionThrown() {
        assertThrows(DataLoadingException.class, () -> readSearchHistory(Paths.get(TEST_DATA_FOLDER.toString(),
                "NotJsonFormatSearchHistory.json")));
    }

    @Test
    public void readSearchHistory_fileInOrder_successfullyRead() throws Exception {
        Optional<List<String>> searchHistory = readSearchHistory(Paths.get(TEST_DATA_FOLDER.toString(),
                "TypicalSearchHistory.json"));
        assertTrue(searchHistory.isPresent());
        List<String> expectedHistory = Arrays.asList("list", "add n/John p/123 e/john@example.com", "find John");
        assertEquals(expectedHistory, searchHistory.get());
    }

    @Test
    public void readSearchHistory_valuesMissingFromFile_defaultValuesUsed() throws Exception {
        Optional<List<String>> searchHistory = readSearchHistory(Paths.get(TEST_DATA_FOLDER.toString(),
                "EmptySearchHistory.json"));
        assertTrue(searchHistory.isPresent());
        assertTrue(searchHistory.get().isEmpty());
    }

    @Test
    public void readSearchHistory_extraValuesInFile_extraValuesIgnored() throws Exception {
        Optional<List<String>> searchHistory = readSearchHistory(Paths.get(TEST_DATA_FOLDER.toString(),
                "ExtraValuesSearchHistory.json"));
        assertTrue(searchHistory.isPresent());
        List<String> expectedHistory = Arrays.asList("list", "find test");
        assertEquals(expectedHistory, searchHistory.get());
    }

    @Test
    public void saveSearchHistory_nullSearchHistory_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveSearchHistory(null, Paths.get("SomeFile.json")));
    }

    @Test
    public void saveSearchHistory_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveSearchHistory(Arrays.asList("test"), null));
    }

    @Test
    public void saveAndReadSearchHistory_allInOrder_success() throws Exception {
        Path filePath = testFolder.resolve("TempSearchHistory.json");
        List<String> original = Arrays.asList("list", "add n/Test p/123 e/test@example.com", "find Test");

        saveSearchHistory(original, filePath);
        Optional<List<String>> readBack = readSearchHistory(filePath);
        assertTrue(readBack.isPresent());
        assertEquals(original, readBack.get());
    }

    @Test
    public void getSearchHistoryFilePath() {
        JsonSearchHistoryStorage storage = new JsonSearchHistoryStorage(Paths.get("test.json"));
        assertEquals(Paths.get("test.json"), storage.getSearchHistoryFilePath());
    }

    @Test
    public void saveSearchHistoryIoExceptionThrown() throws IOException {
        JsonSearchHistoryStorage storage = new JsonSearchHistoryStorage(Paths.get("invalid/path/file.json"));
        List<String> searchHistory = Arrays.asList("test command");
        assertThrows(IOException.class, () -> storage.saveSearchHistory(searchHistory));
    }

    private Optional<List<String>> readSearchHistory(Path filePath) throws Exception {
        return new JsonSearchHistoryStorage(filePath).readSearchHistory();
    }

    private void saveSearchHistory(List<String> searchHistory, Path filePath) throws IOException {
        new JsonSearchHistoryStorage(filePath).saveSearchHistory(searchHistory);
    }
}
