package com.kurose.jmlib.gui.main.body;

import com.kurose.jmlib.song.Song;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.transformation.FilteredList;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.util.Callback;

import java.util.List;


/*
    TODO:
        - sembra brutto che non specifico i tipi in molte componenti, ma finche va
        - add more controls to the filter textArea, but which?
 */

public class SongColumn
{
    private TextField filterArea;
    private ListView<Song>  listView;
    private Button clearButton;

    private FilteredList<Song> filteredSourceList;

    public SongColumn(TextField filterArea, ListView<Song> listView, Button button)
    {
        this.filterArea = filterArea;
        this.listView = listView;
        this.clearButton = button;
        this.filteredSourceList = null;
        
        this.filterArea.textProperty().addListener(filterAreaChangeListener());

        this.listView.setCellFactory(cellColorChanges());
    }
    
    public void setUp(List<Song> sourceList)
    {
        this.filteredSourceList = new FilteredList<>(FXCollections.observableArrayList(sourceList), p -> true);
    
        this.listView.setItems(filteredSourceList);
        
        this.listView.getSelectionModel().select(null);
        
        setListMultipleSelection(false);
    }
    
    private ChangeListener<? super String> filterAreaChangeListener()
    {
        return (ChangeListener<String>) (observable, oldValue, newValue) -> {
            
            filteredSourceList.setPredicate(p -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                } else return p.toString().toLowerCase().contains(newValue.toLowerCase());
            });
        };
    }

    private Callback<ListView<Song>, ListCell<Song>> cellColorChanges() {
        return new Callback<ListView<Song>, ListCell<Song>>() {

            @Override
            public ListCell<Song> call(ListView<Song> param) {
                return new ListCell<Song>(){

                    @Override
                    public void updateItem(Song item, boolean empty){
                        super.updateItem(item, empty);

                        if (item != null) {
                            setText(item.toString());

                            if (item.toString().equals("Unknown tag")) {
                                setTextFill(Color.RED);
                                setTooltip(new Tooltip(item.getPath()));
                            }else {
                                setTextFill(Color.BLACK);
                                setTooltip(null);
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
    
    public Song getSelectedElem()
    {
        return listView.getSelectionModel().getSelectedItem();
    }
    
    public ListView<Song> getListView(){
        return listView;
    }

}
