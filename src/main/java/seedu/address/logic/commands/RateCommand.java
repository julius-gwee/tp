package seedu.address.logic.commands;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_RATE;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Adds a rating to a candidate in the candidate list.
 */
public class RateCommand extends Command {
    public static final String COMMAND_WORD = "rate";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the rating of the candidate identified "
            + "by the index number used in the displayed candidate list. "
            + "Existing remark will be overwritten by the input.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + PREFIX_RATE + "[RATING]\n"
            + "Example: " + COMMAND_WORD + "1"
            + PREFIX_RATE + "FIVE";

    public static final String MESSAGE_RATE_SUCCESS = "Rating for %1$s: %2$s";
    public static final String MESSAGE_NOT_IMPLEMENTED_YET = "Rating is invalid";

    public static final String MESSAGE_ARGUMENTS = "Index: %1$d, Rating: %2$s";

    private final Index index;
    private final String rating;

    /**
     * @param index of the candidate in the filtered candidate list to edit the rating
     * @param rating of the candidate to be updated to
     */
    public RateCommand(Index index, String rating) {
        requireAllNonNull(index, rating);
        this.index = index;
        this.rating = rating;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        throw new CommandException(
                String.format(MESSAGE_ARGUMENTS, index.getOneBased(), rating));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof RateCommand)) {
            return false;
        }

        RateCommand e = (RateCommand) other;
        return index.equals(e.index)
                && rating.equals(e.rating);
    }
}
