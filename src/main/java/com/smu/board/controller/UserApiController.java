package com.smu.board.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.smu.board.dto.ResponseDto;
import com.smu.board.model.User;
import com.smu.board.service.UserService;

@RestController
public class UserApiController {
	
	@Autowired
	private UserService userService;
	
	@PostMapping("/auth/signup")
	public ResponseDto<Integer> save(@RequestBody User user){
		System.out.println("UserApiController : save 호출됨");
		userService.회원가입(user);
		return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
		// HTTP 상태코드, 커스텀 코드
	}
}

//{
//    "email": "example@example.com",
//    "name": "Example User",
//    "password": "password123",
//    "userType": "STUDENT"
//}