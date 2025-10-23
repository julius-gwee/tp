package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;

import seedu.address.commons.exceptions.DataLoadingException;
import seedu.address.model.ReadOnlyFindr;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.UserPrefs;

/**
 * API of the Storage component
 */
public interface Storage extends FindrStorage, UserPrefsStorage, SearchHistoryStorage {

    @Override
    Optional<UserPrefs> readUserPrefs() throws DataLoadingException;

    @Override
    void saveUserPrefs(ReadOnlyUserPrefs userPrefs) throws IOException;

    @Override
    Path getFindrFilePath();

    @Override
    Optional<ReadOnlyFindr> readCandidateList() throws DataLoadingException;

    @Override
    void saveCandidateList(ReadOnlyFindr candidateList) throws IOException;

    @Override
    Path getSearchHistoryFilePath();

    @Override
    Optional<List<String>> readSearchHistory() throws DataLoadingException;

    @Override
    void saveSearchHistory(List<String> searchHistory) throws IOException;

}
