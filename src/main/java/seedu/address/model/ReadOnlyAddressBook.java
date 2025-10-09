package seedu.address.model;

import javafx.collections.ObservableList;
import seedu.address.model.person.Person;

/**
 * Unmodifiable view of the candidate list
 */
public interface ReadOnlyAddressBook {

    /**
     * Returns an unmodifiable view of the candidates list.
     * This list will not contain any duplicate candidates.
     */
    ObservableList<Person> getCandidateList();

}
