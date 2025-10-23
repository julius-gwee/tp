package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_RATE;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_CANDIDATE;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.RateCommand;

public class RateCommandParserTest {
    private RateCommandParser parser = new RateCommandParser();
    private final String nonEmptyRating = "ONE";

    @Test
    public void parse_indexSpecified_success() {
        // have rating
        Index targetIndex = INDEX_FIRST_CANDIDATE;
        String userInput = targetIndex.getOneBased() + " " + PREFIX_RATE + nonEmptyRating;
        RateCommand expectedCommand = new RateCommand(INDEX_FIRST_CANDIDATE, nonEmptyRating);
        assertParseSuccess(parser, userInput, expectedCommand);

        // no rating
        userInput = targetIndex.getOneBased() + " " + PREFIX_RATE;
        expectedCommand = new RateCommand(INDEX_FIRST_CANDIDATE, "");
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_missingCompulsoryField_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, RateCommand.MESSAGE_USAGE);

        // no parameters
        assertParseFailure(parser, RateCommand.COMMAND_WORD, expectedMessage);

        // no index
        assertParseFailure(parser, RateCommand.COMMAND_WORD + " " + nonEmptyRating, expectedMessage);
    }
}
