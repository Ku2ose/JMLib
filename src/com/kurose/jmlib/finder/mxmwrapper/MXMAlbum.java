package com.kurose.jmlib.finder.mxmwrapper;

import com.kurose.jmlib.finder.entity.MyAlbum;
import com.myjmxm.entity.album.Album;

import java.util.List;

public class MXMAlbum implements MyAlbum {

    private final String name;
    private final int    id;
    private final String mbid;
    private final String releaseDate;
    private final String type;
    private final String pline;
    private final String label;
    private final int    tracksCount;

    private final int    artistId;
    private final String artistName;


    public MXMAlbum(Album album) {
        this.name = album.getName();
        this.id = album.getId();
        this.mbid = album.getAlbumMbid();
        this.releaseDate = album.getReleaseDate();
        this.type = album.getType();
        this.pline = album.getPline();
        this.label = album.getLabel();
        this.tracksCount = album.getTrackCount();
        this.artistId = album.getArtistId();
        this.artistName = album.getArtistName();
    }



    @Override public String getName() { return name; }

    @Override public String getArtist() { return artistName; }

    @Override public String getReleaseDate() { return releaseDate; }

    @Override public String getType() { return type; }

    @Override public String getLabel() { return label; }

    @Override public List<String> getCovers() { return null; }

    @Override public int getTracksCount() { return tracksCount; }

    public int getId() { return id; }

    public String getMbid() { return mbid; }

    public String getPline() { return pline; }

    public int getArtistId() { return artistId; }

    public String getArtistName() { return artistName; }


}
