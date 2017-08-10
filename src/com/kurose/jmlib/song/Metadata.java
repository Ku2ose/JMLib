package com.kurose.jmlib.song;

import com.mpatric.mp3agic.ID3v1;
import com.mpatric.mp3agic.ID3v2;
import com.mpatric.mp3agic.ID3v23Tag;

/***
 * A class that gives an easy access at the most common info stored in the metadata tags of an mp3 file.
 *      - path = the path of the mp3 on the hard disk
 *      - artist = the name of the artist/band who made the song
 *      - album = the name of the album that the song belongs
 *      - author = the artist/band who made the album
 *      - date = release date of the track
 *      - track = the number of the track in the album
 *      - genre = the genre description of the track
 *      - cover = a byte[] with the data of the tracks Cover art
 *
 * If a tag is unknown it's generally set to null. This values can be accessed by the getters and the setters.
 */
public class Metadata
{
    // never really usefull but im stil not deleting it maybe later
    private String path;
    
    private String artist, title, album, author, date, track, genre, version;
    
    private byte[] cover;

    public Metadata(){
        path = null; artist = null; title = null; album = null; author = null; date = null; track = null;
        genre = null; version = null; cover = null;
    }

    /***
     * Generates An interface for songs ID3v2 tags. This version doesn't use space for the coverart and the artist album.
     * @param tags ID3v2 tags
     */
    public Metadata(ID3v2 tags)
    {
        version = tags.getVersion();
        artist = tags.getArtist();
        album = tags.getAlbum();
        title = tags.getTitle();
        author = tags.getAlbumArtist();
        date = tags.getYear();
        track = tags.getTrack();
        genre = tags.getGenreDescription();
        cover = tags.getAlbumImage();
    }

    /***
     * Generates An interface for songs ID3v1 tags
     * @param tags ID3v1 tags
     */
    public Metadata(ID3v1 tags)
    {
        version = tags.getVersion();
        artist = tags.getArtist();
        album = tags.getAlbum();
        title = tags.getTitle();
        date = tags.getYear();
        track = tags.getTrack();
        genre = tags.getGenreDescription();
        author = null;
        cover = null;
    }
    
    /***
     *      Converts this class fields in ID3v2 tags. The version of the tags is the 3th.
     * @return ID3v23Tag
     */
    public ID3v2 toID3v2Tags()
    {
        ID3v2 tags = new ID3v23Tag();
        tags.setTitle(title);
        tags.setArtist(artist);
        tags.setAlbum(album);
        tags.setAlbumArtist(author);
        tags.setYear(date);
        tags.setTrack(track);
        if (genre != null) tags.setGenreDescription(genre); //Todo: add the possibility of a adding genres for free
        tags.setAlbumImage(cover, "jpg");

        return tags;
    }
    
    // Bunch of setters and getters
    
    public String getPath()
    {
        return path;
    }
    
    public void setPath(String path)
    {
        this.path = path;
    }
    
    public String getArtist()
    {
        return artist;
    }
    
    public void setArtist(String artist)
    {
        this.artist = artist;
    }
    
    public String getTitle()
    {
        return title;
    }
    
    public void setTitle(String title)
    {
        this.title = title;
    }
    
    public String getAlbum()
    {
        return album;
    }
    
    public void setAlbum(String album)
    {
        this.album = album;
    }
    
    public String getAuthor()
    {
        return author;
    }
    
    public void setAuthor(String author)
    {
        this.author = author;
    }
    
    public String getDate()
    {
        return date;
    }
    
    public void setDate(String date)
    {
        this.date = date;
    }
    
    public String getTrack()
    {
        return track;
    }
    
    public void setTrack(String track)
    {
        this.track = track;
    }
    
    public String getGenre()
    {
        return genre;
    }
    
    public void setGenre(String genre)
    {
        this.genre = genre;
    }
    
    public byte[] getCover()
    {
        return cover;
    }
    
    public void setCover(byte[] cover)
    {
        this.cover = cover;
    }
    
    public String getVersion()
    {
        return version;
    }
    
    public Boolean hasCover() { return cover != null;}
}
