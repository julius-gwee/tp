package seedu.address.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

public class UserPrefsTest {

    @Test
    public void setGuiSettings_nullGuiSettings_throwsNullPointerException() {
        UserPrefs userPref = new UserPrefs();
        assertThrows(NullPointerException.class, () -> userPref.setGuiSettings(null));
    }

    @Test
    public void setAddressBookFilePath_nullPath_throwsNullPointerException() {
        UserPrefs userPrefs = new UserPrefs();
        assertThrows(NullPointerException.class, () -> userPrefs.setAddressBookFilePath(null));
    }

    @Test
    public void setSearchHistoryFilePath_nullPath_throwsNullPointerException() {
        UserPrefs userPrefs = new UserPrefs();
        assertThrows(NullPointerException.class, () -> userPrefs.setSearchHistoryFilePath(null));
    }

    @Test
    public void getSearchHistoryFilePath_defaultPath() {
        UserPrefs userPrefs = new UserPrefs();
        Path expectedPath = Paths.get("data", "searchhistory.json");
        assertEquals(expectedPath, userPrefs.getSearchHistoryFilePath());
    }

    @Test
    public void setSearchHistoryFilePath_validPath_success() {
        UserPrefs userPrefs = new UserPrefs();
        Path testPath = Paths.get("test", "searchhistory.json");
        userPrefs.setSearchHistoryFilePath(testPath);
        assertEquals(testPath, userPrefs.getSearchHistoryFilePath());
    }

    @Test
    public void equals_sameObject_returnsTrue() {
        UserPrefs userPrefs = new UserPrefs();
        assertTrue(userPrefs.equals(userPrefs));
    }

    @Test
    public void equals_differentSearchHistoryFilePath_returnsFalse() {
        UserPrefs userPrefs1 = new UserPrefs();
        UserPrefs userPrefs2 = new UserPrefs();
        userPrefs2.setSearchHistoryFilePath(Paths.get("different", "path.json"));
        assertFalse(userPrefs1.equals(userPrefs2));
    }

    @Test
    public void equals_sameSearchHistoryFilePath_returnsTrue() {
        UserPrefs userPrefs1 = new UserPrefs();
        UserPrefs userPrefs2 = new UserPrefs();
        assertTrue(userPrefs1.equals(userPrefs2));
    }

    @Test
    public void hashCode_sameSearchHistoryFilePath_sameHashCode() {
        UserPrefs userPrefs1 = new UserPrefs();
        UserPrefs userPrefs2 = new UserPrefs();
        assertEquals(userPrefs1.hashCode(), userPrefs2.hashCode());
    }

    @Test
    public void hashCode_differentSearchHistoryFilePath_differentHashCode() {
        UserPrefs userPrefs1 = new UserPrefs();
        UserPrefs userPrefs2 = new UserPrefs();
        userPrefs2.setSearchHistoryFilePath(Paths.get("different", "path.json"));
        assertFalse(userPrefs1.hashCode() == userPrefs2.hashCode());
    }

    @Test
    public void toString_containsSearchHistoryFilePath() {
        UserPrefs userPrefs = new UserPrefs();
        String toString = userPrefs.toString();
        assertTrue(toString.contains("Search history file location"));
        assertTrue(toString.contains("searchhistory.json"));
    }

    @Test
    public void resetData_withSearchHistoryFilePath_success() {
        UserPrefs original = new UserPrefs();
        UserPrefs newPrefs = new UserPrefs();
        newPrefs.setSearchHistoryFilePath(Paths.get("new", "path.json"));
        original.resetData(newPrefs);
        assertEquals(Paths.get("new", "path.json"), original.getSearchHistoryFilePath());
    }

}
