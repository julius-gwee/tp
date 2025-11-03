package seedu.address.logic.commands;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FROM;
import static seedu.address.logic.parser.CliSyntax.PREFIX_RATE;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.List;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;
import seedu.address.model.person.Rating;
import seedu.address.model.person.Stage;

/**
 * Adds a rating to a candidate in the candidate list.
 */
public class RateCommand extends Command {
    public static final String COMMAND_WORD = "rate";
    public static final String MESSAGE_EXAMPLE = "Example: " + COMMAND_WORD + " 1 " + PREFIX_FROM
            + "Candidates " + PREFIX_RATE + "Excellent";

    public static final String MESSAGE_STAGES = "Stages: Candidates, Contacted, Interviewed, Hired. (case-insensitive)";
    public static final String MESSAGE_RATINGS =
            "Ratings: Unrated, Very Poor, Poor, Average, Good, Excellent. (case-insensitive)";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the rating of the candidate. "
            + "You can scope the index to a specific stage column using " + PREFIX_FROM + ".\n"
            + "Parameters: INDEX (positive integer) " + PREFIX_FROM + "STAGE " + PREFIX_RATE + "RATING\n"
            + MESSAGE_STAGES + ".\n"
            + MESSAGE_RATINGS + ".\n"
            + MESSAGE_EXAMPLE;

    public static final String MESSAGE_RATE_SUCCESS = "Rating for %1$s: %2$s";

    public static final String MESSAGE_INVALID_INDEX =
            "The index provided is invalid. Please provide a positive integer.";
    public static final String MESSAGE_INVALID_INDEX_FOR_STAGE = "Invalid index for stage %s";
    public static final String MESSAGE_MISSING_RATE =
            "Missing rating value. Use r/ followed by a valid rating.\n" + MESSAGE_RATINGS;
    public static final String MESSAGE_INVALID_RATE = "Invalid rating. " + MESSAGE_RATINGS;
    public static final String MESSAGE_MISSING_STAGE =
            "Missing stage value. Use from/ followed by a valid stage.\n" + MESSAGE_STAGES;
    public static final String MESSAGE_INVALID_STAGE = "Invalid stage. " + MESSAGE_STAGES;
    public static final String MESSAGE_DUPLICATE_RATE = "Multiple ratings detected. Provide only one r/ prefix.";
    public static final String MESSAGE_DUPLICATE_STAGE = "Multiple stages detected. Provide only one from/ prefix.";


    private static final Logger logger = LogsCenter.getLogger(RateCommand.class);
    private final Index index;
    private final Rating rating;
    private final Stage fromStage;

    /**
     * @param index of the candidate in the filtered candidate list to edit the rating
     * @param rating of the candidate to be updated to
     * @param fromStage stage candidate belongs to
     */
    public RateCommand(Index index, Rating rating, Stage fromStage) {
        requireAllNonNull(index, rating, fromStage);
        this.index = index;
        this.rating = rating;
        this.fromStage = fromStage;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireAllNonNull(model);
        logger.info(String.format("Executing RateCommand: index=%s, stage=%s, rating=%s", index, fromStage, rating));

        List<Person> targetList = model.getObservableCandidateList().stream()
                .filter(person -> person.getStage().equals(fromStage))
                .collect(java.util.stream.Collectors.toList());

        logger.fine(String.format("Filtered %d candidates for stage %s", targetList.size(), fromStage));

        if (index.getZeroBased() >= targetList.size()) {
            logger.warning(String.format("Invalid index %s for stage %s", index, fromStage));
            throw new CommandException(String.format(MESSAGE_INVALID_INDEX_FOR_STAGE, fromStage));
        }

        Person candidateToEdit = targetList.get(index.getZeroBased());
        logger.info(String.format("Updating rating for %s to %s", candidateToEdit.getName(), rating));

        Person editedPerson = new Person(
                candidateToEdit.getName(),
                candidateToEdit.getPhone(),
                candidateToEdit.getEmail(),
                candidateToEdit.getAddress(),
                candidateToEdit.getTags(),
                candidateToEdit.getDateAdded(),
                rating,
                candidateToEdit.getStage()
        );

        model.setPerson(candidateToEdit, editedPerson);
        model.updateFilteredCandidateList(PREDICATE_SHOW_ALL_PERSONS);

        logger.fine(String.format("Successfully updated rating for %s", candidateToEdit.getName()));

        return new CommandResult(String.format(MESSAGE_RATE_SUCCESS, candidateToEdit.getName(), rating));
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
