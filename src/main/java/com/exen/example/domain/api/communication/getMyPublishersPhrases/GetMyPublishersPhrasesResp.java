package com.exen.example.domain.api.communication.getMyPublishersPhrases;

import com.exen.example.domain.api.common.PhraseResp;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetMyPublishersPhrasesResp {
    private List<PhraseResp> phrases;
}
