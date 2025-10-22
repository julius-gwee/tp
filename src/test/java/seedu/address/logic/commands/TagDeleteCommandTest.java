package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.tag.Tag;

class TagDeleteCommandTest {

    @Test
    void constructor_nullName_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new TagDeleteCommand(null));
    }

    @Test
    void execute_existingTag_deletesTag() throws Exception {
        Tag existingTag = new Tag("backend", "Engineering", "#1F75FE", "Backend specialist");
        TagCommandTestUtil.ModelStub modelStub = new TagCommandTestUtil.ModelStub();
        modelStub.addExistingTag(existingTag);

        TagDeleteCommand command = new TagDeleteCommand("backend");
        CommandResult result = command.execute(modelStub);

        assertEquals(String.format(TagDeleteCommand.MESSAGE_SUCCESS, "backend"), result.getFeedbackToUser());
        assertFalse(modelStub.hasTag(existingTag));
    }

    @Test
    void execute_missingTag_throwsCommandException() {
        TagCommandTestUtil.ModelStub modelStub = new TagCommandTestUtil.ModelStub();
        TagDeleteCommand command = new TagDeleteCommand("backend");

        assertThrows(CommandException.class, TagDeleteCommand.MESSAGE_TAG_NOT_FOUND, () -> command.execute(modelStub));
    }

    @Test
    void equals() {
        TagDeleteCommand backendCommand = new TagDeleteCommand("backend");
        TagDeleteCommand backendCommandCopy = new TagDeleteCommand("backend");
        TagDeleteCommand frontendCommand = new TagDeleteCommand("frontend");

        assertTrue(backendCommand.equals(backendCommand));
        assertTrue(backendCommand.equals(backendCommandCopy));
        assertFalse(backendCommand.equals(frontendCommand));
        assertFalse(backendCommand.equals(null));
        assertFalse(backendCommand.equals(5));
    }
}
