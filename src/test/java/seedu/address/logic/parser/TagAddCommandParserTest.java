package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG_CATEGORY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG_COLOUR;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG_DESCRIPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG_NAME;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.TagAddCommand;
import seedu.address.model.tag.Tag;

class TagAddCommandParserTest {

    private static final String VALID_NAME = "backend";
    private static final String VALID_CATEGORY = "Engineering";
    private static final String VALID_COLOUR = "#1F75FE";
    private static final String VALID_DESCRIPTION = "Backend specialist";

    private static final String NAME_DESC = " " + PREFIX_TAG_NAME + VALID_NAME;
    private static final String CATEGORY_DESC = " " + PREFIX_TAG_CATEGORY + VALID_CATEGORY;
    private static final String COLOUR_DESC = " " + PREFIX_TAG_COLOUR + VALID_COLOUR;
    private static final String DESCRIPTION_DESC = " " + PREFIX_TAG_DESCRIPTION + VALID_DESCRIPTION;

    private final TagAddCommandParser parser = new TagAddCommandParser();

    @Test
    void parse_allFieldsPresent_success() {
        Tag tag = new Tag(VALID_NAME, VALID_CATEGORY, VALID_COLOUR, VALID_DESCRIPTION);
        assertParseSuccess(parser, NAME_DESC + CATEGORY_DESC + COLOUR_DESC + DESCRIPTION_DESC,
                new TagAddCommand(tag));
    }

    @Test
    void parse_optionalFieldsMissing_usesDefaults() {
        Tag tag = new Tag(VALID_NAME);
        assertParseSuccess(parser, NAME_DESC, new TagAddCommand(tag));
    }

    @Test
    void parse_duplicatePrefixes_failure() {
        String duplicateNameInput = NAME_DESC + NAME_DESC;
        String expectedMessage = seedu.address.logic.Messages
                .getErrorMessageForDuplicatePrefixes(PREFIX_TAG_NAME);
        assertParseFailure(parser, duplicateNameInput, expectedMessage);
    }

    @Test
    void parse_missingName_failure() {
        assertParseFailure(parser, CATEGORY_DESC, String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                TagAddCommand.MESSAGE_USAGE));
    }

    @Test
    void parse_invalidValues_failure() {
        assertParseFailure(parser, " " + PREFIX_TAG_NAME, Tag.MESSAGE_NAME_EMPTY);
        assertParseFailure(parser, " " + PREFIX_TAG_NAME + "invalid name", Tag.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, NAME_DESC + " " + PREFIX_TAG_CATEGORY, Tag.MESSAGE_CATEGORY_EMPTY);
        assertParseFailure(parser, NAME_DESC + " " + PREFIX_TAG_CATEGORY + "!invalid",
                Tag.MESSAGE_CATEGORY_CONSTRAINTS);
        assertParseFailure(parser, NAME_DESC + " " + PREFIX_TAG_COLOUR, Tag.MESSAGE_COLOUR_EMPTY);
        assertParseFailure(parser, NAME_DESC + " " + PREFIX_TAG_COLOUR + "123456",
                Tag.MESSAGE_COLOUR_CONSTRAINTS);
        assertParseFailure(parser, NAME_DESC + " " + PREFIX_TAG_DESCRIPTION, Tag.MESSAGE_DESCRIPTION_EMPTY);
        assertParseFailure(parser, NAME_DESC + " " + PREFIX_TAG_DESCRIPTION
                + "a".repeat(Tag.DESCRIPTION_MAX_LENGTH + 1), Tag.MESSAGE_DESCRIPTION_CONSTRAINTS);
    }
}
