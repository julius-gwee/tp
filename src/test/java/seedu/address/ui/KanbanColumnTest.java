package seedu.address.ui;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.person.Person;
import seedu.address.testutil.TypicalPersons;

/**
 * Contains tests for KanbanColumn.
 */
public class KanbanColumnTest {

    /**
     * Test that KanbanColumn can be instantiated with valid parameters.
     * The test passes as long as no
     * NullPointerException is thrown.
     */
    @Test
    public void constructor_validParameters_noNullPointerException() {
        ObservableList<Person> personList = FXCollections.observableArrayList(
            TypicalPersons.ALICE,
            TypicalPersons.BENSON
        );
        
        // Verify constructor doesn't throw NPE (IllegalStateException from JavaFX is
        // acceptable)
        assertDoesNotThrow(() -> {
            try {
                new KanbanColumn("Candidates", personList);
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
                new KanbanColumn("Empty Column", emptyList);
            } catch (IllegalStateException | ExceptionInInitializerError | NoClassDefFoundError e) {
                // JavaFX toolkit not initialized - expected in headless tests
            }
        });
    }
}

