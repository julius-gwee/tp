package seedu.address.ui;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
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

    @BeforeEach
    public void checkToolkitAvailability() {
        // Skip tests if JavaFX toolkit is not available (e.g., in headless CI environments)
        assumeTrue(GuiTestUtil.isToolkitAvailable(), "JavaFX toolkit not available - skipping UI tests");
    }

    @Test
    public void constructor_validParameters_success() {
        ObservableList<Person> personList = FXCollections.observableArrayList(
            TypicalPersons.ALICE,
            TypicalPersons.BENSON
        );

        KanbanColumn kanbanColumn = new KanbanColumn("Candidates", personList);
        assertNotNull(kanbanColumn);
        assertNotNull(kanbanColumn.getRoot());
    }

    @Test
    public void constructor_emptyPersonList_success() {
        ObservableList<Person> emptyList = FXCollections.observableArrayList();

        KanbanColumn kanbanColumn = new KanbanColumn("Empty Column", emptyList);
        assertNotNull(kanbanColumn);
        assertNotNull(kanbanColumn.getRoot());
    }

    @Test
    public void constructor_differentHeaderTexts_success() {
        ObservableList<Person> personList = FXCollections.observableArrayList(TypicalPersons.CARL);

        KanbanColumn column1 = new KanbanColumn("Interviewed", personList);
        assertNotNull(column1);

        KanbanColumn column2 = new KanbanColumn("Hired", personList);
        assertNotNull(column2);

        KanbanColumn column3 = new KanbanColumn("Rejected", personList);
        assertNotNull(column3);
    }
}

