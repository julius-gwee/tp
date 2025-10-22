package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Person's rating in the candidate list.
 * Guarantees: immutable; is valid as declared in {@link #isValidRating(String)}
 */
public class Rating {

    /**
     * Enum listing valid ratings.
     */
    public enum RatingType {
        UNRATED,
        ONE,
        TWO,
        THREE,
        FOUR,
        FIVE;
    }

    public static final String MESSAGE_CONSTRAINTS =
            "Ratings should be one of the following: UNRATED, ONE, TWO, THREE, FOUR, FIVE";
    public final RatingType value;

    /**
     * Constructs a {@code Rating}.
     *
     * @param rating A valid rating
     */
    public Rating(String rating) {
        requireNonNull(rating);
        checkArgument(isValidRating(rating), MESSAGE_CONSTRAINTS);
        this.value = convertToRating(rating);
    }

    /**
     * Constructs a {@code Rating} with a default value of UNRATED.
     *
     */
    public Rating() {
        this.value = RatingType.UNRATED;

    }

    /**
     * Returns true if a given string is a valid rating.
     */
    public static boolean isValidRating(String test) {
        requireNonNull(test);
        try {
            convertToRating(test);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    private static RatingType convertToRating(String rating) {
        return RatingType.valueOf(rating.trim().toUpperCase());
    }

    @Override
    public String toString() {
        return value.name();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Rating)) {
            return false;
        }

        Rating otherRating = (Rating) other;
        return value.equals(otherRating.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
