package com.kurose.jmlib.finder.mxmwrapper;

import com.kurose.jmlib.util.Refs;
import com.myjmxm.core.MusixMatch;
import com.myjmxm.core.MusixMatchException;
import com.myjmxm.entity.track.TrackList;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

public class MXM {

    private static MusixMatch mxm = new MusixMatch(Refs.MXM_API_KEY);

    public static MXMTrack getMatchingTrack(String artist, String title) {// TODO; implement this methos on jmxm with the album parameter
        MXMTrack t = null;
        try {
            t = new MXMTrack( mxm.getMatchingTrack(title, artist) );
        } catch (MusixMatchException e) {
            e.printStackTrace();
            debugAlert(e);
        }
        return t;
    }

    public static List<MXMTrack> track_Search(String q_artist, String q_title) {
        List<TrackList> temp = new ArrayList<>();
        List<MXMTrack> res = new ArrayList<>(Refs.MAX_MXM_FILTER);
        try {
            temp = mxm.searchTracks("", q_artist, q_title, 1, Refs.MAX_MXM_FILTER, false);
        } catch (MusixMatchException e) {
            debugAlert(e);
        }
        temp.forEach( t -> res.add(new MXMTrack(t.getTrack())));

        return res;
    }

    public static MXMAlbum getAlbum(int albumId) {
        MXMAlbum album = null;
        try {
            album = new MXMAlbum(mxm.getAlbum(albumId));
        } catch (MusixMatchException e) {
            debugAlert(e).showAndWait();
        }
        return album;
    }

    public static List<TrackList> getAlbumTrackList(MXMAlbum album)
    {
        List<TrackList> tracks = null;

        try {
            tracks = mxm.getAlbumTrackList(album.getId(), 1, album.getTracksCount(), false);
        } catch (MusixMatchException e) {
            debugAlert(e);
        }
        return tracks;
    }


    private static Alert debugAlert(MusixMatchException exception) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);

        exception.printStackTrace(pw);

        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.getDialogPane().setContent(null);
        alert.setTitle("MusixMatch Exception Thrown");
        alert.setHeaderText(exception.getMessage());
        TextArea ta = new TextArea(sw.toString());
        ta.setEditable(false);
        ta.setWrapText(true);
        ta.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        alert.getDialogPane().setExpandableContent(ta);

        return alert;
    }
}
