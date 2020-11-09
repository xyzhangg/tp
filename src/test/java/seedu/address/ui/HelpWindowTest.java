package seedu.address.ui;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.awt.Toolkit;
import java.awt.datatransfer.DataFlavor;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 * Tests for HelpWindow
 */
public class HelpWindowTest extends Application {

    private static final String startCommandFormat = "start SEMESTER\n\n";
    private static final String addCommandFormat = "add m/MODULE_CODE [g/GRADE] [mc/MODULAR CREDITS]\n\n";
    private static final String updateCommandFormat = "update m/MODULE_CODE [g/GRADE] [s/SEMESTER]\n\n";
    private static final String listCommandFormat = "list\n\n";
    private static final String goalCommandFormat = "goal set LEVEL OR goal list\n\n";
    private static final String recommendSuCommandFormat = "recommendSU\n\n";
    private static final String suCommandFormat = "su MODULE_CODE\n\n";
    private static final String deleteCommandFormat = "delete MODULE_CODE\n\n";
    private static final String doneCommandFormat = "done\n\n";
    private static final String findCommandFormat = "find KEYWORD\n\n";
    private static final String progressCommandFormat = "progress [ddp]\n\n";
    private static final String clearCommandFormat = "clear\n\n";
    private static final String helpCommandFormat = "help\n\n";
    private static final String exitCommandFormat = "exit\n\n";

    private HelpWindow helpWindow;

    @Test
    public void testA() throws InterruptedException {

        String expectedCommands =
            "Command Formats:\n\n"
                + startCommandFormat
                + addCommandFormat
                + updateCommandFormat
                + listCommandFormat
                + goalCommandFormat
                + recommendSuCommandFormat
                + suCommandFormat
                + deleteCommandFormat
                + doneCommandFormat
                + findCommandFormat
                + progressCommandFormat
                + clearCommandFormat
                + helpCommandFormat
                + exitCommandFormat;
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                //new JFXPanel(); // Initializes the JavaFx Platform
                Platform.runLater(new Runnable() {

                    @Override
                    public void run() {
                        try {
                            new FakeApp().start(new Stage());
                            HelpWindow helpWindow = new HelpWindow();

                            //check instance of help window
                            assertNotNull(helpWindow);

                            //check that help window is hidden
                            assertFalse(helpWindow.isShowing());

                            helpWindow.show();

                            //check that show() works
                            assertTrue(helpWindow.isShowing());

                            helpWindow.hide();

                            //check that hide() works
                            assertFalse(helpWindow.isShowing());

                            //check help commands match
                            assertEquals(expectedCommands, helpWindow.getHelpCommands());

                            Hyperlink hyperlink = helpWindow.getHyperLink();

                            //check hyperlink text
                            assertEquals("Click for the User Guide", hyperlink.getText());

                            //check help message
                            assertEquals("OR, ", helpWindow.getHelpMessage());

                            Button copyButton = helpWindow.getCopyButton();

                            MouseEvent mouseEvent = new MouseEvent(MouseEvent.MOUSE_CLICKED, 0,
                                0, 0, 0, MouseButton.PRIMARY, 1,
                                true, true, true, true,
                                true, true, true,
                                true, true, true, null);

                            //simulate copy button click
                            copyButton.fire();

                            String url = (String) Toolkit.getDefaultToolkit()
                                .getSystemClipboard().getData(DataFlavor.stringFlavor);

                            //check if clipboard has the link
                            assertEquals("https://ay2021s1-cs2103t-t17-1.github.io/tp/UserGuide.html", url);

                            hyperlink.fire();

                            assertEquals(Color.PURPLE, hyperlink.getTextFill());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });
        thread.start(); // Initialize the thread
        Thread.sleep(100); // Time to use the app, with out this, the thread
        // will be killed before you can tell.
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
        helpWindow = new HelpWindow(primaryStage);
        primaryStage.setScene(new Scene(helpWindow.getRoot().getScene().getRoot(), 100, 100));
        primaryStage.show();
    }
}
