package seedu.address.model.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.address.model.ReadOnlyFindr;
import seedu.address.model.person.Person;
import seedu.address.model.tag.Tag;

public class SampleDataUtilTest {

    @Test
    public void getSamplePersons_validData_success() {
        Person[] samplePersons = SampleDataUtil.getSamplePersons();
        assertNotNull(samplePersons);
        assertFalse(samplePersons.length == 0);
        // Verify a known value for correctness
        assertEquals("Amelia Chen", samplePersons[0].getName().fullName);
    }

    @Test
    public void getSampleAddressBook_containsAllSamplePersons() {
        ReadOnlyFindr sampleFindr = SampleDataUtil.getSampleAddressBook();
        assertNotNull(sampleFindr);
        assertFalse(sampleFindr.getCandidateList().isEmpty());

        // Ensure all sample persons are inside the address book
        for (Person p : SampleDataUtil.getSamplePersons()) {
            assertTrue(sampleFindr.getCandidateList().contains(p));
        }
    }

    @Test
    public void getTagSet_validTags_success() {
        Set<Tag> tags = SampleDataUtil.getTagSet("frontend", "backend");
        assertNotNull(tags);
        assertEquals(2, tags.size());

        Tag frontendTag = tags.stream()
                .filter(tag -> tag.tagName.equals("frontend"))
                .findFirst()
                .orElseThrow();
        assertEquals("Engineering Focus", frontendTag.category);
        assertEquals("#0B5FFF", frontendTag.colour);
        assertEquals("Specialises in building responsive user interfaces.", frontendTag.description);

        Tag backendTag = tags.stream()
                .filter(tag -> tag.tagName.equals("backend"))
                .findFirst()
                .orElseThrow();
        assertEquals("Engineering Focus", backendTag.category);
        assertEquals("#8E44AD", backendTag.colour);
        assertEquals("Experienced with server-side systems and APIs.", backendTag.description);

        // Tags outside the curated sample list should still fall back to defaults.
        Tag customTag = SampleDataUtil.getTagSet("gymbuddy").iterator().next();
        assertEquals(Tag.DEFAULT_CATEGORY, customTag.category);
        assertEquals(Tag.DEFAULT_COLOUR, customTag.colour);
        assertEquals(Tag.DEFAULT_DESCRIPTION, customTag.description);
    }
}
