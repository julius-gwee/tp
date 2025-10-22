package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Person's recruitment stage in the kanban board.
 * Guarantees: immutable; is valid as declared in {@link #isValidStage(String)}
 */
public enum Stage {
    CANDIDATES("Candidates"),
    CONTACTED("Contacted"),
    INTERVIEWED("Interviewed"),
    HIRED("Hired");

    public static final String MESSAGE_CONSTRAINTS =
            "Stage should be one of: Candidates, Contacted, Interviewed, Hired (case-insensitive)";

    private final String displayName;

    Stage(String displayName) {
        this.displayName = displayName;
    }

    /**
     * Returns the display name of the stage.
     */
    public String getDisplayName() {
        return displayName;
    }

    /**
     * Returns true if a given string is a valid stage name.
     */
    public static boolean isValidStage(String test) {
        requireNonNull(test);
        String normalized = test.trim().toUpperCase();
        try {
            Stage.valueOf(normalized);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    /**
     * Returns the Stage enum from a string (case-insensitive).
     *
     * @param stage The stage string to parse.
     * @return The corresponding Stage enum.
     * @throws IllegalArgumentException if the stage string is invalid.
     */
    public static Stage fromString(String stage) {
        requireNonNull(stage);
        checkArgument(isValidStage(stage), MESSAGE_CONSTRAINTS);
        return Stage.valueOf(stage.trim().toUpperCase());
    }

    @Override
    public String toString() {
        return displayName;
    }
}

