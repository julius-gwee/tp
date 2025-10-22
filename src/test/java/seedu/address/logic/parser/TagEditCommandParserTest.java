package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_DUPLICATE_FIELDS;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NEW_TAG_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG_CATEGORY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG_COLOUR;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG_DESCRIPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG_NAME;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.TagEditCommand;
import seedu.address.logic.commands.TagEditCommand.EditTagDescriptor;
import seedu.address.model.tag.Tag;

class TagEditCommandParserTest {

    private static final String VALID_NAME = "backend";
    private static final String VALID_NEW_NAME = "infrastructure";
    private static final String VALID_CATEGORY = "Engineering";
    private static final String VALID_COLOUR = "#1F75FE";
    private static final String VALID_DESCRIPTION = "Backend specialist";

    private static final String NAME_DESC = " " + PREFIX_TAG_NAME + VALID_NAME;
    private static final String NEW_NAME_DESC = " " + PREFIX_NEW_TAG_NAME + VALID_NEW_NAME;
    private static final String CATEGORY_DESC = " " + PREFIX_TAG_CATEGORY + VALID_CATEGORY;
    private static final String COLOUR_DESC = " " + PREFIX_TAG_COLOUR + VALID_COLOUR;
    private static final String DESCRIPTION_DESC = " " + PREFIX_TAG_DESCRIPTION + VALID_DESCRIPTION;

    private final TagEditCommandParser parser = new TagEditCommandParser();

    @Test
    void parse_allFieldsPresent_success() {
        EditTagDescriptor descriptor = new EditTagDescriptor();
        descriptor.setName(VALID_NEW_NAME);
        descriptor.setCategory(VALID_CATEGORY);
        descriptor.setColour(VALID_COLOUR);
        descriptor.setDescription(VALID_DESCRIPTION);

        assertParseSuccess(parser, NAME_DESC + NEW_NAME_DESC + CATEGORY_DESC + COLOUR_DESC + DESCRIPTION_DESC,
                new TagEditCommand(VALID_NAME, descriptor));
    }

    @Test
    void parse_subsetFields_success() {
        EditTagDescriptor descriptor = new EditTagDescriptor();
        descriptor.setCategory(VALID_CATEGORY);
        descriptor.setColour(VALID_COLOUR);

        assertParseSuccess(parser, NAME_DESC + CATEGORY_DESC + COLOUR_DESC,
                new TagEditCommand(VALID_NAME, descriptor));
    }

    @Test
    void parse_missingName_failure() {
        assertParseFailure(parser, CATEGORY_DESC,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, TagEditCommand.MESSAGE_USAGE));
    }

    @Test
    void parse_noFieldsProvided_failure() {
        assertParseFailure(parser, NAME_DESC, TagEditCommand.MESSAGE_NOT_EDITED);
    }

    @Test
    void parse_duplicatePrefixes_failure() {
        String expectedMessage = MESSAGE_DUPLICATE_FIELDS + PREFIX_TAG_NAME.getPrefix();
        assertParseFailure(parser,
                NAME_DESC + NAME_DESC + NEW_NAME_DESC + CATEGORY_DESC + COLOUR_DESC + DESCRIPTION_DESC,
                expectedMessage);
    }

    @Test
    void parse_invalidValues_failure() {
        assertParseFailure(parser, NAME_DESC + " " + PREFIX_NEW_TAG_NAME + "invalid name",
                Tag.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, NAME_DESC + CATEGORY_DESC + " " + PREFIX_TAG_COLOUR + "123456",
                Tag.MESSAGE_COLOUR_CONSTRAINTS);
        assertParseFailure(parser, NAME_DESC + CATEGORY_DESC + COLOUR_DESC + " " + PREFIX_TAG_DESCRIPTION
                + "a".repeat(Tag.DESCRIPTION_MAX_LENGTH + 1), Tag.MESSAGE_DESCRIPTION_CONSTRAINTS);
    }
}
