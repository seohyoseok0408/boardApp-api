package com.smu.board.jwt;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import com.smu.board.config.auth.CustomUserDetails;
import com.smu.board.model.RoleType;
import com.smu.board.model.User;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class JWTFilter extends OncePerRequestFilter{
	
	private JWTUtil jwtUtil;
	
	public JWTFilter(JWTUtil jwtUtil) {
		this.jwtUtil = jwtUtil;
	}
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		String authorization = request.getHeader("Authorization");
		
		if (authorization == null || !authorization.startsWith("Bearer")) {

            System.out.println("token null");
            filterChain.doFilter(request, response);
						
			//조건이 해당되면 메소드 종료 (필수)
            return;
        }
		
		System.out.println("authorization now");
		
		// Bearer 부분 제거 후 순수 토큰 획득
		String token = authorization.split(" ")[1];
		
		// 토큰 소멸 시간 검증
		if (jwtUtil.isExpired(token)) {

            System.out.println("token expired");
            filterChain.doFilter(request, response);

			//조건이 해당되면 메소드 종료 (필수)
            return;
        }
		
		String username = jwtUtil.getUsername(token);
        String role = jwtUtil.getRole(token);
        Integer id = jwtUtil.getId(token);
        
        System.out.println(username+role+id);
        
        RoleType roleType;
        roleType = RoleType.valueOf(role);
		
		//userEntity를 생성하여 값 set
        User user = new User();
        user.setId(id);
        user.setUsername(username);
        user.setPassword("temppassword");
        user.setRole(roleType);
        
		//UserDetails에 회원 정보 객체 담기
        CustomUserDetails customUserDetails = new CustomUserDetails(user);
        
		//스프링 시큐리티 인증 토큰 생성
        Authentication authToken = new UsernamePasswordAuthenticationToken(customUserDetails, null, customUserDetails.getAuthorities());
		
        //세션에 사용자 등록
        SecurityContextHolder.getContext().setAuthentication(authToken);
        System.out.println("세션에 사용자 등록됨");

        filterChain.doFilter(request, response);
	}
	
}
