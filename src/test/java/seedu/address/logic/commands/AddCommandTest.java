package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPersons.ALICE;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Findr;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyFindr;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.person.Person;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.exceptions.TagNotFoundException;
import seedu.address.testutil.PersonBuilder;

public class AddCommandTest {

    @Test
    public void constructor_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddCommand(null));
    }

    @Test
    public void execute_personAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingPersonAdded modelStub = new ModelStubAcceptingPersonAdded();
        Person validCandidate = new PersonBuilder().build();

        CommandResult commandResult = new AddCommand(validCandidate).execute(modelStub);

        assertEquals(String.format(AddCommand.MESSAGE_SUCCESS, Messages.format(validCandidate)),
                commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(validCandidate), modelStub.personsAdded);
    }

    @Test
    public void execute_personWithExistingTag_addSuccessful() throws Exception {
        ModelStubAcceptingPersonAdded modelStub = new ModelStubAcceptingPersonAdded();
        Person taggedCandidate = new PersonBuilder().withTags("friends").build();
        modelStub.addAvailableTags(taggedCandidate.getTags());

        CommandResult commandResult = new AddCommand(taggedCandidate).execute(modelStub);

        assertEquals(String.format(AddCommand.MESSAGE_SUCCESS, Messages.format(taggedCandidate)),
                commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(taggedCandidate), modelStub.personsAdded);
    }

    @Test
    public void execute_missingTag_throwsCommandException() {
        ModelStubAcceptingPersonAdded modelStub = new ModelStubAcceptingPersonAdded();
        Person candidateWithNewTag = new PersonBuilder().withTags("newtag").build();
        AddCommand addCommand = new AddCommand(candidateWithNewTag);

        assertThrows(CommandException.class, TagCommandUtil.MESSAGE_TAG_NOT_FOUND, () -> addCommand.execute(modelStub));
    }

    @Test
    public void execute_duplicatePerson_throwsCommandException() {
        Person validCandidate = new PersonBuilder().build();
        AddCommand addCommand = new AddCommand(validCandidate);
        ModelStub modelStub = new ModelStubWithPerson(validCandidate);

        assertThrows(CommandException.class,
                AddCommand.MESSAGE_DUPLICATE_CANDIDATE, () -> addCommand.execute(modelStub));
    }

    @Test
    public void equals() {
        Person alice = new PersonBuilder().withName("Alice").build();
        Person bob = new PersonBuilder().withName("Bob").build();
        AddCommand addAliceCommand = new AddCommand(alice);
        AddCommand addBobCommand = new AddCommand(bob);

        // same object -> returns true
        assertTrue(addAliceCommand.equals(addAliceCommand));

        // same values -> returns true
        AddCommand addAliceCommandCopy = new AddCommand(alice);
        assertTrue(addAliceCommand.equals(addAliceCommandCopy));

        // different types -> returns false
        assertFalse(addAliceCommand.equals(1));

        // null -> returns false
        assertFalse(addAliceCommand.equals(null));

        // different person -> returns false
        assertFalse(addAliceCommand.equals(addBobCommand));
    }

    @Test
    public void toStringMethod() {
        AddCommand addCommand = new AddCommand(ALICE);
        String expected = AddCommand.class.getCanonicalName() + "{toAdd=" + ALICE + "}";
        assertEquals(expected, addCommand.toString());
    }

    /**
     * A default model stub that have all of the methods failing.
     */
    private class ModelStub implements Model {
        private final Set<Tag> availableTags = new HashSet<>();

        protected void addAvailableTags(Set<Tag> tags) {
            availableTags.addAll(tags);
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
        public void addCandidate(Person person) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setAddressBook(ReadOnlyFindr newData) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyFindr getCandidateList() {
            throw new AssertionError("This method should not be called.");
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
        public void setPerson(Person target, Person editedPerson) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Person> getFilteredCandidateList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredCandidateList(Predicate<Person> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Person> getSortedCandidateList() {
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
            return availableTags.stream().anyMatch(tag::isSameTag);
        }

        @Override
        public Tag getTag(Tag tag) {
            return availableTags.stream()
                    .filter(tag::isSameTag)
                    .findFirst()
                    .orElseThrow(TagNotFoundException::new);
        }

        @Override
        public void addTag(Tag tag) {
            availableTags.add(tag);
        }

        @Override
        public void setTag(Tag target, Tag editedTag) {
            availableTags.removeIf(target::isSameTag);
            availableTags.add(editedTag);
        }

        @Override
        public void deleteTag(Tag tag) {
            availableTags.removeIf(tag::isSameTag);
        }
    }

    /**
     * A Model stub that contains a single person.
     */
    private class ModelStubWithPerson extends ModelStub {
        private final Person person;

        ModelStubWithPerson(Person person) {
            requireNonNull(person);
            this.person = person;
            addAvailableTags(person.getTags());
        }

        @Override
        public boolean hasCandidate(Person person) {
            requireNonNull(person);
            return this.person.isSamePerson(person);
        }
    }

    /**
     * A Model stub that always accept the person being added.
     */
    private class ModelStubAcceptingPersonAdded extends ModelStub {
        final ArrayList<Person> personsAdded = new ArrayList<>();

        @Override
        public boolean hasCandidate(Person person) {
            requireNonNull(person);
            return personsAdded.stream().anyMatch(person::isSamePerson);
        }

        @Override
        public void addCandidate(Person person) {
            requireNonNull(person);
            personsAdded.add(person);
        }

        @Override
        public ReadOnlyFindr getCandidateList() {
            return new Findr();
        }
    }

}
