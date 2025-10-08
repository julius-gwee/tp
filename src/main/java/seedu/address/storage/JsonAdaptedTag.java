package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.tag.Tag;

/**
 * Jackson-friendly version of {@link Tag}.
 */
class JsonAdaptedTag {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Tag's %s field is missing!";

    private final String tagName;
    private final String category;
    private final String colour;
    private final String description;

    /**
     * Constructs a {@code JsonAdaptedTag} with the given {@code tagName}.
     */
    @JsonCreator
    public JsonAdaptedTag(@JsonProperty("tagName") String tagName,
                          @JsonProperty("category") String category,
                          @JsonProperty("colour") String colour,
                          @JsonProperty("description") String description) {
        this.tagName = tagName;
        this.category = category;
        this.colour = colour;
        this.description = description;
    }

    public JsonAdaptedTag(String tagName) {
        this(tagName, null, null, null);
    }

    /**
     * Converts a given {@code Tag} into this class for Jackson use.
     */
    public JsonAdaptedTag(Tag source) {
        tagName = source.tagName;
        category = source.category;
        colour = source.colour;
        description = source.description;
    }

    @JsonProperty("tagName")
    public String getTagName() {
        return tagName;
    }

    @JsonProperty("category")
    public String getCategory() {
        return category;
    }

    @JsonProperty("colour")
    public String getColour() {
        return colour;
    }

    @JsonProperty("description")
    public String getDescription() {
        return description;
    }

    /**
     * Converts this Jackson-friendly adapted tag object into the model's {@code Tag} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted tag.
     */
    public Tag toModelType() throws IllegalValueException {
        if (tagName == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "name"));
        }
        if (!Tag.isValidTagName(tagName)) {
            throw new IllegalValueException(Tag.MESSAGE_CONSTRAINTS);
        }

        final String modelCategory = category == null ? Tag.DEFAULT_CATEGORY : category;
        if (!Tag.isValidCategory(modelCategory)) {
            throw new IllegalValueException(Tag.MESSAGE_CATEGORY_CONSTRAINTS);
        }

        final String modelColour = colour == null ? Tag.DEFAULT_COLOUR : colour;
        if (!Tag.isValidColour(modelColour)) {
            throw new IllegalValueException(Tag.MESSAGE_COLOUR_CONSTRAINTS);
        }

        final String modelDescription = description == null ? Tag.DEFAULT_DESCRIPTION : description;
        if (!Tag.isValidDescription(modelDescription)) {
            throw new IllegalValueException(Tag.MESSAGE_DESCRIPTION_CONSTRAINTS);
        }

        return new Tag(tagName, modelCategory, modelColour, modelDescription);
    }
}
