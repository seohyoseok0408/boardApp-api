package com.smu.board.dto;

import com.smu.board.model.User;
import lombok.Data;

@Data
public class UserResponseDTO {
    private Integer id;
    private String username;
    private String name;
    private String role;
    private String userType;

    public UserResponseDTO(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.name = user.getName();
        this.role = user.getRole().toString();
        this.userType = user.getUserType().toString();
    }
}
