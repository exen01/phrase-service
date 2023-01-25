package com.exen.example.domain.api.common;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommentResp {
    private long userId;
    private String nickname;
    private long commentId;
    private String text;
    private String timeInsert;
}
