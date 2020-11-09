package seedu.address.ui;

import java.util.Arrays;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.Region;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * The UI component that is responsible for receiving user command inputs.
 */
public class CommandBox extends UiPart<Region> {

    public static final String ERROR_STYLE_CLASS = "error";
    private static final String FXML = "CommandBox.fxml";
    private static final String[] autocompleteSuggestions =
        new String[]{"start             ",
            "add             ",
            "update             ",
            "list             ",
            "su             ",
            "delete             ",
            "find             ",
            "help             ",
            "exit             ",
            "goal             ",
            "recommendSU   ",
            "done             ",
            "progress             ",
            "clear             "};

    private final CommandExecutor commandExecutor;
    private final Alert alert;

    @FXML
    private AutoCompleteTextField commandTextField;

    /**
     * Creates a {@code CommandBox} with the given {@code CommandExecutor}.
     */
    public CommandBox(CommandExecutor commandExecutor) {
        super(FXML);
        this.commandExecutor = commandExecutor;
        // calls #setStyleToDefault() whenever there is a change to the text of the command box.
        commandTextField.textProperty().addListener((unused1, unused2, unused3) -> setStyleToDefault());
        //add all suggestions to the autocomplete
        commandTextField.getEntries().addAll(Arrays.asList(autocompleteSuggestions));
        alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to clear all modules?",
            ButtonType.YES, ButtonType.NO);
    }

    /**
     * Handles the Enter button pressed event.
     */
    @FXML
    private void handleCommandEntered() {
        String userInput = commandTextField.getText();
        String trimmedUserInput = userInput.trim();

        try {
            if (trimmedUserInput.equals(ClearCommand.COMMAND_WORD)) {
                handleClearCommand();
            } else {
                commandExecutor.execute(commandTextField.getText());
                commandTextField.setText("");
            }
        } catch (CommandException | ParseException e) {
            setStyleToIndicateCommandFailure();
        }
    }

    /**
     * Handles the event that clear is called, an alert dialog is poped up to make confirmation.
     */
    private void handleClearCommand() {
        alert.showAndWait();
        try {
            if (alert.getResult() == ButtonType.YES) {
                commandExecutor.execute(commandTextField.getText());
            }
            commandTextField.setText("");
        } catch (CommandException | ParseException e) {
            setStyleToIndicateCommandFailure();
        }
    }

    /**
     * Sets the command box style to use the default style.
     */
    private void setStyleToDefault() {
        commandTextField.getStyleClass().remove(ERROR_STYLE_CLASS);
    }

    /**
     * Sets the command box style to indicate a failed command.
     */
    private void setStyleToIndicateCommandFailure() {
        ObservableList<String> styleClass = commandTextField.getStyleClass();

        if (styleClass.contains(ERROR_STYLE_CLASS)) {
            return;
        }

        styleClass.add(ERROR_STYLE_CLASS);
    }

    public AutoCompleteTextField getCommandTextField() {
        return commandTextField;
    }

    /**
     * Represents a function that can execute commands.
     */
    @FunctionalInterface
    public interface CommandExecutor {
        /**
         * Executes the command and returns the result.
         *
         * @see seedu.address.logic.Logic#execute(String)
         */
        CommandResult execute(String commandText) throws CommandException, ParseException;
    }
}
