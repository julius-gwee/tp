package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.Findr;
import seedu.address.model.ReadOnlyFindr;
import seedu.address.model.person.Person;

/**
 * An Immutable Candidate List that is serializable to JSON format.
 */
@JsonRootName(value = "candidatelist")
class JsonSerializableFindr {

    public static final String MESSAGE_DUPLICATE_PERSON = "Persons list contains duplicate person(s).";

    private final List<JsonAdaptedPerson> persons = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializableFindr} with the given persons.
     */
    @JsonCreator
    public JsonSerializableFindr(@JsonProperty("persons") List<JsonAdaptedPerson> persons) {
        this.persons.addAll(persons);
    }

    /**
     * Converts a given {@code ReadOnlyFindr} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializableFindr}.
     */
    public JsonSerializableFindr(ReadOnlyFindr source) {
        persons.addAll(source.getCandidateList().stream().map(JsonAdaptedPerson::new).collect(Collectors.toList()));
    }

    /**
     * Converts this candidate list into the model's {@code Findr} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public Findr toModelType() throws IllegalValueException {
        Findr findr = new Findr();
        for (JsonAdaptedPerson jsonAdaptedPerson : persons) {
            Person person = jsonAdaptedPerson.toModelType();
            if (findr.hasPerson(person)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_PERSON);
            }
            findr.addPerson(person);
        }
        return findr;
    }

}
