package com.smu.board.config.auth;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.smu.board.model.User;
import com.smu.board.repository.UserRepository;

// 사용자 인증을 처리하기 위한 서비스 클래스
@Service
public class CustomUserDetailsService implements UserDetailsService{
	
	private final UserRepository userRepository;
	
	public CustomUserDetailsService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User userData = userRepository.findByUsername(username);
		
		if (userData != null) { 
			return new CustomUserDetails(userData);
		}
		
		return null;
	}
	
}
