package com.exen.example.domain.api.communication.reaction.getBlockUsers;

import com.exen.example.domain.api.common.UserResp;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetBlockUsersResp {
    private List<UserResp> blockedUsers;
}
