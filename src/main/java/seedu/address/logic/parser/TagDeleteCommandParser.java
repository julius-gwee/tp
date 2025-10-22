package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG_NAME;

import seedu.address.logic.commands.TagDeleteCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new TagDeleteCommand object.
 */
public class TagDeleteCommandParser implements Parser<TagDeleteCommand> {

    @Override
    public TagDeleteCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_TAG_NAME);

        if (!argMultimap.getValue(PREFIX_TAG_NAME).isPresent()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, TagDeleteCommand.MESSAGE_USAGE));
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_TAG_NAME);

        String targetName = ParserUtil.parseTagName(argMultimap.getValue(PREFIX_TAG_NAME).get());
        return new TagDeleteCommand(targetName);
    }
}