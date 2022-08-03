package com.cg.model.dto;

import com.cg.model.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserLoginDTO {
    private String username;
    private String password;

    public User toUser() {
        return new User()
                .setUsername(username)
                .setPassword(password);
    }
}
