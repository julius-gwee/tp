package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class NameTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Name(null));
    }

    @Test
    public void constructor_invalidName_throwsIllegalArgumentException() {
        String invalidName = "";
        assertThrows(IllegalArgumentException.class, () -> new Name(invalidName));
    }

    @Test
    public void isValidName() {
        // null name
        assertThrows(NullPointerException.class, () -> Name.isValidName(null));

        // invalid name
        assertFalse(Name.isValidName("")); // empty string
        assertFalse(Name.isValidName(" ")); // spaces only
        assertFalse(Name.isValidName("^")); // only non-alphanumeric characters
        assertFalse(Name.isValidName("peter*")); // contains non-alphanumeric characters

        // valid name
        assertTrue(Name.isValidName("peter jack")); // alphabets only
        assertTrue(Name.isValidName("12345")); // numbers only
        assertTrue(Name.isValidName("peter the 2nd")); // alphanumeric characters
        assertTrue(Name.isValidName("Capital Tan")); // with capital letters
        assertTrue(Name.isValidName("David Roger Jackson Ray Jr 2nd")); // long names
        assertTrue(Name.isValidName("peter-@")); // contains '-' and '@'
        assertTrue(Name.isValidName("peter s/o maria")); // contains s/o
        assertTrue(Name.isValidName("Aurélie")); // contains accented characters
    }

    @Test
    public void equals() {
        Name name = new Name("Valid Name");

        // same values -> returns true
        assertTrue(name.equals(new Name("Valid Name")));

        // same object -> returns true
        assertTrue(name.equals(name));

        // null -> returns false
        assertFalse(name.equals(null));

        // different types -> returns false
        assertFalse(name.equals(5.0f));

        // different values -> returns false
        assertFalse(name.equals(new Name("Other Valid Name")));

        // differs in case and spacing -> returns true
        assertTrue(new Name("Valid    Name").equals(new Name("valid name")));
    }

    @Test
    public void displayFormatting_collapsesWhitespaceAndCapitalizesWords() {
        Name name = new Name("  bRIAN   lee   ");
        assertEquals("Brian Lee", name.fullName);
        assertEquals("Brian Lee", name.toString());

        Name accentedName = new Name("  aURÉLIE   d'éon ");
        assertEquals("Aurélie D'Éon", accentedName.fullName);
        assertEquals("Aurélie D'Éon", accentedName.toString());

        Name relationshipName = new Name("  brian   s/o   tan ");
        assertEquals("Brian S/O Tan", relationshipName.fullName);
        assertEquals("Brian S/O Tan", relationshipName.toString());

        Name hyphenatedName = new Name("  anne-marie  smith  ");
        assertEquals("Anne-Marie Smith", hyphenatedName.fullName);
        assertEquals("Anne-Marie Smith", hyphenatedName.toString());
    }
}
