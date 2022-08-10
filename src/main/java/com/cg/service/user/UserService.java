package com.cg.service.user;

import com.cg.model.User;
import com.cg.model.dto.UserDTO;
import com.cg.service.IGeneralService;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;
import java.util.Optional;

public interface UserService extends IGeneralService<User>, UserDetailsService {

    User getByUsername(String email);

    List<UserDTO> findAllUserDTO();

    Optional<User> findByUsername(String email);

    Optional<UserDTO> findUserDTOByEmail(String email);

    Optional<UserDTO> findUserDTOByEmailPassword(String email);

    Optional<UserDTO> findUserDTOByPhone(String email);

    Optional<UserDTO> findUserDTOById(Long id);

    Boolean existsById(Long id);

    Boolean existsByEmail(String email);

    Boolean existsByEmailAndIdIsNot(String email, Long id);

    User saveNoPassword(User user);
}
