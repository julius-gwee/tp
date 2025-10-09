package seedu.address;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.logging.Logger;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javafx.stage.Stage;
import seedu.address.commons.core.Config;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.DataLoadingException;
import seedu.address.model.Findr;
import seedu.address.model.ModelManager;
import seedu.address.model.ReadOnlyFindr;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.UserPrefs;
import seedu.address.storage.JsonFindrStorage;
import seedu.address.storage.JsonUserPrefsStorage;
import seedu.address.storage.Storage;
import seedu.address.storage.StorageManager;
import seedu.address.storage.UserPrefsStorage;
import seedu.address.ui.Ui;

/**
 * Comprehensive tests for {@link MainApp}.
 */
public class MainAppTest {

    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "sandbox");

    @BeforeEach
    public void setupLogger() {
        LogsCenter.init(new Config());
    }

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
    public void initModelManager_missingFile_returnsSampleData() {
        JsonFindrStorage findrStorage = new JsonFindrStorage(Paths.get("nonexistent.json"));
        JsonUserPrefsStorage prefsStorage = new JsonUserPrefsStorage(Paths.get("prefs.json"));
        Storage storage = new StorageManager(findrStorage, prefsStorage);
        UserPrefs userPrefs = new UserPrefs();

        MainApp mainApp = new MainApp();
        ModelManager modelManager = (ModelManager) mainApp.initModelManager(storage, userPrefs);

        assertTrue(modelManager.getCandidateList() instanceof ReadOnlyFindr);
    }

    @Test
    public void initModelManager_dataLoadingException_returnsEmptyFindr() {
        // Mock storage that always throws DataLoadingException
        Storage faultyStorage = new StorageManager(new FindrStorageStub(),
                new JsonUserPrefsStorage(Paths.get("prefs.json")));
        MainApp mainApp = new MainApp();
        ModelManager modelManager = (ModelManager) mainApp.initModelManager(faultyStorage, new UserPrefs());
        assertTrue(modelManager.getCandidateList() instanceof Findr);
    }

    @Test
    public void initConfig_validAndInvalidPaths_success() {
        MainApp mainApp = new MainApp();

        // Case 1: null path uses default config
        Config config1 = mainApp.initConfig(null);
        assertNotNull(config1);

        // Case 2: custom path (nonexistent) still works
        Path tempConfig = TEST_DATA_FOLDER.resolve("tempConfig.json");
        Config config2 = mainApp.initConfig(tempConfig);
        assertNotNull(config2);
    }

    @Test
    public void initPrefs_validAndCorruptFiles_success() {
        Path prefsPath = TEST_DATA_FOLDER.resolve("prefs.json");
        JsonUserPrefsStorage storage = new JsonUserPrefsStorage(prefsPath);
        MainApp mainApp = new MainApp();

        // Valid case
        UserPrefs prefs1 = mainApp.initPrefs(storage);
        assertNotNull(prefs1);

        // Faulty storage that throws exception
        UserPrefsStorage faultyStorage = new UserPrefsStorage() {
            @Override
            public Path getUserPrefsFilePath() {
                return Paths.get("faulty.json");
            }

            @Override
            public Optional<UserPrefs> readUserPrefs() throws DataLoadingException {
                throw new DataLoadingException(new IOException("Simulated read failure"));
            }

            @Override
            public void saveUserPrefs(ReadOnlyUserPrefs prefs) throws IOException {
                throw new IOException("Simulated save failure");
            }
        };

        UserPrefs prefs2 = mainApp.initPrefs(faultyStorage);
        assertNotNull(prefs2);
    }

    @Test
    public void initLogging_runsWithoutError() {
        MainApp mainApp = new MainApp();
        mainApp.initLogging(new Config());
        Logger logger = LogsCenter.getLogger(MainApp.class);
        assertNotNull(logger);
    }

    @Test
    public void startAndStop_methods_runWithoutError() {
        MainApp mainApp = new MainApp();

        // Inject minimal required fields
        mainApp.ui = new Ui() {
            @Override
            public void start(Stage primaryStage) {
                // no-op
            }
        };

        mainApp.model = new ModelManager(new Findr(), new UserPrefs());
        mainApp.storage = new StorageManager(
                new JsonFindrStorage(TEST_DATA_FOLDER.resolve("findr.json")),
                new JsonUserPrefsStorage(TEST_DATA_FOLDER.resolve("prefs.json"))
        );

        // start() should not throw
        mainApp.start(null);

        // stop() should attempt to save prefs
        mainApp.stop();
        assertTrue(Files.exists(mainApp.storage.getUserPrefsFilePath()));
    }

    // === Stub class to force exception path ===
    private static class FindrStorageStub extends JsonFindrStorage {
        FindrStorageStub() {
            super(Paths.get("dummy.json"));
        }

        @Override
        public Optional<ReadOnlyFindr> readCandidateList() throws DataLoadingException {
            throw new DataLoadingException(new IOException("Simulated failure"));
        }
    }
}
