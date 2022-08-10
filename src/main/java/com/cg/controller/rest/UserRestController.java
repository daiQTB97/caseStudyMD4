package com.cg.controller.rest;


import com.cg.exception.DataInputException;
import com.cg.exception.EmailExistsException;
import com.cg.exception.ResourceNotFoundException;
import com.cg.model.User;
import com.cg.model.dto.UserDTO;
import com.cg.service.user.UserService;
import com.cg.util.AppUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserRestController {

    @Autowired
    private AppUtil appUtil;

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<?> getAllUser(){
        List<UserDTO> userDTOList = userService.findAllUserDTO();

        return new ResponseEntity<>(userDTOList, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getUserById(@PathVariable long id) {
        Optional<User> userOptional = userService.findById(id);

        if (!userOptional.isPresent()) {
            throw new ResourceNotFoundException("Invalid user ID");
        }

        return new ResponseEntity<>(userOptional.get().toUserDTO(), HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<?> doCreate(@Validated @RequestBody UserDTO userDTO, BindingResult bindingResult) {

        new UserDTO().validate(userDTO, bindingResult);
        if (bindingResult.hasErrors()) {
            return appUtil.mapErrorToResponse(bindingResult);
        }

        userDTO.setId(String.valueOf(0L));
        userDTO.getLocationRegion().setId(0L);

        Boolean exitsEmail = userService.existsByEmail(userDTO.getUsername());

        if (exitsEmail) {
            throw new EmailExistsException("Email already exists");
        }

        User newUser = userService.save(userDTO.toUser());

        return new ResponseEntity<>(newUser.toUserDTO(), HttpStatus.CREATED);
    }

    @PutMapping("/update")
//    @PreAuthorize("ADMIN")
    public ResponseEntity<?> doUpdate(@Validated @RequestBody UserDTO userDTO, BindingResult bindingResult) {


        if (bindingResult.hasErrors()) {
            return appUtil.mapErrorToResponse(bindingResult);
        }

        Boolean existId = userService.existsById(Long.parseLong(userDTO.getId()));

        if (!existId) {
            throw new ResourceNotFoundException("Customer ID invalid");
        }

        Boolean existEmail = userService.existsByEmailAndIdIsNot(userDTO.getUsername(), Long.parseLong(userDTO.getId()));

        if (existEmail) {
            throw new DataInputException("Email is exist");
        }

        userDTO.getLocationRegion().setId(0L);

        User updatedUser = userService.saveNoPassword(userDTO.toUser());

        return new ResponseEntity<>(updatedUser.toUserDTO(), HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/delete/{id}")
//    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<?> doDelete(@PathVariable Long id) {
        System.out.println("delete method");
        Optional<UserDTO> user = userService.findUserDTOById(id);

        if (user.isPresent()) {
            try {
                user.get().setDeleted(true);
                userService.save(user.get().toUser());
                return new ResponseEntity<>(HttpStatus.ACCEPTED);

            } catch (DataInputException e) {
                throw new DataInputException("Invalid suspension information");
            }
        } else {
            throw new DataInputException("Invalid apartment information");
        }
    }
}
