package seedu.address.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.tag.Tag;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

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
    public void resetData_replacesData_success() {
        Findr newData = new Findr();
        newData.addPerson(createPerson("Bob"));
        findr.resetData(newData);
        assertEquals(newData, findr);
    }

    @Test
    public void equalsAndHashCode_validCases() {
        Findr anotherFindr = new Findr();
        assertEquals(findr, anotherFindr);
        assertEquals(findr.hashCode(), anotherFindr.hashCode());

        anotherFindr.addPerson(createPerson("Charlie"));
        assertNotEquals(findr, anotherFindr);
    }

    @Test
    public void toString_containsFindrAndPersonList() {
        findr.addPerson(createPerson("David"));
        String str = findr.toString();
        assertTrue(str.contains("Findr"));
        assertTrue(str.contains("David"));
    }
}
