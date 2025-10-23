package seedu.address.logic.commands;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_RATE;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.List;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Address;
import seedu.address.model.person.DateAdded;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Rating;
import seedu.address.model.person.Stage;
import seedu.address.model.tag.Tag;

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
    public static final String MESSAGE_ARGUMENTS = "Index: %1$d, Rating: %2$s";

    private final Index index;
    private final Rating rating;

    /**
     * @param index of the candidate in the filtered candidate list to edit the rating
     * @param rating of the candidate to be updated to
     */
    public RateCommand(Index index, Rating rating) {
        requireAllNonNull(index, rating);
        this.index = index;
        this.rating = rating;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        List<Person> lastShownList = model.getFilteredCandidateList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_CANDIDATE_DISPLAYED_INDEX);
        }

        Person candidateToEdit = lastShownList.get(index.getZeroBased());
        Name currentName = candidateToEdit.getName();
        Phone currentPhone = candidateToEdit.getPhone();
        Email currentEmail = candidateToEdit.getEmail();
        Address currentAddress = candidateToEdit.getAddress();
        Set<Tag> currentTags = candidateToEdit.getTags();
        DateAdded currentDateAdded = candidateToEdit.getDateAdded();
        Stage currentStage = candidateToEdit.getStage();

        Person editedPerson = new Person(currentName, currentPhone, currentEmail, currentAddress, currentTags,
                currentDateAdded, rating, currentStage);

        model.setPerson(candidateToEdit, editedPerson);
        model.updateFilteredCandidateList(PREDICATE_SHOW_ALL_PERSONS);

        return new CommandResult(String.format(MESSAGE_RATE_SUCCESS, currentName, rating));
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
