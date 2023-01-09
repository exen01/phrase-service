package com.exen.example.domain.api.search.searchPhrasesByPartWord;

import com.exen.example.domain.constant.RegExp;
import com.exen.example.domain.constant.Sort;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SearchPhrasesByPartWordReq {

    @NotBlank(message = "partWord должен быть заполнен.")
    @Pattern(regexp = RegExp.partWord, message = "Некорректный partWord")
    private String partWord;

    @NotNull(message = "sort должен быть заполнен.")
    private Sort sort;
}
