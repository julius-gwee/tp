package seedu.address.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.model.person.Address;
import seedu.address.model.person.DateAdded;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.tag.Tag;

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
                new Address("123 Street"), new DateAdded(new Date()), tags);
    }

    @Test
    public void constructor_initialState_emptyList() {
        assertTrue(findr.getCandidateList().isEmpty());
    }

    @Test
    public void addCandidate_success() {
        Person alice = createPerson("Alice");
        findr.addCandidate(alice);
        assertTrue(findr.getCandidateList().contains(alice));
    }

    @Test
    public void resetData_replacesData_success() {
        Findr newData = new Findr();
        newData.addCandidate(createPerson("Bob"));
        findr.resetData(newData);
        assertEquals(newData, findr);
    }

    @Test
    public void equalsAndHashCode_validCases() {
        Findr anotherFindr = new Findr();
        assertEquals(findr, anotherFindr);
        assertEquals(findr.hashCode(), anotherFindr.hashCode());

        anotherFindr.addCandidate(createPerson("Charlie"));
        assertNotEquals(findr, anotherFindr);
    }

    @Test
    public void toString_containsFindrAndPersonList() {
        findr.addCandidate(createPerson("David"));
        String str = findr.toString();
        assertTrue(str.contains("Findr"));
        assertTrue(str.contains("David"));
    }
}
