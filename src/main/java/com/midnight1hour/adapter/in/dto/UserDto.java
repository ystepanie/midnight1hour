package com.midnight1hour.adapter.in.dto;

import com.midnight1hour.util.MessageUtil;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class UserDto {
    @NotBlank(message = MessageUtil.BLANK_ID)
    @Size(min = 4, max = 15, message = MessageUtil.INVALID_LENGTH_ID)
    private String userId;

    @NotBlank(message = MessageUtil.BLANK_PASSWORD)
    @Size(min = 8, max = 15, message = MessageUtil.INVALID_LENGTH_PASSWORD)
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$",
            message = MessageUtil.INVALID_PASSWORD)
    private String userPw;

    @NotBlank(message = MessageUtil.BLANK_PASSWORD_CONFIRM)
    private String userPwConfirm;

    @NotBlank(message = MessageUtil.BLANK_PHONENUMBER)
    @Pattern(regexp = "^01(?:0|1|[6-9])-(?:\\d{4}|\\d{3})-\\d{4}$",
            message = MessageUtil.INVALID_PHONENUMBER)
    private String phoneNumber;
}
