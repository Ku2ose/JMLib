package com.kurose.jmlib.finder.mxmwrapper;

import com.kurose.jmlib.finder.entity.MyTrack;
import com.myjmxm.entity.genre.MusicGenre;
import com.myjmxm.entity.genre.MusicGenreList;
import com.myjmxm.entity.track.Track;
import com.myjmxm.entity.track.TrackList;

import java.util.ArrayList;
import java.util.List;

public class MXMTrack implements MyTrack  {

    private final String    title;
    private final String    mbid;
    private final int       id;
    private final String    spotifyId;
    private final String    xboxMusicId;
    private final int       length;
    private final int       commonTrackId;
    private final boolean   instrumental;
    private final boolean   lyrics;
    private final boolean   subtitles;
    private final int       lyricsId;
    private final String    releaseDate;
    private final String    position;

    private final String    artist;
    private final int       artistid;
    private final String    artistMbid;

    private MXMAlbum        album;

    private final String    cover;

    private final List<MusicGenre> genres;


    public MXMTrack(Track track) {
        title = track.getTrackName();
        mbid = track.getTrackMbid();
        id = track.getTrackId();
        spotifyId = track.getTrackSpotifyId();
        xboxMusicId = track.getTrackXboxmusicId();
        length = track.getTrackLength();
        commonTrackId = track.getCommontrackId();
        instrumental = track.getInstrumental() == 0;
        lyrics = track.getHasLyrics() == 0;
        subtitles = track.getHasSubtitles() == 0;
        lyricsId = track.getLyricsId();
        releaseDate = track.getFirstReleaseDate();

        artist = track.getArtistName();
        artistid = track.getArtistId();
        artistMbid = track.getArtistMbid();

        if (track.getAlbumId() != 0) album = MXM.getAlbum(track.getAlbumId());

        position = retrieveTrackPosition();
        genres = retrievePrimaryGenre(track);
        cover = new Cover(album).getCover();

    }


    @Override public String getTitle() { return title; }

    @Override public String getArtist() { return artist; }

    @Override public MXMAlbum getAlbum() { return album; }

    @Override public String getAuthor() { return artist; }

    @Override public String getReleaseDate() { return releaseDate; }

    @Override public String getTrackPosition() { return position; }

    @Override public String getGenres() {
        return !genres.isEmpty() ? genres.get(0).getGenreName() : ""; }

    @Override public String getCover() { return cover; }

    public String getMbid() { return mbid; }

    public int getId() { return id; }

    public String getSpotifyId() { return spotifyId; }

    public String getXboxMusicId() { return xboxMusicId; }

    public int getLength() { return length; }

    public int getCommonTrackId() { return commonTrackId; }

    public boolean isInstrumental() { return instrumental; }

    public boolean isLyrics() { return lyrics; }

    public boolean isSubtitles() { return subtitles; }

    public int getLyricsId() { return lyricsId; }

    public int getArtistid() { return artistid; }

    public String getArtistMbid() { return artistMbid; }

    public List<MusicGenre> getPrimaryGenre() { return genres; }

    private String retrieveTrackPosition() {
        int pos = 0;
        List<TrackList> tl = MXM.getAlbumTrackList(album);

        if (tl == null) {
            return "";
        }
        for (TrackList track : tl) {
            if (track.getTrack().getTrackId() == id &&
                    track.getTrack().getTrackName().equals(title))
            {
                pos = tl.indexOf(track) + 1;
            }
        }
        return Integer.toString(pos) + "/" + getAlbum().getTracksCount();
    }

    private ArrayList<MusicGenre> retrievePrimaryGenre(Track track){
        ArrayList<MusicGenre> result = new ArrayList<>(4);
        List<MusicGenreList> temp = track.getPrimaryGenres().getMusicGenreList();
        temp.forEach( mg -> result.add(mg.getMusicGenre()));
        temp = track.getSecondaryGenres().getMusicGenreList();
        temp.forEach( mg -> result.add(mg.getMusicGenre()));
        return result;
    }

    public String toString(){ return title + " " + id + "; " + album.getName() + " " + album.getMbid();}
}
