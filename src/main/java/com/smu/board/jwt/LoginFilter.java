package com.smu.board.jwt;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.security.core.AuthenticationException;

import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

// JWT를 사용한 인증 방식을 구현하기 위해 커스텀 필터 작성.
// UsernamePasswordAuthenticationFilter 얘가 로그인을 가로챔
public class LoginFilter extends UsernamePasswordAuthenticationFilter{
	
	// 인증 요청 처리, 사용자 인증 정보 검증하는 역할.
	// 커스텀 필터에서 사용하기 위해 주입받음
	private final  AuthenticationManager authenticationManager;
	
	public LoginFilter (AuthenticationManager authenticationManager) {
		this.authenticationManager = authenticationManager;
		
		setFilterProcessesUrl("/api/user/login");
	}

	// 로그인 요청을 처리하는 메소드
	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException  {
		try {
            ObjectMapper mapper = new ObjectMapper();
            // ObjectMapper 를 이용해서 request의 입력스트림에서 JSON 데이터 파싱
            Map<String, String> authRequest = mapper.readValue(request.getInputStream(), HashMap.class);
            String username = authRequest.get("username");
            String password = authRequest.get("password");

            // 인증 토큰 생성
            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(username, password, null);

            // 인증 시도
            return authenticationManager.authenticate(authToken);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
	}
	
	@Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication) {
		System.out.println("success");
    }

		//로그인 실패시 실행하는 메소드
    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) {
    	System.out.println("fail");
    }
}
