package com.exen.example.domain.api.search.searchPhrasesByPartWord;

import com.exen.example.domain.api.user.myPhrases.PhraseResp;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SearchPhrasesByPartWordResp {
    private List<PhraseResp> phrases;
}
