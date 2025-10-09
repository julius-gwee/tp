package seedu.address.model;

import javafx.collections.ObservableList;
import seedu.address.model.person.Person;
import seedu.address.model.tag.Tag;

/**
 * Unmodifiable view of the candidate list
 */
public interface ReadOnlyAddressBook {

    /**
     * Returns an unmodifiable view of the candidates list.
     * This list will not contain any duplicate candidates.
     */
    ObservableList<Person> getCandidateList();

    /**
     * Returns an unmodifiable view of the tag definitions list.
     * This list will not contain any duplicate tags.
     */
    ObservableList<Tag> getTagList();
}
