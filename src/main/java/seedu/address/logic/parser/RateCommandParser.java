package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FROM;
import static seedu.address.logic.parser.CliSyntax.PREFIX_RATE;

import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.RateCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Rating;
import seedu.address.model.person.Stage;

/**
 * Parses input arguments and creates a new {@code RateCommand} object
 */
public class RateCommandParser implements Parser<RateCommand> {
    private static final Logger logger = LogsCenter.getLogger(RateCommandParser.class);

    /**
     * Parses the given {@code String} of arguments in the context of the {@code RateCommand}
     * and returns a {@code RateCommand} object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public RateCommand parse(String args) throws ParseException {
        requireNonNull(args);
        logger.info("Parsing RateCommand with arguments: " + args);

        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_RATE, PREFIX_FROM);

        // Check if completely malformed (no prefixes at all)
        if (!argMultimap.getValue(PREFIX_RATE).isPresent()
                && !argMultimap.getValue(PREFIX_FROM).isPresent()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, RateCommand.MESSAGE_USAGE));
        }

        // Check for missing index specifically (empty preamble when prefixes exist)
        if (argMultimap.getPreamble().trim().isEmpty()) {
            logger.warning("Missing index in RateCommand input: " + args);
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, RateCommand.MESSAGE_INVALID_INDEX));
        }

        // Stage must be provided via from/
        String stageArg = argMultimap.getValue(PREFIX_FROM).orElse(null);
        if (stageArg == null || stageArg.isEmpty()) {
            logger.warning("Missing /from argument in RateCommand: " + args);
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, RateCommand.MESSAGE_MISSING_STAGE));
        }

        String rating = argMultimap.getValue(PREFIX_RATE).orElse(null);
        if (rating == null || rating.isEmpty()) {
            logger.warning("Missing /rate argument in RateCommand: " + args);
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, RateCommand.MESSAGE_MISSING_RATE));
        }

        Index index;
        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
            logger.fine("Parsed index: " + index);
        } catch (IllegalValueException ive) {
            logger.warning("Invalid index in RateCommand input: " + args);
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    RateCommand.MESSAGE_INVALID_INDEX), ive);
        }

        Stage fromStage;
        try {
            fromStage = ParserUtil.parseStage(stageArg);
            logger.fine("Parsed stage: " + fromStage);
        } catch (ParseException pe) {
            logger.warning("Invalid stage string: " + stageArg);
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    RateCommand.MESSAGE_INVALID_STAGE), pe);
        }

        Rating parsed;
        try {
            parsed = Rating.fromString(rating);
            logger.fine("Parsed rating: " + parsed);
        } catch (IllegalArgumentException e) {
            logger.warning("Invalid rating string: " + rating);
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, RateCommand.MESSAGE_INVALID_RATE));
        }

        logger.fine(String.format("Successfully parsed RateCommand: index=%s, stage=%s, rating=%s",
                index, fromStage, parsed));
        return new RateCommand(index, parsed, fromStage);
    }
}
