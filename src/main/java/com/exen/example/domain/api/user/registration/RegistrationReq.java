package com.exen.example.domain.api.user.registration;

import com.exen.example.domain.api.user.common.AuthorizationReq;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegistrationReq {
    @Valid
    @NotNull(message = "authorization должен быть заполнен.")
    private AuthorizationReq authorization;
}
