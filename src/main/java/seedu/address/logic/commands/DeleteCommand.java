package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FROM;

import java.util.List;
import java.util.stream.Collectors;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;
import seedu.address.model.person.Stage;

/**
 * Deletes a candidate identified using it's displayed index from the candidate list.
 */
public class DeleteCommand extends Command {

    public static final String COMMAND_WORD = "delete";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the candidate identified by the index number in the specified stage.\n"
            + "Parameters: INDEX (must be a positive integer) " + PREFIX_FROM + "STAGE\n"
            + "The index refers to the position within that stage column.\n"
            + "Stages: Candidates, Contacted, Interviewed, Hired (case-insensitive)\n"
            + "Examples:\n"
            + COMMAND_WORD + " 1 " + PREFIX_FROM + "candidates\n"
            + COMMAND_WORD + " 1 " + PREFIX_FROM + "Contacted";

    public static final String MESSAGE_DELETE_PERSON_SUCCESS = "Deleted Candidate: %1$s";

    private final Index targetIndex;
    private final Stage fromStage;

    /**
     * Creates a DeleteCommand to delete the candidate at the specified index within a specific stage.
     *
     * @param targetIndex Index of the candidate within the specified stage column (1-based).
     * @param fromStage   The stage from which to delete the candidate.
     */
    public DeleteCommand(Index targetIndex, Stage fromStage) {
        requireNonNull(targetIndex);
        requireNonNull(fromStage);
        this.targetIndex = targetIndex;
        this.fromStage = fromStage;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        // Filter the full list to get only persons in the specified fromStage
        List<Person> targetList = model.getObservableCandidateList().stream()
                .filter(person -> person.getStage().equals(fromStage))
                .collect(Collectors.toList());

        if (targetIndex.getZeroBased() >= targetList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_CANDIDATE_DISPLAYED_INDEX);
        }

        Person personToDelete = targetList.get(targetIndex.getZeroBased());
        model.deleteCandidate(personToDelete);
        return new CommandResult(String.format(MESSAGE_DELETE_PERSON_SUCCESS, Messages.format(personToDelete)));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof DeleteCommand)) {
            return false;
        }

        DeleteCommand otherDeleteCommand = (DeleteCommand) other;
        return targetIndex.equals(otherDeleteCommand.targetIndex)
                && fromStage.equals(otherDeleteCommand.fromStage);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("targetIndex", targetIndex)
                .add("fromStage", fromStage)
                .toString();
    }
}
