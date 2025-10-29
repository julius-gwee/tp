package seedu.address.model.person;

import static java.util.Objects.requireNonNull;

/**
 * Represents a Person's rating in the candidate list.
 * Guarantees: immutable; is valid as declared in {@link #isValidRating(String)}
 * Now modeled as an enum similar to {@link Stage}.
 */
public enum Rating {
    UNRATED("Unrated", 5),
    VERY_POOR("Very Poor", 4),
    POOR("Poor", 3),
    AVERAGE("Average", 2),
    GOOD("Good", 1),
    EXCELLENT("Excellent", 0);

    public static final String MESSAGE_CONSTRAINTS =
            "Rating should be one of: Unrated, Very Poor, Poor, Average, Good, Excellent"
            + " (case-insensitive)";

    private final String displayName;
    private final int integer;

    Rating(String displayName, int integer) {
        this.displayName = displayName;
        this.integer = integer;
    }

    /**
     * Returns the display name of the rating.
     */
    public String getDisplayName() {
        return displayName;
    }

    /**
     * Returns the integer of the rating.
     */
    public int getInteger() {
        return integer;
    }

    /**
     * Returns true if a given string is a valid rating name.
     * Accepts current display names and enum names (case-insensitive).
     */
    public static boolean isValidRating(String test) {
        requireNonNull(test);
        try {
            fromString(test);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    /**
     * Returns the Rating enum from a string (case-insensitive).
     * Accepts display names with spaces (e.g., "very poor") and enum names (e.g., VERY_POOR).
     */
    public static Rating fromString(String rating) {
        requireNonNull(rating);
        String trimmed = rating.trim();
        if (trimmed.isEmpty()) {
            throw new IllegalArgumentException(MESSAGE_CONSTRAINTS);
        }
        String upper = trimmed.toUpperCase();

        // Try enum name (allow spaces by converting to underscore)
        String normalized = upper.replace(' ', '_');
        try {
            return Rating.valueOf(normalized);
        } catch (IllegalArgumentException e) {
            // Try matching display names exactly (case-insensitive)
            for (Rating r : Rating.values()) {
                if (r.displayName.equalsIgnoreCase(trimmed)) {
                    return r;
                }
            }
            throw new IllegalArgumentException(MESSAGE_CONSTRAINTS);
        }
    }

    @Override
    public String toString() {
        return displayName;
    }
}
