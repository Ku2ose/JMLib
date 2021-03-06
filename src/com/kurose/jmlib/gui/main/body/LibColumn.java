package com.kurose.jmlib.gui.main.body;

import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.transformation.FilteredList;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.util.Callback;

import java.util.List;


public class LibColumn
{
    private ListView<String>  listView;

    
    private FilteredList<String> filteredSourceList;

    
    public LibColumn(TextField filterArea, ListView<String> listView)
    {
        this.listView = listView;

        this.filteredSourceList = null;
        
        filterArea.textProperty().addListener(filterAreaChangeListener());

        this.listView.setCellFactory(cellColorChanges());
    }

    public void setUp(List<String> sourceList)
    {
        this.filteredSourceList = new FilteredList<>(FXCollections.observableArrayList(sourceList), p -> true);
    
        this.listView.setItems(filteredSourceList);
        
        this.listView.getSelectionModel().select(null);
        
        setListMultipleSelection(false);
    }

    
    private ChangeListener<? super String> filterAreaChangeListener()
    {
        return (ChangeListener<String>) (observable, oldValue, newValue) -> filteredSourceList.setPredicate(p -> {
            if (newValue == null || newValue.isEmpty()) {
                return true;
            } else return p.toLowerCase().startsWith(newValue.toLowerCase());
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
