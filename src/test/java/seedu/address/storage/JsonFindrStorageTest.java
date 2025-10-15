package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.HOON;
import static seedu.address.testutil.TypicalPersons.IDA;
import static seedu.address.testutil.TypicalPersons.getTypicalFindr;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.address.commons.exceptions.DataLoadingException;
import seedu.address.model.Findr;
import seedu.address.model.ReadOnlyFindr;

public class JsonFindrStorageTest {
    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonAddressBookStorageTest");

    @TempDir
    public Path testFolder;

    @Test
    public void readCandidateList_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> readCandidateList(null));
    }

    private java.util.Optional<ReadOnlyFindr> readCandidateList(String filePath) throws Exception {
        return new JsonFindrStorage(Paths.get(filePath)).readCandidateList(addToTestDataPathIfNotNull(filePath));
    }

    private Path addToTestDataPathIfNotNull(String prefsFileInTestDataFolder) {
        return prefsFileInTestDataFolder != null
                ? TEST_DATA_FOLDER.resolve(prefsFileInTestDataFolder)
                : null;
    }

    @Test
    public void read_missingFile_emptyResult() throws Exception {
        assertFalse(readCandidateList("NonExistentFile.json").isPresent());
    }

    @Test
    public void read_notJsonFormat_exceptionThrown() {
        assertThrows(DataLoadingException.class, () -> readCandidateList("notJsonFormatAddressBook.json"));
    }

    @Test
    public void readAddressBook_invalidPersonFindr_throwDataLoadingException() {
        assertThrows(DataLoadingException.class, () -> readCandidateList("invalidPersonAddressBook.json"));
    }

    @Test
    public void readAddressBook_invalidAndValidPersonFindr_throwDataLoadingException() {
        assertThrows(DataLoadingException.class, () -> readCandidateList("invalidAndValidPersonAddressBook.json"));
    }

    @Test
    public void readAndSaveCandidateList_allInOrder_success() throws Exception {
        Path filePath = testFolder.resolve("TempAddressBook.json");
        Findr original = getTypicalFindr();
        JsonFindrStorage jsonAddressBookStorage = new JsonFindrStorage(filePath);

        // Save in new file and read back
        jsonAddressBookStorage.saveCandidateList(original, filePath);
        ReadOnlyFindr readBack = jsonAddressBookStorage.readCandidateList(filePath).get();
        assertEquals(original, new Findr(readBack));

        // Modify data, overwrite exiting file, and read back
        original.addCandidate(HOON);
        original.removePerson(ALICE);
        jsonAddressBookStorage.saveCandidateList(original, filePath);
        readBack = jsonAddressBookStorage.readCandidateList(filePath).get();
        assertEquals(original, new Findr(readBack));

        // Save and read without specifying file path
        original.addCandidate(IDA);
        jsonAddressBookStorage.saveCandidateList(original); // file path not specified
        readBack = jsonAddressBookStorage.readCandidateList().get(); // file path not specified
        assertEquals(original, new Findr(readBack));

    }

    @Test
    public void saveAddressBook_nullCandidateList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveCandidateList(null, "SomeFile.json"));
    }

    /**
     * Saves {@code addressBook} at the specified {@code filePath}.
     */
    private void saveCandidateList(ReadOnlyFindr addressBook, String filePath) {
        try {
            new JsonFindrStorage(Paths.get(filePath))
                    .saveCandidateList(addressBook, addToTestDataPathIfNotNull(filePath));
        } catch (IOException ioe) {
            throw new AssertionError("There should not be an error writing to the file.", ioe);
        }
    }

    @Test
    public void saveCandidateList_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveCandidateList(new Findr(), null));
    }
}
