package com.exen.example.dao;

import com.exen.example.domain.api.search.searchTags.TagResp;

import java.util.List;

public interface SearchDao {
    /**
     * Searches tags by title
     *
     * @param partTag part or full title of tag
     * @return list of tags
     */
    List<TagResp> searchTags(String partTag);
}
