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
            new Tag("frontend", "Engineering Focus", "#0B5FFF",
                    "Specialises in building responsive user interfaces."),
            new Tag("backend", "Engineering Focus", "#8E44AD",
                    "Experienced with server-side systems and APIs."),
            new Tag("datascience", "Analytics", "#16A085",
                    "Works with machine learning and data pipelines."),
            new Tag("product", "Product Leadership", "#C0392B",
                    "Coordinates product strategy and roadmaps."),
            new Tag("ux", "Design", "#F39C12", "Designs intuitive user experiences.")
    );

    private static final Map<String, Tag> SAMPLE_TAG_LOOKUP = SAMPLE_TAGS.stream()
            .collect(Collectors.toUnmodifiableMap(tag -> tag.tagName, Function.identity()));

    public static Person[] getSamplePersons() {
        return new Person[] {
            new Person(new Name("Amelia Chen"), new Phone("82345670"), new Email("amelia.chen@talentmail.com"),
                    new Address("12 Marina View, #18-01"),
                    getTagSet("frontend", "ux"), dateAddedOf(2024, 3, 15),
                    Rating.EXCELLENT, Stage.INTERVIEWED),
            new Person(new Name("Brian Lee"), new Phone("86451234"), new Email("brian.lee@candidatehub.io"),
                    new Address("55 Pasir Ris Grove, #09-21"),
                    getTagSet("backend"), dateAddedOf(2024, 1, 27),
                    Rating.GOOD, Stage.CANDIDATES),
            new Person(new Name("Carla Gomez"), new Phone("81235678"), new Email("carla.gomez@datafolk.ai"),
                    new Address("88 Science Park Drive, #03-05"),
                    getTagSet("datascience"), dateAddedOf(2023, 12, 2),
                    Rating.EXCELLENT, Stage.CONTACTED),
            new Person(new Name("Diego Patel"), new Phone("97886543"), new Email("diego.patel@productlane.com"),
                    new Address("47 Holland Village Way, #04-12"),
                    getTagSet("product"), dateAddedOf(2023, 9, 18),
                    Rating.AVERAGE, Stage.INTERVIEWED),
            new Person(new Name("Elena Rossi"), new Phone("93456781"), new Email("elena.rossi@uxcraft.co"),
                    new Address("3 Tanjong Pagar Plaza, #15-07"),
                    getTagSet("ux", "frontend"), dateAddedOf(2023, 6, 6),
                    Rating.GOOD, Stage.HIRED),
            new Person(new Name("Farah Khan"), new Phone("90123456"), new Email("farah.khan@cloudforge.dev"),
                    new Address("102 Jalan Bukit Merah, #13-02"),
                    getTagSet("backend", "datascience"), dateAddedOf(2024, 2, 8),
                    Rating.UNRATED, Stage.CANDIDATES)
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
