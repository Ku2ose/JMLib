package com.kurose.jmlib.util;

import com.kurose.jmlib.gui.main.body.InfoTab;
import com.kurose.jmlib.song.Metadata;
import com.kurose.jmlib.song.Song;
import javafx.scene.image.Image;


public class MetadataGUI {

    private Song song;

    private String artist, author, title, album, year, track, genre;
    private Image cover;

    public MetadataGUI(Song song, String artist, String author, String title, String album,
                       String year, String track, String genre, Image cover)
    {
        this.song = song;
        this.artist = artist;
        this.author = author;
        this.title = title;
        this.album = album;
        this.year = year;
        this.track = track;
        this.genre = genre;
        this.cover = cover;
    }

    public void createMetadata(Metadata newData) {

        newData.setArtist(Util.capitalizeFirstLetter(artist));
        newData.setAlbum(Util.capitalizeFirstLetter(album));
        newData.setAuthor(Util.capitalizeFirstLetter(author));
        newData.setTitle(Util.capitalizeFirstLetter(title));
        newData.setDate(Util.capitalizeFirstLetter(year));
        newData.setTrack(Util.capitalizeFirstLetter(track));

        if (genre == null || genre.equals("NONE")) {
            newData.setGenre(null);
        } else {
            if (Util.getGenreOptions().contains(genre)){
                newData.setGenre(genre);
            }else {
                newData.setGenre(null);
            }

        }

            /*
            *  IF -- saves the cover of the new song with a new one (can be null)
            *  ELSE -- the cover of the old song is going to be used as cover for the new song.
            *       doing in this way the image used as cover wont get any Javafx.Image operations that
            *       it actually supports in the imageviews (resizing and scaling)
            *  */
        if (InfoTab.UPDATE_COVER){
            newData.setCover(
                    cover != null ? Util.fromImageToByteArray(cover) : null);
        } else {
            if (song.getMetadata().hasCover()){
                newData.setCover(song.getMetadata().getCover());
            }
        }
    }
}
