package com.exen.example.domain.api.search.searchPhrasesByTag;

import com.exen.example.domain.constant.Sort;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SearchPhrasesByTagReq {

    @DecimalMin(value = "1", message = "Значение tagId должно быть больше 0.")
    private long tagId;

    @NotNull(message = "Sort должен быть заполнен.")
    private Sort sort;
}
