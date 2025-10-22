package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import seedu.address.model.Model;
import seedu.address.model.tag.Tag;

/**
 * Lists all tag definitions in the global tag catalogue.
 */
public class TagListCommand extends Command {

    public static final String COMMAND_WORD = "taglist";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Lists all tags in the tag catalogue.\n"
            + "Example: " + COMMAND_WORD;

    public static final String MESSAGE_NO_TAGS = "There are no tags in the tag list.";

    public static final String MESSAGE_TAGS_LISTED = "%1$d tags in the tag list:%n%2$s";

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);

        List<Tag> tags = new ArrayList<>(model.getTagList());
        if (tags.isEmpty()) {
            return new CommandResult(MESSAGE_NO_TAGS);
        }

        tags.sort(Comparator.comparing(tag -> tag.tagName, String.CASE_INSENSITIVE_ORDER));
        String formattedTags = IntStream.range(0, tags.size())
                .mapToObj(index -> formatTagEntry(index, tags.get(index)))
                .collect(Collectors.joining(System.lineSeparator()));

        return new CommandResult(String.format(MESSAGE_TAGS_LISTED, tags.size(), formattedTags));
    }

    private String formatTagEntry(int index, Tag tag) {
        List<String> parts = new ArrayList<>();
        parts.add("Category: " + tag.category);
        parts.add("Colour: " + tag.colour);
        if (!tag.description.isEmpty()) {
            parts.add("Description: " + tag.description);
        }
        return String.format("%d. %s (%s)", index + 1, tag.tagName, String.join(", ", parts));
    }
}
