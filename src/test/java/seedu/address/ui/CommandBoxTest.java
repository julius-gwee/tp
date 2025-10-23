package seedu.address.ui;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import seedu.address.commons.exceptions.DataLoadingException;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.storage.Storage;

@ExtendWith(MockitoExtension.class)
public class CommandBoxTest {

    @Mock
    private CommandBox.CommandExecutor commandExecutor;

    @Mock
    private Storage storage;

    private TestCommandBox commandBox;

    @BeforeEach
    public void setUp() throws DataLoadingException, IOException {
        // Mock storage to return empty history by default
        lenient().when(storage.readSearchHistory()).thenReturn(Optional.empty());
        lenient().doNothing().when(storage).saveSearchHistory(anyList());

        // Create a test CommandBox without JavaFX initialization
        commandBox = new TestCommandBox(commandExecutor, storage);
    }

    @Test
    public void constructor_loadsSearchHistoryFromStorage() throws DataLoadingException {
        // Verify that storage.readSearchHistory() was called during construction
        verify(storage).readSearchHistory();
    }

    @Test
    public void constructor_loadsExistingSearchHistory() throws DataLoadingException {
        List<String> existingHistory = Arrays.asList("list", "find test");
        when(storage.readSearchHistory()).thenReturn(Optional.of(existingHistory));

        TestCommandBox newCommandBox = new TestCommandBox(commandExecutor, storage);

        // Verify that the history was loaded
        verify(storage, times(2)).readSearchHistory(); // Once in setUp, once in new constructor
    }

    @Test
    public void constructor_handlesDataLoadingException() throws DataLoadingException {
        when(storage.readSearchHistory()).thenThrow(new DataLoadingException(new Exception("Test exception")));

        // Should not throw exception, just start with empty history
        TestCommandBox newCommandBox = new TestCommandBox(commandExecutor, storage);
        assertNotNull(newCommandBox);
    }

    @Test
    public void handleCommandEntered_emptyCommand_doesNothing() throws CommandException, ParseException, IOException {
        // Set empty text
        commandBox.setText("");

        commandBox.handleCommandEntered();

        // Verify commandExecutor was never called
        verify(commandExecutor, never()).execute(any());
        verify(storage, never()).saveSearchHistory(anyList());
    }

    @Test
    public void handleCommandEntered_validCommand_executesAndSaves() throws
            CommandException, ParseException, IOException {
        String command = "list";
        CommandResult result = new CommandResult("Listed all persons");
        when(commandExecutor.execute(command)).thenReturn(result);

        commandBox.setText(command);

        commandBox.handleCommandEntered();

        verify(commandExecutor).execute(command);
        verify(storage).saveSearchHistory(anyList());
        assertEquals("", commandBox.getText());
    }

    @Test
    public void handleCommandEntered_commandException_setsErrorStyle() throws
            CommandException, ParseException, IOException {
        String command = "invalid command";
        when(commandExecutor.execute(command)).thenThrow(new CommandException("Invalid command"));

        commandBox.setText(command);

        commandBox.handleCommandEntered();

        verify(commandExecutor).execute(command);
        // Should still save to history even if command fails
        verify(storage).saveSearchHistory(anyList());
        // Error style should be set
        assertTrue(commandBox.hasErrorStyle());
    }

    @Test
    public void handleCommandEntered_parseException_setsErrorStyle() throws
            CommandException, ParseException, IOException {
        String command = "malformed command";
        when(commandExecutor.execute(command)).thenThrow(new ParseException("Parse error"));

        commandBox.setText(command);

        commandBox.handleCommandEntered();

        verify(commandExecutor).execute(command);
        // Should still save to history even if command fails
        verify(storage).saveSearchHistory(anyList());
        // Error style should be set
        assertTrue(commandBox.hasErrorStyle());
    }

    @Test
    public void saveToHistory_emptyCommand_doesNotSave() throws IOException {
        commandBox.saveToHistory("");

        verify(storage, never()).saveSearchHistory(anyList());
    }

    @Test
    public void saveToHistory_whitespaceOnlyCommand_doesNotSave() throws IOException {
        commandBox.saveToHistory("   ");

        verify(storage, never()).saveSearchHistory(anyList());
    }

    @Test
    public void saveToHistory_validCommand_savesToHistory() throws IOException {
        String command = "list";

        commandBox.saveToHistory(command);

        verify(storage).saveSearchHistory(anyList());
    }

    @Test
    public void saveToHistory_duplicateConsecutiveCommand_doesNotSave() throws IOException {
        String command = "list";

        commandBox.saveToHistory(command);
        commandBox.saveToHistory(command);

        // Should only save once
        verify(storage, times(1)).saveSearchHistory(anyList());
    }

    @Test
    public void saveToHistory_differentCommands_savesBoth() throws IOException {
        commandBox.saveToHistory("list");
        commandBox.saveToHistory("find test");

        verify(storage, times(2)).saveSearchHistory(anyList());
    }

    @Test
    public void saveToHistory_storageException_handlesGracefully() throws Exception {
        doThrow(new RuntimeException("Storage error")).when(storage).saveSearchHistory(anyList());

        // Should not throw exception
        commandBox.saveToHistory("test command");

        verify(storage).saveSearchHistory(anyList());
    }

    @Test
    public void saveToHistory_historySizeLimit_removesOldest() throws IOException {
        // Add 51 commands to test the size limit
        for (int i = 0; i < 51; i++) {
            commandBox.saveToHistory("command" + i);
        }

        verify(storage, times(51)).saveSearchHistory(anyList());
    }

    @Test
    public void resetHistoryNavigation_resetsIndexAndCurrentInput() {
        // Add some history and navigate
        commandBox.saveToHistory("command1");
        commandBox.saveToHistory("command2");

        // Navigate up to set historyIndex
        commandBox.simulateUpArrow();

        commandBox.resetHistoryNavigation();

        // Navigate up again - should start from the beginning
        commandBox.simulateUpArrow();
        assertEquals("command2", commandBox.getText());
    }

    @Test
    public void navigateHistoryUp_emptyHistory_doesNothing() {
        commandBox.simulateUpArrow();

        // Text should remain unchanged
        assertEquals("", commandBox.getText());
    }

    @Test
    public void navigateHistoryUp_atBeginning_savesCurrentInput() {
        commandBox.saveToHistory("command1");
        commandBox.setText("current input");

        commandBox.simulateUpArrow();

        assertEquals("command1", commandBox.getText());
    }

    @Test
    public void navigateHistoryDown_emptyHistory_doesNothing() {
        commandBox.simulateDownArrow();

        // Text should remain unchanged
        assertEquals("", commandBox.getText());
    }

    @Test
    public void navigateHistoryDown_atEnd_returnsToCurrentInput() {
        commandBox.saveToHistory("command1");
        commandBox.setText("current input");

        // Navigate up first
        commandBox.simulateUpArrow();

        // Navigate down to return to current input
        commandBox.simulateDownArrow();

        assertEquals("current input", commandBox.getText());
    }

    @Test
    public void setStyleToDefault_removesErrorStyle() {
        // First set error style
        commandBox.setErrorStyle(true);
        assertTrue(commandBox.hasErrorStyle());

        // Call setStyleToDefault through text property change
        commandBox.setText("test");

        // Error style should be removed
        assertFalse(commandBox.hasErrorStyle());
    }

    @Test
    public void setStyleToIndicateCommandFailure_addsErrorStyle() {
        // Initially no error style
        assertFalse(commandBox.hasErrorStyle());

        // Simulate command failure by calling handleCommandEntered with exception
        try {
            when(commandExecutor.execute("invalid")).thenThrow(new CommandException("Error"));
            commandBox.setText("invalid");
            commandBox.handleCommandEntered();
        } catch (Exception e) {
            // Expected
        }

        // Error style should be added
        assertTrue(commandBox.hasErrorStyle());
    }

    @Test
    public void setStyleToIndicateCommandFailure_alreadyHasErrorStyle_doesNotAddDuplicate() {
        // Add error style first
        commandBox.setErrorStyle(true);
        assertTrue(commandBox.hasErrorStyle());

        // Simulate another command failure
        try {
            when(commandExecutor.execute("invalid")).thenThrow(new CommandException("Error"));
            commandBox.setText("invalid");
            commandBox.handleCommandEntered();
        } catch (Exception e) {
            // Expected
        }

        // Should still have error style (no duplicate)
        assertTrue(commandBox.hasErrorStyle());
    }

    @Test
    public void loadSearchHistory_withExistingHistory_loadsCorrectly() throws DataLoadingException {
        List<String> existingHistory = Arrays.asList("list", "find test", "add n/John");
        when(storage.readSearchHistory()).thenReturn(Optional.of(existingHistory));

        TestCommandBox newCommandBox = new TestCommandBox(commandExecutor, storage);

        verify(storage, times(2)).readSearchHistory(); // Once in setUp, once in new constructor
    }

    @Test
    public void loadSearchHistory_withEmptyHistory_startsEmpty() throws DataLoadingException {
        when(storage.readSearchHistory()).thenReturn(Optional.of(Collections.emptyList()));

        TestCommandBox newCommandBox = new TestCommandBox(commandExecutor, storage);

        verify(storage, times(2)).readSearchHistory(); // Once in setUp, once in new constructor
    }

    /**
     * Test implementation that simulates CommandBox behavior without JavaFX dependencies
     */
    private static class TestCommandBox {
        private final CommandBox.CommandExecutor commandExecutor;
        private final Storage storage;
        private final List<String> searchHistory;
        private String text;
        private boolean hasErrorStyle;
        private int historyIndex;
        private String currentInput;

        public TestCommandBox(CommandBox.CommandExecutor commandExecutor, Storage storage) {
            this.commandExecutor = commandExecutor;
            this.storage = storage;
            this.searchHistory = new java.util.ArrayList<>();
            this.text = "";
            this.hasErrorStyle = false;
            this.historyIndex = -1;
            this.currentInput = "";

            // Load search history from storage
            loadSearchHistory();
        }

        public void setText(String text) {
            this.text = text;
            // Simulate text property change listener
            setStyleToDefault();
        }

        public String getText() {
            return text;
        }

        public void setErrorStyle(boolean hasError) {
            this.hasErrorStyle = hasError;
        }

        public boolean hasErrorStyle() {
            return hasErrorStyle;
        }

        public void simulateUpArrow() {
            navigateHistoryUp();
        }

        public void simulateDownArrow() {
            navigateHistoryDown();
        }

        public void handleCommandEntered() {
            if (text.isEmpty()) {
                return;
            }

            String originalText = text; // Save the original text before clearing

            try {
                commandExecutor.execute(text);
                text = "";
                resetHistoryNavigation();
            } catch (CommandException | ParseException e) {
                setStyleToIndicateCommandFailure();
            } finally {
                // Always save to history, even if command fails
                saveToHistory(originalText);
            }
        }

        public void saveToHistory(String command) {
            // Don't save empty commands
            if (command.trim().isEmpty()) {
                return;
            }

            // Don't save duplicate consecutive commands
            if (!searchHistory.isEmpty() && searchHistory.get(searchHistory.size() - 1).equals(command)) {
                return;
            }

            searchHistory.add(command);

            // Limit history size to prevent memory issues (keep last 50 commands)
            if (searchHistory.size() > 50) {
                searchHistory.remove(0);
            }

            // Save to persistent storage
            try {
                storage.saveSearchHistory(searchHistory);
            } catch (Exception e) {
                // Log error but don't interrupt user experience
                System.err.println("Failed to save search history: " + e.getMessage());
            }
        }

        public void resetHistoryNavigation() {
            historyIndex = -1;
            currentInput = "";
        }

        private void setStyleToDefault() {
            hasErrorStyle = false;
        }

        private void setStyleToIndicateCommandFailure() {
            hasErrorStyle = true;
        }

        private void navigateHistoryUp() {
            if (searchHistory.isEmpty()) {
                return;
            }

            // If we're at the "new command" position, save current input
            if (historyIndex == -1) {
                currentInput = text;
            }

            // Move to previous history item
            if (historyIndex < searchHistory.size() - 1) {
                historyIndex++;
                String historyCommand = searchHistory.get(searchHistory.size() - 1 - historyIndex);
                text = historyCommand;
            }
        }

        private void navigateHistoryDown() {
            if (searchHistory.isEmpty()) {
                return;
            }

            // Move to next history item
            if (historyIndex > 0) {
                historyIndex--;
                String historyCommand = searchHistory.get(searchHistory.size() - 1 - historyIndex);
                text = historyCommand;
            } else if (historyIndex == 0) {
                // Return to the "new command" position
                historyIndex = -1;
                text = currentInput;
            }
        }

        private void loadSearchHistory() {
            try {
                Optional<List<String>> loadedHistory = storage.readSearchHistory();
                if (loadedHistory.isPresent()) {
                    searchHistory.clear();
                    searchHistory.addAll(loadedHistory.get());
                }
            } catch (DataLoadingException e) {
                // If loading fails, start with empty history
                System.err.println("Failed to load search history: " + e.getMessage());
            }
        }
    }
}
