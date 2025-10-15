package seedu.address.ui;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.person.Person;
import seedu.address.testutil.TypicalPersons;
import seedu.address.ui.testutil.GuiTestUtil;

/**
 * Contains tests for KanbanColumn.
 */
public class KanbanColumnTest {

    @BeforeAll
    public static void initToolkit() {
        GuiTestUtil.initToolkit();
    }

    @Test
    public void constructor_validParameters_success() {
        ObservableList<Person> personList = FXCollections.observableArrayList(
            TypicalPersons.ALICE,
            TypicalPersons.BENSON
        );

        // Use test constructor that doesn't require FXML
        KanbanColumn kanbanColumn = new KanbanColumn("Candidates", personList, true);
        assertNotNull(kanbanColumn);
        assertNotNull(kanbanColumn.getRoot());
    }

    @Test
    public void constructor_emptyPersonList_success() {
        ObservableList<Person> emptyList = FXCollections.observableArrayList();

        // Use test constructor that doesn't require FXML
        KanbanColumn kanbanColumn = new KanbanColumn("Empty Column", emptyList, true);
        assertNotNull(kanbanColumn);
        assertNotNull(kanbanColumn.getRoot());
    }

    @Test
    public void constructor_differentHeaderTexts_success() {
        ObservableList<Person> personList = FXCollections.observableArrayList(TypicalPersons.CARL);

        // Use test constructor that doesn't require FXML
        KanbanColumn column1 = new KanbanColumn("Interviewed", personList, true);
        assertNotNull(column1);

        KanbanColumn column2 = new KanbanColumn("Hired", personList, true);
        assertNotNull(column2);

        KanbanColumn column3 = new KanbanColumn("Rejected", personList, true);
        assertNotNull(column3);
    }
}

