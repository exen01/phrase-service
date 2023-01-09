package com.exen.example.domain.api.search.searchUsersByPartNickname;

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
public class SearchUsersByPartNicknameReq {

    @NotBlank(message = "partNickname должен быть заполнен.")
    @Pattern(regexp = RegExp.partNickname, message = "Некорректный partNickname.")
    private String partNickname;
}
