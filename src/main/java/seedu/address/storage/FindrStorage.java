package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.address.commons.exceptions.DataLoadingException;
import seedu.address.model.Findr;
import seedu.address.model.ReadOnlyFindr;

/**
 * Represents a storage for {@link Findr}.
 */
public interface FindrStorage {

    /**
     * Returns the file path of the data file.
     */
    Path getFindrFilePath();

    /**
     * Returns Candidate List data as a {@link ReadOnlyFindr}.
     * Returns {@code Optional.empty()} if storage file is not found.
     *
     * @throws DataLoadingException if loading the data from storage failed.
     */
    Optional<ReadOnlyFindr> readCandidateList() throws DataLoadingException;

    /**
     * @see #getFindrFilePath()
     */
    Optional<ReadOnlyFindr> readCandidateList(Path filePath) throws DataLoadingException;

    /**
     * Saves the given {@link ReadOnlyFindr} to the storage.
     * @param candidateList cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveCandidateList(ReadOnlyFindr candidateList) throws IOException;

    /**
     * @see #saveCandidateList(ReadOnlyFindr)
     */
    void saveCandidateList(ReadOnlyFindr candidateList, Path filePath) throws IOException;

}
