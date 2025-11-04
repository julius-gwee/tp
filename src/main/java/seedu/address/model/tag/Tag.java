package seedu.address.model.tag;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.util.Objects;

/**
 * Represents a Tag in the address book.
 * Guarantees: immutable; name is valid as declared in {@link #isValidTagName(String)}
 */
public class Tag {

    public static final String MESSAGE_CONSTRAINTS = "Tag names must contain only letters and digits, "
            + "without spaces or special characters. Tag names are case-insensitive.";
    public static final String MESSAGE_NAME_EMPTY = "Tag name cannot be empty. "
            + "Provide at least one letter or number.";
    public static final String MESSAGE_CATEGORY_EMPTY = "Tag category cannot be empty. "
            + "Remove the prefix if you do not want to set it.";
    public static final String MESSAGE_COLOUR_EMPTY = "Tag colour cannot be empty. "
            + "Remove the prefix or provide a hex colour code such as #A1B2C3.";
    public static final String MESSAGE_DESCRIPTION_EMPTY = "Tag description cannot be empty. "
            + "Remove the prefix if you do not want to set it.";
    public static final String MESSAGE_COLOUR_CONSTRAINTS = "Tag colours should be valid hex codes (e.g. #A1B2C3).";
    public static final String MESSAGE_CATEGORY_CONSTRAINTS =
            "Tag categories should start with an alphanumeric character and may contain spaces.";
    public static final String MESSAGE_DESCRIPTION_CONSTRAINTS =
            "Tag descriptions should be 200 characters or fewer.";

    public static final String VALIDATION_REGEX = "\\p{Alnum}+";
    public static final String COLOUR_VALIDATION_REGEX = "^#(?:[0-9a-fA-F]{6})$";
    public static final String CATEGORY_VALIDATION_REGEX = "[\\p{Alnum}](?:[ \\p{Alnum}])*";
    public static final int DESCRIPTION_MAX_LENGTH = 200;

    public static final String DEFAULT_CATEGORY = "General";
    public static final String DEFAULT_COLOUR = "#7A7A7A";
    public static final String DEFAULT_DESCRIPTION = "";

    public final String tagName;
    public final String category;
    public final String colour;
    public final String description;

    /**
     * Constructs a {@code Tag}.
     *
     * @param tagName A valid tag name.
     */
    public Tag(String tagName) {
        this(tagName, DEFAULT_CATEGORY, DEFAULT_COLOUR, DEFAULT_DESCRIPTION);
    }

    /**
     * Constructs a {@code Tag} with full information.
     */
    public Tag(String tagName, String category, String colour, String description) {
        requireNonNull(tagName);
        requireNonNull(category);
        requireNonNull(colour);
        requireNonNull(description);
        checkArgument(isValidTagName(tagName), MESSAGE_CONSTRAINTS);
        checkArgument(isValidCategory(category), MESSAGE_CATEGORY_CONSTRAINTS);
        checkArgument(isValidColour(colour), MESSAGE_COLOUR_CONSTRAINTS);
        checkArgument(isValidDescription(description), MESSAGE_DESCRIPTION_CONSTRAINTS);
        this.tagName = tagName;
        this.category = category.trim();
        this.colour = normaliseColour(colour);
        this.description = description.trim();
    }

    /**
     * Returns true if a given string is a valid tag name.
     */
    public static boolean isValidTagName(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    /**
     * Returns true if a given string is a valid tag category.
     */
    public static boolean isValidCategory(String test) {
        requireNonNull(test);
        return test.matches(CATEGORY_VALIDATION_REGEX);
    }

    /**
     * Returns true if a given string is a valid tag colour.
     */
    public static boolean isValidColour(String test) {
        requireNonNull(test);
        return test.matches(COLOUR_VALIDATION_REGEX);
    }

    /**
     * Returns true if a given string is a valid tag description.
     */
    public static boolean isValidDescription(String test) {
        requireNonNull(test);
        return test.length() <= DESCRIPTION_MAX_LENGTH;
    }

    private static String normaliseColour(String colour) {
        return colour.toUpperCase();
    }

    /**
     * Returns true if both tags have the same identity fields (name only).
     */
    public boolean isSameTag(Tag otherTag) {
        if (otherTag == this) {
            return true;
        }

        return otherTag != null && tagName.equalsIgnoreCase(otherTag.tagName);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Tag)) {
            return false;
        }

        Tag otherTag = (Tag) other;
        return tagName.equalsIgnoreCase(otherTag.tagName)
                && category.equals(otherTag.category)
                && colour.equals(otherTag.colour)
                && description.equals(otherTag.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(tagName.toLowerCase(), category, colour, description);
    }

    /**
     * Format state as text for viewing.
     */
    public String toString() {
        return '[' + tagName + ']';
    }

}
