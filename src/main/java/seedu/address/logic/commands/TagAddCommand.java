package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.tag.Tag;

/**
 * Adds a new tag definition to the global tag catalogue.
 */
public class TagAddCommand extends Command {

    public static final String COMMAND_WORD = "tagadd";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a tag to the tag list. "
            + "Parameters: tn/TAG_NAME "
            + "[tc/CATEGORY] "
            + "[tcol/COLOUR] "
            + "[td/DESCRIPTION]\n"
            + "Example: " + COMMAND_WORD + " tn/backend tc/Engineering tcol/#1F75FE td/Backend specialist";

    public static final String MESSAGE_SUCCESS = "New tag added: %1$s";
    public static final String MESSAGE_DUPLICATE_TAG = "This tag already exists in the tag list.";

    private final Tag tag;

    public TagAddCommand(Tag tag) {
        this.tag = requireNonNull(tag);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (model.hasTag(tag)) {
            throw new CommandException(MESSAGE_DUPLICATE_TAG);
        }

        model.addTag(tag);
        return new CommandResult(String.format(MESSAGE_SUCCESS, tag.tagName));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof TagAddCommand)) {
            return false;
        }

        TagAddCommand otherCommand = (TagAddCommand) other;
        return tag.equals(otherCommand.tag);
    }
}
