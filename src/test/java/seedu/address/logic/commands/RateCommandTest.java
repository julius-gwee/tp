package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_CANDIDATE;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_CANDIDATE;
import static seedu.address.testutil.TypicalPersons.getTypicalFindr;

import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.model.Findr;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;
import seedu.address.model.person.Rating;
import seedu.address.model.person.Stage;
import seedu.address.testutil.PersonBuilder;

/**
 * Contains integration tests (interaction with the Model) and unit tests for RateCommand.
 */
public class RateCommandTest {

    private Model model = new ModelManager(getTypicalFindr(), new UserPrefs());

    @Test
    public void execute_validIndexAndRating_success() {
        Person firstCandidate = model.getObservableCandidateList().get(INDEX_FIRST_CANDIDATE.getZeroBased());
        Person editedCandidate = new PersonBuilder(firstCandidate).withRating(Rating.fromString("EXCELLENT")).build();

        RateCommand rateCommand = new RateCommand(INDEX_FIRST_CANDIDATE,
                editedCandidate.getRating(), Stage.CANDIDATES);

        String expectedMessage = String.format(RateCommand.MESSAGE_RATE_SUCCESS,
                editedCandidate.getName(), editedCandidate.getRating());

        Model expectedModel = new ModelManager(new Findr(model.getCandidateList()), new UserPrefs());
        expectedModel.setPerson(firstCandidate, editedCandidate);

        assertCommandSuccess(rateCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_withFromStage_success() {
        List<Person> candidatesInStage = model.getObservableCandidateList().stream()
                .filter(p -> p.getStage() == Stage.CANDIDATES)
                .toList();

        assertTrue(candidatesInStage.size() >= 1, "Expected at least 1 candidate in CANDIDATES stage");

        Person candidateInCandidates = candidatesInStage.get(0);
        RateCommand cmd = new RateCommand(Index.fromOneBased(1), Rating.GOOD, Stage.CANDIDATES);

        Person edited = new PersonBuilder(candidateInCandidates).withRating(Rating.GOOD).build();
        Model expected = new ModelManager(new Findr(model.getCandidateList()), new UserPrefs());
        expected.setPerson(candidateInCandidates, edited);

        assertCommandSuccess(cmd, model,
                String.format(RateCommand.MESSAGE_RATE_SUCCESS, candidateInCandidates.getName(), Rating.GOOD),
                expected);
    }

    @Test
    public void execute_allRatingTypes_success() {
        // Test all rating values
        Rating[] ratings = {Rating.UNRATED,
                            Rating.VERY_POOR,
                            Rating.POOR,
                            Rating.AVERAGE,
                            Rating.GOOD,
                            Rating.EXCELLENT};

        for (Rating rating : ratings) {
            Model freshModel = new ModelManager(getTypicalFindr(), new UserPrefs());
            List<Person> candidatesInStage = freshModel.getObservableCandidateList().stream()
                    .filter(p -> p.getStage() == Stage.CANDIDATES)
                    .toList();

            assertTrue(candidatesInStage.size() >= 1, "Expected at least 1 candidate in CANDIDATES stage");

            Person candidate = candidatesInStage.get(0);
            RateCommand cmd = new RateCommand(Index.fromOneBased(1), rating, Stage.CANDIDATES);

            Person edited = new PersonBuilder(candidate).withRating(rating).build();
            Model expected = new ModelManager(new Findr(freshModel.getCandidateList()), new UserPrefs());
            expected.setPerson(candidate, edited);

            assertCommandSuccess(cmd, freshModel,
                    String.format(RateCommand.MESSAGE_RATE_SUCCESS, candidate.getName(), rating),
                    expected);
        }
    }

    @Test
    public void execute_ratingOverwritesExisting_success() {
        // Test that updating a rating overwrites the previous one
        List<Person> candidatesInStage = model.getObservableCandidateList().stream()
                .filter(p -> p.getStage() == Stage.CANDIDATES)
                .toList();

        Person firstCandidate = candidatesInStage.get(0);

        // First rate to GOOD
        Person candidateRatedGood = new PersonBuilder(firstCandidate).withRating(Rating.GOOD).build();
        model.setPerson(firstCandidate, candidateRatedGood);

        // Then rate to EXCELLENT
        Person candidateRatedExcellent = new PersonBuilder(candidateRatedGood)
                .withRating(Rating.EXCELLENT).build();

        RateCommand rateCommand = new RateCommand(INDEX_FIRST_CANDIDATE, Rating.EXCELLENT, Stage.CANDIDATES);

        Model expectedModel = new ModelManager(new Findr(model.getCandidateList()), new UserPrefs());
        expectedModel.setPerson(candidateRatedGood, candidateRatedExcellent);

        assertCommandSuccess(rateCommand, model,
                String.format(RateCommand.MESSAGE_RATE_SUCCESS,
                        candidateRatedExcellent.getName(), Rating.EXCELLENT),
                expectedModel);
    }

    @Test
    public void execute_otherFieldsUnchanged_success() {
        List<Person> candidatesInStage = model.getObservableCandidateList().stream()
                .filter(p -> p.getStage() == Stage.CANDIDATES)
                .toList();

        assertTrue(candidatesInStage.size() >= 1, "Expected at least 1 candidate in CANDIDATES stage");

        Person original = candidatesInStage.get(0);
        RateCommand cmd = new RateCommand(Index.fromOneBased(1), Rating.GOOD, Stage.CANDIDATES);

        try {
            cmd.execute(model);
        } catch (Exception e) {
            // Should not throw
        }

        // Find the updated person
        Person updated = model.getObservableCandidateList().stream()
                .filter(p -> p.getName().equals(original.getName()))
                .findFirst()
                .orElse(null);

        // Assert all fields except rating are unchanged
        assertEquals(original.getName(), updated.getName());
        assertEquals(original.getPhone(), updated.getPhone());
        assertEquals(original.getEmail(), updated.getEmail());
        assertEquals(original.getAddress(), updated.getAddress());
        assertEquals(original.getTags(), updated.getTags());
        assertEquals(original.getDateAdded(), updated.getDateAdded());
        assertEquals(original.getStage(), updated.getStage());
        assertEquals(Rating.GOOD, updated.getRating());
    }

    @Test
    public void execute_invalidIndexForStage_failure() {
        RateCommand cmd = new RateCommand(Index.fromOneBased(999), Rating.GOOD, Stage.CANDIDATES);
        assertCommandFailure(cmd, model,
                String.format(RateCommand.MESSAGE_INVALID_INDEX_FOR_STAGE, Stage.CANDIDATES));
    }

    @Test
    public void execute_emptyStage_failure() {
        // Typical persons HIRED should be empty
        Stage emptyStage = Stage.HIRED;
        RateCommand cmd = new RateCommand(Index.fromOneBased(1), Rating.GOOD, emptyStage);
        assertCommandFailure(cmd, model, String.format(RateCommand.MESSAGE_INVALID_INDEX_FOR_STAGE, emptyStage));
    }

    @Test
    public void execute_indexExceedsStageListSize_failure() {
        List<Person> candidatesInStage = model.getObservableCandidateList().stream()
                .filter(p -> p.getStage() == Stage.CANDIDATES)
                .toList();

        int invalidIndex = candidatesInStage.size() + 5;
        RateCommand cmd = new RateCommand(Index.fromZeroBased(invalidIndex), Rating.GOOD, Stage.CANDIDATES);

        assertCommandFailure(cmd, model,
                String.format(RateCommand.MESSAGE_INVALID_INDEX_FOR_STAGE, Stage.CANDIDATES));
    }

    @Test
    public void execute_boundaryIndexLastInStage_success() {
        List<Person> candidatesInStage = model.getObservableCandidateList().stream()
                .filter(p -> p.getStage() == Stage.CANDIDATES)
                .toList();

        assertTrue(candidatesInStage.size() >= 1, "Expected at least 1 candidate in CANDIDATES stage");

        int lastIndex = candidatesInStage.size();
        Person lastCandidate = candidatesInStage.get(lastIndex - 1);
        RateCommand cmd = new RateCommand(Index.fromOneBased(lastIndex), Rating.AVERAGE, Stage.CANDIDATES);

        Person edited = new PersonBuilder(lastCandidate).withRating(Rating.AVERAGE).build();
        Model expected = new ModelManager(new Findr(model.getCandidateList()), new UserPrefs());
        expected.setPerson(lastCandidate, edited);

        assertCommandSuccess(cmd, model,
                String.format(RateCommand.MESSAGE_RATE_SUCCESS, lastCandidate.getName(), Rating.AVERAGE),
                expected);
    }

    @Test
    public void execute_differentStages_success() {
        // Create a model with candidates in different stages
        Model testModel = new ModelManager(new Findr(), new UserPrefs());

        // Add candidates to different stages
        Person candidateInCandidates = new PersonBuilder().withName("Alice")
                .withStage(Stage.CANDIDATES).build();
        Person candidateInContacted = new PersonBuilder().withName("Bob")
                .withStage(Stage.CONTACTED).build();
        Person candidateInInterviewed = new PersonBuilder().withName("Charlie")
                .withStage(Stage.INTERVIEWED).build();
        Person candidateInHired = new PersonBuilder().withName("Diana")
                .withStage(Stage.HIRED).build();

        testModel.addCandidate(candidateInCandidates);
        testModel.addCandidate(candidateInContacted);
        testModel.addCandidate(candidateInInterviewed);
        testModel.addCandidate(candidateInHired);

        Stage[] stages = {Stage.CANDIDATES, Stage.CONTACTED, Stage.INTERVIEWED, Stage.HIRED};

        for (Stage stage : stages) {
            Model freshModel = new ModelManager(new Findr(testModel.getCandidateList()), new UserPrefs());
            List<Person> candidatesInStage = freshModel.getObservableCandidateList().stream()
                    .filter(p -> p.getStage() == stage)
                    .toList();

            if (candidatesInStage.isEmpty()) {
                continue;
            }

            Person candidate = candidatesInStage.get(0);
            RateCommand cmd = new RateCommand(Index.fromOneBased(1), Rating.EXCELLENT, stage);

            Person edited = new PersonBuilder(candidate).withRating(Rating.EXCELLENT).build();
            Model expected = new ModelManager(new Findr(freshModel.getCandidateList()), new UserPrefs());
            expected.setPerson(candidate, edited);

            assertCommandSuccess(cmd, freshModel,
                    String.format(RateCommand.MESSAGE_RATE_SUCCESS, candidate.getName(), Rating.EXCELLENT),
                    expected);
        }
    }

    @Test
    public void equals() {
        final RateCommand standardCommand = new RateCommand(INDEX_FIRST_CANDIDATE, Rating.EXCELLENT, Stage.CANDIDATES);

        // same values -> returns true
        RateCommand commandWithSameValues = new RateCommand(INDEX_FIRST_CANDIDATE, Rating.EXCELLENT, Stage.CANDIDATES);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand
                .equals(new RateCommand(INDEX_SECOND_CANDIDATE, Rating.EXCELLENT, Stage.CANDIDATES)));

        // different rating -> returns false
        assertFalse(standardCommand.equals(new RateCommand(INDEX_FIRST_CANDIDATE, Rating.GOOD, Stage.CANDIDATES)));

        // different stage -> returns false
        assertFalse(standardCommand.equals(new RateCommand(INDEX_FIRST_CANDIDATE, Rating.EXCELLENT, Stage.CONTACTED)));
    }
}
