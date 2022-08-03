package com.cg.controller.rest;

import com.cg.exception.DataInputException;
import com.cg.exception.EmailExistsException;
import com.cg.model.User;
import com.cg.model.JwtResponse;
import com.cg.model.dto.LocationRegionDTO;
import com.cg.model.dto.RoleDTO;
import com.cg.model.dto.UserDTO;
import com.cg.model.dto.UserLoginDTO;
import com.cg.service.jwt.JwtService;
import com.cg.service.role.IRoleService;
import com.cg.service.user.UserService;
import com.cg.util.AppUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
public class AuthRestController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private UserService userService;

    @Autowired
    private IRoleService roleService;

    @Autowired
    private AppUtil appUtils;


    @PostMapping("/register")
    public ResponseEntity<?> register() {

        UserDTO userDTO = new UserDTO();
        userDTO.setFullName("Dai");
        userDTO.setUsername("dai@co.cc");
        userDTO.setPassword("123");
        userDTO.setRole(new RoleDTO().setId("1"));
        userDTO.setLocationRegion(new LocationRegionDTO().setId(1));



        Optional<UserDTO> optUser = userService.findUserDTOByEmail(userDTO.getUsername());

        if (optUser.isPresent()) {
            throw new EmailExistsException("Email đã tồn tại");
        }

        Optional<UserDTO> opUserByPhone = userService.findUserDTOByPhone(userDTO.getPhone());

        if (opUserByPhone.isPresent()) {
            throw new EmailExistsException("Số điện thoại này đã tồn tại");
        }

        try {
//            RoleDTO roleDTO = new RoleDTO();
//            roleDTO.setId("1");
//            userDTO.setRoleDTO(roleDTO);
//            userDTO.getLocationRegion().setId(0L);
//            userDTO.setId("0");
            User user = userDTO.toUser();
            userService.save(user);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (DataIntegrityViolationException e) {
            throw new DataInputException("Vui Lòng Liên Hệ Quản Trị Viên Hệ Thống");
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User user) {

//        User user = userLoginDTO.toUser();

        System.out.println("Xu ly sau khi login");
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = jwtService.generateTokenLogin(authentication);
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        User currentUser = userService.getByUsername(user.getUsername());

        JwtResponse jwtResponse = new JwtResponse(
                jwt,
                currentUser.getId(),
                userDetails.getUsername(),
                currentUser.getUsername(),
                userDetails.getAuthorities()
        );

        ResponseCookie springCookie = ResponseCookie.from("JWT", jwt)
                .httpOnly(false)
                .secure(false)
                .path("/")
                .maxAge(60 * 1000)
                .domain("localhost")
//                .domain("ajax-bank-location-jwt.herokuapp.com")
//                .domain("bank-transaction.azurewebsites.net")
                .build();

        System.out.println(jwtResponse);

        return ResponseEntity
                .ok()
                .header(HttpHeaders.SET_COOKIE, springCookie.toString())
                .body(jwtResponse);
    }
}
