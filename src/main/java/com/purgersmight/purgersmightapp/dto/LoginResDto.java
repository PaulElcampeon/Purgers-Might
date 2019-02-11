package com.purgersmight.purgersmightapp.dto;

import com.purgersmight.purgersmightapp.models.Avatar;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.validation.ObjectError;

import java.util.List;

@AllArgsConstructor
@Data
public class LoginResDto {

    private boolean success;
    private List<ObjectError> error;
    private Avatar avatar;

    public LoginResDto() {
    }

    public static LoginResDto getUnsuccessfulLoginResDto(List<ObjectError> errors) {
        return new LoginResDto(false, errors, null);
    }
}
