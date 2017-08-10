package com.kurose.jmlib.song;

import com.kurose.jmlib.util.Refs;
import com.mpatric.mp3agic.InvalidDataException;
import com.mpatric.mp3agic.Mp3File;
import com.mpatric.mp3agic.NotSupportedException;
import com.mpatric.mp3agic.UnsupportedTagException;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.Comparator;


public class Song
{
    
    private String path;
    private Metadata metadata;
    
    // Void Constructor, all null
    public Song()
    {
        path = null; metadata = null;
    }
    
    public Song(String path)
    {
        if (path == null || path.length() == 0) {
            throw new IllegalArgumentException(
                    "The path is not correct or there's no such file at the corresponding location");
        }
        
        this.path = path;
        
        Mp3File mp3File;
        try {
           
            mp3File = new Mp3File(path);
            
            if (mp3File.hasId3v2Tag()) {
                metadata = new Metadata(mp3File.getId3v2Tag());
            } else if (mp3File.hasId3v1Tag()){
                metadata = new Metadata(mp3File.getId3v1Tag());
            } else {
                // if has no metadata tag it creates an empty metadata interface. it solves the crash at the begging
                // of song without any tags
                metadata = new Metadata();
            }

        } catch (IOException | UnsupportedTagException | InvalidDataException e) {
            e.printStackTrace();
        }
    }
    
    public static void save(Song old, Metadata newTags) throws IOException, NotSupportedException, InvalidDataException, UnsupportedTagException {

        Mp3File o = new Mp3File(old.getPath());

        o.setId3v2Tag(newTags.toID3v2Tags());

        String newpath = Refs.LIB_HOME + "/" + newTags.getAuthor().replace("?","") + " - "
                            + newTags.getTitle().replace("?","") + ".mp3";
        o.save(newpath + ".bak");
        System.out.println("LOG SAVE:: file temporally saved in : " + newpath + ".bak");

        File oldFile = new File(old.getPath());
        File newFile = new File(newpath + ".bak");

        System.out.println("LOG SAVE:: \"" + old.getPath() + "\" erase state: " + oldFile.delete());
        System.out.println("LOG SAVE:: file rename to \""+ newpath + "\" :: " + newFile.renameTo(new File(newpath)) );
        System.out.println();

        // alert confirmation ok
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        Stage s = (Stage) alert.getDialogPane().getScene().getWindow();
        s.getIcons().add(new Image(Refs.APP_LOGO));
        alert.setTitle("Saving result:");
        alert.setHeaderText("Changes saved correctly! :)");
        alert.setContentText(newFile.getPath());
        alert.showAndWait();
    }

    public static Comparator<Song> compareByTrack(){
        return (o1, o2) -> {
            String s1 = o1.getMetadata().getTrack() != null ? o1.getMetadata().getTrack() : "-1";
            String s2 = o2.getMetadata().getTrack() != null ? o2.getMetadata().getTrack() : "-1";

            s1 = s1.contains("/") ? s1.substring(0, s1.indexOf("/")) : s1; // get the part before the slash
            s2 = s2.contains("/") ? s2.substring(0, s2.indexOf("/")) : s2; // same as above

            return Integer.valueOf(s1).compareTo(Integer.valueOf(s2));

        };
    }

    public static Comparator<Song> compareByAlbum(){
        return (o1, o2) -> {
            String s1 = o1.getMetadata().getAlbum() != null ? o1.getMetadata().getAlbum() : "";
            String s2 = o2.getMetadata().getAlbum() != null ? o2.getMetadata().getAlbum() : "";

            return s1.compareTo(s2);

        };
    }

    // another bunch of setters and getters
    
    public String getPath()
    {
        return path;
    }
    
    public void setPath(String path)
    {
        this.path = path;
    }
    
    public Metadata getMetadata()
    {
        return metadata;
    }
    
    public void setMetadata(Metadata metadata)
    {
        this.metadata = metadata;
    }

    public String toString() {
        return metadata.getTitle() != null ? metadata.getTitle() : "Unknown tag";
    }

}
