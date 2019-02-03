package com.purgersmight.purgersmightapp.dto;

import com.purgersmight.purgersmightapp.models.Avatar;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.validation.ObjectError;

import java.util.List;

@Data
@AllArgsConstructor
public class CreateNewUserResDto {

    private boolean success;
    private List<ObjectError> error;
    private Avatar avatar;

}
