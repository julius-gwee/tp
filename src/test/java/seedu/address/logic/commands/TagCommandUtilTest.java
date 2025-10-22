package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.Person;
import seedu.address.model.tag.Tag;
import seedu.address.testutil.PersonBuilder;

class TagCommandUtilTest {

    @Test
    void resolveTags_allTagsExist_returnsCatalogueEntries() throws Exception {
        Tag existingTag = new Tag("backend", "Engineering", "#1F75FE", "Backend specialist");
        TagCommandTestUtil.ModelStub modelStub = new TagCommandTestUtil.ModelStub();
        modelStub.addExistingTag(existingTag);

        Set<Tag> inputTags = new HashSet<>();
        inputTags.add(new Tag("backend"));

        Set<Tag> resolvedTags = TagCommandUtil.resolveTags(modelStub, inputTags);

        assertEquals(1, resolvedTags.size());
        Tag resolvedTag = resolvedTags.iterator().next();
        assertSame(existingTag, resolvedTag);
    }

    @Test
    void resolveTags_missingTag_throwsCommandException() {
        TagCommandTestUtil.ModelStub modelStub = new TagCommandTestUtil.ModelStub();
        Set<Tag> inputTags = Set.of(new Tag("missing"));

        CommandException exception = assertThrows(CommandException.class, () -> TagCommandUtil
                .resolveTags(modelStub, inputTags));
        assertEquals(TagCommandUtil.MESSAGE_TAG_NOT_FOUND, exception.getMessage());
    }

    @Test
    void resolveTags_nullArguments_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> TagCommandUtil.resolveTags(null, Set.of()));
        assertThrows(NullPointerException.class, () -> TagCommandUtil
                .resolveTags(new TagCommandTestUtil.ModelStub(), null));
    }

    @Test
    void rebuildPersonWithTags_copiesCoreFields() {
        Person original = new PersonBuilder().withName("Alice").withPhone("12345678")
                .withEmail("alice@example.com").withAddress("123 Street").withTags("friends").build();

        Set<Tag> newTags = Set.of(new Tag("backend"));
        Person rebuilt = TagCommandUtil.rebuildPersonWithTags(original, newTags);

        assertEquals(original.getName(), rebuilt.getName());
        assertEquals(original.getPhone(), rebuilt.getPhone());
        assertEquals(original.getEmail(), rebuilt.getEmail());
        assertEquals(original.getAddress(), rebuilt.getAddress());
        assertTrue(rebuilt.getTags().containsAll(newTags));
        assertThrows(UnsupportedOperationException.class, () -> rebuilt.getTags().remove(new Tag("backend")));
    }

    @Test
    void rebuildPersonWithTags_nullArguments_throwsNullPointerException() {
        Person person = new PersonBuilder().build();
        assertThrows(NullPointerException.class, () -> TagCommandUtil.rebuildPersonWithTags(null, Set.of()));
        assertThrows(NullPointerException.class, () -> TagCommandUtil.rebuildPersonWithTags(person, null));
    }
}
