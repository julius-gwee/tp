package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG_CATEGORY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG_COLOUR;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG_DESCRIPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG_NAME;

import java.util.Optional;

import seedu.address.logic.commands.TagAddCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.tag.Tag;

/**
 * Parses input arguments and creates a new TagAddCommand object.
 */
public class TagAddCommandParser implements Parser<TagAddCommand> {

    @Override
    public TagAddCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args,
                PREFIX_TAG_NAME, PREFIX_TAG_CATEGORY, PREFIX_TAG_COLOUR, PREFIX_TAG_DESCRIPTION);

        if (!argMultimap.getValue(PREFIX_TAG_NAME).isPresent()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, TagAddCommand.MESSAGE_USAGE));
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_TAG_NAME, PREFIX_TAG_CATEGORY,
                PREFIX_TAG_COLOUR, PREFIX_TAG_DESCRIPTION);

        String name = ParserUtil.parseTagName(argMultimap.getValue(PREFIX_TAG_NAME).get());
        String category = parseOptionalCategory(argMultimap.getValue(PREFIX_TAG_CATEGORY));
        String colour = parseOptionalColour(argMultimap.getValue(PREFIX_TAG_COLOUR));
        String description = parseOptionalDescription(argMultimap.getValue(PREFIX_TAG_DESCRIPTION));

        Tag tag = new Tag(name, category, colour, description);
        return new TagAddCommand(tag);
    }

    private String parseOptionalCategory(Optional<String> category) throws ParseException {
        return category.isPresent() ? ParserUtil.parseTagCategory(category.get()) : Tag.DEFAULT_CATEGORY;
    }

    private String parseOptionalColour(Optional<String> colour) throws ParseException {
        return colour.isPresent() ? ParserUtil.parseTagColour(colour.get()) : Tag.DEFAULT_COLOUR;
    }

    private String parseOptionalDescription(Optional<String> description) throws ParseException {
        return description.isPresent() ? ParserUtil.parseTagDescription(description.get())
                : Tag.DEFAULT_DESCRIPTION;
    }
}