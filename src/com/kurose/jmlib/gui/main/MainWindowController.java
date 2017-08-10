package com.kurose.jmlib.gui.main;


import com.kurose.jmlib.gui.main.body.InfoTab;
import com.kurose.jmlib.gui.main.body.LibColumn;
import com.kurose.jmlib.gui.main.body.SongColumn;
import com.kurose.jmlib.gui.main.header.Logo;
import com.kurose.jmlib.song.MusicLib;
import com.kurose.jmlib.song.Song;
import com.kurose.jmlib.song.Sorter;
import com.kurose.jmlib.util.Refs;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ResourceBundle;

public class MainWindowController implements Initializable
{
    
    @FXML private AnchorPane
        rootAPane, headerAPane, artistAPane, albumAPane, trackAPane, trackInfoAPane, footerAPane;
    @FXML private SplitPane splitPane;

    // LibColumn Components
    @FXML private Button click, clearFilterArtistButton, clearFilterAlbumButton, clearFilterTitleButton;
    @FXML private TextField tffartist, tffalbum, tfftrack;
    @FXML private ListView<String> lwartist, lwalbum;
    @FXML private ListView<Song> lwtrack;
    // InfoTab components
    @FXML private TextField artistTA, titleTA, albumTA, authorTA, trackTA, yearTA;
    @FXML private ComboBox<String> genreCB;
    @FXML private ImageView coverIV;
    @FXML private Button deleteSongButton, saveButton, findButton, playButton;
    @FXML private ToggleButton updateCoverTButton;
    // Header components
    @FXML private ImageView logoCover;
    
    
    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        Sorter sorter = new Sorter(new MusicLib(Refs.LIB_HOME).getLib());
        System.out.println("Library folder being read is located at: " + Refs.LIB_HOME);

        LibColumn artistColumn = new LibColumn(tffartist, lwartist, clearFilterArtistButton);
        LibColumn albumColumn = new LibColumn(tffalbum, lwalbum, clearFilterAlbumButton);
        SongColumn titleColumn = new SongColumn(tfftrack, lwtrack, clearFilterTitleButton);

        InfoTab infoTab = new InfoTab(titleTA, artistTA, albumTA, authorTA, trackTA, yearTA,
                updateCoverTButton, coverIV, genreCB);

        LibHandler LH = new LibHandler(artistColumn, albumColumn, titleColumn, infoTab, sorter);
        LH.setUpButtons(saveButton, findButton, playButton, deleteSongButton);
        new Logo(logoCover, artistColumn);
    }

    
    public void click(ActionEvent event){

        System.out.println(lwtrack.getSelectionModel().getSelectedItem().getMetadata().getPath());
    }
    
    
}
