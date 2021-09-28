package seedu.address.model.person;

import static java.util.Objects.requireNonNull;

/**
 * Represents the remark for a Person in the address book.
 * Guarantees: immutable; Is always valid
 */
public class Remark {
    public final String value;

    /**
     * Constructs an {@code Remark}.
     *
     * @param remark Remarks about a person.
     */
    public Remark(String remark) {
        requireNonNull(remark);
        value = remark;
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Remark // instanceof handles nulls
                && value.equals(((Remark) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
