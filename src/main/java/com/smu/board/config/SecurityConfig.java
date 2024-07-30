package com.smu.board.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.smu.board.jwt.LoginFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	
	@Autowired
	AuthenticationConfiguration authenticationConfiguration;
	
	@Bean
	public BCryptPasswordEncoder encodePWD() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
		return authenticationConfiguration.getAuthenticationManager();
	}

	@Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable()); // CSRF 비활성화
            
       http
       		.formLogin(form -> form.disable()); // 폼 로그인을 사용하지 않음. JSON 기반의 로그인 처리를 구현하기 위함
       	
       	http
       		.httpBasic(httpBasic -> httpBasic.disable()); // 기본 인증 비활성화
        
        http
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/auth/**").permitAll() // 인증이 필요없는 엔드포인트 설정
                .requestMatchers("/admin/**").hasRole("ADMIN")
                .anyRequest().authenticated() // 나머지 요청은 인증 필요
            );
            
        http
        	.addFilterAt(new LoginFilter(authenticationManager(authenticationConfiguration)), UsernamePasswordAuthenticationFilter.class);
        
        http
        	.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)); // 세션을 사용하지 않음
        
        return http.build();
    }

}
