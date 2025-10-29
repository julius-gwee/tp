package seedu.address.ui;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Region;
import seedu.address.commons.exceptions.DataLoadingException;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.storage.Storage;

/**
 * The UI component that is responsible for receiving user command inputs.
 */
public class CommandBox extends UiPart<Region> {

    public static final String ERROR_STYLE_CLASS = "error";
    private static final String FXML = "CommandBox.fxml";

    private final CommandExecutor commandExecutor;
    private final Storage storage;
    private final FeedbackDisplay feedbackDisplay;

    // Search history navigation fields
    private final List<String> searchHistory = new ArrayList<>();
    private int historyIndex = -1; // -1 means we're at the "new command" position
    private String currentInput = ""; // Store current input when navigating history

    @FXML
    private TextField commandTextField;

    /**
     * Creates a {@code CommandBox} with the given {@code CommandExecutor}, {@code Storage}, and {@code FeedbackDisplay}.
     */
    public CommandBox(CommandExecutor commandExecutor, Storage storage, FeedbackDisplay feedbackDisplay) {
        super(FXML);
        this.commandExecutor = commandExecutor;
        this.storage = storage;
        this.feedbackDisplay = feedbackDisplay;
        // calls #setStyleToDefault() whenever there is a change to the text of the command box.
        commandTextField.textProperty().addListener((unused1, unused2, unused3) -> setStyleToDefault());

        // Add key event listener for arrow key navigation
        commandTextField.setOnKeyPressed(this::handleKeyPressed);

        // Load search history from storage
        loadSearchHistory();
    }

    /**
     * Handles the Enter button pressed event.
     */
    @FXML
    public void handleCommandEntered() {
        String commandText = commandTextField.getText();
        if (commandText.equals("")) {
            return;
        }

        try {
            commandExecutor.execute(commandText);

            // Save command to history
            saveToHistory(commandText);

            commandTextField.setText("");
            resetHistoryNavigation();
        } catch (CommandException | ParseException e) {
            setStyleToIndicateCommandFailure();
        }
    }

    /**
     * Sets the command box style to use the default style.
     */
    private void setStyleToDefault() {
        commandTextField.getStyleClass().remove(ERROR_STYLE_CLASS);
    }

    /**
     * Sets the command box style to indicate a failed command.
     */
    private void setStyleToIndicateCommandFailure() {
        ObservableList<String> styleClass = commandTextField.getStyleClass();

        if (styleClass.contains(ERROR_STYLE_CLASS)) {
            return;
        }

        styleClass.add(ERROR_STYLE_CLASS);
    }

    /**
     * Handles key press events for arrow key navigation through search history.
     */
    public void handleKeyPressed(KeyEvent event) {
        if (event.getCode() == KeyCode.UP) {
            navigateHistoryUp();
            event.consume(); // Prevent default behavior
        } else if (event.getCode() == KeyCode.DOWN) {
            navigateHistoryDown();
            event.consume(); // Prevent default behavior
        }
    }

    /**
     * Navigates up in the search history (shows previous commands).
     */
    private void navigateHistoryUp() {
        if (searchHistory.isEmpty()) {
            feedbackDisplay.showFeedback("No previous command!");
            return;
        }

        // If we're at the "new command" position, save current input
        if (historyIndex == -1) {
            currentInput = commandTextField.getText();
        }

        // Check if we're already at the oldest command
        if (historyIndex >= searchHistory.size() - 1) {
            feedbackDisplay.showFeedback("No previous command!");
            return;
        }

        // Move to previous history item
        historyIndex++;
        String historyCommand = searchHistory.get(searchHistory.size() - 1 - historyIndex);
        commandTextField.setText(historyCommand);
        commandTextField.positionCaret(historyCommand.length()); // Move cursor to end
    }

    /**
     * Navigates down in the search history (shows newer commands or clears input).
     */
    private void navigateHistoryDown() {
        if (searchHistory.isEmpty()) {
            return;
        }

        // Move to next history item
        if (historyIndex > 0) {
            historyIndex--;
            String historyCommand = searchHistory.get(searchHistory.size() - 1 - historyIndex);
            commandTextField.setText(historyCommand);
            commandTextField.positionCaret(historyCommand.length()); // Move cursor to end
        } else if (historyIndex == 0) {
            // Return to the "new command" position
            historyIndex = -1;
            commandTextField.setText(currentInput);
            commandTextField.positionCaret(currentInput.length()); // Move cursor to end
        }
    }

    /**
     * Saves a command to the search history.
     */
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

    /**
     * Resets the history navigation position to the "new command" state.
     */
    public void resetHistoryNavigation() {
        historyIndex = -1;
        currentInput = "";
    }

    /**
     * Loads search history from persistent storage.
     */
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

    /**
     * Represents a function that can execute commands.
     */
    @FunctionalInterface
    public interface CommandExecutor {
        /**
         * Executes the command and returns the result.
         *
         * @see seedu.address.logic.Logic#execute(String)
         */
        CommandResult execute(String commandText) throws CommandException, ParseException;
    }

    /**
     * Represents a function that can display feedback messages to the user.
     */
    @FunctionalInterface
    public interface FeedbackDisplay {
        /**
         * Displays feedback message to the user.
         */
        void showFeedback(String message);
    }

}
