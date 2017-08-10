package com.kurose.jmlib.gui.main;

import com.kurose.jmlib.gui.main.body.InfoTab;
import com.kurose.jmlib.gui.main.body.LibColumn;
import com.kurose.jmlib.gui.main.body.SongColumn;
import com.kurose.jmlib.gui.main.find.MXMGui;
import com.kurose.jmlib.song.Metadata;
import com.kurose.jmlib.song.MusicLib;
import com.kurose.jmlib.song.Song;
import com.kurose.jmlib.song.Sorter;
import com.kurose.jmlib.util.Refs;
import com.mpatric.mp3agic.InvalidDataException;
import com.mpatric.mp3agic.NotSupportedException;
import com.mpatric.mp3agic.UnsupportedTagException;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

public class LibHandler {
    private LibColumn artistColumn;
    private LibColumn albumColumn;
    private SongColumn trackColumn;
    private InfoTab infoTab;

    private Button saveB, findB, playB, delB;

    public LibHandler(LibColumn artistC, LibColumn albumC, SongColumn titleC, InfoTab tab, Sorter sort) {
        this.artistColumn = artistC;
        this.albumColumn = albumC;
        this.trackColumn = titleC;
        this.infoTab = tab;


        columnsHandling();
    }

    public void setUpButtons(Button s, Button f, Button p, Button d) {
        this.saveB = s;
        this.findB = f;
        this.playB = p;
        this.delB = d;

        saveB.setOnAction(saveAction());
        findB.setOnMouseClicked(findAction());
    }

    private void columnsHandling() {

        artistColumn.setUp(Sorter.sortArtists());

        artistColumn.getLVSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> albumColumn.setUp(Sorter.sortAlbums(newValue)));

        albumColumn.getLVSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> {
                    trackColumn.setUp(Sorter.sortTracks(newValue));
                    infoTab.clearInfoTab();
                });

        trackColumn.getListView().getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> infoTab.setUp(newValue));
    }

    private EventHandler<? super MouseEvent> findAction() {
        return (EventHandler<MouseEvent>) event -> {
            if (event.getButton() == MouseButton.PRIMARY) {

                if (!infoTab.isAnySongSelected()) return;
                Metadata data = new Metadata();

                new MXMGui(data, infoTab.getSong());

                // here saves the song
                if (data.getTitle() != null) {
                    save(data);
                }
            }
        };
    }

    private EventHandler<ActionEvent> saveAction(){
        return e -> {

            if (infoTab.isAnySongSelected()) {
                Metadata metadata = infoTab.createMetadata();

                save(metadata);
            }
        };
    }

    private void save(Metadata metadata){
        // i am columnsHandling here the exceptions instead of in Song method because if the save succeeds I can launch other
        // methods to clean the graphic components
        try {
            Song.save(infoTab.getSong(), metadata);

            infoTab.clearInfoTab();
            artistColumn.clearSelection();
            Sorter.updateLib(new MusicLib(Refs.LIB_HOME).getLib());
            artistColumn.updateC1(Sorter.sortArtists());

        } catch (InvalidDataException | IOException | UnsupportedTagException | NotSupportedException | IllegalArgumentException e1) {
            e1.printStackTrace();
            saveErrorAlert(e1).showAndWait();
        }
    }


    private Alert saveErrorAlert(Exception exception){
        // memo: Ive changed this and will not use precise exception but their generic Exception class, hope it would still work
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        exception.printStackTrace(pw);

        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.getDialogPane().setMinWidth(600d);
        // TODO change this get DialogPane windows buttons

        Stage s = (Stage) alert.getDialogPane().getScene().getWindow();
        s.getIcons().add(new Image(Refs.APP_LOGO));

        alert.setTitle("Saving result:");
        alert.setHeaderText("Changes were unsuccessful! :( ");
        alert.setContentText("Click beleow to show error message");
        TextArea ta = new TextArea(sw.toString());
        ta.setEditable(false);
        ta.setWrapText(true);
        ta.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        alert.getDialogPane().setExpandableContent(ta);

        return alert;
    }

}