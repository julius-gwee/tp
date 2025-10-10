package seedu.address.testutil;

import seedu.address.model.Findr;
import seedu.address.model.person.Person;

/**
 * A utility class to help with building Addressbook objects.
 * Example usage: <br>
 *     {@code AddressBook ab = new AddressBookBuilder().withPerson("John", "Doe").build();}
 */
public class AddressBookBuilder {

    private Findr findr;

    public AddressBookBuilder() {
        findr = new Findr();
    }

    public AddressBookBuilder(Findr findr) {
        this.findr = findr;
    }

    /**
     * Adds a new {@code Person} to the {@code AddressBook} that we are building.
     */
    public AddressBookBuilder withPerson(Person person) {
        findr.addPerson(person);
        return this;
    }

    public Findr build() {
        return findr;
    }
}
