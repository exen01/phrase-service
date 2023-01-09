package com.exen.example.domain.api.search.searchTags;

import com.exen.example.domain.constant.RegExp;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SearchTagsReq {
    @NotBlank(message = "partTag должен быть заполнен.")
    @Pattern(regexp = RegExp.tag, message = "Некорректный partTag.")
    private String partTag;
}
