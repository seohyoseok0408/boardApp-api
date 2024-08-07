package com.smu.board.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.smu.board.dto.BoardResponseDTO;
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
	
//	@Transactional(readOnly = true)
//	public List<Board> 글목록() {
//		return boardRepository.findAll();
//	}
	
	@Transactional(readOnly = true)
    public List<BoardResponseDTO> 글목록() {
        return boardRepository.findAll()
                .stream()
                .map(BoardResponseDTO::new)
                .collect(Collectors.toList());
    }
	
	@Transactional(readOnly = true)
    public BoardResponseDTO 글상세보기(int id) {
        return boardRepository.findById(id).map(BoardResponseDTO::new).orElse(null);
    }
	
	@Transactional
    public void 글삭제(int id) {
        boardRepository.deleteById(id);
    }
	
	 @Transactional
	    public BoardResponseDTO 글수정(int id, Board newBoard) {
	        Board board = boardRepository.findById(id)
	                .orElseThrow(() -> new IllegalArgumentException("Invalid board Id:" + id));
	        
	        board.setTitle(newBoard.getTitle());
	        board.setContent(newBoard.getContent());
	        return new BoardResponseDTO(boardRepository.save(board));
	    }
}
