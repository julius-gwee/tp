package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG_NAME;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.TagDeleteCommand;
import seedu.address.model.tag.Tag;

class TagDeleteCommandParserTest {

    private static final String VALID_NAME = "backend";
    private static final String NAME_DESC = " " + PREFIX_TAG_NAME + VALID_NAME;

    private final TagDeleteCommandParser parser = new TagDeleteCommandParser();

    @Test
    void parse_validValue_success() {
        assertParseSuccess(parser, NAME_DESC, new TagDeleteCommand(VALID_NAME));
    }

    @Test
    void parse_missingPrefix_failure() {
        assertParseFailure(parser, VALID_NAME, String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                TagDeleteCommand.MESSAGE_USAGE));
    }

    @Test
    void parse_duplicatePrefix_failure() {
        String expectedMessage = seedu.address.logic.Messages.getErrorMessageForDuplicatePrefixes(PREFIX_TAG_NAME);
        assertParseFailure(parser, NAME_DESC + NAME_DESC, expectedMessage);
    }

    @Test
    void parse_invalidName_failure() {
        assertParseFailure(parser, " " + PREFIX_TAG_NAME + "invalid name", Tag.MESSAGE_CONSTRAINTS);
    }
}
