package com.smu.board.jwt;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StreamUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.smu.board.config.auth.CustomUserDetails;
import com.smu.board.dto.LoginDTO;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletInputStream;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

// JWT를 사용한 인증 방식을 구현하기 위해 커스텀 필터 작성.
// UsernamePasswordAuthenticationFilter 얘가 로그인을 가로챔
public class LoginFilter extends UsernamePasswordAuthenticationFilter{
	
	// 인증 요청 처리, 사용자 인증 정보 검증하는 역할.
	// 커스텀 필터에서 사용하기 위해 주입받음
	private final  AuthenticationManager authenticationManager;
	private final JWTUtil jwtUtil;
	
	public LoginFilter (AuthenticationManager authenticationManager, JWTUtil jwtUtil) {
		this.authenticationManager = authenticationManager;
		this.jwtUtil = jwtUtil;
		
		setFilterProcessesUrl("/auth/login");
	}

	// 로그인 요청을 처리하는 메소드
	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException  {
		LoginDTO loginDTO = new LoginDTO();
		try {
			ObjectMapper objectMapper = new ObjectMapper();
		    ServletInputStream inputStream = request.getInputStream();
		    String messageBody = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);
		    loginDTO = objectMapper.readValue(messageBody, LoginDTO.class);
			
            String username = loginDTO.getUsername();
            String password = loginDTO.getPassword();

            // 인증 토큰 생성
            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(username, password, null);

            // 인증 시도
            return authenticationManager.authenticate(authToken);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
	}
	
	//로그인 성공시 실행하는 메소드
	@Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication) {
		CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();	
		String username = customUserDetails.getUsername();
		Integer id = customUserDetails.getUser().getId();
		
		Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        Iterator<? extends GrantedAuthority> iterator = authorities.iterator();
        GrantedAuthority auth = iterator.next();

        String role = auth.getAuthority();
		
        String token = jwtUtil.createJwt(username, role, id, 10 * 60 * 60 * 1000L); // 10시간 설정
        
        response.addHeader("Authorization", "Bearer " + token);
        
		System.out.println("success");
    }

	//로그인 실패시 실행하는 메소드
    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) {
    	response.setStatus(401);
    	
    	System.out.println("fail");
    }
}
