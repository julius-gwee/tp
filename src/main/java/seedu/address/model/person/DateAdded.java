package seedu.address.model.person;

import static java.util.Objects.requireNonNull;

import java.util.Date;

/**
 * Represents the date a Candidate is added to Findr.
 * Guarantees: immutable;
 */
public record DateAdded(Date date) {


    public static final String MESSAGE_CONSTRAINTS = "Date added should be a valid date";

    /**
     * Constructs a {@code Date}.
     *
     * @param date A valid date.
     */
    public DateAdded {
        requireNonNull(date);
    }

    @Override
    public String toString() {
        return date.toString();
    }

    public Date toDate() {
        return this.date;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof DateAdded)) {
            return false;
        }

        DateAdded otherDateAdded = (DateAdded) other;
        return date.equals(otherDateAdded.date);
    }

}
