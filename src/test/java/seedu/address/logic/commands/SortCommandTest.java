package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.SortCommand.SORT_BY_ALPHABET;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

public class SortCommandTest {

    private Model model;
    private Model expectedModel;

    @Test
    public void execute_sortByAlphabet_showsSortedByAlphabet() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel = new ModelManager(model.getCandidateList(), new UserPrefs());
        assertCommandSuccess(new SortCommand(SORT_BY_ALPHABET), model, SortCommand.MESSAGE_SUCCESS, expectedModel);
    }
}
