package com.smu.board.service;

import com.smu.board.model.RoleType;
import com.smu.board.model.User;
import com.smu.board.repository.UserRepository;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

// 스프링이 컴포넌트 스캔을 통해 Bean 등록
@Service
public class UserService {
	
	private final UserRepository userRepository;
	private final BCryptPasswordEncoder encoder;
    
	public UserService(UserRepository userRepository, BCryptPasswordEncoder encoder) {
		this.userRepository = userRepository;
		this.encoder = encoder;
	}
	
    @Transactional
    public int 회원가입(User user) {
        System.out.println("UserService: 회원가입 함수 실행됨");
        
        Boolean isExist = userRepository.existsByUsername(user.getUsername()); // 이메일이 존재하는지 확인
        
        if (isExist) { // 이메일이 이미 존재함
        	return -1;
        }
        
        String rawPassword = user.getPassword();
        String encPassword = encoder.encode(rawPassword);
        user.setPassword(encPassword);
        user.setRole(RoleType.USER);
        try {
            userRepository.save(user);
            return 1;
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

}
