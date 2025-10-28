package seedu.address.model;

import java.nio.file.Path;
import java.util.Comparator;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.model.person.Person;
import seedu.address.model.tag.Tag;

/**
 * The API of the Model component.
 */
public interface Model {
    /** {@code Predicate} that always evaluate to true */
    Predicate<Person> PREDICATE_SHOW_ALL_PERSONS = unused -> true;

    /**
     * Replaces user prefs data with the data in {@code userPrefs}.
     */
    void setUserPrefs(ReadOnlyUserPrefs userPrefs);

    /**
     * Returns the user prefs.
     */
    ReadOnlyUserPrefs getUserPrefs();

    /**
     * Returns the user prefs' GUI settings.
     */
    GuiSettings getGuiSettings();

    /**
     * Sets the user prefs' GUI settings.
     */
    void setGuiSettings(GuiSettings guiSettings);

    /**
     * Returns the user prefs' address book file path.
     */
    Path getAddressBookFilePath();

    /**
     * Sets the user prefs' address book file path.
     */
    void setAddressBookFilePath(Path addressBookFilePath);

    /**
     * Replaces address book data with the data in {@code addressBook}.
     */
    void setAddressBook(ReadOnlyFindr addressBook);

    /** Returns the Candidate List */
    ReadOnlyFindr getCandidateList();

    /**
     * Returns true if a person with the same identity as {@code person} exists in the address book.
     */
    boolean hasCandidate(Person person);

    /**
     * Deletes the given candidate.
     * The candidate must exist in the candidate list.
     */
    void deleteCandidate(Person target);

    /**
     * Adds the given person.
     * {@code person} must not already exist in the address book.
     */
    void addCandidate(Person person);

    /**
     * Replaces the given person {@code target} with {@code editedPerson}.
     * {@code target} must exist in the address book.
     * The person identity of {@code editedPerson} must not be the same as another existing person in the address book.
     */
    void setPerson(Person target, Person editedPerson);

    /** Returns an unmodifiable view of the filtered person list */
    ObservableList<Person> getObservableCandidateList();

    /**
     * Updates the filter of the filtered person list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredCandidateList(Predicate<Person> predicate);

    /**
     * Updates the sort type of the sorted person list to sort by the given criteria.
     */
    void updateSortedCandidateList(Comparator<Person> comparator);

    /** Returns an unmodifiable view of the tag definition list. */
    ObservableList<Tag> getTagList();

    /** Returns true if the tag exists in the global tag catalogue. */
    boolean hasTag(Tag tag);

    /** Returns the matching tag from the global catalogue. */
    Tag getTag(Tag tag);

    /** Adds the given tag to the global catalogue. */
    void addTag(Tag tag);

    /** Replaces {@code target} with {@code editedTag} in the global catalogue. */
    void setTag(Tag target, Tag editedTag);

    /** Deletes {@code tag} from the global catalogue. */
    void deleteTag(Tag tag);
}
