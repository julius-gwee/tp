package seedu.address.ui;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.person.Person;
import seedu.address.model.person.Stage;

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
     * Each column displays persons filtered by their recruitment stage.
     */
    public KanbanBoard(ObservableList<Person> personList) {
        super(FXML);
        initializeColumns(personList);
    }

    /**
     * Initializes the kanban columns.
     * Creates four columns: Candidates, Contacted, Interviewed, and Hired.
     * Each column displays persons filtered by their recruitment stage.
     * The filtered lists automatically update when persons are moved between stages.
     * Each person card displays a column-relative index (1, 2, 3...).
     */
    private void initializeColumns(ObservableList<Person> personList) {
        // Create filtered lists for each recruitment stage
        FilteredList<Person> candidatesList = new FilteredList<>(personList,
                person -> person.getStage() == Stage.CANDIDATES);
        FilteredList<Person> contactedList = new FilteredList<>(personList,
                person -> person.getStage() == Stage.CONTACTED);
        FilteredList<Person> interviewedList = new FilteredList<>(personList,
                person -> person.getStage() == Stage.INTERVIEWED);
        FilteredList<Person> hiredList = new FilteredList<>(personList,
                person -> person.getStage() == Stage.HIRED);

        // Create the four kanban columns with column-relative indices
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

