package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.*;
import static seedu.address.logic.commands.task.TaddCommand.MESSAGE_USAGE;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.logic.parser.ParserUtil.MESSAGE_INVALID_INDEX;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.task.TaddCommand;
import seedu.address.logic.parser.task.TaddCommandParser;
import seedu.address.model.module.Name;
import seedu.address.model.module.task.Task;
import seedu.address.model.module.task.TaskDeadline;
import seedu.address.testutil.TaskBuilder;

class TaddCommandParserTest {
    private TaddCommandParser parser = new TaddCommandParser();

    @Test
    void parse_allFieldsPresent_success() {
        Task expectedTask = new TaskBuilder().withName(VALID_POEM_TASK_NAME)
                .withDeadline(VALID_POEM_TASK_DEADLINE).build();
        Index expectedMemberId = Index.fromOneBased(VALID_MEMBER_INDEX);
        Set<Index> expectedMemberIdList = new HashSet<>();
        expectedMemberIdList.add(expectedMemberId);

        assertParseSuccess(parser, TASK_NAME_DESC_POEM + TASK_DEADLINE_DESC_POEM + MEMBER_INDEX_DESC_ONE,
                new TaddCommand(expectedMemberIdList, expectedTask));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_USAGE);

        //missing member id
        assertParseFailure(parser, TASK_NAME_DESC_POEM, expectedMessage);

        //missing task name
        assertParseFailure(parser, MEMBER_INDEX_DESC_ONE, expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        //invalid task name (blank)
        assertParseFailure(parser, INVALID_TASK_NAME_DESC+ TASK_DEADLINE_DESC_POEM + MEMBER_INDEX_DESC_ONE,
                Name.MESSAGE_CONSTRAINTS);

        //invalid task deadline
        assertParseFailure(parser, TASK_NAME_DESC_POEM+ INVALID_TASK_DEADLINE_DESC + MEMBER_INDEX_DESC_ONE,
                TaskDeadline.MESSAGE_CONSTRAINTS);

        //invalid member id
        assertParseFailure(parser, TASK_NAME_DESC_POEM + TASK_DEADLINE_DESC_POEM + INVALID_MEMBER_INDEX_DESC,
                MESSAGE_INVALID_INDEX);
    }
}
