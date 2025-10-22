package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import seedu.address.model.tag.Tag;

class TagListCommandTest {

    @Test
    void execute_noTags_returnsEmptyMessage() {
        TagCommandTestUtil.ModelStub modelStub = new TagCommandTestUtil.ModelStub();

        CommandResult result = new TagListCommand().execute(modelStub);
        assertEquals(TagListCommand.MESSAGE_NO_TAGS, result.getFeedbackToUser());
    }

    @Test
    void execute_tagsPresent_returnsFormattedList() {
        TagCommandTestUtil.ModelStub modelStub = new TagCommandTestUtil.ModelStub();
        Tag backend = new Tag("backend", "Engineering", "#1F75FE", "Backend specialist");
        Tag frontend = new Tag("frontend", "Product", "#FF5733", "");
        Tag design = new Tag("design", "Creative", "#ABCDEF", "UX focus");

        modelStub.addExistingTag(frontend);
        modelStub.addExistingTag(backend);
        modelStub.addExistingTag(design);

        CommandResult result = new TagListCommand().execute(modelStub);

        String formatted = String.join(System.lineSeparator(),
                "1. backend (Category: Engineering, Colour: #1F75FE, Description: Backend specialist)",
                "2. design (Category: Creative, Colour: #ABCDEF, Description: UX focus)",
                "3. frontend (Category: Product, Colour: #FF5733)");

        String expectedMessage = String.format(TagListCommand.MESSAGE_TAGS_LISTED, 3, formatted);
        assertEquals(expectedMessage, result.getFeedbackToUser());
    }
}
