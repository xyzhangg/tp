package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_MOD_NAME_B;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_MOD_NAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.VALID_GRADE_SU;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MOD_NAME_B;
import static seedu.address.logic.commands.CommandTestUtil.WHITESPACE;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalModules.COM_ORG;
import static seedu.address.testutil.TypicalModules.EFF_COM;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.SuCommand;
import seedu.address.logic.commands.UpdateCommand;
import seedu.address.model.module.Grade;
import seedu.address.model.module.ModuleName;
import seedu.address.testutil.UpdateModNameDescriptorBuilder;

public class SuCommandParserTest {

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, SuCommand.MESSAGE_USAGE);

    private Parser parser = new SuCommandParser();

    @Test
    public void parse_validArgs_returnsSuCommand() {

        ModuleName moduleName = COM_ORG.getModuleName();

        UpdateCommand.UpdateModNameDescriptor updateModNameDescriptor = new UpdateCommand.UpdateModNameDescriptor();
        updateModNameDescriptor.setName(moduleName);
        updateModNameDescriptor.setGrade(new Grade("SU"));

        assertParseSuccess(parser, VALID_MOD_NAME_B, new SuCommand(moduleName, updateModNameDescriptor));
    }

    @Test
    public void parse_invalidArgs_failure() {

        assertParseFailure(parser, INVALID_MOD_NAME_B, ModuleName.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_missingParts_failure() {
        assertParseFailure(parser, "", ModuleName.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_invalidValue_failure() {
        String moduleName = COM_ORG.getModuleName().fullModName;
        assertParseFailure(parser, INVALID_MOD_NAME_DESC, ModuleName.MESSAGE_CONSTRAINTS); // invalid module name
        assertParseFailure(parser, WHITESPACE, ModuleName.MESSAGE_CONSTRAINTS); // no module name
    }

    @Test
    public void parse_allFieldsSpecified_success() {
        ModuleName nameThirdModule = EFF_COM.getModuleName();
        String userInput = nameThirdModule.fullModName;
        SuCommand.UpdateModNameDescriptor descriptor = new UpdateModNameDescriptorBuilder()
                .withGrade(VALID_GRADE_SU).build();
        SuCommand expectedCommand = new SuCommand(nameThirdModule, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }
}
