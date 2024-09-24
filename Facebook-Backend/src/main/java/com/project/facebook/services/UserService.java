package com.project.facebook.services;

import java.util.List;
import java.util.Optional;

import com.project.facebook.components.JwtTokenUtils;
import com.project.facebook.components.LocalizationUtils;
import com.project.facebook.exceptions.DataNotFoundException;

import com.project.facebook.utils.MessageKeys;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.project.facebook.dtos.UserDTO;
import com.project.facebook.exceptions.PermissionDenyException;
import com.project.facebook.models.Role;
import com.project.facebook.models.User;
import com.project.facebook.repositories.RoleRepository;
import com.project.facebook.repositories.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    // Da duoc cau hinh Bcrypt trong UserDetail
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenUtils jwtTokenUtil;
    private final AuthenticationManager authenticationManager;
    private final LocalizationUtils localizationUtils;
    @Override
    public List<User> getAllUsers() {
        //chuyen sang stream de co the map, sau do chuyen lai list
        return userRepository.findAll();
    }

    @Override
    public User createUser(UserDTO userDTO) throws Exception {
        String phoneNumber = userDTO.getPhoneNumber();
        if(userRepository.existsByPhoneNumber(phoneNumber)){
            throw new DataIntegrityViolationException(localizationUtils.getLocalizedMessage(MessageKeys.PHONE_NUMBER_ALREADY_EXISTS));
        }
        Role role = roleRepository.findById(userDTO.getRoleId())
            .orElseThrow(() -> new DataNotFoundException(localizationUtils.getLocalizedMessage(MessageKeys.ROLE_NOT_EXISTS)));
        if(role.getRoleName().equals("ADMIN")){
            throw new PermissionDenyException(localizationUtils.getLocalizedMessage(MessageKeys.PERMISSION_DENIED));
        }
        String password = userDTO.getPassword();
        // ma hoa password da cau hinh trong UserDetail
        String encodedPassword = passwordEncoder.encode(password);
        User user = User.builder()
                .username(userDTO.getUsername())
                .password(encodedPassword)
                .avatar(userDTO.getAvatar())
                .phoneNumber(userDTO.getPhoneNumber())
                .isOnline(false)
                .isActive(true)
                .build();
        user.setRole(role);
        return userRepository.save(user);
    }

    @Override
    public String login(String phoneNumber, String password) throws Exception {
        Optional<User> optionalUser = userRepository.findByPhoneNumber(phoneNumber);
        if (optionalUser.isEmpty()){
            throw new DataNotFoundException(localizationUtils.getLocalizedMessage(MessageKeys.INVALID_PHONE_NUMBER_PASSWORD));
        }
        User existingUser = optionalUser.get();
        // authenticate roi moi tra ve token khi thanh cong
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                phoneNumber, password, existingUser.getAuthorities()
        );
        authenticationManager.authenticate(authenticationToken); // su dung AuthenticationProvider da cau hinh de xac thuc khi authenticate
        return jwtTokenUtil.genarateToken(existingUser);
    }
    @Override
    public Optional<User> getUserByPhoneNumber(String phoneNumber) throws DataNotFoundException{
        if (userRepository.findByPhoneNumber(phoneNumber).isEmpty()){
            throw new DataNotFoundException("Phone number is not found");
        }
        return userRepository.findByPhoneNumber(phoneNumber);
    }
    @Override
    public User getUserById(Long id) throws DataNotFoundException{
        return userRepository.findById(id).orElseThrow(
                () -> new DataNotFoundException("User not found"));
    }

    @Override
    public User updateUser(Long userId, UserDTO userDTO) throws Exception {
        User existingUser = getUserById(userId);
        if(existingUser != null){
            Role role = roleRepository.findById(userDTO.getRoleId())
                    .orElseThrow(() -> new DataNotFoundException(
                        "Cannot find role with id: "+userDTO.getRoleId()
                    ));
            existingUser.setRole(role);
            existingUser.setUsername(userDTO.getUsername());
            existingUser.setPassword(userDTO.getPassword());
            existingUser.setAvatar(userDTO.getAvatar());
            existingUser.setPhoneNumber(userDTO.getPhoneNumber());
            return userRepository.save(existingUser);
        }
        return null;
    }
    @Override
    public User updateUserAvatar(Long userId, String avatarFileName) throws Exception{
        User existingUser = getUserById(userId);
        existingUser.setAvatar(avatarFileName);
        return userRepository.save(existingUser);
    }
}
