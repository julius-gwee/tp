package seedu.address.model.tag;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.model.tag.exceptions.DuplicateTagException;
import seedu.address.model.tag.exceptions.TagNotFoundException;

class UniqueTagListTest {

    private UniqueTagList uniqueTagList;
    private Tag backend;
    private Tag frontend;

    @BeforeEach
    void setUp() {
        uniqueTagList = new UniqueTagList();
        backend = new Tag("backend", "Engineering", "#1F75FE", "Backend specialist");
        frontend = new Tag("frontend", "Product", "#FF5733", "Frontend work");
    }

    @Test
    void contains_nullTag_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueTagList.contains(null));
    }

    @Test
    void contains_tagNotInList_returnsFalse() {
        assertFalse(uniqueTagList.contains(backend));
    }

    @Test
    void contains_tagInList_returnsTrue() {
        uniqueTagList.add(backend);
        assertTrue(uniqueTagList.contains(new Tag("backend")));
    }

    @Test
    void get_tagInList_returnsStoredInstance() {
        uniqueTagList.add(backend);
        assertEquals(backend, uniqueTagList.get(new Tag("backend")));
    }

    @Test
    void add_nullTag_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueTagList.add(null));
    }

    @Test
    void add_duplicateTag_throwsDuplicateTagException() {
        uniqueTagList.add(backend);
        assertThrows(DuplicateTagException.class, () -> uniqueTagList.add(new Tag("backend")));
    }

    @Test
    void setTag_nullArguments_throwsNullPointerException() {
        uniqueTagList.add(backend);
        assertThrows(NullPointerException.class, () -> uniqueTagList.setTag(null, backend));
        assertThrows(NullPointerException.class, () -> uniqueTagList.setTag(backend, null));
    }

    @Test
    void setTag_targetNotFound_throwsTagNotFoundException() {
        assertThrows(TagNotFoundException.class, () -> uniqueTagList.setTag(backend, frontend));
    }

    @Test
    void setTag_sameIdentity_success() {
        uniqueTagList.add(backend);
        Tag updatedBackend = new Tag("backend", "Infrastructure", "#00FF00", "Maintains infra");
        uniqueTagList.setTag(backend, updatedBackend);

        assertEquals(updatedBackend, uniqueTagList.get(new Tag("backend")));
    }

    @Test
    void setTag_duplicateIdentity_throwsDuplicateTagException() {
        uniqueTagList.add(backend);
        uniqueTagList.add(frontend);

        assertThrows(DuplicateTagException.class, () -> uniqueTagList.setTag(frontend, new Tag("backend")));
    }

    @Test
    void remove_nullTag_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueTagList.remove(null));
    }

    @Test
    void remove_tagNotFound_throwsTagNotFoundException() {
        assertThrows(TagNotFoundException.class, () -> uniqueTagList.remove(backend));
    }

    @Test
    void remove_existingTag_success() {
        uniqueTagList.add(backend);
        uniqueTagList.remove(backend);

        assertFalse(uniqueTagList.contains(backend));
    }

    @Test
    void setTags_uniqueList_replacesData() {
        uniqueTagList.add(backend);
        UniqueTagList replacement = new UniqueTagList();
        replacement.add(frontend);

        uniqueTagList.setTags(replacement);
        assertTrue(uniqueTagList.contains(frontend));
        assertFalse(uniqueTagList.contains(backend));
    }

    @Test
    void setTags_listWithDuplicates_throwsDuplicateTagException() {
        List<Tag> tagsWithDuplicates = Arrays.asList(backend, new Tag("backend"));
        assertThrows(DuplicateTagException.class, () -> uniqueTagList.setTags(tagsWithDuplicates));
    }

    @Test
    void asUnmodifiableObservableList_modify_throwsUnsupportedOperationException() {
        uniqueTagList.add(backend);
        assertThrows(UnsupportedOperationException.class, () -> uniqueTagList.asUnmodifiableObservableList().remove(0));
    }
}
