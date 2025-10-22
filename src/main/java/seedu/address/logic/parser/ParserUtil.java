package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;

import java.util.Collection;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.StringUtil;
import seedu.address.logic.commands.SortCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Stage;
import seedu.address.model.tag.Tag;

/**
 * Contains utility methods used for parsing strings in the various *Parser classes.
 */
public class ParserUtil {

    public static final String MESSAGE_INVALID_INDEX = "Index is not a non-zero unsigned integer.";
    public static final String MESSAGE_INVALID_SORT_CRITERIA =
            "Invalid sort criteria, check user guide for list of valid sort criteria.";

    /**
     * Parses {@code oneBasedIndex} into an {@code Index} and returns it. Leading and trailing whitespaces will be
     * trimmed.
     * @throws ParseException if the specified index is invalid (not non-zero unsigned integer).
     */
    public static Index parseIndex(String oneBasedIndex) throws ParseException {
        String trimmedIndex = oneBasedIndex.trim();
        if (!StringUtil.isNonZeroUnsignedInteger(trimmedIndex)) {
            throw new ParseException(MESSAGE_INVALID_INDEX);
        }
        return Index.fromOneBased(Integer.parseInt(trimmedIndex));
    }

    /**
     * Parses a {@code String name} into a {@code Name}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code name} is invalid.
     */
    public static Name parseName(String name) throws ParseException {
        requireNonNull(name);
        String trimmedName = name.trim();
        if (!Name.isValidName(trimmedName)) {
            throw new ParseException(Name.MESSAGE_CONSTRAINTS);
        }
        return new Name(trimmedName);
    }

    /**
     * Parses a {@code String phone} into a {@code Phone}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code phone} is invalid.
     */
    public static Phone parsePhone(String phone) throws ParseException {
        requireNonNull(phone);
        String trimmedPhone = phone.trim();
        if (!Phone.isValidPhone(trimmedPhone)) {
            throw new ParseException(Phone.MESSAGE_CONSTRAINTS);
        }
        return new Phone(trimmedPhone);
    }

    /**
     * Parses a {@code String address} into an {@code Address}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code address} is invalid.
     */
    public static Address parseAddress(String address) throws ParseException {
        requireNonNull(address);
        String trimmedAddress = address.trim();
        if (!Address.isValidAddress(trimmedAddress)) {
            throw new ParseException(Address.MESSAGE_CONSTRAINTS);
        }
        return new Address(trimmedAddress);
    }

    /**
     * Parses a {@code String email} into an {@code Email}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code email} is invalid.
     */
    public static Email parseEmail(String email) throws ParseException {
        requireNonNull(email);
        String trimmedEmail = email.trim();
        if (!Email.isValidEmail(trimmedEmail)) {
            throw new ParseException(Email.MESSAGE_CONSTRAINTS);
        }
        return new Email(trimmedEmail);
    }

    /**
     * Parses a {@code String tag} into a {@code Tag}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code tag} is invalid.
     */
    public static Tag parseTag(String tag) throws ParseException {
        requireNonNull(tag);
        String trimmedTag = tag.trim();
        if (!Tag.isValidTagName(trimmedTag)) {
            throw new ParseException(Tag.MESSAGE_CONSTRAINTS);
        }
        return new Tag(trimmedTag);
    }

    /**
     * Parses a {@code String tagName} into a trimmed tag name.
     *
     * @throws ParseException if the given {@code tagName} is invalid.
     */
    public static String parseTagName(String tagName) throws ParseException {
        requireNonNull(tagName);
        String trimmedTagName = tagName.trim();
        if (!Tag.isValidTagName(trimmedTagName)) {
            throw new ParseException(Tag.MESSAGE_CONSTRAINTS);
        }
        return trimmedTagName;
    }

    /**
     * Parses a {@code String category} into a trimmed tag category.
     *
     * @throws ParseException if the given {@code category} is invalid.
     */
    public static String parseTagCategory(String category) throws ParseException {
        requireNonNull(category);
        String trimmedCategory = category.trim();
        if (!Tag.isValidCategory(trimmedCategory)) {
            throw new ParseException(Tag.MESSAGE_CATEGORY_CONSTRAINTS);
        }
        return trimmedCategory;
    }

    /**
     * Parses a {@code String colour} into a trimmed tag colour.
     *
     * @throws ParseException if the given {@code colour} is invalid.
     */
    public static String parseTagColour(String colour) throws ParseException {
        requireNonNull(colour);
        String trimmedColour = colour.trim();
        if (!Tag.isValidColour(trimmedColour)) {
            throw new ParseException(Tag.MESSAGE_COLOUR_CONSTRAINTS);
        }
        return trimmedColour;
    }

    /**
     * Parses a {@code String description} into a trimmed tag description.
     *
     * @throws ParseException if the given {@code description} is invalid.
     */
    public static String parseTagDescription(String description) throws ParseException {
        requireNonNull(description);
        String trimmedDescription = description.trim();
        if (!Tag.isValidDescription(trimmedDescription)) {
            throw new ParseException(Tag.MESSAGE_DESCRIPTION_CONSTRAINTS);
        }
        return trimmedDescription;
    }

    /**
     * Parses {@code Collection<String> tags} into a {@code Set<Tag>}.
     */
    public static Set<Tag> parseTags(Collection<String> tags) throws ParseException {
        requireNonNull(tags);
        final Set<Tag> tagSet = new HashSet<>();
        for (String tagName : tags) {
            tagSet.add(parseTag(tagName));
        }
        return tagSet;
    }

    /**
     * Parses {@code String sortCriteria} into a {@code Comparator<Person>} and returns it.
     *
     * @throws ParseException if the specified sortCriteria is invalid (not in the list specified in MESSAGE_USAGE).
     */
    public static Comparator<Person> parseSortCriteria(String sortCriteria) throws ParseException {
        requireNonNull(sortCriteria);

        switch (sortCriteria) {

        case "alphabetical":
            return SortCommand.SORT_BY_ALPHABET;

        default:
            throw new ParseException(MESSAGE_INVALID_SORT_CRITERIA);
        }
    }

    /**
     * Parses a {@code String stage} into a {@code Stage}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code stage} is invalid.
     */
    public static Stage parseStage(String stage) throws ParseException {
        requireNonNull(stage);
        String trimmedStage = stage.trim();
        if (!Stage.isValidStage(trimmedStage)) {
            throw new ParseException(Stage.MESSAGE_CONSTRAINTS);
        }
        return Stage.fromString(trimmedStage);
    }
}
