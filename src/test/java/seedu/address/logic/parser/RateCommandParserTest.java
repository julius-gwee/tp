package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_CANDIDATE;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.RateCommand;
import seedu.address.model.person.Rating;
import seedu.address.model.person.Stage;

public class RateCommandParserTest {
    private RateCommandParser parser = new RateCommandParser();

    @Test
    public void parse_missingRating_throwsParseException() {
        String userInput = "1 f/Candidates"; // stage provided, rating missing
        assertParseFailure(parser, userInput,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, RateCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidIndex_throwsParseException() {
        String userInput = "0 f/Candidates r/Good"; // invalid index
        assertParseFailure(parser, userInput,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, RateCommand.MESSAGE_USAGE));

        userInput = "abc f/Candidates r/Good"; // non-numeric index
        assertParseFailure(parser, userInput,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, RateCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidRating_throwsParseException() {
        String userInput = INDEX_FIRST_CANDIDATE.getOneBased() + " f/Candidates rAndom";
        assertParseFailure(parser, userInput,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, RateCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_missingStage_throwsParseException() {
        String userInput = INDEX_FIRST_CANDIDATE.getOneBased() + " r/Good";
        assertParseFailure(parser, userInput,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, RateCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidStage_throwsParseException() {
        String userInput = INDEX_FIRST_CANDIDATE.getOneBased() + " from/NotAStage r/Good";
        assertParseFailure(parser, userInput,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, RateCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validDisplayName_success() {
        String userInput = INDEX_FIRST_CANDIDATE.getOneBased() + " f/Candidates r/Good";
        RateCommand expected = new RateCommand(INDEX_FIRST_CANDIDATE, Rating.GOOD, Stage.CANDIDATES);
        assertParseSuccess(parser, userInput, expected);
    }

    @Test
    public void parse_withShortFromPrefix_success() {
        String userInput = INDEX_FIRST_CANDIDATE.getOneBased() + " f/Candidates r/Excellent";
        RateCommand expected = new RateCommand(INDEX_FIRST_CANDIDATE, Rating.EXCELLENT, Stage.CANDIDATES);
        assertParseSuccess(parser, userInput, expected);
    }

    @Test
    public void parse_withFromLongPrefix_success() {
        String userInput = INDEX_FIRST_CANDIDATE.getOneBased() + " from/Interviewed r/Poor";
        RateCommand expected = new RateCommand(INDEX_FIRST_CANDIDATE, Rating.POOR, Stage.INTERVIEWED);
        assertParseSuccess(parser, userInput, expected);
    }
}
