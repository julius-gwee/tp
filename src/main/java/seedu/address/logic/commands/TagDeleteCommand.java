package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.exceptions.TagNotFoundException;

/**
 * Deletes a tag definition from the global catalogue.
 */
public class TagDeleteCommand extends Command {

    public static final String COMMAND_WORD = "tagdelete";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Deletes the tag identified by its name.\n"
            + "Parameters: tn/TAG_NAME\n"
            + "Example: " + COMMAND_WORD + " tn/backend";

    public static final String MESSAGE_SUCCESS = "Deleted tag: %1$s";
    public static final String MESSAGE_TAG_NOT_FOUND = TagCommandUtil.MESSAGE_TAG_NOT_FOUND;

    private final String targetTagName;

    public TagDeleteCommand(String targetTagName) {
        this.targetTagName = requireNonNull(targetTagName);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        Tag tagToDelete;
        try {
            tagToDelete = model.getTag(new Tag(targetTagName));
        } catch (TagNotFoundException e) {
            throw new CommandException(MESSAGE_TAG_NOT_FOUND, e);
        }

        model.deleteTag(tagToDelete);
        return new CommandResult(String.format(MESSAGE_SUCCESS, tagToDelete.tagName));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof TagDeleteCommand)) {
            return false;
        }

        TagDeleteCommand otherCommand = (TagDeleteCommand) other;
        return targetTagName.equals(otherCommand.targetTagName);
    }
}
