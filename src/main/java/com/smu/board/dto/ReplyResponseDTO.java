package com.smu.board.dto;

import com.smu.board.model.Reply;
import lombok.Data;

@Data
public class ReplyResponseDTO {
    private Integer id;
    private String content;
    private UserResponseDTO user;

    public ReplyResponseDTO(Reply reply) {
        this.id = reply.getId();
        this.content = reply.getContent();
        this.user = new UserResponseDTO(reply.getUser());
    }
}
