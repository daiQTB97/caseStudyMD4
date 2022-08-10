package com.cg.model.dto;

import com.cg.model.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import javax.validation.constraints.NotNull;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Accessors(chain = true)
public class RoleDTO implements Validator {

    @NotNull(message = "id phải chắc chắn là có tồn tại")
    private String id;

    private String code;
    private String name;

    public Role toRole() {
        return new Role()
                .setId(Long.parseLong(id))
                .setCode(code)
                .setName(name);
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return false;
    }

    @Override
    public void validate(Object o, Errors errors) {

    }
}
