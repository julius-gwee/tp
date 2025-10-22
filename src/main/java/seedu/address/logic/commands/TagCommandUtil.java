package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.HashSet;
import java.util.Set;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.exceptions.TagNotFoundException;

/**
 * Helper utilities shared by commands that operate on tags.
 */
public final class TagCommandUtil {

    public static final String MESSAGE_TAG_NOT_FOUND = "Would you like to create this tag first?";

    private TagCommandUtil() {
        // Utility class
    }

    /**
     * Resolves the provided {@code tags} against the model's tag catalogue, ensuring that all tags exist and
     * returning a set containing the actual catalogue entries.
     *
     * @throws CommandException if any tag cannot be found in the catalogue.
     */
    public static Set<Tag> resolveTags(Model model, Set<Tag> tags) throws CommandException {
        requireNonNull(model);
        requireNonNull(tags);

        Set<Tag> resolvedTags = new HashSet<>();
        for (Tag tag : tags) {
            try {
                resolvedTags.add(model.getTag(tag));
            } catch (TagNotFoundException e) {
                throw new CommandException(MESSAGE_TAG_NOT_FOUND, e);
            }
        }
        return resolvedTags;
    }

    /**
     * Builds a new {@link Person} using {@code source}'s core fields and the supplied {@code tags} set.
     */
    public static Person rebuildPersonWithTags(Person source, Set<Tag> tags) {
        requireNonNull(source);
        requireNonNull(tags);
        return new Person(source.getName(), source.getPhone(), source.getEmail(), source.getAddress(), tags);
    }
}
