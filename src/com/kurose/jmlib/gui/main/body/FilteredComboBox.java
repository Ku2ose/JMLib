package com.kurose.jmlib.gui.main.body;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.Event;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Tooltip;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Window;

/***
 *  COMBOBOXES IN JAVAFX AND THIS CLASS ARE LIKE SADDEST THING I'VE EVER SEEN.
 *
 *  THIS CLASS MAY PRODUCE SO MANY BUGS THAN NEITHER GOD WOULD BE ABLE TO COUNT EM ALL.
 *
 *  PROB BEST TO LEAVE AS IT IS...
 */


public class FilteredComboBox {

    private ComboBox<String> cb;
    private FilteredList<String> filteredList;
    private String filter = "";

    public FilteredComboBox(ComboBox<String> comboBox) {
        cb = comboBox;
        filteredList = new FilteredList<>(cb.getItems(), p -> true);

        Tooltip tooltip = new Tooltip("");
        cb.setTooltip(tooltip);

        cb.setOnKeyReleased(this::setOnKeyRelease);

        cb.getTooltip().textProperty().addListener(filterListener());

        cb.setOnHidden(this::setOnHidden);

        cb.setItems(filteredList);
    }

    private void setOnHidden(Event event) {
        filter = "";
        if (cb.getTooltip() != null )cb.getTooltip().hide();
        cb.setValue(cb.getSelectionModel().getSelectedItem());
    }

    private ChangeListener<? super String> filterListener() {
        return (observable, oldValue, newValue) -> {
            String selected = cb.getSelectionModel().getSelectedItem();

            if (selected == null || !selected.equals(filter)) {
                Platform.runLater(() -> filteredList.setPredicate(item ->{
                    if (item.toUpperCase().startsWith(newValue.toUpperCase())) {
                        return true;
                    } else {
                        return false;
                    }
                }));
            }
            showTooltip();
        };
    }

    private void setOnKeyRelease(KeyEvent event) {
        KeyCode kc = event.getCode();

        if (kc.isLetterKey())
        {
            if (!cb.isShowing()) {
                cb.show();
            }
            filter = filter.concat(event.getText());
        }
        else if (kc == KeyCode.BACK_SPACE && filter.length() > 0) {
            filter = filter.substring(0, filter.length() - 1);
        }
        else if (kc == KeyCode.ESCAPE) {
            filter = "";
        }
        cb.getTooltip().setText(filter);
    }

    private void showTooltip() {
        if (filter.equals("")) {
            cb.getTooltip().hide(); return;
        }
        if (!cb.getTooltip().isShowing()) {
            Window stage = cb.getScene().getWindow();
            double posX = stage.getX() + cb.localToScene(cb.getBoundsInLocal()).getMinX() + 4;
            double posY = stage.getY() + cb.localToScene(cb.getBoundsInLocal()).getMinY() - 29;
            cb.getTooltip().show(stage, posX, posY);
        }
    }

    public String getSelectedItem(){
        return cb.getSelectionModel().getSelectedItem();
    }

    public int getSelectedIndex(){
        return cb.getSelectionModel().getSelectedIndex();
    }

    public void select(String s) {
        cb.getSelectionModel().select(s);
    }

    public ObservableList<String> getItems() {
        return cb.getItems();
    }

    public String getValue() {
        return cb.getValue();
    }

    public void setPromptText(String text) {
        cb.setPromptText(text);
    }

    public void setValue(String t){
        cb.setValue(t);
    }
}
