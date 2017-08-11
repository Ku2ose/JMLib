package com.kurose.jmlib.song;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class Sorter {

    private static List<Song> lib = new ArrayList<>(0);

    private static final String ALL_MATCHES = "***";
    private static final String NO_TAG = "Unknown tag";

    private static List<String> listArtists = new ArrayList<>();
    private static List<Song> listAlbums = new ArrayList<>();
    private static List<Song> listTracks = new ArrayList<>();

    public Sorter(List<Song> source) {
        lib = source;
    }

    public static List<String> sortArtists() {

        listArtists = new ArrayList<>(0);
        lib.forEach(s -> {
            if (!listArtists.contains(s.getMetadata().getAuthor())) {
                if (s.getMetadata().getAuthor() != null &&
                        !s.getMetadata().getAuthor().equals(""))
                {
                    listArtists.add(s.getMetadata().getAuthor());
                } else {
                    if (!listArtists.contains(NO_TAG)) {
                        listArtists.add(NO_TAG);
                    }
                }
            }
        });
        Collections.sort(listArtists);
        return listArtists;
    }

    public static List<String> sortAlbums(String artist) {

        listAlbums = new ArrayList<>();
        List<String> albums = new ArrayList<>();

        if (artist == null) return albums;

        // first make a list of song with only the songs from the selected artists
        if (!artist.equals(NO_TAG)) {
            // check and add the tracks that have a autor tag
            lib.forEach(s -> {
                if (s.getMetadata().getAuthor() == null) return;
                if (s.getMetadata().getAuthor().equals(artist)) {
                    listAlbums.add(s);
                }
            });
        } else {
            // checks and adds the tags that have no author
            lib.forEach(s -> {
                if (s.getMetadata().getAuthor() == null) {
                    listAlbums.add(s);
                }
            });
        }

        // from the previous list extract a list of names of the albums to use as indices in the corresponding listView
        listAlbums.forEach(s -> {
            if (s.getMetadata().getAlbum() != null ) {
                if (!albums.contains(s.getMetadata().getAlbum())){
                    albums.add(s.getMetadata().getAlbum());
                }
            } else {
                if (!albums.contains(NO_TAG)) {
                    albums.add(NO_TAG);
                }
            }
        });

        // sort the list anc add all album option. Since the list is so small those two step won't be very heavy
        Collections.sort(albums);
        albums.add(0, ALL_MATCHES);

        return albums;
    }

    public static List<Song> sortTracks(String album) {
        listTracks = new ArrayList<>();
        //List<String> tracks = new ArrayList<>();
        if (album == null) return new ArrayList<>();


        // make first the list with the songs
        if (album.equals(ALL_MATCHES)) {
            if (listAlbums.size() >= 1) listTracks = listAlbums;
        } else if (album.equals(NO_TAG)) {
            if (listAlbums.size() >= 1) {
                listAlbums.forEach(s -> {
                    if ((s.getMetadata().getAlbum() == null || s.getMetadata().getAlbum().equals(""))
                            && !listTracks.contains(s) ){
                        listTracks.add( s );
                    }
                });
            }
        } else {
            if (listAlbums.size() >= 1) {
                listAlbums.forEach(s -> {
                    if (s.getMetadata().getAlbum() == null) return;
                    if (s.getMetadata().getAlbum().equals(album) && !listTracks.contains(s)) {
                        listTracks.add(s);
                    }
                });
            }
        }

        // sorting the processed list
        // at the moment i prefer leaving the songs ordered by their name when all matches il selected
        if (!album.equals(ALL_MATCHES)) {
            listTracks.sort(Song.compareByTrack());
        } else {
            listTracks.sort(Song.compareByAlbum());
        }

        return listTracks;
    }

    public static void updateLib(List<Song> newLib){
        lib = newLib;
    }

}
