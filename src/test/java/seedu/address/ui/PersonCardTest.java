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

        PersonCard personCard = new PersonCard(person, 1);
        assertNotNull(personCard);
        assertNotNull(personCard.getRoot());
        assertEquals(person, personCard.person);
    }

    @Test
    public void constructor_differentIndexes_success() {
        Person person = TypicalPersons.BENSON;

        // Test with index 1
        PersonCard personCard1 = new PersonCard(person, 1);
        assertNotNull(personCard1);
        assertEquals(person, personCard1.person);

        // Test with index 10
        PersonCard personCard10 = new PersonCard(person, 10);
        assertNotNull(personCard10);
        assertEquals(person, personCard10.person);

        // Test with index 100
        PersonCard personCard100 = new PersonCard(person, 100);
        assertNotNull(personCard100);
        assertEquals(person, personCard100.person);
    }

    @Test
    public void constructor_personWithTags_success() {
        Person personWithTags = TypicalPersons.BENSON; // Has tags: "owesMoney", "friends"

        PersonCard personCard = new PersonCard(personWithTags, 1);
        assertNotNull(personCard);
        assertNotNull(personCard.getRoot());
        assertEquals(personWithTags, personCard.person);
    }

    @Test
    public void constructor_personWithoutTags_success() {
        Person personWithoutTags = TypicalPersons.CARL; // No tags

        PersonCard personCard = new PersonCard(personWithoutTags, 1);
        assertNotNull(personCard);
        assertNotNull(personCard.getRoot());
        assertEquals(personWithoutTags, personCard.person);
    }
}
