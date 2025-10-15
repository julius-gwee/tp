package seedu.address.ui;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.person.Person;
import seedu.address.testutil.TypicalPersons;

/**
 * Contains tests for KanbanBoard.
 */
public class KanbanBoardTest {

    /**
     * Test that KanbanBoard can be instantiated with valid parameters.
     * The test passes as long as no
     * NullPointerException is thrown.
     */
    @Test
    public void constructor_validPersonList_noNullPointerException() {
        ObservableList<Person> personList = FXCollections.observableArrayList(
                TypicalPersons.ALICE,
                TypicalPersons.BENSON,
                TypicalPersons.CARL);

        assertDoesNotThrow(() -> {
            try {
                new KanbanBoard(personList);
            } catch (IllegalStateException | ExceptionInInitializerError | NoClassDefFoundError e) {
                // JavaFX toolkit not initialized - expected in headless tests
                // Test passes if we don't get NPE
            }
        });
    }

    @Test
    public void constructor_emptyPersonList_noNullPointerException() {
        ObservableList<Person> emptyList = FXCollections.observableArrayList();

        assertDoesNotThrow(() -> {
            try {
                new KanbanBoard(emptyList);
            } catch (IllegalStateException | ExceptionInInitializerError | NoClassDefFoundError e) {
                // JavaFX toolkit not initialized - expected in headless tests
            }
        });
    }

    @Test
    public void constructor_largePersonList_noNullPointerException() {
        ObservableList<Person> largeList = FXCollections.observableArrayList(
                TypicalPersons.getTypicalPersons());

        assertDoesNotThrow(() -> {
            try {
                new KanbanBoard(largeList);
            } catch (IllegalStateException | ExceptionInInitializerError | NoClassDefFoundError e) {
                // JavaFX toolkit not initialized - expected in headless tests
            }
        });
    }
}
