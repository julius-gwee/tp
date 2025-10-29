package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.ClearCommand;
import seedu.address.model.person.Stage;

public class ClearCommandParserTest {

    private ClearCommandParser parser = new ClearCommandParser();

    @Test
    public void parse_allSpecified_returnsClearCommand() {
        assertParseSuccess(parser, "all", new ClearCommand());
        assertParseSuccess(parser, "ALL", new ClearCommand());
        assertParseSuccess(parser, " all ", new ClearCommand());
    }

    @Test
    public void parse_validStage_returnsClearCommand() {
        // Valid stages
        assertParseSuccess(parser, "candidates", new ClearCommand(Stage.CANDIDATES));
        assertParseSuccess(parser, "contacted", new ClearCommand(Stage.CONTACTED));
        assertParseSuccess(parser, "interviewed", new ClearCommand(Stage.INTERVIEWED));
        assertParseSuccess(parser, "hired", new ClearCommand(Stage.HIRED));

        // Case insensitive
        assertParseSuccess(parser, "CANDIDATES", new ClearCommand(Stage.CANDIDATES));
        assertParseSuccess(parser, "Contacted", new ClearCommand(Stage.CONTACTED));

        // With whitespace
        assertParseSuccess(parser, " candidates ", new ClearCommand(Stage.CANDIDATES));
    }

    @Test
    public void parse_invalidStage_throwsParseException() {
        // Invalid stage name
        assertParseFailure(parser, "invalid",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ClearCommand.MESSAGE_USAGE));

        // Misspelled stage
        assertParseFailure(parser, "candidate",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ClearCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ClearCommand.MESSAGE_USAGE));

        assertParseFailure(parser, "  ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ClearCommand.MESSAGE_USAGE));
    }
}

