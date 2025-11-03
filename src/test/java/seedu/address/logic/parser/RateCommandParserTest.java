package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FROM;
import static seedu.address.logic.parser.CliSyntax.PREFIX_RATE;
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
    public void parse_missingIndex_throwsParseException() {
        String userInput = " " + PREFIX_FROM + "Candidates " + PREFIX_RATE + "Good";
        assertParseFailure(parser, userInput,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, RateCommand.MESSAGE_MISSING_INDEX));
    }

    @Test
    public void parse_missingRating_throwsParseException() {
        String userInput = INDEX_FIRST_CANDIDATE.getOneBased() + " "
                + PREFIX_FROM + "Candidates"; // stage provided, rating missing
        assertParseFailure(parser, userInput,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, RateCommand.MESSAGE_MISSING_RATE));
    }

    @Test
    public void parse_emptyRating_throwsParseException() {
        String userInput = INDEX_FIRST_CANDIDATE.getOneBased() + " " + PREFIX_FROM + "Candidates " + PREFIX_RATE;
        assertParseFailure(parser, userInput,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, RateCommand.MESSAGE_MISSING_RATE));
    }

    @Test
    public void parse_invalidIndexZero_throwsParseException() {
        String userInput = "0 " + PREFIX_FROM + "Candidates " + PREFIX_RATE + "Good";
        assertParseFailure(parser, userInput,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, RateCommand.MESSAGE_INVALID_INDEX));
    }

    @Test
    public void parse_invalidIndexNegative_throwsParseException() {
        String userInput = "-1 " + PREFIX_FROM + "Candidates " + PREFIX_RATE + "Good";
        assertParseFailure(parser, userInput,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, RateCommand.MESSAGE_INVALID_INDEX));
    }

    @Test
    public void parse_invalidIndexNonNumeric_throwsParseException() {
        String userInput = "abc " + PREFIX_FROM + "Candidates " + PREFIX_RATE + "Good";
        assertParseFailure(parser, userInput,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, RateCommand.MESSAGE_INVALID_INDEX));
    }

    @Test
    public void parse_invalidRating_throwsParseException() {
        String userInput = INDEX_FIRST_CANDIDATE.getOneBased() + " " + PREFIX_FROM + "Candidates "
                + PREFIX_RATE + "InvalidRating";
        assertParseFailure(parser, userInput,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, RateCommand.MESSAGE_INVALID_RATE));
    }

    @Test
    public void parse_missingStage_throwsParseException() {
        String userInput = INDEX_FIRST_CANDIDATE.getOneBased() + " " + PREFIX_RATE + "Good";
        assertParseFailure(parser, userInput,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, RateCommand.MESSAGE_MISSING_STAGE));
    }

    @Test
    public void parse_emptyStage_throwsParseException() {
        String userInput = INDEX_FIRST_CANDIDATE.getOneBased() + " " + PREFIX_FROM + " " + PREFIX_RATE + "Good";
        assertParseFailure(parser, userInput,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, RateCommand.MESSAGE_MISSING_STAGE));
    }

    @Test
    public void parse_invalidStage_throwsParseException() {
        String userInput = INDEX_FIRST_CANDIDATE.getOneBased() + " " + PREFIX_FROM
                + "NotAStage " + PREFIX_RATE + "Good";
        assertParseFailure(parser, userInput,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, RateCommand.MESSAGE_INVALID_STAGE));
    }

    @Test
    public void parse_missingAllPrefixes_throwsParseException() {
        String userInput = INDEX_FIRST_CANDIDATE.getOneBased() + "";
        assertParseFailure(parser, userInput,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, RateCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_allFieldsPresent_success() {
        String userInput = INDEX_FIRST_CANDIDATE.getOneBased() + " " + PREFIX_FROM + "Candidates "
                + PREFIX_RATE + "Good";
        RateCommand expected = new RateCommand(INDEX_FIRST_CANDIDATE, Rating.GOOD, Stage.CANDIDATES);
        assertParseSuccess(parser, userInput, expected);
    }

    @Test
    public void parse_allFieldsPresentDifferentStage_success() {
        String userInput = INDEX_FIRST_CANDIDATE.getOneBased() + " " + PREFIX_FROM + "Interviewed "
                + PREFIX_RATE + "Poor";
        RateCommand expected = new RateCommand(INDEX_FIRST_CANDIDATE, Rating.POOR, Stage.INTERVIEWED);
        assertParseSuccess(parser, userInput, expected);
    }

    @Test
    public void parse_allRatingValues_success() {
        // Test all valid rating values
        Rating[] ratings = {Rating.UNRATED, Rating.VERY_POOR, Rating.POOR,
                            Rating.AVERAGE, Rating.GOOD, Rating.EXCELLENT};
        String[] ratingStrings = {"Unrated", "Very Poor", "Poor", "Average", "Good", "Excellent"};

        for (int i = 0; i < ratings.length; i++) {
            String userInput = INDEX_FIRST_CANDIDATE.getOneBased() + " " + PREFIX_FROM + "Candidates "
                    + PREFIX_RATE + ratingStrings[i];
            RateCommand expected = new RateCommand(INDEX_FIRST_CANDIDATE, ratings[i], Stage.CANDIDATES);
            assertParseSuccess(parser, userInput, expected);
        }
    }

    @Test
    public void parse_allStageValues_success() {
        // Test all valid stage values
        Stage[] stages = {Stage.CANDIDATES, Stage.CONTACTED, Stage.INTERVIEWED, Stage.HIRED};
        String[] stageStrings = {"Candidates", "Contacted", "Interviewed", "Hired"};

        for (int i = 0; i < stages.length; i++) {
            String userInput = INDEX_FIRST_CANDIDATE.getOneBased() + " " + PREFIX_FROM + stageStrings[i] + " "
                    + PREFIX_RATE + "Good";
            RateCommand expected = new RateCommand(INDEX_FIRST_CANDIDATE, Rating.GOOD, stages[i]);
            assertParseSuccess(parser, userInput, expected);
        }
    }

    @Test
    public void parse_caseInsensitiveRating_success() {
        // Test case insensitivity for ratings
        String[] ratingVariants = {"EXCELLENT", "excellent", "ExCeLlEnT", "Excellent"};

        for (String rating : ratingVariants) {
            String userInput = INDEX_FIRST_CANDIDATE.getOneBased() + " " + PREFIX_FROM + "Candidates "
                    + PREFIX_RATE + rating;
            RateCommand expected = new RateCommand(INDEX_FIRST_CANDIDATE, Rating.EXCELLENT, Stage.CANDIDATES);
            assertParseSuccess(parser, userInput, expected);
        }
    }

    @Test
    public void parse_caseInsensitiveStage_success() {
        // Test case insensitivity for stages
        String[] stageVariants = {"CANDIDATES", "candidates", "CaNdIdAtEs", "Candidates"};

        for (String stage : stageVariants) {
            String userInput = INDEX_FIRST_CANDIDATE.getOneBased() + " " + PREFIX_FROM + stage + " "
                    + PREFIX_RATE + "Good";
            RateCommand expected = new RateCommand(INDEX_FIRST_CANDIDATE, Rating.GOOD, Stage.CANDIDATES);
            assertParseSuccess(parser, userInput, expected);
        }
    }

    @Test
    public void parse_extraWhitespace_success() {
        // Test with extra whitespace
        String userInput = "  " + INDEX_FIRST_CANDIDATE.getOneBased() + "   " + PREFIX_FROM + "  Candidates  "
                + PREFIX_RATE + "  Good  ";
        RateCommand expected = new RateCommand(INDEX_FIRST_CANDIDATE, Rating.GOOD, Stage.CANDIDATES);
        assertParseSuccess(parser, userInput, expected);
    }

    @Test
    public void parse_duplicateRatingPrefix_success() {
        String userInput = INDEX_FIRST_CANDIDATE.getOneBased() + " " + PREFIX_FROM + "Candidates "
                + PREFIX_RATE + "Poor " + PREFIX_RATE + "Excellent";
        assertParseFailure(parser, userInput,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, RateCommand.MESSAGE_DUPLICATE_RATE));
    }

    @Test
    public void parse_duplicateStagePrefix_success() {
        String userInput = INDEX_FIRST_CANDIDATE.getOneBased() + " " + PREFIX_FROM + "Interviewed "
                + PREFIX_FROM + "Candidates " + PREFIX_RATE + "Good";
        assertParseFailure(parser, userInput,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, RateCommand.MESSAGE_DUPLICATE_STAGE));
    }
}
