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
        assertTrue(tags.stream().anyMatch(tag -> tag.tagName.equals("friends")));
        tags.forEach(tag -> {
            assertEquals(Tag.DEFAULT_CATEGORY, tag.category);
            assertEquals(Tag.DEFAULT_COLOUR, tag.colour);
            assertEquals(Tag.DEFAULT_DESCRIPTION, tag.description);
        });
    }
}
