package com.smu.board.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.smu.board.model.Board;

public interface BoardRepository extends JpaRepository<Board, Integer>{

}
