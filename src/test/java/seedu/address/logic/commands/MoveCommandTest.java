package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_CANDIDATE;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_CANDIDATE;
import static seedu.address.testutil.TypicalPersons.getTypicalFindr;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;
import seedu.address.model.person.Stage;
import seedu.address.testutil.PersonBuilder;

/**
 * Contains integration tests (interaction with the Model) and unit tests for
 * {@code MoveCommand}.
 */
public class MoveCommandTest {

    private Model model = new ModelManager(getTypicalFindr(), new UserPrefs());

    @Test
    public void execute_validMoveUnfilteredList_success() {
        Person personToMove = model.getObservableCandidateList().get(INDEX_FIRST_CANDIDATE.getZeroBased());
        MoveCommand moveCommand = new MoveCommand(INDEX_FIRST_CANDIDATE, Stage.CANDIDATES, Stage.CONTACTED);

        Person movedPerson = new PersonBuilder(personToMove).withStage(Stage.CONTACTED).build();

        String expectedMessage = String.format(MoveCommand.MESSAGE_MOVE_PERSON_SUCCESS,
                Stage.CANDIDATES.getDisplayName(), Stage.CONTACTED.getDisplayName(), Messages.format(movedPerson));

        ModelManager expectedModel = new ModelManager(model.getCandidateList(), new UserPrefs());
        expectedModel.setPerson(personToMove, movedPerson);

        assertCommandSuccess(moveCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getObservableCandidateList().size() + 1);
        MoveCommand moveCommand = new MoveCommand(outOfBoundIndex, Stage.CANDIDATES, Stage.CONTACTED);

        assertCommandFailure(moveCommand, model, Messages.MESSAGE_INVALID_CANDIDATE_DISPLAYED_INDEX);
    }

    @Test
    public void execute_sameStage_throwsCommandException() {
        MoveCommand moveCommand = new MoveCommand(INDEX_FIRST_CANDIDATE, Stage.CANDIDATES, Stage.CANDIDATES);

        assertCommandFailure(moveCommand, model, MoveCommand.MESSAGE_SAME_STAGE);
    }

    @Test
    public void execute_invalidIndexForStage_throwsCommandException() {
        // Try to move from HIRED stage when there are no persons in that stage
        MoveCommand moveCommand = new MoveCommand(INDEX_FIRST_CANDIDATE, Stage.HIRED, Stage.INTERVIEWED);

        assertCommandFailure(moveCommand, model, Messages.MESSAGE_INVALID_CANDIDATE_DISPLAYED_INDEX);
    }

    @Test
    public void execute_moveFromCandidatesToHired_success() {
        Person personToMove = model.getObservableCandidateList().get(INDEX_FIRST_CANDIDATE.getZeroBased());
        MoveCommand moveCommand = new MoveCommand(INDEX_FIRST_CANDIDATE, Stage.CANDIDATES, Stage.HIRED);

        Person movedPerson = new PersonBuilder(personToMove).withStage(Stage.HIRED).build();

        String expectedMessage = String.format(MoveCommand.MESSAGE_MOVE_PERSON_SUCCESS,
                Stage.CANDIDATES.getDisplayName(), Stage.HIRED.getDisplayName(), Messages.format(movedPerson));

        ModelManager expectedModel = new ModelManager(model.getCandidateList(), new UserPrefs());
        expectedModel.setPerson(personToMove, movedPerson);

        assertCommandSuccess(moveCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void equals() {
        MoveCommand moveFirstCommand = new MoveCommand(INDEX_FIRST_CANDIDATE, Stage.CANDIDATES, Stage.CONTACTED);
        MoveCommand moveSecondCommand = new MoveCommand(INDEX_SECOND_CANDIDATE, Stage.CANDIDATES, Stage.CONTACTED);
        MoveCommand moveDifferentStages = new MoveCommand(INDEX_FIRST_CANDIDATE, Stage.CANDIDATES, Stage.INTERVIEWED);

        // same object -> returns true
        assertTrue(moveFirstCommand.equals(moveFirstCommand));

        // same values -> returns true
        MoveCommand moveFirstCommandCopy = new MoveCommand(INDEX_FIRST_CANDIDATE, Stage.CANDIDATES, Stage.CONTACTED);
        assertTrue(moveFirstCommand.equals(moveFirstCommandCopy));

        // different types -> returns false
        assertFalse(moveFirstCommand.equals(1));

        // null -> returns false
        assertFalse(moveFirstCommand.equals(null));

        // different index -> returns false
        assertFalse(moveFirstCommand.equals(moveSecondCommand));

        // different stages -> returns false
        assertFalse(moveFirstCommand.equals(moveDifferentStages));
    }

    @Test
    public void toStringMethod() {
        Index targetIndex = Index.fromOneBased(1);
        Stage fromStage = Stage.CANDIDATES;
        Stage toStage = Stage.CONTACTED;
        MoveCommand moveCommand = new MoveCommand(targetIndex, fromStage, toStage);
        String expected = MoveCommand.class.getCanonicalName()
                + "{targetIndex=" + targetIndex
                + ", fromStage=" + fromStage
                + ", toStage=" + toStage + "}";
        assertEquals(expected, moveCommand.toString());
    }
}

