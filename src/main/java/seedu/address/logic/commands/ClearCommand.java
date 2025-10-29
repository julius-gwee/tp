package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.stream.Collectors;

import seedu.address.model.Findr;
import seedu.address.model.Model;
import seedu.address.model.person.Person;
import seedu.address.model.person.Stage;

/**
 * Clears candidates from the candidate list.
 * Can clear candidates from a specific stage or all stages.
 */
public class ClearCommand extends Command {

    public static final String COMMAND_WORD = "clear";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Clears all candidates from the specified stage.\n"
            + "Use 'clear all' to clear all candidates from all stages.\n"
            + "Parameters: STAGE (candidates, contacted, interviewed, hired) or 'all'\n"
            + "Example: " + COMMAND_WORD + " candidates\n"
            + "Example: " + COMMAND_WORD + " all";

    public static final String MESSAGE_SUCCESS_ALL = "All candidates have been cleared!";
    public static final String MESSAGE_SUCCESS_STAGE = "All candidates from %1$s have been cleared!";

    private final Stage stage;
    private final boolean clearAll;

    /**
     * Creates a ClearCommand to clear candidates from the specified {@code Stage}.
     */
    public ClearCommand(Stage stage) {
        this.stage = stage;
        this.clearAll = false;
    }

    /**
     * Creates a ClearCommand to clear all candidates from all stages.
     */
    public ClearCommand() {
        this.stage = null;
        this.clearAll = true;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);

        if (clearAll) {
            model.setAddressBook(new Findr());
            return new CommandResult(MESSAGE_SUCCESS_ALL);
        } else {
            List<Person> candidatesToDelete = model.getObservableCandidateList().stream()
                    .filter(person -> person.getStage().equals(stage))
                    .collect(Collectors.toList());

            for (Person person : candidatesToDelete) {
                model.deleteCandidate(person);
            }

            return new CommandResult(String.format(MESSAGE_SUCCESS_STAGE, stage));
        }
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof ClearCommand)) {
            return false;
        }

        ClearCommand otherCommand = (ClearCommand) other;
        return clearAll == otherCommand.clearAll
                && (clearAll || stage.equals(otherCommand.stage));
    }

    @Override
    public String toString() {
        if (clearAll) {
            return "ClearCommand{clearAll=true}";
        } else {
            return "ClearCommand{stage=" + stage + "}";
        }
    }
}
