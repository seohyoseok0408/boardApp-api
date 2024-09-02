package com.smu.board.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReplyDTO {
    private int boardId;  // 댓글이 달린 게시글 ID
    private String content;  // 댓글 내용
}
