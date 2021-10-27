package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.address.logic.commands.CommandTestUtil.MEMBER_ID_DESC_ONE;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_MEMBER;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.member.MaddCommand;
import seedu.address.logic.commands.member.MdelCommand;
import seedu.address.logic.commands.member.MeditCommand;
import seedu.address.logic.commands.member.MeditCommand.EditMemberDescriptor;
import seedu.address.logic.commands.member.MfindCommand;
import seedu.address.logic.commands.member.MlistCommand;
import seedu.address.logic.commands.task.TaddCommand;
import seedu.address.logic.commands.task.TdelCommand;
import seedu.address.logic.commands.task.TlistCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.module.NameContainsKeywordsPredicate;
import seedu.address.model.module.member.Member;
import seedu.address.model.module.task.Task;
import seedu.address.testutil.EditMemberDescriptorBuilder;
import seedu.address.testutil.MemberBuilder;
import seedu.address.testutil.MemberUtil;
import seedu.address.testutil.TaskBuilder;
import seedu.address.testutil.TaskUtil;

public class AddressBookParserTest {

    private final AddressBookParser parser = new AddressBookParser();

    @Test
    public void parseCommand_add() throws Exception {
        Member member = new MemberBuilder().build();
        MaddCommand command = (MaddCommand) parser.parseCommand(MemberUtil.getPaddCommand(member));
        assertEquals(new MaddCommand(member), command);
    }

    @Test
    public void parseCommand_add_task() throws Exception {
        Index validMemberID = Index.fromOneBased(1);
        Task validTask = new TaskBuilder().build();
        TaddCommand command = (TaddCommand) parser.parseCommand(TaskUtil.getTaddCommand(validTask, validMemberID));
        assertEquals(new TaddCommand(validMemberID, validTask), command);
    }

    @Test
    public void parseCommand_del_task() throws Exception {
        Index validMemberID = Index.fromOneBased(1);
        Index validTaskID = Index.fromOneBased(1);
        TdelCommand command = (TdelCommand) parser.parseCommand(TaskUtil.getTdelCommand(validTaskID, validMemberID));
        assertEquals(new TdelCommand(validMemberID, validTaskID), command);
    }

    @Test
    public void parseCommand_delete() throws Exception {
        MdelCommand command = (MdelCommand) parser.parseCommand(
                MdelCommand.COMMAND_WORD + " " + CliSyntax.PREFIX_MEMBER_ID + INDEX_FIRST_MEMBER.getOneBased());
        assertEquals(new MdelCommand(INDEX_FIRST_MEMBER), command);
    }

    @Test
    public void parseCommand_edit() throws Exception {
        Member member = new MemberBuilder().build();
        EditMemberDescriptor descriptor = new EditMemberDescriptorBuilder(member).build();
        MeditCommand command = (MeditCommand) parser.parseCommand(MeditCommand.COMMAND_WORD + " "
                + INDEX_FIRST_MEMBER.getOneBased() + " " + MemberUtil.getEditMemberDescriptorDetails(descriptor));
        assertEquals(new MeditCommand(INDEX_FIRST_MEMBER, descriptor), command);
    }

    @Test
    public void parseCommand_exit() throws Exception {
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD) instanceof ExitCommand);
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD + " 3") instanceof ExitCommand);
    }

    @Test
    public void parseCommand_find() throws Exception {
        List<String> keywords = Arrays.asList("foo", "bar", "baz");
        MfindCommand command = (MfindCommand) parser.parseCommand(
                MfindCommand.COMMAND_WORD + " " + keywords.stream().collect(Collectors.joining(" ")));
        assertEquals(new MfindCommand(new NameContainsKeywordsPredicate<Member>(keywords)), command);
    }

    @Test
    public void parseCommand_help() throws Exception {
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD) instanceof HelpCommand);
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD + " 3") instanceof HelpCommand);
    }

    @Test
    public void parseCommand_list() throws Exception {
        assertTrue(parser.parseCommand(MlistCommand.COMMAND_WORD) instanceof MlistCommand);
        assertThrows(ParseException.class, String.format(MESSAGE_INVALID_COMMAND_FORMAT, MlistCommand.MESSAGE_USAGE), ()
            -> parser.parseCommand(MlistCommand.COMMAND_WORD + " 3"));
    }

    @Test
    public void parseCommand_tlist() throws Exception {
        assertTrue(parser.parseCommand(TlistCommand.COMMAND_WORD + MEMBER_ID_DESC_ONE) instanceof TlistCommand);
    }

    @Test
    public void parseCommand_unrecognisedInput_throwsParseException() {
        assertThrows(ParseException.class, String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE), ()
            -> parser.parseCommand(""));
    }

    @Test
    public void parseCommand_unknownCommand_throwsParseException() {
        assertThrows(ParseException.class, MESSAGE_UNKNOWN_COMMAND, () -> parser.parseCommand("unknownCommand"));
    }

}
