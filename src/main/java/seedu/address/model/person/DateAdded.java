package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.util.Date;

/**
 * Represents the date a Candidate is added to Findr.
 * Guarantees: immutable; is valid as declared in {@link #isValidDate(Date)}
 */
public class DateAdded {


    public static final String MESSAGE_CONSTRAINTS = "Date added should be a valid date";
    public final Date date;

    /**
     * Constructs a {@code Date}.
     *
     * @param date A valid date.
     */
    public DateAdded(Date date) {
        requireNonNull(date);
        checkArgument(isValidDate(date), MESSAGE_CONSTRAINTS);
        this.date = date;
    }

    /**
     * Returns true if a given date is a valid date.
     */
    public static boolean isValidDate(Date test) {
        return test.getClass() == Date.class;
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

    @Override
    public int hashCode() {
        return date.hashCode();
    }

}
