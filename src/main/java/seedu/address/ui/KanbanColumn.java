package seedu.address.ui;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.person.Person;

/**
 * A kanban column that displays a list of persons with a header.
 */
public class KanbanColumn extends UiPart<Region> {
    private static final String FXML = "KanbanColumn.fxml";
    private final Logger logger = LogsCenter.getLogger(KanbanColumn.class);
    private boolean isTestMode = false;

    @FXML
    private Label columnHeader;

    @FXML
    private ListView<Person> personListView;

    /**
     * Creates a {@code KanbanColumn} with the given header and {@code ObservableList}.
     */
    public KanbanColumn(String headerText, ObservableList<Person> personList) {
        super(FXML);
        columnHeader.setText(headerText);
        personListView.setItems(personList);
        personListView.setCellFactory(listView -> new PersonListViewCell());
    }

    /**
     * Constructor for testing purposes that doesn't require FXML loading.
     */
    protected KanbanColumn(String headerText, ObservableList<Person> personList, boolean isTest) {
        super(new Region(), true);
        this.isTestMode = isTest;
        columnHeader = new Label();
        personListView = new ListView<>();
        columnHeader.setText(headerText);
        personListView.setItems(personList);
        // In test mode, don't set cell factory to avoid FXML loading
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Person} using a {@code PersonCard}.
     */
    class PersonListViewCell extends ListCell<Person> {
        @Override
        protected void updateItem(Person person, boolean empty) {
            super.updateItem(person, empty);

            if (empty || person == null) {
                setGraphic(null);
                setText(null);
            } else {
                PersonCard card = isTestMode
                        ? new PersonCard(person, getIndex() + 1, true)
                        : new PersonCard(person, getIndex() + 1);
                setGraphic(card.getRoot());
            }
        }
    }
}

