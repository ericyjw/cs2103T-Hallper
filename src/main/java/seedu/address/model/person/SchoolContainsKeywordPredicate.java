package seedu.address.model.person;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;

/**
 * Tests that a {@code Person}'s {@code School} matches the keyword given.
 */
//@@author javenseow
public class SchoolContainsKeywordPredicate implements Predicate<Person> {
    private final List<String> keywords;

    public SchoolContainsKeywordPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Person person) {
        return keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(person.getSchool().value, keyword));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof SchoolContainsKeywordPredicate //instanceof handles null
            && keywords.equals(((SchoolContainsKeywordPredicate) other).keywords)); //state check
    }
}
