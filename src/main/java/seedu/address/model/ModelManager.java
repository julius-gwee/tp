package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.nio.file.Path;
import java.util.Comparator;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.person.Person;
import seedu.address.model.tag.Tag;

/**
 * Represents the in-memory model of the address book data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final Findr findr;
    private final UserPrefs userPrefs;
    private final FilteredList<Person> filteredPersons;
    private final SortedList<Person> sortedPersons;

    /**
     * Initializes a ModelManager with the given addressBook and userPrefs.
     */
    public ModelManager(ReadOnlyFindr addressBook, ReadOnlyUserPrefs userPrefs) {
        requireAllNonNull(addressBook, userPrefs);

        logger.fine("Initializing with address book: " + addressBook + " and user prefs " + userPrefs);

        this.findr = new Findr(addressBook);
        this.userPrefs = new UserPrefs(userPrefs);
        this.filteredPersons = new FilteredList<>(this.findr.getCandidateList());
        this.sortedPersons = new SortedList<>(this.filteredPersons);
    }

    public ModelManager() {
        this(new Findr(), new UserPrefs());
    }

    //=========== UserPrefs ==================================================================================

    @Override
    public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
        requireNonNull(userPrefs);
        this.userPrefs.resetData(userPrefs);
    }

    @Override
    public ReadOnlyUserPrefs getUserPrefs() {
        return userPrefs;
    }

    @Override
    public GuiSettings getGuiSettings() {
        return userPrefs.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        requireNonNull(guiSettings);
        userPrefs.setGuiSettings(guiSettings);
    }

    @Override
    public Path getAddressBookFilePath() {
        return userPrefs.getAddressBookFilePath();
    }

    @Override
    public void setAddressBookFilePath(Path addressBookFilePath) {
        requireNonNull(addressBookFilePath);
        userPrefs.setAddressBookFilePath(addressBookFilePath);
    }

    //=========== AddressBook ================================================================================

    @Override
    public void setAddressBook(ReadOnlyFindr addressBook) {
        this.findr.resetData(addressBook);
    }

    @Override
    public ReadOnlyFindr getCandidateList() {
        return findr;
    }

    @Override
    public boolean hasCandidate(Person person) {
        requireNonNull(person);
        return findr.hasCandidate(person);
    }

    @Override
    public void deleteCandidate(Person target) {
        findr.removePerson(target);
    }

    @Override
    public void addCandidate(Person person) {
        findr.addCandidate(person);
        updateFilteredCandidateList(PREDICATE_SHOW_ALL_PERSONS);
    }

    @Override
    public void setPerson(Person target, Person editedPerson) {
        requireAllNonNull(target, editedPerson);

        findr.setPerson(target, editedPerson);
    }

    @Override
    public ObservableList<Tag> getTagList() {
        return findr.getTagList();
    }

    @Override
    public boolean hasTag(Tag tag) {
        requireNonNull(tag);
        return findr.hasTag(tag);
    }

    @Override
    public Tag getTag(Tag tag) {
        requireNonNull(tag);
        return findr.getTag(tag);
    }

    @Override
    public void addTag(Tag tag) {
        requireNonNull(tag);
        findr.addTag(tag);
    }

    @Override
    public void setTag(Tag target, Tag editedTag) {
        requireAllNonNull(target, editedTag);
        findr.setTag(target, editedTag);
    }

    @Override
    public void deleteTag(Tag tag) {
        requireNonNull(tag);
        findr.removeTag(tag);
    }

    //=========== Sorted and Filtered Person List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Person} backed by the internal list of
     * {@code versionedAddressBook}
     */
    @Override
    public ObservableList<Person> getObservableCandidateList() {
        return sortedPersons;
    }

    @Override
    public void updateFilteredCandidateList(Predicate<Person> predicate) {
        requireNonNull(predicate);
        filteredPersons.setPredicate(predicate);
    }

    @Override
    public void updateSortedCandidateList(Comparator<Person> comparator) {
        requireNonNull(comparator);
        sortedPersons.setComparator(comparator);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ModelManager)) {
            return false;
        }

        ModelManager otherModelManager = (ModelManager) other;
        return findr.equals(otherModelManager.findr)
                && userPrefs.equals(otherModelManager.userPrefs)
                && sortedPersons.equals(otherModelManager.sortedPersons);
    }
}
