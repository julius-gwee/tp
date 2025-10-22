package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FROM;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TO;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_CANDIDATE;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.MoveCommand;
import seedu.address.model.person.Stage;

/**
 * As we are only doing white-box testing, our test cases do not cover path variations
 * outside of the MoveCommand code. For example, inputs "1 from/Candidates to/Contacted"
 * and "1 from/Candidates to/Contacted extra" take the same path through the MoveCommand,
 * and therefore we test only one of them.
 * The path variation for those two cases occur inside the ParserUtil, and
 * therefore should be covered by the ParserUtilTest.
 */
public class MoveCommandParserTest {

    private MoveCommandParser parser = new MoveCommandParser();

    @Test
    public void parse_validArgs_returnsMoveCommand() {
        assertParseSuccess(parser, "1 " + PREFIX_FROM + "Candidates " + PREFIX_TO + "Contacted",
                new MoveCommand(INDEX_FIRST_CANDIDATE, Stage.CANDIDATES, Stage.CONTACTED));
    }

    @Test
    public void parse_validArgsCaseInsensitive_returnsMoveCommand() {
        // lowercase
        assertParseSuccess(parser, "1 " + PREFIX_FROM + "candidates " + PREFIX_TO + "contacted",
                new MoveCommand(INDEX_FIRST_CANDIDATE, Stage.CANDIDATES, Stage.CONTACTED));

        // mixed case
        assertParseSuccess(parser, "1 " + PREFIX_FROM + "CaNdIdAtEs " + PREFIX_TO + "CoNtAcTeD",
                new MoveCommand(INDEX_FIRST_CANDIDATE, Stage.CANDIDATES, Stage.CONTACTED));
    }

    @Test
    public void parse_validArgsAllStages_returnsMoveCommand() {
        // Candidates to Interviewed
        assertParseSuccess(parser, "1 " + PREFIX_FROM + "Candidates " + PREFIX_TO + "Interviewed",
                new MoveCommand(INDEX_FIRST_CANDIDATE, Stage.CANDIDATES, Stage.INTERVIEWED));

        // Contacted to Hired
        assertParseSuccess(parser, "1 " + PREFIX_FROM + "Contacted " + PREFIX_TO + "Hired",
                new MoveCommand(INDEX_FIRST_CANDIDATE, Stage.CONTACTED, Stage.HIRED));

        // Interviewed to Hired
        assertParseSuccess(parser, "1 " + PREFIX_FROM + "Interviewed " + PREFIX_TO + "Hired",
                new MoveCommand(INDEX_FIRST_CANDIDATE, Stage.INTERVIEWED, Stage.HIRED));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        // invalid index
        assertParseFailure(parser, "a " + PREFIX_FROM + "Candidates " + PREFIX_TO + "Contacted",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, MoveCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_missingFrom_throwsParseException() {
        assertParseFailure(parser, "1 " + PREFIX_TO + "Contacted",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, MoveCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_missingTo_throwsParseException() {
        assertParseFailure(parser, "1 " + PREFIX_FROM + "Candidates",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, MoveCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_missingIndex_throwsParseException() {
        assertParseFailure(parser, PREFIX_FROM + "Candidates " + PREFIX_TO + "Contacted",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, MoveCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidStage_throwsParseException() {
        // invalid from stage
        assertParseFailure(parser, "1 " + PREFIX_FROM + "InvalidStage " + PREFIX_TO + "Contacted",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, MoveCommand.MESSAGE_USAGE));

        // invalid to stage
        assertParseFailure(parser, "1 " + PREFIX_FROM + "Candidates " + PREFIX_TO + "InvalidStage",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, MoveCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_emptyStage_throwsParseException() {
        // empty from stage
        assertParseFailure(parser, "1 " + PREFIX_FROM + " " + PREFIX_TO + "Contacted",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, MoveCommand.MESSAGE_USAGE));

        // empty to stage
        assertParseFailure(parser, "1 " + PREFIX_FROM + "Candidates " + PREFIX_TO + " ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, MoveCommand.MESSAGE_USAGE));
    }
}

