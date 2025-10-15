package seedu.address.ui;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import seedu.address.model.person.Person;
import seedu.address.testutil.TypicalPersons;
import seedu.address.ui.testutil.GuiTestUtil;

/**
 * Contains tests for PersonCard.
 */
public class PersonCardTest {

    @BeforeAll
    public static void initToolkit() {
        GuiTestUtil.initToolkit();
    }

    @Test
    public void constructor_validPerson_success() {
        Person person = TypicalPersons.ALICE;

        // Use test constructor that doesn't require FXML
        PersonCard personCard = new PersonCard(person, 1, true);
        assertNotNull(personCard);
        assertNotNull(personCard.getRoot());
        assertEquals(person, personCard.person);
    }

    @Test
    public void constructor_differentIndexes_success() {
        Person person = TypicalPersons.BENSON;

        // Use test constructor that doesn't require FXML
        PersonCard personCard1 = new PersonCard(person, 1, true);
        assertNotNull(personCard1);
        assertEquals(person, personCard1.person);

        PersonCard personCard10 = new PersonCard(person, 10, true);
        assertNotNull(personCard10);
        assertEquals(person, personCard10.person);

        PersonCard personCard100 = new PersonCard(person, 100, true);
        assertNotNull(personCard100);
        assertEquals(person, personCard100.person);
    }

    @Test
    public void constructor_personWithTags_success() {
        Person personWithTags = TypicalPersons.BENSON; // Has tags: "owesMoney", "friends"

        // Use test constructor that doesn't require FXML
        PersonCard personCard = new PersonCard(personWithTags, 1, true);
        assertNotNull(personCard);
        assertNotNull(personCard.getRoot());
        assertEquals(personWithTags, personCard.person);
    }

    @Test
    public void constructor_personWithoutTags_success() {
        Person personWithoutTags = TypicalPersons.CARL; // No tags

        // Use test constructor that doesn't require FXML
        PersonCard personCard = new PersonCard(personWithoutTags, 1, true);
        assertNotNull(personCard);
        assertNotNull(personCard.getRoot());
        assertEquals(personWithoutTags, personCard.person);
    }
}
