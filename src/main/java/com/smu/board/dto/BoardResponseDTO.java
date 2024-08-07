package com.smu.board.dto;

import com.smu.board.model.Board;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class BoardResponseDTO {
    private Integer id;
    private String title;
    private String content;
    private int count;
    private UserResponseDTO user;
    private List<ReplyResponseDTO> replys;
    private LocalDateTime createDate;

    public BoardResponseDTO(Board board) {
        this.id = board.getId();
        this.title = board.getTitle();
        this.content = board.getContent();
        this.count = board.getCount();
        this.user = new UserResponseDTO(board.getUser());
        this.replys = board.getReplys().stream()
                            .map(ReplyResponseDTO::new)
                            .collect(Collectors.toList());
        this.createDate = board.getCreateDate();
    }
}
