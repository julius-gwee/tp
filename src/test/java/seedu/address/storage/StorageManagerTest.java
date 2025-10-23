package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static seedu.address.testutil.TypicalPersons.getTypicalFindr;

import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.address.commons.core.GuiSettings;
import seedu.address.model.Findr;
import seedu.address.model.ReadOnlyFindr;
import seedu.address.model.UserPrefs;

public class StorageManagerTest {

    @TempDir
    public Path testFolder;

    private StorageManager storageManager;

    @BeforeEach
    public void setUp() {
        JsonFindrStorage addressBookStorage = new JsonFindrStorage(getTempFilePath("ab"));
        JsonUserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(getTempFilePath("prefs"));
        JsonSearchHistoryStorage searchHistoryStorage = new JsonSearchHistoryStorage(getTempFilePath("searchHistory"));
        storageManager = new StorageManager(addressBookStorage, userPrefsStorage, searchHistoryStorage);
    }

    private Path getTempFilePath(String fileName) {
        return testFolder.resolve(fileName);
    }

    @Test
    public void prefsReadSave() throws Exception {
        /*
         * Note: This is an integration test that verifies the StorageManager is properly wired to the
         * {@link JsonUserPrefsStorage} class.
         * More extensive testing of UserPref saving/reading is done in {@link JsonUserPrefsStorageTest} class.
         */
        UserPrefs original = new UserPrefs();
        original.setGuiSettings(new GuiSettings(300, 600, 4, 6));
        storageManager.saveUserPrefs(original);
        UserPrefs retrieved = storageManager.readUserPrefs().get();
        assertEquals(original, retrieved);
    }

    @Test
    public void addressBookReadSave() throws Exception {
        /*
         * Note: This is an integration test that verifies the StorageManager is properly wired to the
         * {@link JsonFindrStorage} class.
         * More extensive testing of UserPref saving/reading is done in {@link JsonFindrStorageTest} class.
         */
        Findr original = getTypicalFindr();
        storageManager.saveCandidateList(original);
        ReadOnlyFindr retrieved = storageManager.readCandidateList().get();
        assertEquals(original, new Findr(retrieved));
    }

    @Test
    public void getFindrFilePath() {
        assertNotNull(storageManager.getFindrFilePath());
    }

    @Test
    public void searchHistoryReadSave() throws Exception {
        /*
         * Note: This is an integration test that verifies the StorageManager is properly wired to the
         * {@link JsonSearchHistoryStorage} class.
         * More testing of search history saving/reading is done in {@link JsonSearchHistoryStorageTest} class.
         */
        List<String> original = Arrays.asList("list", "add n/Test p/123 e/test@example.com", "find Test");
        storageManager.saveSearchHistory(original);
        List<String> retrieved = storageManager.readSearchHistory().get();
        assertEquals(original, retrieved);
    }

    @Test
    public void getSearchHistoryFilePath() {
        assertNotNull(storageManager.getSearchHistoryFilePath());
    }

    @Test
    public void getUserPrefsFilePath() {
        assertNotNull(storageManager.getUserPrefsFilePath());
    }

}
