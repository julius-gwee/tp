package seedu.address.ui;

import java.util.logging.Logger;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.person.Person;

/**
 * Panel containing the kanban board with multiple columns.
 */
public class KanbanBoard extends UiPart<Region> {
    private static final String FXML = "KanbanBoard.fxml";
    private final Logger logger = LogsCenter.getLogger(KanbanBoard.class);

    @FXML
    private HBox columnContainer;

    /**
     * Creates a {@code KanbanBoard} with four columns: Candidates, Contacted, Interviewed, and Hired.
     * For MVP, all persons are displayed in the Candidates column.
     * Persons will be moved between columns via move commands in future implementation.
     */
    public KanbanBoard(ObservableList<Person> personList) {
        super(FXML);
        initializeColumns(personList);
    }

    /**
     * Initializes the kanban columns.
     * Creates four columns: Candidates, Contacted, Interviewed, and Hired.
     * For MVP, all persons are displayed in the Candidates column by default.
     * Persons will be moved between columns via move commands in future implementation.
     */
    private void initializeColumns(ObservableList<Person> personList) {
        // For MVP: All persons appear in Candidates column, other columns are empty
        ObservableList<Person> candidatesList = personList;
        ObservableList<Person> contactedList = FXCollections.observableArrayList();
        ObservableList<Person> interviewedList = FXCollections.observableArrayList();
        ObservableList<Person> hiredList = FXCollections.observableArrayList();

        // Create the four kanban columns
        KanbanColumn candidatesColumn = new KanbanColumn("Candidates", candidatesList);
        KanbanColumn contactedColumn = new KanbanColumn("Contacted", contactedList);
        KanbanColumn interviewedColumn = new KanbanColumn("Interviewed", interviewedList);
        KanbanColumn hiredColumn = new KanbanColumn("Hired", hiredList);

        // Add all columns to the container in order
        columnContainer.getChildren().addAll(
                candidatesColumn.getRoot(),
                contactedColumn.getRoot(),
                interviewedColumn.getRoot(),
                hiredColumn.getRoot()
        );

        // Set each column to grow and fill available space evenly
        HBox.setHgrow(candidatesColumn.getRoot(), Priority.ALWAYS);
        HBox.setHgrow(contactedColumn.getRoot(), Priority.ALWAYS);
        HBox.setHgrow(interviewedColumn.getRoot(), Priority.ALWAYS);
        HBox.setHgrow(hiredColumn.getRoot(), Priority.ALWAYS);
    }
}

