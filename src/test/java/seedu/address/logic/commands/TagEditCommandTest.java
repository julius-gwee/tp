package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.TagEditCommand.EditTagDescriptor;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.tag.Tag;

class TagEditCommandTest {

    @Test
    void constructor_nullArguments_throwsNullPointerException() {
        EditTagDescriptor descriptor = new EditTagDescriptor();
        descriptor.setCategory("Engineering");
        assertThrows(NullPointerException.class, () -> new TagEditCommand(null, descriptor));
        assertThrows(NullPointerException.class, () -> new TagEditCommand("backend", null));
    }

    @Test
    void execute_editFields_success() throws Exception {
        Tag existingTag = new Tag("backend", "Engineering", "#1F75FE", "Backend specialist");
        TagCommandTestUtil.ModelStub modelStub = new TagCommandTestUtil.ModelStub();
        modelStub.addExistingTag(existingTag);

        EditTagDescriptor descriptor = new EditTagDescriptor();
        descriptor.setCategory("Infrastructure");
        descriptor.setColour("#00FF00");
        descriptor.setDescription("Maintains infrastructure");

        TagEditCommand command = new TagEditCommand("backend", descriptor);
        CommandResult result = command.execute(modelStub);

        assertEquals(String.format(TagEditCommand.MESSAGE_SUCCESS, "backend"), result.getFeedbackToUser());
        assertTrue(modelStub.hasTag(new Tag("backend", "Infrastructure", "#00FF00", "Maintains infrastructure")));
    }

    @Test
    void execute_noFieldsEdited_throwsCommandException() {
        Tag existingTag = new Tag("backend");
        TagCommandTestUtil.ModelStub modelStub = new TagCommandTestUtil.ModelStub();
        modelStub.addExistingTag(existingTag);

        TagEditCommand command = new TagEditCommand("backend", new EditTagDescriptor());
        assertThrows(CommandException.class, TagEditCommand.MESSAGE_NOT_EDITED, () -> command.execute(modelStub));
    }

    @Test
    void execute_duplicateTag_throwsCommandException() {
        Tag backend = new Tag("backend");
        Tag frontend = new Tag("frontend");
        TagCommandTestUtil.ModelStub modelStub = new TagCommandTestUtil.ModelStub();
        modelStub.addExistingTag(backend);
        modelStub.addExistingTag(frontend);

        EditTagDescriptor descriptor = new EditTagDescriptor();
        descriptor.setName("frontend");

        TagEditCommand command = new TagEditCommand("backend", descriptor);
        assertThrows(CommandException.class, TagEditCommand.MESSAGE_DUPLICATE_TAG, () -> command.execute(modelStub));
    }

    @Test
    void execute_missingTag_throwsCommandException() {
        TagCommandTestUtil.ModelStub modelStub = new TagCommandTestUtil.ModelStub();
        EditTagDescriptor descriptor = new EditTagDescriptor();
        descriptor.setCategory("Engineering");

        TagEditCommand command = new TagEditCommand("backend", descriptor);
        assertThrows(CommandException.class, TagEditCommand.MESSAGE_TAG_NOT_FOUND, () -> command.execute(modelStub));
    }

    @Test
    void equals() {
        EditTagDescriptor descriptor = new EditTagDescriptor();
        descriptor.setCategory("Engineering");

        TagEditCommand editBackend = new TagEditCommand("backend", descriptor);
        TagEditCommand editBackendCopy = new TagEditCommand("backend", new EditTagDescriptor(descriptor));
        TagEditCommand editFrontend = new TagEditCommand("frontend", descriptor);

        assertTrue(editBackend.equals(editBackend));
        assertTrue(editBackend.equals(editBackendCopy));
        assertFalse(editBackend.equals(editFrontend));
        assertFalse(editBackend.equals(null));
        assertFalse(editBackend.equals(5));
    }
}
