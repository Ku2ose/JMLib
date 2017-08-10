package com.kurose.jmlib.gui.main.body;

import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.util.Callback;

import java.util.List;


/*
    TODO:
        - sembra brutto che non specifico i tipi in molte componenti, ma finche va
        - add more controls to the filter textArea, but which?
 */

public class LibColumn
{
    private TextField filterArea;
    private ListView<String>  listView;
    private Button clearButton;
    
    private FilteredList<String> filteredSourceList;

    
    public LibColumn(TextField filterArea, ListView<String> listView, Button button)
    {
        this.filterArea = filterArea;
        this.listView = listView;
        this.clearButton = button;
        this.filteredSourceList = null;
        
        this.filterArea.textProperty().addListener(filterAreaChangeListener());
        this.listView.setOnKeyReleased(setOnKeyRelesed());
    
        this.clearButton.setOnAction(clearButtonEvent());
        this.listView.setCellFactory(cellColorChanges());

    }

    public void setUp(List<String> sourceList)
    {
        this.filteredSourceList = new FilteredList<>(FXCollections.observableArrayList(sourceList), p -> true);
    
        this.listView.setItems(filteredSourceList);
        
        this.listView.getSelectionModel().select(null);
        
        setListMultipleSelection(false);
    }
    
    /***
     *  Clears the text on the Textfield and gives the focus to the belonging ListView. Set the listView's selected item
     *  to none
     *
     * @return OnAction EventHandler
     */
    private EventHandler<ActionEvent> clearButtonEvent()
    {
        // toDO may change this event handling to LibHandler
        return event -> {
            filterArea.setText("");
            listView.requestFocus();
            listView.getSelectionModel().setSelectionMode(null);
        };
    }
    
    private ChangeListener<? super String> filterAreaChangeListener()
    {
        return (ChangeListener<String>) (observable, oldValue, newValue) -> filteredSourceList.setPredicate(p -> {
            if (newValue == null || newValue.isEmpty()) {
                return true;
            } else return p.toLowerCase().contains(newValue.toLowerCase());
        });
    }

    private Callback<ListView<String>, ListCell<String>> cellColorChanges() {
        return new Callback<ListView<String>, ListCell<String>>() {
            @Override
            public ListCell<String> call(ListView<String> param) {
                return new ListCell<String>() {
                    @Override public void updateItem(String item,
                                                     boolean empty) {
                        super.updateItem(item, empty);
                        if (item != null) {
                            setText(item);
                            if (item.equals("Unknown tag")) {
                                setTextFill(Color.RED);
                            } else if (item.equals("***")) {
                                setTextFill(Color.GREEN);
                            } else {
                                setTextFill(Color.BLACK);
                            }
                        }
                        else {
                            setText(null);
                        }
                    }
                };
            }
        };
    }

    private EventHandler<? super KeyEvent> setOnKeyRelesed() {
        return event -> {
            if (event.getCode().isLetterKey()){
                filterArea.requestFocus();
                filterArea.setText(event.getText());
                filterArea.positionCaret(1);
            }
        };
    }

    public void setListMultipleSelection(Boolean flag){
        listView.getSelectionModel().setSelectionMode( flag ? SelectionMode.MULTIPLE : SelectionMode.SINGLE );
    }
    
    public void clearSelection() { this.listView.getSelectionModel().clearSelection();}

    public void nullifyFocus() { this.listView.getParent().requestFocus();}

    public MultipleSelectionModel<String> getLVSelectionModel() { return this.listView.getSelectionModel();}

    public void updateC1(List<String> newList) {
        this.filteredSourceList = new FilteredList<>(FXCollections.observableArrayList(newList), p -> true);

        this.listView.setItems(filteredSourceList);
    }
}
