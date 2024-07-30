package com.smu.board.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.smu.board.model.User;

import java.util.Optional;

// DAO (데이터 접근 객체)
// 자동으로 bean 등록이 됨 = IoC 컨테이너에 의해 관리됨
// JpaRepository 를 상속받기에 @Repository 안써도 됨.
public interface UserRepository extends JpaRepository<User, Integer> {
    User findByUsername(String username); // 이메일로 유저를 찾는 메소드
    
    User findByUsernameAndPassword(String username, String password);
    
    Boolean existsByUsername(String username);
}
