package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.nio.file.Path;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Predicate;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.model.Findr;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyFindr;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.person.Person;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.exceptions.DuplicateTagException;
import seedu.address.model.tag.exceptions.TagNotFoundException;

/**
 * Helper utilities shared by tag-related command tests.
 */
final class TagCommandTestUtil {

    private TagCommandTestUtil() {}

    static class ModelStub implements Model {
        final Set<Tag> availableTags = new HashSet<>();

        void addExistingTag(Tag tag) {
            requireNonNull(tag);
            availableTags.add(tag);
        }

        @Override
        public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyUserPrefs getUserPrefs() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public GuiSettings getGuiSettings() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setGuiSettings(GuiSettings guiSettings) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Path getAddressBookFilePath() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setAddressBookFilePath(Path addressBookFilePath) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setAddressBook(ReadOnlyFindr addressBook) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyFindr getCandidateList() {
            return new Findr();
        }

        @Override
        public boolean hasCandidate(Person person) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteCandidate(Person target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addCandidate(Person person) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setPerson(Person target, Person editedPerson) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Person> getObservableCandidateList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredCandidateList(Predicate<Person> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateSortedCandidateList(Comparator<Person> comparator) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Tag> getTagList() {
            return FXCollections.observableArrayList(availableTags);
        }

        @Override
        public boolean hasTag(Tag tag) {
            requireNonNull(tag);
            return availableTags.stream().anyMatch(tag::isSameTag);
        }

        @Override
        public Tag getTag(Tag tag) {
            requireNonNull(tag);
            return availableTags.stream()
                    .filter(tag::isSameTag)
                    .findFirst()
                    .orElseThrow(TagNotFoundException::new);
        }

        @Override
        public void addTag(Tag tag) {
            requireNonNull(tag);
            if (hasTag(tag)) {
                throw new DuplicateTagException();
            }
            availableTags.add(tag);
        }

        @Override
        public void setTag(Tag target, Tag editedTag) {
            requireNonNull(target);
            requireNonNull(editedTag);
            Tag existingTag = availableTags.stream()
                    .filter(target::isSameTag)
                    .findFirst()
                    .orElseThrow(TagNotFoundException::new);

            availableTags.remove(existingTag);
            if (availableTags.stream().anyMatch(editedTag::isSameTag)) {
                availableTags.add(existingTag);
                throw new DuplicateTagException();
            }
            availableTags.add(editedTag);
        }

        @Override
        public void deleteTag(Tag tag) {
            requireNonNull(tag);
            boolean removed = availableTags.removeIf(tag::isSameTag);
            if (!removed) {
                throw new TagNotFoundException();
            }
        }
    }
}
