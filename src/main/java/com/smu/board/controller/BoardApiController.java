package com.smu.board.controller;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.smu.board.config.auth.CustomUserDetails;
import com.smu.board.dto.ResponseDto;
import com.smu.board.model.Board;
import com.smu.board.service.BoardService;

@RestController
public class BoardApiController {
	
	private final BoardService boardService;
	
	public BoardApiController(BoardService boardService) {
		this.boardService = boardService;
	}
	
	@PostMapping("/board")
	public ResponseDto<Integer> save(@RequestBody Board board, @AuthenticationPrincipal CustomUserDetails customUserDetails) {
		System.out.println("BoardApiController 글쓰기 호출됨");
		System.out.println(customUserDetails);
		boardService.글쓰기(board, customUserDetails.getUser());
		return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
	}
}
