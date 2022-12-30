package com.exen.example.domen.api;

import com.exen.example.domen.constant.RegExp;
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
public class LoginReq {

    @NotBlank(message = "nickname должен быть заполнен.")
    @Pattern(regexp = RegExp.nickname, message = "Некорректный nickname.")
    private String nickname;

    @NotBlank(message = "password должен быть заполнен.")
    @Pattern(regexp = RegExp.password, message = "Некорректный password.")
    private String password;
}
