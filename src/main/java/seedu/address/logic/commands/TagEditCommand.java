package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.Objects;
import java.util.Optional;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.exceptions.DuplicateTagException;
import seedu.address.model.tag.exceptions.TagNotFoundException;

/**
 * Edits the details of an existing tag in the global catalogue.
 */
public class TagEditCommand extends Command {

    public static final String COMMAND_WORD = "tagedit";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the tag identified "
            + "by the tag name. Existing values will be overwritten by the input values.\n"
            + "Parameters: tn/TAG_NAME "
            + "[nn/NEW_NAME] "
            + "[tc/CATEGORY] "
            + "[tcol/COLOUR] "
            + "[td/DESCRIPTION]\n"
            + "Example: " + COMMAND_WORD + " tn/backend tc/Engineering";

    public static final String MESSAGE_SUCCESS = "Edited tag: %1$s";
    public static final String MESSAGE_DUPLICATE_TAG = "This tag already exists in the tag list.";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_TAG_NOT_FOUND = "This tag does not exist in the tag list.";

    private final String targetTagName;
    private final EditTagDescriptor editTagDescriptor;

    /**
     * Creates a {@code TagEditCommand} targeting the specified tag name with the provided edits.
     *
     * @param targetTagName the existing tag name to edit.
     * @param editTagDescriptor the descriptor containing the new tag values.
     */
    public TagEditCommand(String targetTagName, EditTagDescriptor editTagDescriptor) {
        this.targetTagName = requireNonNull(targetTagName);
        this.editTagDescriptor = new EditTagDescriptor(requireNonNull(editTagDescriptor));
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        Tag tagToEdit;
        try {
            tagToEdit = model.getTag(new Tag(targetTagName));
        } catch (TagNotFoundException e) {
            throw new CommandException(MESSAGE_TAG_NOT_FOUND, e);
        }

        if (!editTagDescriptor.isAnyFieldEdited()) {
            throw new CommandException(MESSAGE_NOT_EDITED);
        }

        Tag editedTag = createEditedTag(tagToEdit, editTagDescriptor);
        if (tagToEdit.equals(editedTag)) {
            throw new CommandException(MESSAGE_NOT_EDITED);
        }

        try {
            model.setTag(tagToEdit, editedTag);
        } catch (DuplicateTagException e) {
            throw new CommandException(MESSAGE_DUPLICATE_TAG, e);
        }

        return new CommandResult(String.format(MESSAGE_SUCCESS, editedTag.tagName));
    }

    private static Tag createEditedTag(Tag tagToEdit, EditTagDescriptor editTagDescriptor) {
        assert tagToEdit != null;

        String updatedName = editTagDescriptor.getName().orElse(tagToEdit.tagName);
        String updatedCategory = editTagDescriptor.getCategory().orElse(tagToEdit.category);
        String updatedColour = editTagDescriptor.getColour().orElse(tagToEdit.colour);
        String updatedDescription = editTagDescriptor.getDescription().orElse(tagToEdit.description);

        return new Tag(updatedName, updatedCategory, updatedColour, updatedDescription);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof TagEditCommand)) {
            return false;
        }

        TagEditCommand otherCommand = (TagEditCommand) other;
        return targetTagName.equals(otherCommand.targetTagName)
                && editTagDescriptor.equals(otherCommand.editTagDescriptor);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("targetTagName", targetTagName)
                .add("editTagDescriptor", editTagDescriptor)
                .toString();
    }

    /**
     * Stores the details to edit the tag with. Each non-empty field value will replace the
     * corresponding field value of the tag.
     */
    public static class EditTagDescriptor {
        private String name;
        private String category;
        private String colour;
        private String description;

        public EditTagDescriptor() {}

        /**
         * Copy constructor.
         *
         * @param toCopy the descriptor to copy values from.
         */
        public EditTagDescriptor(EditTagDescriptor toCopy) {
            setName(toCopy.name);
            setCategory(toCopy.category);
            setColour(toCopy.colour);
            setDescription(toCopy.description);
        }

        public boolean isAnyFieldEdited() {
            return name != null || category != null || colour != null || description != null;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Optional<String> getName() {
            return Optional.ofNullable(name);
        }

        public void setCategory(String category) {
            this.category = category;
        }

        public Optional<String> getCategory() {
            return Optional.ofNullable(category);
        }

        public void setColour(String colour) {
            this.colour = colour;
        }

        public Optional<String> getColour() {
            return Optional.ofNullable(colour);
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public Optional<String> getDescription() {
            return Optional.ofNullable(description);
        }

        @Override
        public boolean equals(Object other) {
            if (other == this) {
                return true;
            }

            if (!(other instanceof EditTagDescriptor)) {
                return false;
            }

            EditTagDescriptor otherDescriptor = (EditTagDescriptor) other;
            return Objects.equals(name, otherDescriptor.name)
                    && Objects.equals(category, otherDescriptor.category)
                    && Objects.equals(colour, otherDescriptor.colour)
                    && Objects.equals(description, otherDescriptor.description);
        }
    }
}
