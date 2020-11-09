package seedu.address.ui;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.stage.Stage;
import seedu.address.model.module.Module;
import seedu.address.testutil.TypicalModules;

/**
 * Tests for ModuleCardTest
 */
public class ModuleCardTest {

    @Test
    public void generateModuleCardsWithValidModules_success() throws InterruptedException {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            new FakeApp().start(new Stage());
                            Module sweModule = TypicalModules.SWE;
                            Module algoModule = TypicalModules.ALGO;
                            ModuleCard sweModuleCard = new ModuleCard(sweModule, 1);
                            ModuleCard algoModuleCard = new ModuleCard(algoModule, 1);

                            assertNotNull(sweModuleCard);
                            assertNotNull(algoModuleCard);
                            assertNotEquals(sweModuleCard, algoModuleCard); //checking of equality comparator
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });
        thread.start(); // Initialize the thread
        Thread.sleep(1000); // Time to use the app, with out this, the thread
    }

    @Test
    public void generateModuleListPanelWithNoModules_success() throws InterruptedException {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            new FakeApp().start(new Stage());
                            ObservableList<Module> observableModuleList = FXCollections.observableArrayList();
                            ModuleListPanel resultDisplay = new ModuleListPanel(observableModuleList);

                            assertNotNull(resultDisplay);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });
        thread.start(); // Initialize the thread
        Thread.sleep(1000); // Time to use the app, with out this, the thread
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
}
