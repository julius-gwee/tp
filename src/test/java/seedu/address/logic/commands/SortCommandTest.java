package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.SortCommand.SORT_BY_ADDRESS;
import static seedu.address.logic.commands.SortCommand.SORT_BY_ALPHABET;
import static seedu.address.testutil.TypicalPersons.getTypicalFindr;

import java.util.Comparator;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;

public class SortCommandTest {

    private Model model;
    private Model expectedModel;

    @Test
    public void execute_sortByAlphabet_showsSortedByAlphabet() {
        model = new ModelManager(getTypicalFindr(), new UserPrefs());
        expectedModel = new ModelManager(model.getCandidateList(), new UserPrefs());
        assertCommandSuccess(new SortCommand(SORT_BY_ALPHABET), model, SortCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void equals() {
        SortCommand sortFirstCommand = new SortCommand(SORT_BY_ALPHABET);
        SortCommand sortSecondCommand = new SortCommand(SORT_BY_ADDRESS);

        // same object -> returns true
        assertTrue(sortFirstCommand.equals(sortFirstCommand));

        // same values -> returns true
        SortCommand sortFirstCommandCopy = new SortCommand(SORT_BY_ALPHABET);
        assertTrue(sortFirstCommand.equals(sortFirstCommandCopy));

        // different types -> returns false
        assertFalse(sortFirstCommand.equals(1));

        // null -> returns false
        assertFalse(sortFirstCommand.equals(null));

        // different comparator -> returns false
        assertFalse(sortFirstCommand.equals(sortSecondCommand));
    }

    @Test
    public void toStringMethod() {
        Comparator<Person> comparator = SORT_BY_ALPHABET;
        SortCommand sortCommand = new SortCommand(comparator);
        String expected = SortCommand.class.getCanonicalName() + "{comparator=" + comparator + "}";
        assertEquals(expected, sortCommand.toString());
    }
}
