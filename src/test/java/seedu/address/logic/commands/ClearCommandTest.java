package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.getTypicalFindr;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import seedu.address.model.Findr;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;
import seedu.address.model.person.Stage;

public class ClearCommandTest {

    @Test
    public void execute_clearAllEmpty_success() {
        Model model = new ModelManager();
        Model expectedModel = new ModelManager();

        assertCommandSuccess(new ClearCommand(), model, ClearCommand.MESSAGE_SUCCESS_ALL, expectedModel);
    }

    @Test
    public void execute_clearAllNonEmpty_success() {
        Model model = new ModelManager(getTypicalFindr(), new UserPrefs());
        Model expectedModel = new ModelManager(getTypicalFindr(), new UserPrefs());
        expectedModel.setAddressBook(new Findr());

        assertCommandSuccess(new ClearCommand(), model, ClearCommand.MESSAGE_SUCCESS_ALL, expectedModel);
    }

    @Test
    public void execute_clearSpecificStage_success() {
        Model model = new ModelManager(getTypicalFindr(), new UserPrefs());

        // Clear candidates stage
        ClearCommand clearCommand = new ClearCommand(Stage.CANDIDATES);
        String expectedMessage = String.format(ClearCommand.MESSAGE_SUCCESS_STAGE, Stage.CANDIDATES);

        Model expectedModel = new ModelManager(getTypicalFindr(), new UserPrefs());
        // Remove all candidates in CANDIDATES stage from expected model
        expectedModel.getObservableCandidateList().stream()
                .filter(person -> person.getStage().equals(Stage.CANDIDATES))
                .toArray(Person[]::new);
        Arrays.stream(expectedModel.getObservableCandidateList().stream()
                .filter(person -> person.getStage().equals(Stage.CANDIDATES))
                .toArray(Person[]::new))
                .forEach(expectedModel::deleteCandidate);

        assertCommandSuccess(clearCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_clearEmptyStage_success() {
        Model model = new ModelManager(getTypicalFindr(), new UserPrefs());
        Model expectedModel = new ModelManager(getTypicalFindr(), new UserPrefs());

        // Clear HIRED stage (which should be empty in typical findr)
        ClearCommand clearCommand = new ClearCommand(Stage.HIRED);
        String expectedMessage = String.format(ClearCommand.MESSAGE_SUCCESS_STAGE, Stage.HIRED);

        assertCommandSuccess(clearCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void equals() {
        ClearCommand clearAllCommand = new ClearCommand();
        ClearCommand clearCandidatesCommand = new ClearCommand(Stage.CANDIDATES);
        ClearCommand clearContactedCommand = new ClearCommand(Stage.CONTACTED);

        // same object -> returns true
        assertTrue(clearAllCommand.equals(clearAllCommand));
        assertTrue(clearCandidatesCommand.equals(clearCandidatesCommand));

        // same values -> returns true
        ClearCommand clearAllCommandCopy = new ClearCommand();
        assertTrue(clearAllCommand.equals(clearAllCommandCopy));

        ClearCommand clearCandidatesCommandCopy = new ClearCommand(Stage.CANDIDATES);
        assertTrue(clearCandidatesCommand.equals(clearCandidatesCommandCopy));

        // different types -> returns false
        assertFalse(clearAllCommand.equals(1));

        // null -> returns false
        assertFalse(clearAllCommand.equals(null));

        // different clear command -> returns false
        assertFalse(clearAllCommand.equals(clearCandidatesCommand));
        assertFalse(clearCandidatesCommand.equals(clearContactedCommand));
    }

    @Test
    public void toString_clearAll() {
        ClearCommand clearCommand = new ClearCommand();
        assertEquals("ClearCommand{clearAll=true}", clearCommand.toString());
    }

    @Test
    public void toString_clearStage() {
        ClearCommand clearCommand = new ClearCommand(Stage.CANDIDATES);
        assertEquals("ClearCommand{stage=Candidates}", clearCommand.toString());
    }
}
