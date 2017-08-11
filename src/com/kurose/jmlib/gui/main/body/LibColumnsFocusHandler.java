package com.kurose.jmlib.gui.main.body;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;


public class LibColumnsFocusHandler {

    private ListView artistLV, albumLV, songLV;
    private TextField artistTF, albumTF, songTF;
    private Button clearArtist, clearAlbum, clearSong;

    public LibColumnsFocusHandler(
            ListView artistLV, ListView albumLV, ListView songLV,
            TextField artistTF, TextField albumTF, TextField songTF,
            Button clearArtist, Button clearAlbum, Button clearSong)
    {
        this.artistLV = artistLV;
        this.albumLV = albumLV;
        this.songLV = songLV;
        this.artistTF = artistTF;
        this.albumTF = albumTF;
        this.songTF = songTF;
        this.clearArtist = clearArtist;
        this.clearAlbum = clearAlbum;
        this.clearSong = clearSong;

        handling();
    }

    private void handling() {
        // Listview events
        artistLV.setOnKeyReleased(artLVSetOnKeyReleased());
        albumLV.setOnKeyReleased(albLVSetOnKeyReleased());
        songLV.setOnKeyReleased(sngLVSetOnKeyReleased());

        // Textfield events
        artistTF.setOnKeyReleased(artTFsetOnKeyReleased());
        albumTF.setOnKeyReleased(albTFsetOnKeyReleased());
        songTF.setOnKeyReleased(sngTFsetOnKeyReleased());

        // Button events
        clearArtist.setOnAction(artClearOnAction());
        clearAlbum.setOnAction(albClearOnAction());
        clearSong.setOnAction(sngClearOnAction());
    }

    // ARTIST LISTVIEW
    private EventHandler<? super KeyEvent> artLVSetOnKeyReleased() {
        return event -> {
            if (event.getCode().isLetterKey()){
                artistTF.requestFocus();
                artistTF.setText(artistTF.getText().concat(event.getText()));
                artistTF.positionCaret(artistTF.getLength());
            } else if (event.getCode() == KeyCode.BACK_SPACE && artistTF.getLength() > 0) {
                artistTF.requestFocus();
                artistTF.setText(artistTF.getText().substring(0, artistTF.getLength() - 1));
                artistTF.positionCaret(artistTF.getLength());
            } else if (event.getCode() == KeyCode.ESCAPE) {
                artistTF.setText("");
            } else if (event.getCode() == KeyCode.ENTER) {
                albumLV.requestFocus();
                albumLV.getSelectionModel().selectFirst();
            }
        };
    }

    // ALBUM LISTVIEW
    private EventHandler<? super KeyEvent> albLVSetOnKeyReleased() {
        return event -> {
            if (event.getCode().isLetterKey()){
                albumTF.requestFocus();
                albumTF.setText(albumTF.getText().concat(event.getText()));
                albumTF.positionCaret(albumTF.getLength());
            }else if (event.getCode() == KeyCode.BACK_SPACE && albumTF.getLength() > 0) {
                albumTF.requestFocus();
                albumTF.setText(albumTF.getText().substring(0, albumTF.getLength() - 1));
                albumTF.positionCaret(albumTF.getLength());
            } else if (event.getCode() == KeyCode.ESCAPE) {
                albumTF.setText("");
            } else if (event.getCode() == KeyCode.ENTER) {
                songLV.requestFocus();
                songLV.getSelectionModel().selectFirst();
            }
        };
    }

    // SONG LISTVIEW
    private EventHandler<? super KeyEvent> sngLVSetOnKeyReleased() {
        return event -> {
            if (event.getCode().isLetterKey()){
                songTF.requestFocus();
                songTF.setText(songTF.getText().concat(event.getText()));
                songTF.positionCaret(songTF.getLength());
            } else if (event.getCode() == KeyCode.BACK_SPACE && songTF.getLength() > 0) {
                songTF.requestFocus();
                songTF.setText(songTF.getText().substring(0, songTF.getLength() - 1));
                songTF.positionCaret(songTF.getLength());
            } else if (event.getCode() == KeyCode.ESCAPE) {
                songTF.setText("");
            }
        };
    }

    // ARTIST TEXTFIELD
    private EventHandler<? super KeyEvent> artTFsetOnKeyReleased() {
        return event -> {
            if (event.getCode().isArrowKey()){
                if (event.getCode() == KeyCode.UP) {
                    artistLV.requestFocus();
                    artistLV.getSelectionModel().selectLast();
                } else if (event.getCode() == KeyCode.DOWN) {
                    artistLV.requestFocus();
                    artistLV.getSelectionModel().selectFirst();
                }
            } else if (event.getCode().isWhitespaceKey()) {
                if (event.getCode() == KeyCode.ENTER) {
                    artistLV.getSelectionModel().selectFirst();
                    albumLV.requestFocus();
                    // todo devo anche selezionare il primo elemento in albumLV?
                }
            } else if (event.getCode() == KeyCode.ESCAPE) {
                artistLV.requestFocus();
                artistTF.setText("");
            }
        };
    }

    // ALBUM TEXTFIELD
    private EventHandler<? super KeyEvent> albTFsetOnKeyReleased() {
        return event -> {
            if (event.getCode().isArrowKey()){
                if (event.getCode() == KeyCode.UP) {
                    albumLV.requestFocus();
                    albumLV.getSelectionModel().selectLast();
                } else if (event.getCode() == KeyCode.DOWN) {
                    albumLV.requestFocus();
                    albumLV.getSelectionModel().selectFirst();
                }
            } else if (event.getCode().isWhitespaceKey()) { // WHITE SPACE EVENTS
                if (event.getCode() == KeyCode.ENTER) {
                    albumLV.getSelectionModel().selectFirst();
                    songLV.requestFocus();

                    // todo devo anche selezionare il primo elemento in songLW?
                }
            } else if (event.getCode() == KeyCode.ESCAPE){
                albumLV.requestFocus();
                albumTF.setText("");
            }
        };
    }

    // SONG TEXTFIELD
    private EventHandler<? super KeyEvent> sngTFsetOnKeyReleased() {
        return event -> {
            if (event.getCode().isArrowKey()){
                if (event.getCode() == KeyCode.UP) {
                    songLV.requestFocus();
                    songLV.getSelectionModel().selectLast();
                } else if (event.getCode() == KeyCode.DOWN) {
                    songLV.requestFocus();
                    songLV.getSelectionModel().selectFirst();
                }
            } else if (event.getCode().isWhitespaceKey()) {
                if (event.getCode() == KeyCode.ENTER) {
                    songLV.requestFocus();
                    songLV.getSelectionModel().selectFirst();
                }
            } else if (event.getCode() == KeyCode.ESCAPE){
                songLV.requestFocus();
                songTF.setText("");
            }
        };
    }

    // ARTIST DELETEBUTTON
    private EventHandler<ActionEvent> artClearOnAction() {
        return event -> {
            artistTF.setText("");
            artistLV.requestFocus();
            artistLV.getSelectionModel().select(-1);
        };
    }

    // ALBUM DELETEBUTTON
    private EventHandler<ActionEvent> albClearOnAction() {
        return event -> {
            albumTF.setText("");
            albumLV.requestFocus();
            albumLV.getSelectionModel().select(-1);
        };
    }

    // SONG DELETEBUTTON
    private EventHandler<ActionEvent> sngClearOnAction() {
        return event -> {
            songTF.setText("");
            songLV.requestFocus();
            songLV.getSelectionModel().select(-1);
        };
    }
}
