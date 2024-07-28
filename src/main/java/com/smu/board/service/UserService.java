package com.smu.board.service;

import com.smu.board.model.RoleType;
import com.smu.board.model.User;
import com.smu.board.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

// 스프링이 컴포넌트 스캔을 통해 Bean 등록
@Service
public class UserService {
    
	@Autowired
	private UserRepository userRepository;
    
	@Transactional
    public void 회원가입(User user) {
    	System.out.println("userService의 로그인 함수 실행됨");
    	user.setRole(RoleType.USER);
    	userRepository.save(user);
    }
    
}
//@Transactional
//public User 회원가입(User user) {
//    System.out.println("userService의 회원가입 함수 실행됨");
//    Optional<User> existingUser = userRepository.findByEmail(user.getEmail());
//    if (existingUser.isPresent()) {
//        throw new IllegalArgumentException("이미 존재하는 이메일입니다.");
//    }
//    user.setRole(RoleType.USER);
//    return userRepository.save(user);
//}