package com.midnight1hour.adapter.in.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class LoginDto {
       @NotBlank(message = "아이디를 입력해주세요.")
       @Size(max = 15, min = 4, message = "아이디는 4자이상, 15자 이하입니다.")
       private String userId;

       @NotBlank(message = "비밀번호를 입력해주세요.")
       @Size(max = 20, min = 8, message = "비밀번호는 8자 이상, 20자 이하입니다.")
       @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$"
               , message = "비밀번호는 대,소문자, 숫자, 특수문자를 포함한 8자 이상이어야 합니다.")
       private String userPw;
}
