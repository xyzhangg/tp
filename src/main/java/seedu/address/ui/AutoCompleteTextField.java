package seedu.address.ui;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.stream.Collectors;

import javafx.geometry.Side;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.CustomMenuItem;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseButton;

/**
 * This class is a TextField which implements an "autocomplete" functionality,
 * based on a supplied list of entries.
 *
 * @author Caleb Brinkman
 *
 * Reused from https://gist.github.com/floralvikings/10290131
 * with minor modifications by kunnan97, https://github.com/kunnan97
 */
public class AutoCompleteTextField extends TextField {
    /** The existing autocomplete entries. */
    private final SortedSet<String> entries;
    /** The popup used to select an entry. */
    private final ContextMenu entriesPopup;

    /** Construct a new AutoCompleteTextField. */
    public AutoCompleteTextField() {
        super();
        entries = new TreeSet<>();

        //modification by kunnan97, use MaxSizedContextMenu instead of
        //contextMenu to allow setMaxHeight() on contextMenu.
        entriesPopup = new MaxSizedContextMenu();

        textProperty().addListener((observableValue, s, s2) -> {
            if (getText().length() == 0
                || Arrays.asList(getEntries().stream().map(String::trim).toArray()).contains(getText().trim())) {
                entriesPopup.hide();
            } else {
                LinkedList<String> searchResult = new LinkedList<>();
                final List<String> filteredEntries =
                        entries.stream().filter(e -> e.toLowerCase()
                                        .contains(getText().toLowerCase())).collect(Collectors.toList());
                if (filteredEntries.size() == 0) {
                    entriesPopup.hide();
                } else {
                    searchResult.addAll(filteredEntries);
                    if (entries.size() > 0) {
                        populatePopup(searchResult);
                        if (!entriesPopup.isShowing()) {
                            entriesPopup.show(AutoCompleteTextField.this, Side.BOTTOM, 0, 0);
                        }
                    } else {
                        entriesPopup.hide();
                    }
                }
            }
        });

        onTextFieldClicked();

        focusedProperty().addListener((observableValue, aBoolean, aBoolean2) -> entriesPopup.hide());
    }

    /**
     * Modification:
     * Event listener for when mouse is clicked, auto suggestion pops up.
     *
     * @author kunnan97
     */
    private void onTextFieldClicked() {
        this.setOnMouseClicked(arg -> {
            LinkedList<String> searchResult = new LinkedList<>();
            searchResult.addAll(entries);
            populatePopup(searchResult);
            if (!entriesPopup.isShowing()
                && arg.getButton().equals(MouseButton.PRIMARY)
                && getText().isEmpty()) {
                entriesPopup.show(AutoCompleteTextField.this, Side.BOTTOM, 0, 0);
            }
        });
    }

    /**
     * Get the existing set of autocomplete entries.
     * @return The existing autocomplete entries.
     */
    public SortedSet<String> getEntries() {
        return entries;
    }

    /**
     * Populate the entry set with the given search results.  Display is limited to 10 entries, for performance.
     * @param searchResult The set of matching strings.
     */
    private void populatePopup(List<String> searchResult) {
        List<CustomMenuItem> menuItems = new LinkedList<>();
        // If you'd like more entries, modify this line.
        int maxEntries = 20;
        int count = Math.min(searchResult.size(), maxEntries);
        for (int i = 0; i < count; i++) {
            final String result = searchResult.get(i);
            Label entryLabel = new Label(result);
            CustomMenuItem item = new CustomMenuItem(entryLabel, true);
            entryLabel.setPrefWidth(150);
            item.setOnAction(actionEvent -> {
                int stringLength = result.length();
                if (stringLength == 14) {
                    setText(result.trim() + " ");
                } else {
                    setText(result.substring(0, stringLength - 12));
                }
                entriesPopup.hide();
                this.end();
            });
            menuItems.add(item);
        }

        //modification by kunnan97, create a new MaxSizedContextMenu every time it is
        //repopulated so as to avoid contextMenu staying at bottom of scroll
        //from previous pop up scrolling.
        entriesPopup.getItems().clear();
        entriesPopup.getItems().addAll(menuItems);
        entriesPopup.setMaxHeight(490);
    }
}
//@author Caleb Brinkman
//minor modifications by kunnan97
