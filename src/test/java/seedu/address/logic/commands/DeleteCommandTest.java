package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showCandidateAtIndex;
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

/**
 * Contains integration tests (interaction with the Model) and unit tests for
 * {@code DeleteCommand}.
 */
public class DeleteCommandTest {

    private Model model = new ModelManager(getTypicalFindr(), new UserPrefs());

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Person personToDelete = model.getObservableCandidateList().get(INDEX_FIRST_CANDIDATE.getZeroBased());
        DeleteCommand deleteCommand = new DeleteCommand(INDEX_FIRST_CANDIDATE);

        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_PERSON_SUCCESS,
                Messages.format(personToDelete));

        ModelManager expectedModel = new ModelManager(model.getCandidateList(), new UserPrefs());
        expectedModel.deleteCandidate(personToDelete);

        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getObservableCandidateList().size() + 1);
        DeleteCommand deleteCommand = new DeleteCommand(outOfBoundIndex);

        assertCommandFailure(deleteCommand, model, Messages.MESSAGE_INVALID_CANDIDATE_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        showCandidateAtIndex(model, INDEX_FIRST_CANDIDATE);

        Person personToDelete = model.getObservableCandidateList().get(INDEX_FIRST_CANDIDATE.getZeroBased());
        DeleteCommand deleteCommand = new DeleteCommand(INDEX_FIRST_CANDIDATE);

        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_PERSON_SUCCESS,
                Messages.format(personToDelete));

        Model expectedModel = new ModelManager(model.getCandidateList(), new UserPrefs());
        expectedModel.deleteCandidate(personToDelete);
        showNoPerson(expectedModel);

        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showCandidateAtIndex(model, INDEX_FIRST_CANDIDATE);

        Index outOfBoundIndex = INDEX_SECOND_CANDIDATE;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getCandidateList().getCandidateList().size());

        DeleteCommand deleteCommand = new DeleteCommand(outOfBoundIndex);

        assertCommandFailure(deleteCommand, model, Messages.MESSAGE_INVALID_CANDIDATE_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexWithStage_success() {
        // For this test, use the default candidates list where all are in Stage.CANDIDATES
        Model model = new ModelManager(getTypicalFindr(), new UserPrefs());

        Person personToDelete = model.getObservableCandidateList().get(INDEX_FIRST_CANDIDATE.getZeroBased());

        // Delete the first candidate from the CANDIDATES stage
        DeleteCommand deleteCommand = new DeleteCommand(INDEX_FIRST_CANDIDATE, Stage.CANDIDATES);

        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_PERSON_SUCCESS,
                Messages.format(personToDelete));

        // Create expected model and delete the person
        ModelManager expectedModel = new ModelManager(model.getCandidateList(), new UserPrefs());
        expectedModel.deleteCandidate(personToDelete);

        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexWithStage_throwsCommandException() {
        Model model = new ModelManager(getTypicalFindr(), new UserPrefs());

        // Try to delete at an index beyond the number of candidates in CANDIDATES stage
        Index outOfBoundIndex = Index.fromOneBased(model.getObservableCandidateList().size() + 1);
        DeleteCommand deleteCommand = new DeleteCommand(outOfBoundIndex, Stage.CANDIDATES);

        assertCommandFailure(deleteCommand, model, Messages.MESSAGE_INVALID_CANDIDATE_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexWithEmptyStage_throwsCommandException() {
        Model model = new ModelManager(getTypicalFindr(), new UserPrefs());

        // Try to delete from Hired stage (which is empty in typical data)
        DeleteCommand deleteCommand = new DeleteCommand(INDEX_FIRST_CANDIDATE, Stage.HIRED);

        assertCommandFailure(deleteCommand, model, Messages.MESSAGE_INVALID_CANDIDATE_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        DeleteCommand deleteFirstCommand = new DeleteCommand(INDEX_FIRST_CANDIDATE);
        DeleteCommand deleteSecondCommand = new DeleteCommand(INDEX_SECOND_CANDIDATE);

        // same object -> returns true
        assertTrue(deleteFirstCommand.equals(deleteFirstCommand));

        // same values -> returns true
        DeleteCommand deleteFirstCommandCopy = new DeleteCommand(INDEX_FIRST_CANDIDATE);
        assertTrue(deleteFirstCommand.equals(deleteFirstCommandCopy));

        // different types -> returns false
        assertFalse(deleteFirstCommand.equals(1));

        // null -> returns false
        assertFalse(deleteFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(deleteFirstCommand.equals(deleteSecondCommand));

        // test with stage - same values -> returns true
        DeleteCommand deleteFirstWithStage = new DeleteCommand(INDEX_FIRST_CANDIDATE, Stage.CANDIDATES);
        DeleteCommand deleteFirstWithStageCopy = new DeleteCommand(INDEX_FIRST_CANDIDATE, Stage.CANDIDATES);
        assertTrue(deleteFirstWithStage.equals(deleteFirstWithStageCopy));

        // test with stage - different stage -> returns false
        DeleteCommand deleteFirstWithDifferentStage = new DeleteCommand(INDEX_FIRST_CANDIDATE, Stage.CONTACTED);
        assertFalse(deleteFirstWithStage.equals(deleteFirstWithDifferentStage));

        // test with stage - one with stage, one without -> returns false
        assertFalse(deleteFirstCommand.equals(deleteFirstWithStage));
        assertFalse(deleteFirstWithStage.equals(deleteFirstCommand));
    }

    @Test
    public void toStringMethod() {
        Index targetIndex = Index.fromOneBased(1);
        DeleteCommand deleteCommand = new DeleteCommand(targetIndex);
        String expected = DeleteCommand.class.getCanonicalName()
                + "{targetIndex=" + targetIndex + ", fromStage=null}";
        assertEquals(expected, deleteCommand.toString());

        // Test with stage
        DeleteCommand deleteCommandWithStage = new DeleteCommand(targetIndex, Stage.CANDIDATES);
        String expectedWithStage = DeleteCommand.class.getCanonicalName()
                + "{targetIndex=" + targetIndex + ", fromStage=" + Stage.CANDIDATES + "}";
        assertEquals(expectedWithStage, deleteCommandWithStage.toString());
    }

    /**
     * Updates {@code model}'s filtered list to show no one.
     */
    private void showNoPerson(Model model) {
        model.updateFilteredCandidateList(p -> false);

        assertTrue(model.getObservableCandidateList().isEmpty());
    }
}
