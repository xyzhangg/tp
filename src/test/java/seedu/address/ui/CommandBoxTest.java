package seedu.address.ui;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.control.ContextMenu;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Tests for CommandBox
 */
public class CommandBoxTest extends Application {

    @Test
    public void testA() throws InterruptedException {

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                //new JFXPanel(); // Initializes the JavaFx Platform
                Platform.runLater(new Runnable() {

                    @Override
                    public void run() {
                        try {
                            new FakeApp().start(new Stage());
                            CommandBox commandBox = new CommandBox(new CommandBox.CommandExecutor() {
                                @Override
                                public CommandResult execute(String commandText)
                                    throws CommandException, ParseException {
                                    return null;
                                }
                            });
                            //check instance of Command Box
                            assertNotNull(commandBox);

                            AutoCompleteTextField autoCompleteTextField = commandBox.getCommandTextField();
                            assertNotNull(autoCompleteTextField);

                            //check that initial state, textfield is empty
                            assertEquals("", autoCompleteTextField.getText());

                            //check that autocomplete have 14 entities available
                            assertEquals(14, autoCompleteTextField.getEntries().size());

                            //check that autocomplete suggestions is hidden at first
                            assertFalse(autoCompleteTextField.getEntriesPopup().isShowing());

                            //user types "progre"
                            autoCompleteTextField.setText("progre");

                            ContextMenu contextMenu = autoCompleteTextField.getEntriesPopup();

                            //check instance of ContextMenu
                            assertNotNull(contextMenu);

                            //check that only 1 item is in the suggestions
                            assertEquals(1, contextMenu.getItems().size());
                            autoCompleteTextField.setText("");

                            //simulate click on the command box
                            MouseEvent mouseEvent = new MouseEvent(MouseEvent.MOUSE_CLICKED, 0,
                                0, 0, 0, MouseButton.PRIMARY, 1,
                                true, true, true, true,
                                true, true, true,
                                true, true, true, null);

                            autoCompleteTextField.fireEvent(mouseEvent);

                            contextMenu = autoCompleteTextField.getEntriesPopup();

                            //check that suggestions should be full(14)
                            assertEquals(14, contextMenu.getItems().size());

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });
        thread.start(); // Initialize the thread
        Thread.sleep(10000); // Time to use the app, with out this, the thread
    }


    public static class FakeApp extends Application {
        @Override
        public void start(Stage primaryStage) throws Exception {
            // noop
        }
    }

    @BeforeAll
    public static void initJfx() throws InterruptedException {
        Thread t = new Thread("JavaFX Init Thread") {
            public void run() {
                Application.launch(FakeApp.class, new String[0]);
            }
        };
        t.setDaemon(true);
        t.start();
        System.out.printf("FX App thread started\n");
        Thread.sleep(500);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
    }
}
