package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class RatingTest {

    @Test
    public void fromString_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> Rating.fromString(null));
    }

    @Test
    public void fromString_invalidRating_throwsIllegalArgumentException() {
        String invalidRating = "";
        assertThrows(IllegalArgumentException.class, () -> Rating.fromString(invalidRating));
    }

    @Test
    public void isValidRating() {
        // null rating
        assertThrows(NullPointerException.class, () -> Rating.isValidRating(null));

        // invalid ratings
        assertFalse(Rating.isValidRating("")); // empty string
        assertFalse(Rating.isValidRating(" ")); // spaces only
        assertFalse(Rating.isValidRating("not a rating"));

        // valid ratings
        assertTrue(Rating.isValidRating("very poor"));
        assertTrue(Rating.isValidRating("AVERAGE"));
        assertTrue(Rating.isValidRating(" Excellent "));
    }

    @Test
    public void fromString_parsesDisplayNamesAndEnumNames() {
        // display names
        org.junit.jupiter.api.Assertions.assertEquals(Rating.VERY_POOR, Rating.fromString("Very Poor"));
        org.junit.jupiter.api.Assertions.assertEquals(Rating.EXCELLENT, Rating.fromString("excellent"));
        // enum names
        org.junit.jupiter.api.Assertions.assertEquals(Rating.GOOD, Rating.fromString("GOOD"));
        org.junit.jupiter.api.Assertions.assertEquals(Rating.AVERAGE, Rating.fromString("average"));
    }

    @Test
    public void getDisplayName_matchesToString() {
        org.junit.jupiter.api.Assertions.assertEquals(Rating.EXCELLENT.toString(), Rating.EXCELLENT.getDisplayName());
    }
}
