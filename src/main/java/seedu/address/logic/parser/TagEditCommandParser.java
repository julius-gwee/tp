package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NEW_TAG_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG_CATEGORY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG_COLOUR;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG_DESCRIPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG_NAME;

import seedu.address.logic.commands.TagEditCommand;
import seedu.address.logic.commands.TagEditCommand.EditTagDescriptor;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new TagEditCommand object.
 */
public class TagEditCommandParser implements Parser<TagEditCommand> {

    @Override
    public TagEditCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args,
                PREFIX_TAG_NAME, PREFIX_NEW_TAG_NAME, PREFIX_TAG_CATEGORY, PREFIX_TAG_COLOUR, PREFIX_TAG_DESCRIPTION);

        if (!argMultimap.getValue(PREFIX_TAG_NAME).isPresent()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, TagEditCommand.MESSAGE_USAGE));
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_TAG_NAME, PREFIX_NEW_TAG_NAME, PREFIX_TAG_CATEGORY,
                PREFIX_TAG_COLOUR, PREFIX_TAG_DESCRIPTION);

        String targetName = ParserUtil.parseTagName(argMultimap.getValue(PREFIX_TAG_NAME).get());

        EditTagDescriptor descriptor = new EditTagDescriptor();
        if (argMultimap.getValue(PREFIX_NEW_TAG_NAME).isPresent()) {
            descriptor.setName(ParserUtil.parseTagName(argMultimap.getValue(PREFIX_NEW_TAG_NAME).get()));
        }
        if (argMultimap.getValue(PREFIX_TAG_CATEGORY).isPresent()) {
            descriptor.setCategory(ParserUtil.parseTagCategory(argMultimap.getValue(PREFIX_TAG_CATEGORY).get()));
        }
        if (argMultimap.getValue(PREFIX_TAG_COLOUR).isPresent()) {
            descriptor.setColour(ParserUtil.parseTagColour(argMultimap.getValue(PREFIX_TAG_COLOUR).get()));
        }
        if (argMultimap.getValue(PREFIX_TAG_DESCRIPTION).isPresent()) {
            descriptor.setDescription(ParserUtil.parseTagDescription(argMultimap.getValue(PREFIX_TAG_DESCRIPTION).get()));
        }

        if (!descriptor.isAnyFieldEdited()) {
            throw new ParseException(TagEditCommand.MESSAGE_NOT_EDITED);
        }

        return new TagEditCommand(targetName, descriptor);
    }
}