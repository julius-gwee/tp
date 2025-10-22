package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.tag.Tag;

class TagAddCommandTest {

    @Test
    void constructor_nullTag_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new TagAddCommand(null));
    }

    @Test
    void execute_tagAccepted_addsTag() throws Exception {
        TagCommandTestUtil.ModelStub modelStub = new TagCommandTestUtil.ModelStub();
        Tag tag = new Tag("backend", "Engineering", "#1F75FE", "Backend specialist");
        TagAddCommand command = new TagAddCommand(tag);

        CommandResult result = command.execute(modelStub);
        assertEquals(String.format(TagAddCommand.MESSAGE_SUCCESS, tag.tagName), result.getFeedbackToUser());
        assertTrue(modelStub.hasTag(tag));
    }

    @Test
    void execute_duplicateTag_throwsCommandException() {
        Tag existingTag = new Tag("backend", "Engineering", "#1F75FE", "Backend specialist");
        TagCommandTestUtil.ModelStub modelStub = new TagCommandTestUtil.ModelStub();
        modelStub.addExistingTag(existingTag);

        TagAddCommand command = new TagAddCommand(new Tag("backend", "Engineering", "#1F75FE", "Backend specialist"));
        assertThrows(CommandException.class, TagAddCommand.MESSAGE_DUPLICATE_TAG, () -> command.execute(modelStub));
    }

    @Test
    void equals() {
        Tag backend = new Tag("backend");
        Tag frontend = new Tag("frontend");

        TagAddCommand backendCommand = new TagAddCommand(backend);
        TagAddCommand backendCommandCopy = new TagAddCommand(new Tag("backend"));
        TagAddCommand frontendCommand = new TagAddCommand(frontend);

        assertTrue(backendCommand.equals(backendCommand));
        assertTrue(backendCommand.equals(backendCommandCopy));
        assertFalse(backendCommand.equals(frontendCommand));
        assertFalse(backendCommand.equals(null));
        assertFalse(backendCommand.equals(5));
    }
}
