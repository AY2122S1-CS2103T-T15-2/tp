package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MEMBER_ID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;

import java.util.Set;

import javafx.collections.ObservableList;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.data.member.Member;
import seedu.address.model.task.Task;

/**
 * Adds a task to the task list of a person.
 */
public class TaddCommand extends Command {
    public static final String COMMAND_WORD = "tadd";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a task to the task list of a person. "
            + "Parameters: "
            + PREFIX_NAME + " TASKNAME "
            + PREFIX_MEMBER_ID + " MEMBER_ID\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NAME + " Submit form "
            + PREFIX_MEMBER_ID + " 2";

    public static final String MESSAGE_SUCCESS = "New task added: %1$s";

    public final Set<Index> targetMemberIdList;
    public final Task toAdd;

    /**
     * Creates an TaddCommand to add the specified {@code Task} to the member with specified {@code MemberID}.
     */
    public TaddCommand(Set<Index> memberIdList, Task task) {
        requireAllNonNull(memberIdList, task);
        targetMemberIdList = memberIdList;
        toAdd = task;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        ObservableList<Member> members = model.getFilteredMemberList();
        for (Index targetMemberID: targetMemberIdList) {
            Member targetMember = members.get(targetMemberID.getZeroBased());
            model.addTask(targetMember, toAdd);
        }

        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof TaddCommand // instanceof handles nulls
                && toAdd.equals(((TaddCommand) other).toAdd));
    }
}
