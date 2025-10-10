package seedu.address;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

import seedu.address.model.Findr;
import seedu.address.model.ModelManager;
import seedu.address.model.ReadOnlyFindr;
import seedu.address.model.UserPrefs;
import seedu.address.storage.JsonFindrStorage;
import seedu.address.storage.JsonUserPrefsStorage;
import seedu.address.storage.Storage;
import seedu.address.storage.StorageManager;

public class MainAppTest {

    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "sandbox");

    @Test
    public void initModelManager_validStorage_success() {
        JsonFindrStorage findrStorage = new JsonFindrStorage(TEST_DATA_FOLDER.resolve("findr.json"));
        JsonUserPrefsStorage prefsStorage = new JsonUserPrefsStorage(TEST_DATA_FOLDER.resolve("prefs.json"));
        Storage storage = new StorageManager(findrStorage, prefsStorage);
        UserPrefs userPrefs = new UserPrefs();

        MainApp mainApp = new MainApp();
        ModelManager modelManager = (ModelManager) mainApp.initModelManager(storage, userPrefs);

        assertNotNull(modelManager);
        assertTrue(modelManager.getCandidateList() instanceof ReadOnlyFindr);
    }

    @Test
    public void initModelManager_missingFile_createsEmptyAddressBook() {
        JsonFindrStorage findrStorage = new JsonFindrStorage(Paths.get("nonexistent.json"));
        JsonUserPrefsStorage prefsStorage = new JsonUserPrefsStorage(Paths.get("prefs.json"));
        Storage storage = new StorageManager(findrStorage, prefsStorage);
        UserPrefs userPrefs = new UserPrefs();

        MainApp mainApp = new MainApp();
        ModelManager modelManager = (ModelManager) mainApp.initModelManager(storage, userPrefs);

        ReadOnlyFindr addressBook = modelManager.getCandidateList();
        assertTrue(addressBook instanceof Findr);
    }

    @Test
    public void getFilePathMethods_validPaths_returnNonNull() {
        JsonFindrStorage findrStorage = new JsonFindrStorage(TEST_DATA_FOLDER.resolve("findr.json"));
        assertNotNull(findrStorage.getFindrFilePath());
    }
}
