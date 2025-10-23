package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import java.util.Date;

import org.junit.jupiter.api.Test;

public class DateAddedTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new DateAdded(null));
    }

    @Test
    public void equals() {
        Date date = new Date();
        DateAdded dateAdded = new DateAdded(date);

        // same values -> returns true
        assertTrue(dateAdded.equals(new DateAdded(date)));

        // same object -> returns true
        assertTrue(dateAdded.equals(dateAdded));

        // null -> returns false
        assertFalse(dateAdded.equals(null));

        // different types -> returns false
        assertFalse(dateAdded.equals(5.0f));

        // different values -> returns false
        assertFalse(dateAdded.equals(new DateAdded(new Date(12))));
    }
}
