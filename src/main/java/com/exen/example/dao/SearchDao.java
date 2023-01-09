package com.exen.example.dao;

import com.exen.example.domain.api.search.searchPhrasesByTag.SearchPhrasesByTagReq;
import com.exen.example.domain.api.common.TagResp;
import com.exen.example.domain.api.user.myPhrases.PhraseResp;

import java.util.List;

public interface SearchDao {
    /**
     * Searches tags by title
     *
     * @param partTag part or full title of tag
     * @return list of tags
     */
    List<TagResp> searchTags(String partTag);

    /**
     * Searches phrases by tag id
     *
     * @param req tag id, sort method
     * @return list of phrases
     */
    List<PhraseResp> searchPhrasesByTag(SearchPhrasesByTagReq req);
}
