package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_RATING_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_RATING_BOB;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.RateCommand.MESSAGE_ARGUMENTS;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_CANDIDATE;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_CANDIDATE;
import static seedu.address.testutil.TypicalPersons.getTypicalFindr;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

/**
 * Contains integration tests (interaction with the Model) and unit tests for RateCommand.
 */
public class RateCommandTest {

    private Model model = new ModelManager(getTypicalFindr(), new UserPrefs());

    @Test
    public void execute() {
        final String rating = "ONE";
        assertCommandFailure(new RateCommand(INDEX_FIRST_CANDIDATE, rating), model,
                String.format(MESSAGE_ARGUMENTS, INDEX_FIRST_CANDIDATE.getOneBased(), rating));
    }

    @Test
    public void equals() {
        final RateCommand standardCommand = new RateCommand(INDEX_FIRST_CANDIDATE, VALID_RATING_AMY);

        // same values -> returns true
        RateCommand commandWithSameValues = new RateCommand(INDEX_FIRST_CANDIDATE, VALID_RATING_AMY);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new RateCommand(INDEX_SECOND_CANDIDATE, VALID_RATING_AMY)));

        // different remark -> returns false
        assertFalse(standardCommand.equals(new RateCommand(INDEX_FIRST_CANDIDATE, VALID_RATING_BOB)));
    }
}
