package seedu.address.ui;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.layout.HBox;
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
     * Creates a {@code KanbanBoard} with the given {@code ObservableList}.
     * For MVP, displays all persons in a single "Candidates" column.
     */
    public KanbanBoard(ObservableList<Person> personList) {
        super(FXML);
        initializeColumns(personList);
    }

    /**
     * Initializes the kanban columns.
     * Currently creates a single "Candidates" column for MVP.
     * This method can be extended to create multiple columns with filtered lists.
     */
    private void initializeColumns(ObservableList<Person> personList) {
        // For MVP: Single column showing all candidates
        KanbanColumn candidatesColumn = new KanbanColumn("Candidates", personList);
        columnContainer.getChildren().add(candidatesColumn.getRoot());
        
        // Future: Add more columns here for different statuses
        // Example:
        // KanbanColumn interviewedColumn = new KanbanColumn("Interviewed", filteredList);
        // columnContainer.getChildren().add(interviewedColumn.getRoot());
    }
}

