package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_RATING_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_RATING_BOB;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.parser.CliSyntax.PREFIX_RATE;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_CANDIDATE;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_CANDIDATE;
import static seedu.address.testutil.TypicalPersons.getTypicalFindr;

import org.junit.jupiter.api.Test;

import seedu.address.logic.parser.RateCommandParser;
import seedu.address.model.Findr;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;
import seedu.address.model.person.Rating;
import seedu.address.testutil.PersonBuilder;

/**
 * Contains integration tests (interaction with the Model) and unit tests for RateCommand.
 */
public class RateCommandTest {

    private Model model = new ModelManager(getTypicalFindr(), new UserPrefs());
    private RateCommandParser parser = new RateCommandParser();

    @Test
    public void execute_addRating_success() {
        Person firstCandidate = model.getFilteredCandidateList().get(INDEX_FIRST_CANDIDATE.getZeroBased());
        Person editedCandidate = new PersonBuilder(firstCandidate).withRating(new Rating("ONE")).build();

        RateCommand rateCommand = new RateCommand(INDEX_FIRST_CANDIDATE,
                new Rating(editedCandidate.getRating().value.toString()));

        String expectedMessage = String.format(RateCommand.MESSAGE_RATE_SUCCESS,
                editedCandidate.getName(), editedCandidate.getRating());

        Model expectedModel = new ModelManager(new Findr(model.getCandidateList()), new UserPrefs());
        expectedModel.setPerson(firstCandidate, editedCandidate);

        assertCommandSuccess(rateCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void parse_invalidIndex_throwsParseException() {
        String userInput = "0 " + PREFIX_RATE + "ONE"; // 0 is invalid
        assertParseFailure(parser, userInput,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, RateCommand.MESSAGE_USAGE));
    }

    @Test
    public void equals() {
        final RateCommand standardCommand = new RateCommand(INDEX_FIRST_CANDIDATE, new Rating(VALID_RATING_AMY));

        // same values -> returns true
        RateCommand commandWithSameValues = new RateCommand(INDEX_FIRST_CANDIDATE, new Rating(VALID_RATING_AMY));
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new RateCommand(INDEX_SECOND_CANDIDATE, new Rating(VALID_RATING_AMY))));

        // different remark -> returns false
        assertFalse(standardCommand.equals(new RateCommand(INDEX_FIRST_CANDIDATE, new Rating(VALID_RATING_BOB))));
    }
}
