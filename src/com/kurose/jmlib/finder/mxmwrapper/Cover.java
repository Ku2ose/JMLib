package com.kurose.jmlib.finder.mxmwrapper;

import com.kurose.jmlib.musicBrainz.MB;
import fm.last.musicbrainz.coverart.CoverArt;
import fm.last.musicbrainz.coverart.CoverArtArchiveClient;
import fm.last.musicbrainz.coverart.impl.DefaultCoverArtArchiveClient;
import org.musicbrainz.model.entity.ReleaseGroupWs2;

import java.util.List;
import java.util.UUID;

public class Cover {

    private String cover;

    public String getCover() {
        return cover;
    }

    public Cover(MXMAlbum album) {

        CoverArtArchiveClient client = new DefaultCoverArtArchiveClient(true);

        if (album.getMbid() != null && !album.getMbid().equals("")) {
            CoverArt art = client.getByMbid(UUID.fromString(album.getMbid()));

            if (art != null) {
                if (art.getFrontImage() != null) {
                    cover = art.getFrontImage().getImageUrl();
                }
            }else
            {
                cover = null;
            }
            System.out.println("R Cover link " + cover);

            // tODO qui potrei fare in modo che quando cover e' null allora cerca la release su musicbrainz
            // e da quella ottiene la releasegroup e cerca la cover con quella

        } else {

            String mbid = "";
            String query = componeQuery(album.getName(), album.getArtistName());

            List<ReleaseGroupWs2> temp = MB.get_RG_from_query(query);

            if (!temp.isEmpty()) {
                mbid = temp.get(0).getId();
            } else {
                cover = null;
                return;
            }

            CoverArt art = client.getReleaseGroupByMbid(UUID.fromString(mbid));

            if (art != null) {
                if (art.getFrontImage() != null) {
                    cover = art.getFrontImage().getImageUrl();
                }
            }else
            {
                cover = null;
            }
            System.out.println("RG Cover link " + cover);
        }
    }


    private String componeQuery(String name, String artist) {
        String result;

        result = String.format("%s AND artist:\"%s\" AND primarytype:\"%s\" AND status:\"%s\"",
                name,
                artist,
                "album",
                "official"
                );

        return result;
    }

}
