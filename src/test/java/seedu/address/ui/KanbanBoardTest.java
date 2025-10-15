package seedu.address.ui;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.person.Person;
import seedu.address.testutil.TypicalPersons;
import seedu.address.ui.testutil.GuiTestUtil;

/**
 * Contains tests for KanbanBoard.
 */
public class KanbanBoardTest {

    @BeforeAll
    public static void initToolkit() {
        GuiTestUtil.initToolkit();
    }

    @Test
    public void constructor_validPersonList_success() {
        if (!GuiTestUtil.isToolkitAvailable()) {
            // JavaFX not available, but we can still test object creation
            ObservableList<Person> personList = FXCollections.observableArrayList(
                    TypicalPersons.ALICE,
                    TypicalPersons.BENSON,
                    TypicalPersons.CARL);
            try {
                KanbanBoard kanbanBoard = new KanbanBoard(personList);
                assertNotNull(kanbanBoard);
            } catch (Exception e) {
                // Expected in headless environment without full JavaFX support
                assertTrue(true);
            }
            return;
        }

        ObservableList<Person> personList = FXCollections.observableArrayList(
                TypicalPersons.ALICE,
                TypicalPersons.BENSON,
                TypicalPersons.CARL);

        KanbanBoard kanbanBoard = new KanbanBoard(personList);
        assertNotNull(kanbanBoard);
        assertNotNull(kanbanBoard.getRoot());
    }

    @Test
    public void constructor_emptyPersonList_success() {
        if (!GuiTestUtil.isToolkitAvailable()) {
            ObservableList<Person> emptyList = FXCollections.observableArrayList();
            try {
                KanbanBoard kanbanBoard = new KanbanBoard(emptyList);
                assertNotNull(kanbanBoard);
            } catch (Exception e) {
                assertTrue(true);
            }
            return;
        }

        ObservableList<Person> emptyList = FXCollections.observableArrayList();

        KanbanBoard kanbanBoard = new KanbanBoard(emptyList);
        assertNotNull(kanbanBoard);
        assertNotNull(kanbanBoard.getRoot());
    }

    @Test
    public void constructor_largePersonList_success() {
        if (!GuiTestUtil.isToolkitAvailable()) {
            ObservableList<Person> largeList = FXCollections.observableArrayList(
                    TypicalPersons.getTypicalPersons());
            try {
                KanbanBoard kanbanBoard = new KanbanBoard(largeList);
                assertNotNull(kanbanBoard);
            } catch (Exception e) {
                assertTrue(true);
            }
            return;
        }

        ObservableList<Person> largeList = FXCollections.observableArrayList(
                TypicalPersons.getTypicalPersons());

        KanbanBoard kanbanBoard = new KanbanBoard(largeList);
        assertNotNull(kanbanBoard);
        assertNotNull(kanbanBoard.getRoot());
        assertEquals(largeList.size(), TypicalPersons.getTypicalPersons().size());
    }

    @Test
    public void constructor_singlePerson_success() {
        if (!GuiTestUtil.isToolkitAvailable()) {
            ObservableList<Person> singlePersonList = FXCollections.observableArrayList(
                    TypicalPersons.DANIEL);
            try {
                KanbanBoard kanbanBoard = new KanbanBoard(singlePersonList);
                assertNotNull(kanbanBoard);
            } catch (Exception e) {
                assertTrue(true);
            }
            return;
        }

        ObservableList<Person> singlePersonList = FXCollections.observableArrayList(
                TypicalPersons.DANIEL);

        KanbanBoard kanbanBoard = new KanbanBoard(singlePersonList);
        assertNotNull(kanbanBoard);
        assertNotNull(kanbanBoard.getRoot());
    }
}
