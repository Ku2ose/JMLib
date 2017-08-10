package com.kurose.jmlib.musicBrainz;

import org.musicbrainz.controller.ReleaseGroup;
import org.musicbrainz.model.entity.ReleaseGroupWs2;
import org.musicbrainz.model.searchresult.ReleaseGroupResultWs2;

import java.util.ArrayList;
import java.util.List;

public class MB
{
    public static List<ReleaseGroupWs2> get_RG_from_query(String query) {
        ReleaseGroup search = new ReleaseGroup();
        search.search(query);

        List<ReleaseGroupResultWs2> resultReleaseGroupWs2s = search.getFullSearchResultList();
        List<ReleaseGroupWs2> result = new ArrayList<>(resultReleaseGroupWs2s.size());

        resultReleaseGroupWs2s.forEach(rg -> result.add(rg.getReleaseGroup()));

        return result;
    }

}
