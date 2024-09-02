package com.smu.board.controller;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.smu.board.config.auth.CustomUserDetails;
import com.smu.board.dto.ReplyDTO;
import com.smu.board.dto.ResponseDto;
import com.smu.board.model.Reply;
import com.smu.board.service.ReplyService;

@RestController
public class ReplyApiController {
	private final ReplyService replyService;
    
    public ReplyApiController(ReplyService replyService) {
        this.replyService = replyService;
    }
    
    @PostMapping("/board/{boardId}/reply")
    public ResponseDto<Integer> 댓글쓰기(@PathVariable int boardId, @RequestBody ReplyDTO replyDTO, @AuthenticationPrincipal CustomUserDetails customUserDetails) {
    	System.out.println("댓글쓰기 호출됨");
    	Reply reply = Reply.builder()
                .content(replyDTO.getContent())
                .build();
		replyService.saveReply(boardId, reply, customUserDetails.getUser());
		return new ResponseDto<>(HttpStatus.OK.value(), 1);
    }
}
