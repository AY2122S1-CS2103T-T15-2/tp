package seedu.address.logic.commands;

import seedu.address.commons.core.Messages;
import seedu.address.model.Model;
import seedu.address.model.module.NameContainsKeywordsPredicate;
import seedu.address.model.module.task.Task;

import java.util.logging.Logger;

import static java.util.Objects.requireNonNull;

/**
 * Lists all tasks of the user containing the keyword in the address book.
 */
public class TfindCommand extends Command {

    public static final String COMMAND_WORD = "tfind";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Finds all tasks of the current member whose task names contain any of "
            + "the specified keywords (case-insensitive) and displays them as a list with index numbers.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " submit consent form";

    private final NameContainsKeywordsPredicate<Task> predicate;

    private static Logger logger = Logger.getLogger("Foo");

    public TfindCommand(NameContainsKeywordsPredicate<Task> predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredTaskList(predicate);
        return new CommandResult(
                String.format(Messages.MESSAGE_TASKS_LISTED_OVERVIEW, model.getFilteredTaskList().size()));
    }
}
