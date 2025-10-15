package seedu.address.testutil;

import seedu.address.model.Findr;
import seedu.address.model.person.Person;

/**
 * A utility class to help with building {@link Findr} objects.
 * Example usage: <br>
 *     {@code Findr findr = new FindrBuilder().withPerson("John", "Doe").build();}
 */
public class FindrBuilder {

    private Findr findr;

    public FindrBuilder() {
        findr = new Findr();
    }

    public FindrBuilder(Findr findr) {
        this.findr = findr;
    }

    /**
     * Adds a new {@code Person} to the {@code Findr} that we are building.
     */
    public FindrBuilder withPerson(Person person) {
        findr.addCandidate(person);
        return this;
    }

    public Findr build() {
        return findr;
    }
}