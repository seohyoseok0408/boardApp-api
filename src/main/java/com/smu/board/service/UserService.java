package com.smu.board.service;

import com.smu.board.model.RoleType;
import com.smu.board.model.User;
import com.smu.board.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

// 스프링이 컴포넌트 스캔을 통해 Bean 등록
@Service
public class UserService {
    
	@Autowired
	private UserRepository userRepository;
    
	@Autowired
	private BCryptPasswordEncoder encoder;
	
	@Transactional
    public int 회원가입(User user) {
    	System.out.println("userService의 로그인 함수 실행됨");
    	String rawPassword = user.getPassword();
    	String encPassword = encoder.encode(rawPassword);
    	user.setPassword(encPassword);
    	user.setRole(RoleType.USER);
    	try {
    		userRepository.save(user);
    		return 1;
    	} catch (Exception e) {
    		return -1;
    	}
    	
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