package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FROM;
import static seedu.address.logic.parser.CliSyntax.PREFIX_RATE;

import java.util.logging.Level;
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
        logger.log(Level.INFO, "Parsing arguments for RateCommand: {0}", args);

        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_RATE, PREFIX_FROM);

        Index index;
        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
            logger.log(Level.FINE, "Parsed index: {0}", index);
        } catch (IllegalValueException ive) {
            logger.log(Level.WARNING, "Invalid index format: {0}", args);
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, RateCommand.MESSAGE_USAGE), ive);
        }

        String rating = argMultimap.getValue(PREFIX_RATE).orElse(null);
        if (rating == null || rating.isEmpty()) {
            logger.log(Level.WARNING, "Missing rating value in command: {0}", args);
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, RateCommand.MESSAGE_USAGE));
        }

        Rating parsed;
        try {
            parsed = Rating.fromString(rating);
            logger.log(Level.FINE, "Parsed rating: {0}", parsed);
        } catch (IllegalArgumentException e) {
            logger.log(Level.WARNING, "Invalid rating string: {0}", rating);
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, RateCommand.MESSAGE_USAGE));
        }

        // Stage must be provided via from/
        String stageArg = argMultimap.getValue(PREFIX_FROM).orElse(null);
        if (stageArg == null || stageArg.isEmpty()) {
            logger.log(Level.WARNING, "Missing stage (from/) in command: {0}", args);
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, RateCommand.MESSAGE_USAGE));
        }
        Stage fromStage;
        try {
            fromStage = ParserUtil.parseStage(stageArg);
            logger.log(Level.FINE, "Parsed stage: {0}", fromStage);
        } catch (ParseException pe) {
            logger.log(Level.WARNING, "Invalid stage string: {0}", stageArg);
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, RateCommand.MESSAGE_USAGE), pe);
        }

        return new RateCommand(index, parsed, fromStage);
    }
}
