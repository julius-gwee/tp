package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.Comparator;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;

/**
 * Sorts all candidates in the address book alphabetically.
 */
public class SortCommand extends Command {

    public static final String COMMAND_WORD = "sort";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Sorts the candidate list by the criteria stated. "
            + "If no criteria stated, default is sorted alphabetically.\n"
            + "Parameters: [SORT CRITERIA] (optional, only the examples below are valid sort criteria)\n"
            + "Example: " + COMMAND_WORD + "\n"
            + "Example: " + COMMAND_WORD + " alphabetical" + "\n"
            + "Example: " + COMMAND_WORD + " date" + "\n"
            + "Example: " + COMMAND_WORD + " rating";

    public static final String MESSAGE_SUCCESS = "Sorted all candidates.";

    public static final Comparator<Person> SORT_BY_ALPHABET = Comparator.comparing(
            o -> o.getName().toString());
    public static final Comparator<Person> SORT_BY_DATEADDED = Comparator.comparing(
            o -> o.getDateAdded().toDate());
    public static final Comparator<Person> SORT_BY_RATING = Comparator.comparing(
            o -> o.getRating().getInteger());

    private final Comparator<Person> comparator;

    public SortCommand(Comparator<Person> comparator) {
        this.comparator = comparator;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        model.updateSortedCandidateList(comparator);
        return new CommandResult(MESSAGE_SUCCESS);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof SortCommand)) {
            return false;
        }

        SortCommand otherSortCommand = (SortCommand) other;
        return comparator.equals(otherSortCommand.comparator);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("comparator", comparator)
                .toString();
    }
}
