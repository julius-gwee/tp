package seedu.address.model;

import javafx.collections.ObservableList;
import seedu.address.model.person.Person;
import seedu.address.model.tag.Tag;

/**
 * Unmodifiable view of an address book
 */
public interface ReadOnlyFindr {

    /**
     * Returns an unmodifiable view of the persons list.
     * This list will not contain any duplicate persons.
     */
    ObservableList<Person> getCandidateList();

    /**
     * Returns an unmodifiable view of the tag definitions list.
     * This list will not contain any duplicate tags.
     */
    ObservableList<Tag> getTagList();
}
