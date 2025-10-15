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
        assertEquals("Alex Yeoh", samplePersons[0].getName().fullName);
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
        Set<Tag> tags = SampleDataUtil.getTagSet("friends", "colleagues");
        assertNotNull(tags);
        assertEquals(2, tags.size());

        Tag friendsTag = tags.stream()
                .filter(tag -> tag.tagName.equals("friends"))
                .findFirst()
                .orElseThrow();
        assertEquals("Social", friendsTag.category);
        assertEquals("#0B5FFF", friendsTag.colour);
        assertEquals("Close friends and confidants.", friendsTag.description);

        Tag colleaguesTag = tags.stream()
                .filter(tag -> tag.tagName.equals("colleagues"))
                .findFirst()
                .orElseThrow();
        assertEquals("Work", colleaguesTag.category);
        assertEquals("#8E44AD", colleaguesTag.colour);
        assertEquals("Professional contacts and teammates.", colleaguesTag.description);

        // Tags outside the curated sample list should still fall back to defaults.
        Tag customTag = SampleDataUtil.getTagSet("gymbuddy").iterator().next();
        assertEquals(Tag.DEFAULT_CATEGORY, customTag.category);
        assertEquals(Tag.DEFAULT_COLOUR, customTag.colour);
        assertEquals(Tag.DEFAULT_DESCRIPTION, customTag.description);
        });
    }
}
