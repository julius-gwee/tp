package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;

import seedu.address.commons.exceptions.DataLoadingException;

/**
 * Represents a storage for search history.
 */
public interface SearchHistoryStorage {

    /**
     * Returns the file path of the search history data file.
     */
    Path getSearchHistoryFilePath();

    /**
     * Returns search history data from storage.
     * Returns {@code Optional.empty()} if storage file is not found.
     *
     * @throws DataLoadingException if the loading of data from search history file failed.
     */
    Optional<List<String>> readSearchHistory() throws DataLoadingException;

    /**
     * Saves the given search history to the storage.
     * @param searchHistory cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveSearchHistory(List<String> searchHistory) throws IOException;

}
