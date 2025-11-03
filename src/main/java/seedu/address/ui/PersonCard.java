package seedu.address.ui;

import java.util.Comparator;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import seedu.address.model.person.Person;
import seedu.address.model.tag.Tag;

/**
 * An UI component that displays information of a {@code Person}.
 */
public class PersonCard extends UiPart<Region> {

    private static final String FXML = "PersonListCard.fxml";
    private static final double TEXT_COLOUR_LUMINANCE_THRESHOLD = 0.5;

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final Person person;

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private Label phone;
    @FXML
    private Label email;
    @FXML
    private Label address;
    @FXML
    private FlowPane tags;
    @FXML
    private Label rating;

    /**
     * Creates a {@code PersonCode} with the given {@code Person} and index to display.
     */
    public PersonCard(Person person, int displayedIndex) {
        super(FXML);
        this.person = person;
        id.setText(displayedIndex + ". ");
        name.setText(person.getName().fullName);
        phone.setText(person.getPhone().value);
        email.setText(person.getEmail().value);
        address.setText(person.getAddress().value);
        tags.getChildren().clear();
        person.getTags().stream()
                .sorted(Comparator.comparing(tag -> tag.tagName))
                .map(this::createTagLabel)
                .forEach(tagLabel -> tags.getChildren().add(tagLabel));
        rating.setText(person.getRating().toString());
    }

    private Label createTagLabel(Tag tag) {
        Label tagLabel = new Label(tag.tagName);
        String textColour = getContrastingTextColour(tag.colour);
        tagLabel.setStyle(String.format("-fx-background-color: %s; -fx-text-fill: %s;", tag.colour, textColour));
        return tagLabel;
    }

    private String getContrastingTextColour(String hexColour) {
        Color colour = Color.web(hexColour);
        double luminance = calculateRelativeLuminance(colour);
        return luminance > TEXT_COLOUR_LUMINANCE_THRESHOLD ? "#000000" : "#FFFFFF";
    }

    private double calculateRelativeLuminance(Color colour) {
        return 0.2126 * colour.getRed() + 0.7152 * colour.getGreen() + 0.0722 * colour.getBlue();
    }
}
