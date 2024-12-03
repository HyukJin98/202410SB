package edu.du.lms1202.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    private String username;
    private String password;
    private String name;
    private String email;
    
    // 비밀번호 확인을 위한 필드 (선택적)
    private String passwordConfirm;
    
    // 회원가입 시 입력받은 정보만 포함하는 생성자
    public UserDto(String username, String password, String name, String email) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.email = email;
    }
} 