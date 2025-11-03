package seedu.address.model.tag;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class TagTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Tag(null));
    }

    @Test
    public void constructor_invalidTagName_throwsIllegalArgumentException() {
        String invalidTagName = "";
        assertThrows(IllegalArgumentException.class, () -> new Tag(invalidTagName));
    }

    @Test
    public void constructor_invalidCategory_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> new Tag("friend", " *", Tag.DEFAULT_COLOUR,
                Tag.DEFAULT_DESCRIPTION));
    }

    @Test
    public void constructor_invalidColour_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> new Tag("friend", Tag.DEFAULT_CATEGORY, "blue",
                Tag.DEFAULT_DESCRIPTION));
    }

    @Test
    public void constructor_invalidDescription_throwsIllegalArgumentException() {
        String tooLongDescription = "a".repeat(Tag.DESCRIPTION_MAX_LENGTH + 1);
        assertThrows(IllegalArgumentException.class, () -> new Tag("friend", Tag.DEFAULT_CATEGORY,
                Tag.DEFAULT_COLOUR, tooLongDescription));
    }

    @Test
    public void constructor_validFullDetails_success() {
        Tag tag = new Tag("friend", "Work", "#12ab34", " close colleague ");
        assertEquals("friend", tag.tagName);
        assertEquals("Work", tag.category);
        assertEquals("#12AB34", tag.colour);
        assertEquals("close colleague", tag.description);
    }

    @Test
    public void constructor_defaultFields_success() {
        Tag tag = new Tag("neighbour");
        assertEquals("neighbour", tag.tagName);
        assertEquals(Tag.DEFAULT_CATEGORY, tag.category);
        assertEquals(Tag.DEFAULT_COLOUR, tag.colour);
        assertEquals(Tag.DEFAULT_DESCRIPTION, tag.description);
    }

    @Test
    public void isValidTagName() {
        // null tag name
        assertThrows(NullPointerException.class, () -> Tag.isValidTagName(null));

        // invalid tag names
        assertFalse(Tag.isValidTagName(""));
        assertFalse(Tag.isValidTagName(" "));
        assertFalse(Tag.isValidTagName("friend!"));
        assertFalse(Tag.isValidTagName("friend person"));

        // valid tag names
        assertTrue(Tag.isValidTagName("friend"));
        assertTrue(Tag.isValidTagName("FRIEND1"));
        assertTrue(Tag.isValidTagName("friend123"));
    }

    @Test
    public void isValidCategory() {
        assertThrows(NullPointerException.class, () -> Tag.isValidCategory(null));
        assertFalse(Tag.isValidCategory(""));
        assertFalse(Tag.isValidCategory(" friend"));
        assertFalse(Tag.isValidCategory("*friend"));
        assertTrue(Tag.isValidCategory("Friend"));
        assertTrue(Tag.isValidCategory("Close Friends"));
    }

    @Test
    public void isValidColour() {
        assertThrows(NullPointerException.class, () -> Tag.isValidColour(null));
        assertFalse(Tag.isValidColour(""));
        assertFalse(Tag.isValidColour("123456"));
        assertFalse(Tag.isValidColour("#12345"));
        assertFalse(Tag.isValidColour("#GGGGGG"));
        assertTrue(Tag.isValidColour("#123456"));
        assertTrue(Tag.isValidColour("#abcdef"));
    }

    @Test
    public void isValidDescription() {
        assertThrows(NullPointerException.class, () -> Tag.isValidDescription(null));
        assertTrue(Tag.isValidDescription(""));
        assertTrue(Tag.isValidDescription("A".repeat(Tag.DESCRIPTION_MAX_LENGTH)));
        assertFalse(Tag.isValidDescription("A".repeat(Tag.DESCRIPTION_MAX_LENGTH + 1)));
    }

    @Test
    public void isSameTag() {
        Tag friend = new Tag("friend");
        Tag friendDuplicate = new Tag("friend", "Personal", Tag.DEFAULT_COLOUR, "Close pal");
        Tag friendUpperCase = new Tag("FRIEND");
        Tag colleague = new Tag("colleague");

        assertTrue(friend.isSameTag(friend));
        assertTrue(friend.isSameTag(friendDuplicate));
        assertTrue(friend.isSameTag(friendUpperCase));
        assertFalse(friend.isSameTag(colleague));
        assertFalse(friend.isSameTag(null));
    }

    @Test
    public void equals_nameComparisonIsCaseInsensitive() {
        Tag backend = new Tag("backend", "Engineering", "#123456", "Handles servers");
        Tag backendDifferentCase = new Tag("BackEnd", "Engineering", "#123456", "Handles servers");
        Tag backendDifferentDetails = new Tag("backend", "Operations", "#123456", "Handles servers");

        assertTrue(backend.equals(backendDifferentCase));
        assertEquals(backend.hashCode(), backendDifferentCase.hashCode());
        assertFalse(backend.equals(backendDifferentDetails));
    }

}
