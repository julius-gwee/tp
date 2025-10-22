package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;

import seedu.address.commons.exceptions.DataLoadingException;
import seedu.address.commons.util.JsonUtil;

/**
 * A class to access search history stored in the hard disk as a json file
 */
public class JsonSearchHistoryStorage implements SearchHistoryStorage {

    private Path filePath;

    public JsonSearchHistoryStorage(Path filePath) {
        this.filePath = filePath;
    }

    @Override
    public Path getSearchHistoryFilePath() {
        return filePath;
    }

    @Override
    public Optional<List<String>> readSearchHistory() throws DataLoadingException {
        return readSearchHistory(filePath);
    }

    /**
     * Similar to {@link #readSearchHistory()}
     * @param searchHistoryFilePath location of the data. Cannot be null.
     * @throws DataLoadingException if the file format is not as expected.
     */
    @SuppressWarnings("unchecked")
    public Optional<List<String>> readSearchHistory(Path searchHistoryFilePath) throws DataLoadingException {
        return JsonUtil.readJsonFile(searchHistoryFilePath, (Class<List<String>>) (Class<?>) List.class);
    }

    @Override
    public void saveSearchHistory(List<String> searchHistory) throws IOException {
        JsonUtil.saveJsonFile(searchHistory, filePath);
    }

}
