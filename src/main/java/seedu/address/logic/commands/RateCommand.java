package seedu.address.logic.commands;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FROM;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FROM_SHORT;
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

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the rating of the candidate. "
            + "You can scope the index to a specific stage column using " + PREFIX_FROM + " or "
            + PREFIX_FROM_SHORT + ".\n"
            + "Parameters: INDEX (positive integer) " + PREFIX_FROM + "STAGE | " + PREFIX_FROM_SHORT + "STAGE "
            + PREFIX_RATE + "RATING\n"
            + "Stages: Candidates, Contacted, Interviewed, Hired (case-insensitive)\n"
            + "Ratings: Unrated, Very Poor, Poor, Average, Good, Excellent (case-insensitive)\n"
            + "Example: " + COMMAND_WORD + " 1 " + PREFIX_FROM_SHORT + "Candidates " + PREFIX_RATE + "Excellent";

    public static final String MESSAGE_RATE_SUCCESS = "Rating for %1$s: %2$s";
    public static final String MESSAGE_INVALID_INDEX_FOR_STAGE = "Invalid index for stage %s";

    private final Index index;
    private final Rating rating;
    private final Stage fromStage; // optional; when provided, index is relative to this stage column

    /**
     * @param index of the candidate in the filtered candidate list to edit the rating
     * @param rating of the candidate to be updated to
     */
    public RateCommand(Index index, Rating rating) {
        requireAllNonNull(index, rating);
        this.index = index;
        this.rating = rating;
        this.fromStage = null;
    }

    /**
     * Overloaded constructor accepting an optional from-stage to scope the index to a stage column.
     */
    public RateCommand(Index index, Rating rating, Stage fromStage) {
        requireAllNonNull(index, rating);
        this.index = index;
        this.rating = rating;
        this.fromStage = fromStage;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        List<Person> targetList;
        if (fromStage == null) {
            // default behaviour: use current filtered list
            targetList = model.getObservableCandidateList();
        } else {
            // mirror MoveCommand behaviour: index is relative to the specified stage column
            targetList = model.getObservableCandidateList().stream()
                    .filter(person -> person.getStage().equals(fromStage))
                    .collect(java.util.stream.Collectors.toList());
        }

        if (index.getZeroBased() >= targetList.size()) {
            if (fromStage != null) {
                throw new CommandException(String.format(MESSAGE_INVALID_INDEX_FOR_STAGE, fromStage));
            }
            throw new CommandException(Messages.MESSAGE_INVALID_CANDIDATE_DISPLAYED_INDEX);
        }

        Person candidateToEdit = targetList.get(index.getZeroBased());
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

        RateCommand otherRateCommand = (RateCommand) other;
        return index.equals(otherRateCommand.index)
                && rating.equals(otherRateCommand.rating)
                && java.util.Objects.equals(fromStage, otherRateCommand.fromStage);
    }
}
