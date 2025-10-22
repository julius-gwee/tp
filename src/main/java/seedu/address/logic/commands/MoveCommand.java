package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FROM;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TO;

import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;
import seedu.address.model.person.Stage;

/**
 * Moves a candidate from one recruitment stage to another in the kanban board.
 */
public class MoveCommand extends Command {

    public static final String COMMAND_WORD = "move";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Moves the candidate identified by the index number from one stage to another.\n"
            + "INDEX "
            + PREFIX_FROM + "CURRENT_STAGE "
            + PREFIX_TO + "NEW_STAGE\n"
            + "Stages: Candidates, Contacted, Interviewed, Hired (case-insensitive)\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_FROM + "Candidates "
            + PREFIX_TO + "Contacted";

    public static final String MESSAGE_MOVE_PERSON_SUCCESS = "Moved candidate from %1$s to %2$s: %3$s";
    public static final String MESSAGE_SAME_STAGE = "The candidate is already in this list";
    public static final String MESSAGE_WRONG_CURRENT_STAGE =
            "The candidate is not in the specified current stage. Current stage: %1$s";

    private final Index targetIndex;
    private final Stage fromStage;
    private final Stage toStage;

    /**
     * Creates a MoveCommand to move the candidate at the specified index.
     *
     * @param targetIndex Index of the candidate in the filtered candidate list.
     * @param fromStage   The current stage the candidate should be in.
     * @param toStage     The new stage to move the candidate to.
     */
    public MoveCommand(Index targetIndex, Stage fromStage, Stage toStage) {
        requireNonNull(targetIndex);
        requireNonNull(fromStage);
        requireNonNull(toStage);

        this.targetIndex = targetIndex;
        this.fromStage = fromStage;
        this.toStage = toStage;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredCandidateList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_CANDIDATE_DISPLAYED_INDEX);
        }

        Person personToMove = lastShownList.get(targetIndex.getZeroBased());

        // Validate that the person is in the specified current stage
        if (!personToMove.getStage().equals(fromStage)) {
            throw new CommandException(String.format(MESSAGE_WRONG_CURRENT_STAGE,
                    personToMove.getStage().getDisplayName()));
        }

        // Validate that the new stage is different from current stage
        if (fromStage.equals(toStage)) {
            throw new CommandException(MESSAGE_SAME_STAGE);
        }

        // Create a new person with the updated stage
        Person movedPerson = new Person(
                personToMove.getName(),
                personToMove.getPhone(),
                personToMove.getEmail(),
                personToMove.getAddress(),
                personToMove.getTags(),
                toStage);

        model.setPerson(personToMove, movedPerson);
        return new CommandResult(String.format(MESSAGE_MOVE_PERSON_SUCCESS,
                fromStage.getDisplayName(), toStage.getDisplayName(), Messages.format(movedPerson)));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof MoveCommand)) {
            return false;
        }

        MoveCommand otherMoveCommand = (MoveCommand) other;
        return targetIndex.equals(otherMoveCommand.targetIndex)
                && fromStage.equals(otherMoveCommand.fromStage)
                && toStage.equals(otherMoveCommand.toStage);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("targetIndex", targetIndex)
                .add("fromStage", fromStage)
                .add("toStage", toStage)
                .toString();
    }
}
