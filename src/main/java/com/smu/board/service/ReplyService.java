package com.smu.board.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.smu.board.model.Board;
import com.smu.board.model.Reply;
import com.smu.board.model.User;
import com.smu.board.repository.BoardRepository;
import com.smu.board.repository.ReplyRepository;

@Service
public class ReplyService {
	private final ReplyRepository replyRepository;
	private final BoardRepository boardRepository;
	
	public ReplyService(ReplyRepository replyRepository, BoardRepository boardRepository) {
		this.replyRepository = replyRepository;
		this.boardRepository = boardRepository;
	}
	
	@Transactional
	public void saveReply(int boardId, Reply reply, User user) {
		System.out.println("댓글쓰기 서비스 호출됨");
		Board board = boardRepository.findById(boardId).orElseThrow(() -> new IllegalArgumentException("Invalid board ID"));
		reply.setBoard(board);
		reply.setUser(user);
		replyRepository.save(reply);
	
	}
}
