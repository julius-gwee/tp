package seedu.address.model.util;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import seedu.address.model.Findr;
import seedu.address.model.ReadOnlyFindr;
import seedu.address.model.person.Address;
import seedu.address.model.person.DateAdded;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Rating;
import seedu.address.model.person.Stage;
import seedu.address.model.tag.Tag;

/**
 * Contains utility methods for populating {@code AddressBook} with sample data.
 */
public class SampleDataUtil {
    private static final List<Tag> SAMPLE_TAGS = List.of(
            new Tag("friends", "Social", "#0B5FFF", "Close friends and confidants."),
            new Tag("colleagues", "Work", "#8E44AD", "Professional contacts and teammates."),
            new Tag("neighbours", "Community", "#16A085", "Neighbours living nearby."),
            new Tag("family", "Family", "#C0392B", "Immediate family members."),
            new Tag("classmates", "School", "#F39C12", "Schoolmates and course mates.")
    );

    private static final Map<String, Tag> SAMPLE_TAG_LOOKUP = SAMPLE_TAGS.stream()
            .collect(Collectors.toUnmodifiableMap(tag -> tag.tagName, Function.identity()));

    public static Person[] getSamplePersons() {
        return new Person[] {
            new Person(new Name("Alex Yeoh"), new Phone("87438807"), new Email("alexyeoh@example.com"),
                new Address("Blk 30 Geylang Street 29, #06-40"),
                    getTagSet("friends"), dateAddedOf(2024, 1, 12), Rating.GOOD, Stage.CANDIDATES),
            new Person(new Name("Bernice Yu"), new Phone("99272758"), new Email("berniceyu@example.com"),
                new Address("Blk 30 Lorong 3 Serangoon Gardens, #07-18"),
                    getTagSet("colleagues", "friends"), dateAddedOf(2023, 11, 3),
                    Rating.EXCELLENT, Stage.INTERVIEWED),
            new Person(new Name("Charlotte Oliveiro"), new Phone("93210283"), new Email("charlotte@example.com"),
                new Address("Blk 11 Ang Mo Kio Street 74, #11-04"),
                    getTagSet("neighbours"), dateAddedOf(2023, 7, 22), Rating.AVERAGE, Stage.CONTACTED),
            new Person(new Name("David Li"), new Phone("91031282"), new Email("lidavid@example.com"),
                new Address("Blk 436 Serangoon Gardens Street 26, #16-43"),
                    getTagSet("family"), dateAddedOf(2022, 9, 15), Rating.GOOD, Stage.HIRED),
            new Person(new Name("Irfan Ibrahim"), new Phone("92492021"), new Email("irfan@example.com"),
                new Address("Blk 47 Tampines Street 20, #17-35"),
                    getTagSet("classmates"), dateAddedOf(2024, 2, 2), Rating.POOR, Stage.CANDIDATES),
            new Person(new Name("Roy Balakrishnan"), new Phone("92624417"), new Email("royb@example.com"),
                new Address("Blk 45 Aljunied Street 85, #11-31"),
                    getTagSet("colleagues"), dateAddedOf(2023, 5, 9), Rating.UNRATED, Stage.CANDIDATES)
        };
    }

    public static ReadOnlyFindr getSampleAddressBook() {
        Findr sampleAb = new Findr();
        sampleAb.setTags(SAMPLE_TAGS);
        for (Person samplePerson : getSamplePersons()) {
            sampleAb.addCandidate(samplePerson);
        }
        return sampleAb;
    }

    /**
     * Returns a tag set containing the list of strings given.
     */
    public static Set<Tag> getTagSet(String... strings) {
        return Arrays.stream(strings)
                .map(name -> SAMPLE_TAG_LOOKUP.getOrDefault(name, new Tag(name)))
                .collect(Collectors.toSet());
    }

    private static DateAdded dateAddedOf(int year, int month, int dayOfMonth) {
        LocalDate localDate = LocalDate.of(year, month, dayOfMonth);
        Date date = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        return new DateAdded(date);
    }
}
