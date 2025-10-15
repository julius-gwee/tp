package seedu.address.ui;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

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
        ObservableList<Person> personList = FXCollections.observableArrayList(
                TypicalPersons.ALICE,
                TypicalPersons.BENSON,
                TypicalPersons.CARL);

        // Use test constructor that doesn't require FXML
        KanbanBoard kanbanBoard = new KanbanBoard(personList, true);
        assertNotNull(kanbanBoard);
        assertNotNull(kanbanBoard.getRoot());
    }

    @Test
    public void constructor_emptyPersonList_success() {
        ObservableList<Person> emptyList = FXCollections.observableArrayList();

        // Use test constructor that doesn't require FXML
        KanbanBoard kanbanBoard = new KanbanBoard(emptyList, true);
        assertNotNull(kanbanBoard);
        assertNotNull(kanbanBoard.getRoot());
    }

    @Test
    public void constructor_largePersonList_success() {
        ObservableList<Person> largeList = FXCollections.observableArrayList(
                TypicalPersons.getTypicalPersons());

        // Use test constructor that doesn't require FXML
        KanbanBoard kanbanBoard = new KanbanBoard(largeList, true);
        assertNotNull(kanbanBoard);
        assertNotNull(kanbanBoard.getRoot());
        assertEquals(largeList.size(), TypicalPersons.getTypicalPersons().size());
    }

    @Test
    public void constructor_singlePerson_success() {
        ObservableList<Person> singlePersonList = FXCollections.observableArrayList(
                TypicalPersons.DANIEL);

        // Use test constructor that doesn't require FXML
        KanbanBoard kanbanBoard = new KanbanBoard(singlePersonList, true);
        assertNotNull(kanbanBoard);
        assertNotNull(kanbanBoard.getRoot());
    }
}
