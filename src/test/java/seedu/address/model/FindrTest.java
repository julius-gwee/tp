package seedu.address.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.tag.Tag;

/**
 * Ensures 100% test coverage for {@link Findr}.
 */
public class FindrTest {

    private Findr findr;

    @BeforeEach
    public void setUp() {
        findr = new Findr();
    }

    private Person createPerson(String name) {
        Set<Tag> tags = new HashSet<>();
        tags.add(new Tag("friends"));
        return new Person(new Name(name), new Phone("12345678"),
                new Email(name.toLowerCase() + "@example.com"),
                new Address("123 Street"), tags);
    }

    @Test
    public void constructor_initialState_emptyList() {
        assertTrue(findr.getCandidateList().isEmpty());
    }

    @Test
    public void addPerson_success() {
        Person alice = createPerson("Alice");
        findr.addPerson(alice);
        assertTrue(findr.getCandidateList().contains(alice));
    }

    @Test
    public void hasPerson_checksExistence() {
        Person bob = createPerson("Bob");
        assertTrue(!findr.hasPerson(bob));
        findr.addPerson(bob);
        assertTrue(findr.hasPerson(bob));
    }

    @Test
    public void setPersons_replacesList_success() {
        Person charlie = createPerson("Charlie");
        List<Person> newList = new ArrayList<>();
        newList.add(charlie);
        findr.setPersons(newList);
        assertEquals(1, findr.getCandidateList().size());
        assertTrue(findr.getCandidateList().contains(charlie));
    }

    @Test
    public void removePerson_success() {
        Person eric = createPerson("Eric");
        findr.addPerson(eric);
        findr.removePerson(eric);
        assertTrue(!findr.getCandidateList().contains(eric));
    }

    @Test
    public void resetData_replacesData_success() {
        Findr newData = new Findr();
        newData.addPerson(createPerson("Bob"));
        findr.resetData(newData);
        assertEquals(newData, findr);
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> findr.resetData(null));
    }

    @Test
    public void equals_variousCases() {
        Findr sameFindr = new Findr(findr);
        assertEquals(findr, findr); // self comparison
        assertEquals(findr, sameFindr);

        // different type
        assertNotEquals(findr, "String");

        // null
        assertNotEquals(findr, null);

        // different content
        Findr differentFindr = new Findr();
        differentFindr.addPerson(createPerson("Henry"));
        assertNotEquals(findr, differentFindr);
    }

    @Test
    public void hashCode_consistency() {
        Findr another = new Findr(findr);
        assertEquals(findr.hashCode(), another.hashCode());
    }

    @Test
    public void toString_containsFindrAndPersonList() {
        findr.addPerson(createPerson("David"));
        String str = findr.toString();
        assertTrue(str.contains("Findr"));
        assertTrue(str.contains("David"));
    }
}
