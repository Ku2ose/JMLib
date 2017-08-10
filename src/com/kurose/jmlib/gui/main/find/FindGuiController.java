package com.kurose.jmlib.gui.main.find;

import com.kurose.jmlib.finder.entity.MyTrack;
import com.kurose.jmlib.finder.mxmwrapper.MXM;
import com.kurose.jmlib.song.Metadata;
import com.kurose.jmlib.song.Song;
import com.kurose.jmlib.util.MetadataGUI;
import com.kurose.jmlib.util.Refs;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class FindGuiController implements Initializable {

    @FXML private Button find, search, prevResult, nextResult, update, cancel, showCover;
    @FXML private TextField title, artist, album, author, trackNo, date, genre, searchTA;
    @FXML private ImageView cover;
    @FXML private Label label, labelInput;
    @FXML private ComboBox<MyTrack> resultsCB;

    private static Song song;

    private static Metadata res;

    public static void setSong(Song song1) { song = song1;}

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        labelInput.setText("Find");
        labelInput.setTooltip(new Tooltip(song.getPath()));

        searchTA.setText(song.getPath().substring(
                song.getPath().lastIndexOf("\\")+1, song.getPath().lastIndexOf(".")));

        Platform.runLater(() -> {searchTA.deselect(); searchTA.positionCaret(searchTA.getLength()); }); // this is some kind of black magic stuff, he he xd

        nextResult.setFocusTraversable(false);
        prevResult.setFocusTraversable(false);

        showCover.setOnAction(showOnCoverAction());
        showCover.setVisible(false);

        find.setOnAction(findAction());
        search.setOnAction(searchEvent());

        nextResult.setOnAction(nextResEvent());
        prevResult.setOnAction(prevRevEvent());
        resultsCB.getSelectionModel().selectedItemProperty().addListener(itemChaangeListener());

        update.setOnAction(updateAction());
        cancel.setOnAction(cancelAction());
}


    private ChangeListener<? super MyTrack> itemChaangeListener() {
        return (observable, oldValue, newValue) -> updateGui();
    }

    private void updateGui(){
        if (!resultsCB.getItems().isEmpty()) {
            MyTrack aux = resultsCB.getSelectionModel().getSelectedItem();

            label.setText(String.format(
                    "Result: %d/%d", (resultsCB.getSelectionModel().getSelectedIndex()+1), resultsCB.getItems().size()));
            title.setText(aux.getTitle());
            artist.setText(aux.getArtist());
            album.setText(aux.getAlbum().getName());
            author.setText(aux.getAlbum().getArtist());
            date.setText(aux.getReleaseDate().substring(0, aux.getReleaseDate().indexOf("T")));
            trackNo.setText(aux.getTrackPosition());
            genre.setText(aux.getGenres());
            showCover.setVisible(aux.getCover() != null);

        } else {
            label.setText("No matches found! :'(");
            title.setText(""); artist.setText("");album.setText("");author.setText("");date.setText("");
            trackNo.setText("");genre.setText("");
            showCover.setVisible(false);
            cover.setImage(null);
        }
    }

    private String[] inputList() {
        return searchTA.getText().split("-");}


    private EventHandler<ActionEvent> findAction() {
        return event -> {
            resultsCB.getItems().clear();

            System.out.print("Searching for track : " + searchTA.getText() + " ");
            MyTrack temp = MXM.getMatchingTrack(inputList()[0].trim(), inputList()[1].trim());
            System.out.println("*** End find action ***\n");

            resultsCB.getItems().add(temp);
            resultsCB.getSelectionModel().selectFirst();

            updateGui();

        };
    }

    private EventHandler<ActionEvent> searchEvent() {
        return event -> {
            resultsCB.getItems().clear();  // reset the results

            System.out.print("Searching for track : " + searchTA.getText() + " ");

            Thread t = new Thread(() ->
                resultsCB.setItems(FXCollections.observableArrayList(MXM.track_Search(inputList()[0].trim(), inputList()[1].trim()))));
            t.start();
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("*** End search results ***\n");

            resultsCB.getSelectionModel().selectFirst();

            updateGui();

        };
    }

    private EventHandler<ActionEvent> showOnCoverAction(){
        return event -> {
            if (resultsCB.getItems().isEmpty()) return;
            if (resultsCB.getValue().getCover() != null) {
                new CoverPanel(resultsCB.getValue().getCover());
            }
        };
    }

    private EventHandler<ActionEvent> nextResEvent() {
        return event -> {
            if (resultsCB.getSelectionModel().isSelected(resultsCB.getItems().size()-1)){
                resultsCB.getSelectionModel().selectFirst(); return;
            }

            resultsCB.getSelectionModel().selectNext();
            updateGui();
        };
    }

    private EventHandler<ActionEvent> prevRevEvent() {
        return event ->{
            if (resultsCB.getSelectionModel().isSelected(0)){
                resultsCB.getSelectionModel().selectLast(); return;
            }
            resultsCB.getSelectionModel().selectPrevious();
            updateGui();
        };
    }

    private EventHandler<ActionEvent> updateAction() {
        return event -> {
            if (resultsCB.getItems().isEmpty()) return;

            //initialize and resize the cover first
            Image image = null;
            if (resultsCB.getValue().getCover() != null) {
                // TODO here change the Image sizes
                image = new Image(
                        resultsCB.getValue().getCover(),
                        Refs.COVER_SAVE_SIZE, Refs.COVER_SAVE_SIZE,
                        true, true, false);
            }

            MetadataGUI temp = new MetadataGUI(
                    song,
                    artist.getText(),
                    author.getText(),
                    title.getText(),
                    album.getText(),
                    date.getText(),
                    trackNo.getText(),
                    genre.getText(),
                    image
            );

            temp.createMetadata(res);

            // just closes the gui
            Stage stage = (Stage) update.getParent().getScene().getWindow();
            stage.hide();
        };
    }

    private EventHandler<ActionEvent> cancelAction() {
        return event -> {
            Stage stage = (Stage) cancel.getParent().getScene().getWindow();
            stage.hide();
        };
    }

    public static void setMetadata(Metadata metadata) {
        res = metadata;
    }


    public Metadata getRes(){
        return res;
    }
}
