package com.exen.example.domain.api.communication.reaction.comment;

import com.exen.example.domain.constant.RegExp;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommentPhraseReq {

    @DecimalMin(value = "1", message = "Значение phraseId должно быть больше 0.")
    private long phraseId;

    @NotBlank(message = "text должен быть заполнен.")
    @Pattern(regexp = RegExp.phrase, message = "Некорректный text.")
    private String text;
}
