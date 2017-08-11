package com.kurose.jmlib.gui.main.body;

import com.kurose.jmlib.song.Metadata;
import com.kurose.jmlib.song.Song;
import com.kurose.jmlib.util.Util;
import javafx.beans.value.ChangeListener;
import javafx.event.EventHandler;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.util.Optional;


public class InfoTab
{
    private TextField title, artist, album, author, track, date;
    private ImageView cover;
    private FilteredComboBox genre;
    private ToggleButton updtCover;

    public static boolean UPDATE_COVER = false;

    private Song song;

    
    public InfoTab(
            TextField title, TextField artist, TextField album, TextField author, TextField track, TextField date,
            ToggleButton upCovButton, ImageView cover, ComboBox<String> genre
    )
    {
        this.title = title;
        this.artist = artist;
        this.album = album;
        this.author = author;
        this.track = track;
        this.date = date;
        this.cover = cover;
        genre.setItems(Util.getGenreOptions());
        this.genre = new FilteredComboBox(genre);
        this.updtCover = upCovButton;

        updtCover.setSelected(UPDATE_COVER);
        updtCover.setTooltip(new Tooltip("If it's red it's gonna update the cover"));
        updateStyleTB();

        artist.setTooltip(new Tooltip("Artist field"));
        album.setTooltip(new Tooltip("Album field"));
        author.setTooltip(new Tooltip("Album artist field"));
        title.setTooltip(new Tooltip("MyTrack title field"));
        date.setTooltip(new Tooltip("Release field"));
        track.setTooltip(new Tooltip("Track number field"));

        this.cover.setFitWidth(150);
        this.cover.setPreserveRatio(true);
        this.cover.setSmooth(true);
        this.cover.setCache(false);

        this.cover.setOnMouseClicked(coverClickMouseListener());

        updtCover.selectedProperty().addListener(updtCovTButtListener());
    }

    public void setUp(Song song)
    {
        if (song == null || song.getMetadata() == null) {
            title.setText("");
            artist.setText("");
            album.setText("");
            author.setText("");
            track.setText("");
            date.setText("");
            this.song = null;
        } else {
            this.song = song;
            title.setText(song.getMetadata().getTitle());
            artist.setText(song.getMetadata().getArtist());
            album.setText(song.getMetadata().getAlbum());
            author.setText(song.getMetadata().getAuthor());
            track.setText(song.getMetadata().getTrack());
            date.setText(song.getMetadata().getDate());
            
            showGenre(song.getMetadata().getGenre());

            // TODO recheck when i will learn something more about threads and progress indicator
            Thread t = new Thread(() -> cover.setImage(song.getMetadata().hasCover() ? Util.byteArrayToFXImage(song.getMetadata().getCover()) :
                    null));

            t.start();

        }
    }
    
    private void showGenre(String match)
    {
        if (match != null && !match.equalsIgnoreCase("")) {
            if (genre.getItems().contains(match)) {
                genre.select(match);
            }
        }
    }

    private void updateStyleTB()
    {
        if (UPDATE_COVER) {
            updtCover.setStyle("-fx-text-fill:white;" +
                    " -fx-background-color:red;");
        } else {
            updtCover.setStyle("");
        }
    }

    public Song getSong() { return song; }

    public Boolean isAnySongSelected() { return song != null;}

    public Metadata createMetadata()
    {
        Metadata newData = new Metadata();
        newData.setArtist(Util.capitalizeFirstLetter(artist.getText()));
        newData.setAlbum(Util.capitalizeFirstLetter(album.getText()));
        newData.setAuthor(Util.capitalizeFirstLetter(author.getText()));
        newData.setTitle(Util.capitalizeFirstLetter(title.getText()));
        newData.setDate(Util.capitalizeFirstLetter(date.getText()));
        newData.setTrack(Util.capitalizeFirstLetter(track.getText()));

        if (genre.getValue() == null || genre.getValue().equals("NONE")  ) {
            newData.setGenre(null);
        } else {
            newData.setGenre(genre.getValue());
        }

            /*
            *  IF -- saves the cover of the new song with a new one (can be null)
            *  ELSE -- the cover of the old song is going to be used as cover for the new song.
            *       doing in this way the image used as cover wont get any Javafx.Image operations that
            *       it actually supports in the imageviews (resizing and scaling)
            *  */
        if (UPDATE_COVER){
            newData.setCover(
                    cover.getImage() != null ? Util.fromImageToByteArray(cover.getImage()) : null);
        } else {
            if (song.getMetadata().hasCover()){
                newData.setCover(song.getMetadata().getCover());
            }
        }
        return newData;
    }

    public void clearInfoTab()
    {
        song = null;
        artist.setText("");album.setText("");author.setText("");title.setText("");date.setText("");track.setText("");
        cover.setImage(null);
        genre.setValue(null);

    }
    
    private EventHandler<? super MouseEvent> coverClickMouseListener()
    {
        return (EventHandler<MouseEvent>) event ->
        {
            if (!isAnySongSelected()) return;

            CoverWindow cw = new CoverWindow(Alert.AlertType.CONFIRMATION);
            cw.setTitle(album.getText());
            cw.buildUP(cover.getImage());

            Optional<ButtonType> result = cw.showAndWait();
            if (result.isPresent()) {
                if (result.get() == ButtonType.OK ){
                    UPDATE_COVER = true;
                    updtCover.setSelected(true);
                    cover.setImage(cw.getImage());

                    cw.hide();
                } else if (result.get() == CoverWindow.delCover) {
                    UPDATE_COVER = true;
                    updtCover.setSelected(true);
                    cover.setImage(null);
                    cw.hide();
                } else {
                    cw.hide();
                }
            }
            updateStyleTB();
        };
    }

    private ChangeListener<? super Boolean> updtCovTButtListener()
    {
        return (observable, oldValue, newValue) -> {
            UPDATE_COVER = newValue;
            updateStyleTB();
        };
    }

}
