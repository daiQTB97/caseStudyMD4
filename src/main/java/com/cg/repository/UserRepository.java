package com.cg.repository;

import com.cg.model.User;
import com.cg.model.dto.UserDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User getByUsername(String email);
    Optional<User> findByUsername(String email);

    @Query("SELECT new com.cg.model.dto.UserDTO (" +
            "u.id, " +
            "u.fullName, " +
            "u.username, " +
            "u.password, " +
            "u.phone, " +
            "u.role, " +
            "u.locationRegion " +
            ") " +
            "FROM User AS u"
    )
    List<UserDTO> findAllUserDTO();

    Boolean existsByUsername(String email);

    Boolean existsByUsernameAndIdIsNot(String username, Long id);

    @Query("SELECT NEW com.cg.model.dto.UserDTO (u.id, u.username,u.fullName) FROM User u WHERE u.username = ?1")
    Optional<UserDTO> findUserDTOByUsername(String username);

    @Query("SELECT NEW com.cg.model.dto.UserDTO (u.id, u.username,u.password,u.role) FROM User u WHERE u.username = ?1")
    Optional<UserDTO> findUserDTOByUsernamePassword(String username);

    @Query("SELECT NEW com.cg.model.dto.UserDTO (u.id, u.phone) FROM User u WHERE u.phone = ?1")
    Optional<UserDTO> findUserDTOByPhone(String phone);

    @Query("SELECT NEW com.cg.model.dto.UserDTO (u.id,u.fullName, u.username,u.password,u.phone,u.role,u.locationRegion) FROM User u WHERE u.id = ?1")
    Optional<UserDTO> findUserDTOById(Long id);




}
