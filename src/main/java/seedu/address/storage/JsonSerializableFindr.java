package seedu.address.storage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.Findr;
import seedu.address.model.ReadOnlyFindr;
import seedu.address.model.person.Person;
import seedu.address.model.tag.Tag;

/**
 * An Immutable Candidate List that is serializable to JSON format.
 */
@JsonRootName(value = "candidatelist")
class JsonSerializableFindr {

    public static final String MESSAGE_DUPLICATE_PERSON = "Persons list contains duplicate person(s).";
    public static final String MESSAGE_DUPLICATE_TAG = "Tag list contains duplicate tag(s).";

    private final List<JsonAdaptedPerson> persons = new ArrayList<>();
    private final List<JsonAdaptedTag> tags = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializableFindr} with the given persons and tags.
     */
    @JsonCreator
    public JsonSerializableFindr(@JsonProperty("persons") List<JsonAdaptedPerson> persons,
                                       @JsonProperty("tags") List<JsonAdaptedTag> tags) {
        this.persons.addAll(persons);
        if (tags != null) {
            this.tags.addAll(tags);
        }
    }

    /**
     * Converts a given {@code ReadOnlyFindr} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializableFindr}.
     */
    public JsonSerializableFindr(ReadOnlyFindr source) {
        persons.addAll(source.getCandidateList().stream().map(JsonAdaptedPerson::new).collect(Collectors.toList()));
        tags.addAll(source.getTagList().stream().map(JsonAdaptedTag::new).collect(Collectors.toList()));
    }

    /**
     * Converts this candidate list into the model's {@code Findr} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public Findr toModelType() throws IllegalValueException {
        Findr findr = new Findr();


        final List<Tag> modelTags = new ArrayList<>();
        final Set<String> seenTagNames = new HashSet<>();
        for (JsonAdaptedTag jsonAdaptedTag : tags) {
            Tag tag = jsonAdaptedTag.toModelType();
            if (!seenTagNames.add(tag.tagName)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_TAG);
            }
            modelTags.add(tag);
        }

        if (!modelTags.isEmpty()) {
            findr.setTags(modelTags);
        }

        for (JsonAdaptedPerson jsonAdaptedPerson : persons) {
            Person person = jsonAdaptedPerson.toModelType();
            if (findr.hasCandidate(person)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_PERSON);
            }
            findr.addPerson(person);
        }
        return findr;
    }

}
