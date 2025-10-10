package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.DataLoadingException;
import seedu.address.model.ReadOnlyFindr;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.UserPrefs;

/**
 * Manages storage of Candidate List data in local storage.
 */
public class StorageManager implements Storage {

    private static final Logger logger = LogsCenter.getLogger(StorageManager.class);
    private FindrStorage findrStorage;
    private UserPrefsStorage userPrefsStorage;

    /**
     * Creates a {@code StorageManager} with the given {@code FindrStorage} and {@code UserPrefStorage}.
     */
    public StorageManager(FindrStorage findrStorage, UserPrefsStorage userPrefsStorage) {
        this.findrStorage = findrStorage;
        this.userPrefsStorage = userPrefsStorage;
    }

    // ================ UserPrefs methods ==============================

    @Override
    public Path getUserPrefsFilePath() {
        return userPrefsStorage.getUserPrefsFilePath();
    }

    @Override
    public Optional<UserPrefs> readUserPrefs() throws DataLoadingException {
        return userPrefsStorage.readUserPrefs();
    }

    @Override
    public void saveUserPrefs(ReadOnlyUserPrefs userPrefs) throws IOException {
        userPrefsStorage.saveUserPrefs(userPrefs);
    }


    // ================ AddressBook methods ==============================

    @Override
    public Path getFindrFilePath() {
        return findrStorage.getFindrFilePath();
    }

    @Override
    public Optional<ReadOnlyFindr> readCandidateList() throws DataLoadingException {
        return readCandidateList(findrStorage.getFindrFilePath());
    }

    @Override
    public Optional<ReadOnlyFindr> readCandidateList(Path filePath) throws DataLoadingException {
        logger.fine("Attempting to read data from file: " + filePath);
        return findrStorage.readCandidateList(filePath);
    }

    @Override
    public void saveCandidateList(ReadOnlyFindr candidateList) throws IOException {
        saveCandidateList(candidateList, findrStorage.getFindrFilePath());
    }

    @Override
    public void saveCandidateList(ReadOnlyFindr candidateList, Path filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        findrStorage.saveCandidateList(candidateList, filePath);
    }

}
