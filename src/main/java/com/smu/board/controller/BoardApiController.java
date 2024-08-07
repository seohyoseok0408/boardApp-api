package com.smu.board.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.smu.board.config.auth.CustomUserDetails;
import com.smu.board.dto.BoardResponseDTO;
import com.smu.board.dto.ResponseDto;
import com.smu.board.model.Board;
import com.smu.board.service.BoardService;

@RestController
public class BoardApiController {
	
	private final BoardService boardService;
	
	public BoardApiController(BoardService boardService) {
		this.boardService = boardService;
	}
	
	/*
	 * 글쓰기
	 */
	@PostMapping("/post")
	public ResponseDto<Integer> save(@RequestBody Board board, @AuthenticationPrincipal CustomUserDetails customUserDetails) {
		System.out.println("BoardApiController 글쓰기 호출됨");
		System.out.println(customUserDetails);
		boardService.글쓰기(board, customUserDetails.getUser());
		return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
	}
	
	/*
	 * 전체 글 반환
	 */
	@GetMapping("/posts")
    public ResponseDto<List<BoardResponseDTO>> getAllBoards() {
        List<BoardResponseDTO> boardList = boardService.글목록();
        return new ResponseDto<>(HttpStatus.OK.value(), boardList);
    }
	
	/*
	 * 글 상세보기 
	 */
	@GetMapping("/posts/{id}")
    public ResponseDto<BoardResponseDTO> getBoardById(@PathVariable int id) {
        BoardResponseDTO board = boardService.글상세보기(id);
        return new ResponseDto<>(HttpStatus.OK.value(), board);
    }
	
	/*
	 * 글 삭제하기
	 */
	@DeleteMapping("/posts/{id}")
    public ResponseDto<Integer> deleteBoard(@PathVariable int id) {
        boardService.글삭제(id);
        return new ResponseDto<>(HttpStatus.OK.value(), 1);
    }
	
	/*
	 * 글 수정하기
	 */
	@PutMapping("/posts/{id}")
    public ResponseDto<BoardResponseDTO> updateBoard(@PathVariable int id, @RequestBody Board newBoard) {
        BoardResponseDTO updatedBoard = boardService.글수정(id, newBoard);
        return new ResponseDto<>(HttpStatus.OK.value(), updatedBoard);
    }
//	@GetMapping("/posts")
//	public List<Board> getAllBoards(Model model) {
//		return boardService.글목록();
//		}
	
}

