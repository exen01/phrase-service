package com.exen.example.domain.api.communication.subscribe.unsubscription;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.DecimalMin;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UnsubscriptionReq {

    @DecimalMin(value = "1", message = "Значение pubUserId должно быть меньше нуля.")
    private long pubUserId;
}
