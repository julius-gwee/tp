package seedu.address.ui;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import seedu.address.commons.exceptions.DataLoadingException;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.ReadOnlyFindr;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.UserPrefs;
import seedu.address.storage.Storage;

class CommandBoxTest {

    private CommandBox commandBox;
    private FakeStorage storage;
    private List<String> executedCommands;

    @BeforeAll
    static void initJfx() {
        // Initializes JavaFX toolkit
        new JFXPanel();
    }

    @BeforeEach
    void setUp() {
        executedCommands = new ArrayList<>();
        storage = new FakeStorage();
        commandBox = new CommandBox(commandText -> {
            executedCommands.add(commandText);
            return new CommandResult("Executed: " + commandText);
        }, storage);

        // Clear text field before each test
        Platform.runLater(() -> commandBox.getCommandTextField().setText(""));
    }

    @Test
    void handleCommandEntered_success() {
        Platform.runLater(() -> {
            commandBox.getCommandTextField().setText("test command");
            commandBox.handleCommandEntered();
            assertEquals(1, executedCommands.size());
            assertEquals("test command", executedCommands.get(0));
            assertEquals("", commandBox.getCommandTextField().getText());
        });
    }

    @Test
    void handleCommandEntered_failure() {
        CommandBox failingCommandBox = new CommandBox(commandText -> {
            throw new CommandException("fail");
        }, storage);

        Platform.runLater(() -> {
            failingCommandBox.getCommandTextField().setText("fail command");
            failingCommandBox.handleCommandEntered();
            assertTrue(failingCommandBox.getCommandTextField().getStyleClass()
                    .contains(CommandBox.ERROR_STYLE_CLASS));
        });
    }

    @Test
    void saveToHistory_avoidsConsecutiveDuplicates() {
        Platform.runLater(() -> {
            commandBox.saveToHistory("cmd1");
            commandBox.saveToHistory("cmd1"); // duplicate
            commandBox.saveToHistory("cmd2");

            List<String> history = storage.getSavedHistory();
            assertEquals(2, history.size());
            assertEquals("cmd1", history.get(0));
            assertEquals("cmd2", history.get(1));
        });
    }

    @Test
    void navigateHistoryUpDown() {
        Platform.runLater(() -> {
            commandBox.saveToHistory("first");
            commandBox.saveToHistory("second");

            // Simulate UP key press
            commandBox.handleKeyPressed(new KeyEvent(KeyEvent.KEY_PRESSED, "", "",
                    KeyCode.UP, false, false, false, false));
            assertEquals("second", commandBox.getCommandTextField().getText());

            // Another UP
            commandBox.handleKeyPressed(new KeyEvent(KeyEvent.KEY_PRESSED, "", "",
                    KeyCode.UP, false, false, false, false));
            assertEquals("first", commandBox.getCommandTextField().getText());

            // DOWN key
            commandBox.handleKeyPressed(new KeyEvent(KeyEvent.KEY_PRESSED, "", "",
                    KeyCode.DOWN, false, false, false, false));
            assertEquals("second", commandBox.getCommandTextField().getText());

            // DOWN key back to empty/new input
            commandBox.handleKeyPressed(new KeyEvent(KeyEvent.KEY_PRESSED, "", "",
                    KeyCode.DOWN, false, false, false, false));
            assertEquals("", commandBox.getCommandTextField().getText());
        });
    }

    /**
     * Fake storage for testing purposes.
     */
    public class FakeStorage implements Storage {

        private List<String> savedHistory = new ArrayList<>();

        // --- UserPrefsStorage methods ---
        @Override
        public Path getUserPrefsFilePath() {
            return null; // Not needed for CommandBox
        }

        @Override
        public Optional<UserPrefs> readUserPrefs() throws DataLoadingException {
            return Optional.empty(); // Not needed
        }

        @Override
        public void saveUserPrefs(ReadOnlyUserPrefs userPrefs) throws IOException {
            // Do nothing
        }

        // --- FindrStorage methods ---
        @Override
        public Path getFindrFilePath() {
            return null; // Not needed
        }

        @Override
        public Optional<ReadOnlyFindr> readCandidateList() throws DataLoadingException {
            return Optional.empty(); // Not needed
        }

        @Override
        public Optional<ReadOnlyFindr> readCandidateList(Path filePath) throws DataLoadingException {
            return Optional.empty(); // Not needed
        }

        @Override
        public void saveCandidateList(ReadOnlyFindr candidateList) throws IOException {
            // Do nothing
        }

        @Override
        public void saveCandidateList(ReadOnlyFindr candidateList, Path filePath) throws IOException {
            // Do nothing
        }

        // --- SearchHistoryStorage methods ---
        @Override
        public Path getSearchHistoryFilePath() {
            return null; // Not needed
        }

        @Override
        public Optional<List<String>> readSearchHistory() throws DataLoadingException {
            return Optional.of(new ArrayList<>(savedHistory));
        }

        @Override
        public void saveSearchHistory(List<String> searchHistory) throws IOException {
            savedHistory = new ArrayList<>(searchHistory);
        }

        // --- Helper method for tests ---
        public List<String> getSavedHistory() {
            return savedHistory;
        }
    }
}
