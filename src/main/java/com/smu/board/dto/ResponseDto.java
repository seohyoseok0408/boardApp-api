package com.smu.board.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

// DTO : 데이터 전송 객체

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResponseDto<T> { // 제네릭 타입
	int status;  // HTTP 상태 코드 필드
	T data; // 응답 데이터의 실제 내용을 저장 (T는 다양한 데이터타입 지원해줌)
}
