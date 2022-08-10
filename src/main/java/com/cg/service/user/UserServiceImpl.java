package com.cg.service.user;

import com.cg.model.LocationRegion;
import com.cg.model.User;
import com.cg.model.UserPrinciple;
import com.cg.model.dto.UserDTO;
import com.cg.repository.LocationRegionRepository;
import com.cg.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private LocationRegionRepository locationRegionRepository;


    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public List<UserDTO> findAllUserDTO() {
        return userRepository.findAllUserDTO();
    }

    @Override
    public Boolean existsById(Long id) {
        return userRepository.existsById(id);
    }

    @Override
    public Boolean existsByEmail(String email) {
        return userRepository.existsByUsername(email);
    }

    @Override
    public Boolean existsByEmailAndIdIsNot(String email, Long id) {
        return userRepository.existsByUsernameAndIdIsNot(email, id);
    }

    @Override
    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public User getById(Long id) {
        return userRepository.getById(id);
    }

    @Override
    public User getByUsername(String username) {
        return userRepository.getByUsername(username);
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public Optional<UserDTO> findUserDTOByEmail(String username) {
        return userRepository.findUserDTOByUsername(username);
    }

    @Override
    public Optional<UserDTO> findUserDTOByEmailPassword(String username) {
        return userRepository.findUserDTOByUsernamePassword(username);
    }

    @Override
    public Optional<UserDTO> findUserDTOByPhone(String phone) {
        return Optional.empty();
    }

    @Override
    public Optional<UserDTO> findUserDTOById(Long id) {
        return userRepository.findUserDTOById(id);
    }


    @Override
    public User save(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        LocationRegion locationRegion = locationRegionRepository.save(user.getLocationRegion());
        user.setLocationRegion(locationRegion);
        return userRepository.save(user);
    }

    @Override
    public User saveNoPassword(User user) {
        LocationRegion locationRegion = locationRegionRepository.save(user.getLocationRegion());
        user.setLocationRegion(locationRegion);
        return userRepository.save(user);
    }

    @Override
    public void remove(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<User> userOptional = userRepository.findByUsername(email);
        if (!userOptional.isPresent()) {
            throw new UsernameNotFoundException(email);
        }
        return UserPrinciple.build(userOptional.get());
    }
}
