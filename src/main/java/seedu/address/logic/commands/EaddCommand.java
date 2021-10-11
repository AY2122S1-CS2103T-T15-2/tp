package seedu.address.logic.commands;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.member.Member;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.*;

/**
 * Adds a member to the address book.
 */
public class EaddCommand extends Command {

    public static final String COMMAND_WORD = "eadd";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds an event to the address book. "
            + "Parameters: "
            + PREFIX_NAME + "NAME "
            + PREFIX_PHONE + "PHONE "
            + PREFIX_EMAIL + "EMAIL "
            + PREFIX_ADDRESS + "ADDRESS "
            + "[" + PREFIX_POSITION + "POSITION]...\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NAME + "John Doe "
            + PREFIX_PHONE + "98765432 "
            + PREFIX_EMAIL + "johnd@example.com "
            + PREFIX_ADDRESS + "311, Clementi Ave 2, #02-25 "
            + PREFIX_POSITION + "friends "
            + PREFIX_POSITION + "owesMoney";

    public static final String MESSAGE_SUCCESS = "New member added: %1$s";
    public static final String MESSAGE_DUPLICATE_MEMBER = "This member already exists in the address book";

    private final Member toAdd;

    /**
     * Creates an PaddCommand to add the specified {@code Person}
     */
    public EaddCommand(Member member) {
        requireNonNull(member);
        toAdd = member;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (model.hasMember(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_MEMBER);
        }

        model.addMember(toAdd);
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof EaddCommand // instanceof handles nulls
                && toAdd.equals(((EaddCommand) other).toAdd));
    }
}
