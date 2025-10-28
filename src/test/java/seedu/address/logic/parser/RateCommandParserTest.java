package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_CANDIDATE;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.RateCommand;
import seedu.address.model.person.Rating;

public class RateCommandParserTest {
    private RateCommandParser parser = new RateCommandParser();

    @Test
    public void parse_missingRating_throwsParseException() {
        String userInput = "1 "; // index provided, rating missing
        assertParseFailure(parser, userInput,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, RateCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidIndex_throwsParseException() {
        String userInput = "0 ONE"; // invalid index
        assertParseFailure(parser, userInput,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, RateCommand.MESSAGE_USAGE));

        userInput = "abc ONE"; // non-numeric index
        assertParseFailure(parser, userInput,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, RateCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidRating_throwsParseException() {
        String userInput = INDEX_FIRST_CANDIDATE.getOneBased() + " rAndom";
        assertParseFailure(parser, userInput,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, RateCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validDisplayName_success() {
        String userInput = INDEX_FIRST_CANDIDATE.getOneBased() + " r/Good";
        RateCommand expected = new RateCommand(INDEX_FIRST_CANDIDATE, Rating.GOOD);
        assertParseSuccess(parser, userInput, expected);
    }
}
