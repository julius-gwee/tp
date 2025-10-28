package seedu.address.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BENSON;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.GuiSettings;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.tag.Tag;
import seedu.address.testutil.FindrBuilder;

public class ModelManagerTest {

    private ModelManager modelManager = new ModelManager();

    @Test
    public void constructor() {
        assertEquals(new UserPrefs(), modelManager.getUserPrefs());
        assertEquals(new GuiSettings(), modelManager.getGuiSettings());
        assertEquals(new Findr(), new Findr(modelManager.getCandidateList()));
    }

    @Test
    public void setUserPrefs_nullUserPrefs_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setUserPrefs(null));
    }

    @Test
    public void setUserPrefs_validUserPrefs_copiesUserPrefs() {
        UserPrefs userPrefs = new UserPrefs();
        userPrefs.setAddressBookFilePath(Paths.get("address/book/file/path"));
        userPrefs.setGuiSettings(new GuiSettings(1, 2, 3, 4));
        modelManager.setUserPrefs(userPrefs);
        assertEquals(userPrefs, modelManager.getUserPrefs());

        // Modifying userPrefs should not modify modelManager's userPrefs
        UserPrefs oldUserPrefs = new UserPrefs(userPrefs);
        userPrefs.setAddressBookFilePath(Paths.get("new/address/book/file/path"));
        assertEquals(oldUserPrefs, modelManager.getUserPrefs());
    }

    @Test
    public void setGuiSettings_nullGuiSettings_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setGuiSettings(null));
    }

    @Test
    public void setGuiSettings_validGuiSettings_setsGuiSettings() {
        GuiSettings guiSettings = new GuiSettings(1, 2, 3, 4);
        modelManager.setGuiSettings(guiSettings);
        assertEquals(guiSettings, modelManager.getGuiSettings());
    }

    @Test
    public void setAddressBookFilePath_nullPath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setAddressBookFilePath(null));
    }

    @Test
    public void setAddressBookFilePath_validPath_setsAddressBookFilePath() {
        Path path = Paths.get("address/book/file/path");
        modelManager.setAddressBookFilePath(path);
        assertEquals(path, modelManager.getAddressBookFilePath());
    }

    @Test
    public void hasCandidate_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.hasCandidate(null));
    }

    @Test
    public void hasCandidate_personNotInAddressBook_returnsFalse() {
        assertFalse(modelManager.hasCandidate(ALICE));
    }

    @Test
    public void hasCandidate_personInAddressBook_returnsTrue() {
        modelManager.addCandidate(ALICE);
        assertTrue(modelManager.hasCandidate(ALICE));
    }

    @Test
    public void getFilteredPersonList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> modelManager.getObservableCandidateList().remove(0));
    }

    @Test
    public void getTagList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> modelManager.getTagList().remove(0));
    }

    @Test
    public void hasTag_nullTag_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.hasTag(null));
    }

    @Test
    public void hasTag_tagNotInModel_returnsFalse() {
        assertFalse(modelManager.hasTag(new Tag("backend")));
    }

    @Test
    public void hasTag_tagInModel_returnsTrue() {
        Tag tag = new Tag("backend");
        modelManager.addTag(tag);
        assertTrue(modelManager.hasTag(tag));
    }

    @Test
    public void getTag_existingTag_returnsStoredInstance() {
        Tag tag = new Tag("backend", "Engineering", "#1F75FE", "Backend specialist");
        modelManager.addTag(tag);
        assertEquals(tag, modelManager.getTag(new Tag("backend")));
    }

    @Test
    public void setTag_updatesCatalogue() {
        Tag original = new Tag("backend", "Engineering", "#1F75FE", "Backend specialist");
        Tag edited = new Tag("backend", "Infrastructure", "#00FF00", "Maintains infrastructure");
        modelManager.addTag(original);

        modelManager.setTag(original, edited);

        assertTrue(modelManager.hasTag(edited));
        assertFalse(modelManager.getTagList().stream().anyMatch(original::equals));
    }

    @Test
    public void deleteTag_removesFromCatalogue() {
        Tag tag = new Tag("backend");
        modelManager.addTag(tag);

        modelManager.deleteTag(tag);
        assertFalse(modelManager.hasTag(tag));
    }

    @Test
    public void equals() {
        Findr findr = new FindrBuilder().withPerson(ALICE).withPerson(BENSON).build();
        Findr differentFindr = new Findr();
        UserPrefs userPrefs = new UserPrefs();

        // same values -> returns true
        modelManager = new ModelManager(findr, userPrefs);
        ModelManager modelManagerCopy = new ModelManager(findr, userPrefs);
        assertTrue(modelManager.equals(modelManagerCopy));

        // same object -> returns true
        assertTrue(modelManager.equals(modelManager));

        // null -> returns false
        assertFalse(modelManager.equals(null));

        // different types -> returns false
        assertFalse(modelManager.equals(5));

        // different addressBook -> returns false
        assertFalse(modelManager.equals(new ModelManager(differentFindr, userPrefs)));

        // different filteredList -> returns false
        String[] keywords = ALICE.getName().fullName.split("\\s+");
        modelManager.updateFilteredCandidateList(new NameContainsKeywordsPredicate(Arrays.asList(keywords)));
        assertFalse(modelManager.equals(new ModelManager(findr, userPrefs)));

        // resets modelManager to initial state for upcoming tests
        modelManager.updateFilteredCandidateList(PREDICATE_SHOW_ALL_PERSONS);

        // different userPrefs -> returns false
        UserPrefs differentUserPrefs = new UserPrefs();
        differentUserPrefs.setAddressBookFilePath(Paths.get("differentFilePath"));
        assertFalse(modelManager.equals(new ModelManager(findr, differentUserPrefs)));
    }
}
