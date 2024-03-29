package com.exen.example.dao.search;

import com.exen.example.domain.api.common.UserResp;
import com.exen.example.domain.api.search.searchPhrasesByPartWord.SearchPhrasesByPartWordReq;
import com.exen.example.domain.api.search.searchPhrasesByTag.SearchPhrasesByTagReq;
import com.exen.example.domain.api.common.TagResp;
import com.exen.example.domain.api.common.PhraseResp;

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
    List<PhraseResp> searchPhrasesByTag(SearchPhrasesByTagReq req, long userId);

    /**
     * Searches phrases by part or full word
     *
     * @param req part or full word, sort method
     * @return list of phrases
     */
    List<PhraseResp> searchPhrasesByPartWord(SearchPhrasesByPartWordReq req, long userId);

    /**
     * Searches users by part or full nickname
     *
     * @param partNickname part or full nickname
     * @return list of users
     */
    List<UserResp> searchUsersByPartNickname(String partNickname);
}
