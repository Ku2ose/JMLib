package com.kurose.jmlib.gui.main.find;

import com.kurose.jmlib.song.Metadata;
import com.kurose.jmlib.song.Song;
import com.kurose.jmlib.util.Refs;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class MXMGui {

    public MXMGui(Metadata data, Song song) {

        FindGuiController.setSong(song);
        FindGuiController.setMetadata(data);


        try {
            Parent lfmRoot = FXMLLoader.load(getClass().getResource("FindGuiController.fxml"));
            Scene scene = new Scene(lfmRoot, 400, 600);
            Stage stage = new Stage();

            stage.setTitle("Search with MusixMatch");
            stage.getIcons().add(
                    new Image(getClass().getClassLoader().getResourceAsStream(Refs.APP_LOGO)));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(scene);
            stage.setMinHeight(600);
            stage.setMinWidth(400);
            stage.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();
        }


    }

}
