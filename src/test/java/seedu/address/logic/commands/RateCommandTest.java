package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FROM_SHORT;
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
import seedu.address.model.person.Stage;
import seedu.address.testutil.PersonBuilder;

/**
 * Contains integration tests (interaction with the Model) and unit tests for RateCommand.
 */
public class RateCommandTest {

    private Model model = new ModelManager(getTypicalFindr(), new UserPrefs());
    private RateCommandParser parser = new RateCommandParser();

    @Test
    public void execute_addRating_success() {
        Person firstCandidate = model.getObservableCandidateList().get(INDEX_FIRST_CANDIDATE.getZeroBased());
        Person editedCandidate = new PersonBuilder(firstCandidate).withRating(Rating.fromString("EXCELLENT")).build();

        RateCommand rateCommand = new RateCommand(INDEX_FIRST_CANDIDATE,
                editedCandidate.getRating());

        String expectedMessage = String.format(RateCommand.MESSAGE_RATE_SUCCESS,
                editedCandidate.getName(), editedCandidate.getRating());

        Model expectedModel = new ModelManager(new Findr(model.getCandidateList()), new UserPrefs());
        expectedModel.setPerson(firstCandidate, editedCandidate);

        assertCommandSuccess(rateCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_withFromStage_success() throws Exception {
        // pick first candidate in Candidates stage
        Person candidateInCandidates = model.getObservableCandidateList().stream()
                .filter(p -> p.getStage() == Stage.CANDIDATES)
                .findFirst().orElse(model.getObservableCandidateList().get(0));

        // assume index 1 within that stage for typical data; command filters by stage list
        RateCommand cmd = new RateCommand(Index.fromOneBased(1), Rating.GOOD, Stage.CANDIDATES);

        Person edited = new PersonBuilder(candidateInCandidates).withRating(Rating.GOOD).build();
        Model expected = new ModelManager(new Findr(model.getCandidateList()), new UserPrefs());
        expected.setPerson(candidateInCandidates, edited);

        assertCommandSuccess(cmd, model,
                String.format(RateCommand.MESSAGE_RATE_SUCCESS, candidateInCandidates.getName(), Rating.GOOD), expected);
    }

    @Test
    public void execute_withFromStage_invalidIndex_failure() {
        RateCommand cmd = new RateCommand(Index.fromOneBased(999), Rating.GOOD, Stage.INTERVIEWED);
        assertCommandFailure(cmd, model,
                String.format(RateCommand.MESSAGE_INVALID_INDEX_FOR_STAGE, Stage.INTERVIEWED));
    }

    @Test
    public void parse_invalidIndex_throwsParseException() {
        String userInput = "0 " + PREFIX_FROM_SHORT + "Candidates " + PREFIX_RATE + "EXCELLENT"; // 0 is invalid
        assertParseFailure(parser, userInput,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, RateCommand.MESSAGE_USAGE));
    }

    @Test
    public void equals() {
        final RateCommand standardCommand = new RateCommand(INDEX_FIRST_CANDIDATE, Rating.EXCELLENT, Stage.CANDIDATES);

        // same values -> returns true
        RateCommand commandWithSameValues = new RateCommand(INDEX_FIRST_CANDIDATE, Rating.EXCELLENT, Stage.CANDIDATES);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new RateCommand(INDEX_SECOND_CANDIDATE, Rating.EXCELLENT, Stage.CANDIDATES)));

        // different remark -> returns false
        assertFalse(standardCommand.equals(new RateCommand(INDEX_FIRST_CANDIDATE, Rating.GOOD, Stage.CANDIDATES)));

        // different stage -> returns false
        assertFalse(standardCommand.equals(new RateCommand(INDEX_FIRST_CANDIDATE, Rating.EXCELLENT, Stage.CONTACTED)));
    }
}
