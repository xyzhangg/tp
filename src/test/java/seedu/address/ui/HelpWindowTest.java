package seedu.address.ui;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.stage.Stage;

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
                            new HelpWindowTest().start(new Stage());

                            assertNotNull(helpWindow);

                            assertEquals(expectedCommands, helpWindow.getHelpCommands()); // Create and
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        // initialize
                        // your app.

                    }
                });
            }
        });
        thread.start(); // Initialize the thread
        Thread.sleep(100); // Time to use the app, with out this, the thread
        // will be killed before you can tell.
    }

    @BeforeAll
    public static void initJfx() {
        Thread t = new Thread("JavaFX Init Thread") {
            public void run() {
                Application.launch(HelpWindowTest.class, new String[0]);
            }
        };
        t.setDaemon(true);
        t.start();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        helpWindow = new HelpWindow(primaryStage);
        primaryStage.setScene(new Scene(helpWindow.getRoot().getScene().getRoot(), 100, 100));
        primaryStage.show();
    }
}
