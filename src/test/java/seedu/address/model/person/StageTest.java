package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class StageTest {

    @Test
    public void fromString_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> Stage.fromString(null));
    }

    @Test
    public void fromString_invalidStage_throwsIllegalArgumentException() {
        String invalidStage = "InvalidStage";
        assertThrows(IllegalArgumentException.class, () -> Stage.fromString(invalidStage));
    }

    @Test
    public void isValidStage() {
        // null stage
        assertThrows(NullPointerException.class, () -> Stage.isValidStage(null));

        // invalid stages
        assertFalse(Stage.isValidStage("")); // empty string
        assertFalse(Stage.isValidStage(" ")); // spaces only
        assertFalse(Stage.isValidStage("InvalidStage")); // invalid stage name
        assertFalse(Stage.isValidStage("candidate")); // singular form
        assertFalse(Stage.isValidStage("hired123")); // stage name with numbers

        // valid stages - exact case
        assertTrue(Stage.isValidStage("CANDIDATES"));
        assertTrue(Stage.isValidStage("CONTACTED"));
        assertTrue(Stage.isValidStage("INTERVIEWED"));
        assertTrue(Stage.isValidStage("HIRED"));

        // valid stages - case insensitive
        assertTrue(Stage.isValidStage("candidates"));
        assertTrue(Stage.isValidStage("contacted"));
        assertTrue(Stage.isValidStage("interviewed"));
        assertTrue(Stage.isValidStage("hired"));
        assertTrue(Stage.isValidStage("Candidates"));
        assertTrue(Stage.isValidStage("Contacted"));
        assertTrue(Stage.isValidStage("Interviewed"));
        assertTrue(Stage.isValidStage("Hired"));
        assertTrue(Stage.isValidStage("CaNdIdAtEs")); // mixed case
    }

    @Test
    public void fromString_validStages_success() {
        // test case insensitive parsing
        assertEquals(Stage.CANDIDATES, Stage.fromString("CANDIDATES"));
        assertEquals(Stage.CANDIDATES, Stage.fromString("candidates"));
        assertEquals(Stage.CANDIDATES, Stage.fromString("Candidates"));
        assertEquals(Stage.CANDIDATES, Stage.fromString("CaNdIdAtEs"));

        assertEquals(Stage.CONTACTED, Stage.fromString("CONTACTED"));
        assertEquals(Stage.CONTACTED, Stage.fromString("contacted"));
        assertEquals(Stage.CONTACTED, Stage.fromString("Contacted"));

        assertEquals(Stage.INTERVIEWED, Stage.fromString("INTERVIEWED"));
        assertEquals(Stage.INTERVIEWED, Stage.fromString("interviewed"));
        assertEquals(Stage.INTERVIEWED, Stage.fromString("Interviewed"));

        assertEquals(Stage.HIRED, Stage.fromString("HIRED"));
        assertEquals(Stage.HIRED, Stage.fromString("hired"));
        assertEquals(Stage.HIRED, Stage.fromString("Hired"));
    }

    @Test
    public void getDisplayName() {
        assertEquals("Candidates", Stage.CANDIDATES.getDisplayName());
        assertEquals("Contacted", Stage.CONTACTED.getDisplayName());
        assertEquals("Interviewed", Stage.INTERVIEWED.getDisplayName());
        assertEquals("Hired", Stage.HIRED.getDisplayName());
    }

    @Test
    public void toString_returnsDisplayName() {
        assertEquals("Candidates", Stage.CANDIDATES.toString());
        assertEquals("Contacted", Stage.CONTACTED.toString());
        assertEquals("Interviewed", Stage.INTERVIEWED.toString());
        assertEquals("Hired", Stage.HIRED.toString());
    }

    @Test
    public void equals() {
        // same enum value -> returns true
        assertTrue(Stage.CANDIDATES.equals(Stage.CANDIDATES));
        assertTrue(Stage.CONTACTED.equals(Stage.CONTACTED));

        // different enum values -> returns false
        assertFalse(Stage.CANDIDATES.equals(Stage.CONTACTED));
        assertFalse(Stage.INTERVIEWED.equals(Stage.HIRED));

        // null -> returns false
        assertFalse(Stage.CANDIDATES.equals(null));

        // different types -> returns false
        assertFalse(Stage.CANDIDATES.equals("Candidates"));
    }
}

