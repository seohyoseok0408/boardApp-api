package com.smu.board.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.smu.board.model.Board;
import com.smu.board.model.User;
import com.smu.board.repository.BoardRepository;

@Service
public class BoardService {
	
	private final BoardRepository boardRepository;
	
	public BoardService(BoardRepository boardRepository) {
		this.boardRepository = boardRepository;
	}
	
	@Transactional
	public void 글쓰기(Board board, User user) { // title, content
		System.out.println("BoardService 글쓰기 함수 실행됨");
		board.setCount(0);
		board.setUser(user);
		boardRepository.save(board);
	}
	
}
