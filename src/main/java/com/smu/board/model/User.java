package com.smu.board.model;

import java.sql.Timestamp;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(nullable = false, length = 100, unique = true)
	private String username; // 담기는 값은 이메일 -> 로그인 id 로 쓰일 것
	
	@Column(nullable = false, length = 100)
	private String name; 
	
	@Column(nullable = false, length = 100)
	private String password;
	
	@Enumerated(EnumType.STRING)
	private RoleType role;
	
	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private UserType userType; // 사용자 유형 : STUDENT, PROFESSOR, ASSISTANT
	
	@CreationTimestamp
	private Timestamp createDate;
}
