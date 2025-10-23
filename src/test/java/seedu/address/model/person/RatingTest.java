package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class RatingTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Rating(null));
    }

    @Test
    public void constructor_invalidRating_throwsIllegalArgumentException() {
        String invalidRating = "";
        assertThrows(IllegalArgumentException.class, () -> new Rating(invalidRating));
    }

    @Test
    public void isValidRating() {
        // null rating
        assertThrows(NullPointerException.class, () -> Rating.isValidRating(null));

        // invalid ratings
        assertFalse(Rating.isValidRating("")); // empty string
        assertFalse(Rating.isValidRating(" ")); // spaces only
        assertFalse(Rating.isValidRating("rating")); // non-numeric
        assertFalse(Rating.isValidRating("9 4")); // spaces within digits

        // valid ratings
        assertTrue(Rating.isValidRating("one")); // small letters
        assertTrue(Rating.isValidRating("TWO")); //  capital letters
        assertTrue(Rating.isValidRating(" three ")); // whitespace
    }

    @Test
    public void equals() {
        Rating rating = new Rating("ONE");

        // same values -> returns true
        assertTrue(rating.equals(new Rating("ONE")));

        // same object -> returns true
        assertTrue(rating.equals(rating));

        // null -> returns false
        assertFalse(rating.equals(null));

        // different types -> returns false
        assertFalse(rating.equals(5.0f));

        // different values -> returns false
        assertFalse(rating.equals(new Rating("TWO")));
    }
}
