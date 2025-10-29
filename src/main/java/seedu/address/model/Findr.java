package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javafx.collections.ObservableList;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.person.Person;
import seedu.address.model.person.UniquePersonList;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.UniqueTagList;

/**
 * Wraps all data at the address-book level
 * Duplicates are not allowed (by .isSamePerson comparison)
 */
public class Findr implements ReadOnlyFindr {

    private final UniquePersonList persons;
    private final UniqueTagList tags;

    /*
     * The 'unusual' code block below is a non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        persons = new UniquePersonList();
        tags = new UniqueTagList();
    }

    public Findr() {}

    /**
     * Creates an AddressBook using the Persons in the {@code toBeCopied}
     */
    public Findr(ReadOnlyFindr toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// list overwrite operations

    /**
     * Replaces the contents of the person list with {@code persons}.
     * {@code persons} must not contain duplicate persons.
     */
    public void setPersons(List<Person> persons) {
        List<Person> resolvedPersons = persons.stream()
                .map(this::resolveTagsForPerson)
                .collect(Collectors.toList());
        this.persons.setPersons(resolvedPersons);
    }

    /**
     * Resets the existing data of this {@code AddressBook} with {@code newData}.
     */
    public void resetData(ReadOnlyFindr newData) {
        requireNonNull(newData);

        setTags(newData.getTagList());
        setPersons(newData.getCandidateList());
    }

    //// person-level operations

    /**
     * Returns true if a person with the same identity as {@code person} exists in the address book.
     */
    public boolean hasCandidate(Person person) {
        requireNonNull(person);
        return persons.contains(person);
    }

    /**
     * Adds a person to the address book.
     * The person must not already exist in the address book.
     */
    public void addCandidate(Person p) {
        persons.add(resolveTagsForPerson(p));
    }

    /**
     * Replaces the given person {@code target} in the list with {@code editedPerson}.
     * {@code target} must exist in the address book.
     * The person identity of {@code editedPerson} must not be the same as another existing person in the address book.
     */
    public void setPerson(Person target, Person editedPerson) {
        requireNonNull(editedPerson);

        persons.setPerson(target, editedPerson);
    }

    /**
     * Removes {@code key} from this {@code AddressBook}.
     * {@code key} must exist in the address book.
     */
    public void removePerson(Person key) {
        persons.remove(key);
    }

    //// tag-level operations

    /**
     * Replaces the contents of the tag list with {@code tags}.
     * {@code tags} must not contain duplicate tags.
     */
    public void setTags(List<Tag> tags) {
        this.tags.setTags(tags);
    }

    /**
     * Returns the tag from the catalogue that matches {@code tag} by identity.
     */
    public Tag getTag(Tag tag) {
        requireNonNull(tag);
        return tags.get(tag);
    }

    /**
     * Returns true if a tag with the same identity as {@code tag} exists in the address book.
     */
    public boolean hasTag(Tag tag) {
        requireNonNull(tag);
        return tags.contains(tag);
    }

    /**
     * Adds a tag to the address book.
     * The tag must not already exist in the address book.
     */
    public void addTag(Tag tag) {
        tags.add(tag);
    }

    /**
     * Replaces the given tag {@code target} in the list with {@code editedTag}.
     * {@code target} must exist in the address book.
     * The tag identity of {@code editedTag} must not be the same as another existing tag in the address book.
     */
    public void setTag(Tag target, Tag editedTag) {
        requireNonNull(editedTag);
        tags.setTag(target, editedTag);
        replaceTagAcrossPersons(target, editedTag);
    }

    /**
     * Removes {@code tag} from this {@code AddressBook}.
     * {@code tag} must exist in the address book.
     */
    public void removeTag(Tag tag) {
        tags.remove(tag);
        replaceTagAcrossPersons(tag, null);
    }

    private void replaceTagAcrossPersons(Tag target, Tag replacement) {
        List<Person> updatedPersons = new ArrayList<>();
        boolean anyChanges = false;
        for (Person person : persons.asUnmodifiableObservableList()) {
            Set<Tag> updatedTags = new HashSet<>();
            boolean tagModified = false;
            for (Tag existingTag : person.getTags()) {
                if (existingTag.isSameTag(target)) {
                    tagModified = true;
                    if (replacement != null) {
                        updatedTags.add(replacement);
                    }
                } else {
                    updatedTags.add(existingTag);
                }
            }

            if (tagModified) {
                anyChanges = true;
                updatedPersons.add(new Person(person.getName(), person.getPhone(), person.getEmail(),
                        person.getAddress(), updatedTags, person.getDateAdded(), person.getRating(),
                        person.getStage()));
            } else {
                updatedPersons.add(person);
            }
        }

        if (anyChanges) {
            persons.setPersons(updatedPersons);
        }
    }

    private Person resolveTagsForPerson(Person person) {
        Set<Tag> resolvedTags = new HashSet<>();
        for (Tag tag : person.getTags()) {
            if (!tags.contains(tag)) {
                tags.add(tag);
            }
            resolvedTags.add(tags.get(tag));
        }
        return new Person(person.getName(), person.getPhone(), person.getEmail(), person.getAddress(), resolvedTags,
                person.getDateAdded(), person.getRating(),
                person.getStage());
    }

    //// util methods

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("persons", persons)
                .add("tags", tags)
                .toString();
    }

    @Override
    public ObservableList<Person> getCandidateList() {
        return persons.asUnmodifiableObservableList();
    }

    @Override
    public ObservableList<Tag> getTagList() {
        return tags.asUnmodifiableObservableList();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Findr)) {
            return false;
        }

        Findr otherFindr = (Findr) other;
        return persons.equals(otherFindr.persons);
    }

    @Override
    public int hashCode() {
        return 31 * persons.hashCode() + tags.hashCode();
    }
}
