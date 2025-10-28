package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_CANDIDATE;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.DeleteCommand;
import seedu.address.model.person.Stage;

/**
 * As we are only doing white-box testing, our test cases do not cover path variations
 * outside of the DeleteCommand code. For example, inputs "1" and "1 abc" take the
 * same path through the DeleteCommand, and therefore we test only one of them.
 * The path variation for those two cases occur inside the ParserUtil, and
 * therefore should be covered by the ParserUtilTest.
 */
public class DeleteCommandParserTest {

    private DeleteCommandParser parser = new DeleteCommandParser();

    @Test
    public void parse_validArgs_returnsDeleteCommand() {
        assertParseSuccess(parser, "1", new DeleteCommand(INDEX_FIRST_CANDIDATE));
    }

    @Test
    public void parse_validArgsWithStage_returnsDeleteCommand() {
        assertParseSuccess(parser, "1 from/Candidates",
                new DeleteCommand(INDEX_FIRST_CANDIDATE, Stage.CANDIDATES));
        assertParseSuccess(parser, "1 from/Contacted",
                new DeleteCommand(INDEX_FIRST_CANDIDATE, Stage.CONTACTED));
        assertParseSuccess(parser, "1 from/Interviewed",
                new DeleteCommand(INDEX_FIRST_CANDIDATE, Stage.INTERVIEWED));
        assertParseSuccess(parser, "1 from/Hired",
                new DeleteCommand(INDEX_FIRST_CANDIDATE, Stage.HIRED));
    }

    @Test
    public void parse_validArgsWithStageCaseInsensitive_returnsDeleteCommand() {
        assertParseSuccess(parser, "1 from/candidates",
                new DeleteCommand(INDEX_FIRST_CANDIDATE, Stage.CANDIDATES));
        assertParseSuccess(parser, "1 from/CONTACTED",
                new DeleteCommand(INDEX_FIRST_CANDIDATE, Stage.CONTACTED));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "a", String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidStage_throwsParseException() {
        assertParseFailure(parser, "1 from/InvalidStage",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_emptyStage_throwsParseException() {
        assertParseFailure(parser, "1 from/",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));
    }
}
