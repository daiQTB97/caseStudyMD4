package com.cg.model.dto;

import com.cg.model.User;
import com.cg.model.LocationRegion;
import com.cg.model.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import javax.persistence.Column;
import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Accessors(chain = true)
public class UserDTO implements Validator {

    private String id;

//    @NotBlank(message = "Tên đầy đủ không được để trống, vd: Tran Ba Quoc Dai")
    private String fullName;

    @Column(unique = true, nullable = false)
    private String username;


    @Column(nullable = false)
    private String password;

//    @NotBlank(message = "Email is not blank")
//    @Column(unique = true, nullable = false)
//    private String email;

    private String phone;

    private RoleDTO role;

    @Valid
    private LocationRegionDTO locationRegion;

    private boolean deleted;

    public UserDTO(Long id, String password, String username) {
        this.id = id.toString();
        this.password = password;
        this.username = username;
    }

    public UserDTO(Long id, String password, String username, Role role) {
        this.id = id.toString();
        this.password = password;
        this.username = username;
        this.role = role.toRoleDTO();
    }

    public UserDTO(Long id, String phone) {
        this.id = id.toString();
        this.phone = phone;
    }

    public UserDTO(Long id, String fullName, String username, String password, String phone, Role role, LocationRegion locationRegion) {
        this.id = id.toString();
        this.fullName = fullName;
        this.username = username;
        this.password = password;
        this.phone = phone;
        this.role = role.toRoleDTO();
        this.locationRegion = locationRegion.toLocationRegionDTO();
    }

    public UserDTO(Long id, String fullName, String username, String password, String phone, Role role, LocationRegion locationRegion, boolean delete) {
        this.id = id.toString();
        this.fullName = fullName;
        this.username = username;
        this.password = password;
        this.phone = phone;
        this.role = role.toRoleDTO();
        this.locationRegion = locationRegion.toLocationRegionDTO();
        this.deleted = deleted;
    }

    public User toUser() {
        return new User()
                .setId(Long.parseLong(id))
                .setFullName(fullName)
                .setUsername(username)
                .setPassword(password)
                .setPhone(phone)
                .setRole(role.toRole())
                .setLocationRegion(locationRegion.toLocationRegion())
                .setDeleted(deleted);
    }

    @Override
    public boolean supports(Class<?> aClass) {
      return UserDTO.class.isAssignableFrom(aClass);
    }

    @Override
    public void validate(Object target, Errors errors) {
        UserDTO userDTO = (UserDTO) target;

        String fullNameCheck = userDTO.getFullName();
        String usernameCheck = userDTO.getUsername();
        String passwordCheck = userDTO.getPassword();
        String phoneCheck = userDTO.getPhone();

        if ((fullNameCheck.trim().isEmpty())){
            errors.rejectValue("fullName", "fullName.isEmpty", "Tên đầy đủ không được để trống, vd: Tran Ba Quoc Dai");
            return;
        }

        if ((usernameCheck.trim()).isEmpty()) {
            errors.rejectValue("username", "username.isEmpty", "Vui Lòng Nhập Email Người Dùng");
            return;
        }

        if ((passwordCheck.trim()).isEmpty()) {
            errors.rejectValue("password", "password.isEmpty", "Vui Lòng Nhập Mật Khẩu Người Dùng");
            return;
        }

        if ((phoneCheck.trim()).isEmpty()) {
            errors.rejectValue("phone", "phone.isEmpty", "Vui Lòng Nhập Số Điện Thoại Người Dùng");
            return;
        }

        if ((fullNameCheck.length() < 3 || fullNameCheck.length() > 255)) {
            errors.rejectValue("fullName", "fullName.length", "Tên Từ 3 Đến 255 Ký Tự");
            return;
        }

        if ((usernameCheck.length() > 255)) {
            errors.rejectValue("username", "fullName.length", "Email Tối Đa 255 Ký Tự");
            return;
        }

        if (passwordCheck.length() > 50) {
            errors.rejectValue("password", "password.length", "Mật Khẩu Tối Đa 50 Ký Tự");
            return;
        }

        if (!usernameCheck.matches("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,3}$")) {
            errors.rejectValue("username", "username.matches", "Email Nhập Vào Không Hợp Lệ");
            return;
        }

//        if (!passwordCheck.matches("(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^*])(?!.*['\"`]).{6,}")) {
//            errors.rejectValue("password", "password.matches", "Mật Khẩu Nhập Vào Không Hợp Lệ");
//            return;
//        }


    }
}
