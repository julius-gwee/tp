package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.util.Locale;

/**
 * Represents a Person's name in the candidate list.
 * Guarantees: immutable; is valid as declared in {@link #isValidName(String)}
 */
public class Name {

    public static final String MESSAGE_CONSTRAINTS =
            "Names should only contain letters (including accented characters), numbers, spaces, apostrophes, '@',"
                    + " or hyphens, may include 's/o' or 'd/o', and it should not be blank";

    /*
     * The first character of the address must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String VALIDATION_REGEX =
            "(?i)[\\p{L}\\p{M}\\p{N}][\\p{L}\\p{M}\\p{N} @'\\-]*(?:\\b[sd]/o\\b[\\p{L}\\p{M}\\p{N} @'\\-]*)*";


    public final String fullName;
    private final String canonicalName;

    /**
     * Constructs a {@code Name}.
     *
     * @param name A valid name.
     */
    public Name(String name) {
        requireNonNull(name);
        checkArgument(isValidName(name), MESSAGE_CONSTRAINTS);
        String collapsedWhitespace = collapseWhitespace(name);
        fullName = formatDisplayName(collapsedWhitespace);
        canonicalName = canonicalize(collapsedWhitespace);
    }

    /**
     * Returns true if a given string is a valid name.
     */
    public static boolean isValidName(String test) {
        return test.matches(VALIDATION_REGEX);
    }


    @Override
    public String toString() {
        return fullName;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Name)) {
            return false;
        }

        Name otherName = (Name) other;
        return canonicalName.equals(otherName.canonicalName);
    }

    @Override
    public int hashCode() {
        return canonicalName.hashCode();
    }

    private static String canonicalize(String value) {
        return collapseWhitespace(value).toLowerCase(Locale.ROOT);
    }

    private static String collapseWhitespace(String value) {
        return value.trim().replaceAll("\\s+", " ");
    }

    private static String formatDisplayName(String value) {
        String lowerCased = value.toLowerCase(Locale.ROOT);
        StringBuilder builder = new StringBuilder(lowerCased.length());
        boolean capitalizeNext = true;

        for (int i = 0; i < lowerCased.length(); i++) {
            char current = lowerCased.charAt(i);
            if (Character.isWhitespace(current)) {
                builder.append(' ');
                capitalizeNext = true;
                continue;
            }

            if (current == '\'' || current == '-' || current == '/') {
                builder.append(current);
                capitalizeNext = true;
                continue;
            }

            if (capitalizeNext) {
                builder.append(Character.toTitleCase(current));
                capitalizeNext = false;
            } else {
                builder.append(current);
            }
        }

        return builder.toString();
    }
}
