package com.sparta.scheduleappjpa.service;

import com.sparta.scheduleappjpa.dto.UserDTO;
import com.sparta.scheduleappjpa.entity.User;
import com.sparta.scheduleappjpa.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public UserDTO createUser(UserDTO userDTO) {
        if(userRepository.existsByUsername(userDTO.getUsername())) {
            throw new RuntimeException("유저명이 이미 존재합니다.");
        } else {
            User user = new User();
            user.setUsername(userDTO.getUsername());
            user.setEmail(userDTO.getEmail());
            user.setPassword(userDTO.getPassword()); // 비밀번호 암호화
            userRepository.save(user);
            return new UserDTO(user);
        }
    }

    public Optional<UserDTO> getUserById(Long id) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()) {
            return Optional.of(new UserDTO(optionalUser.get()));
        } else {
            throw new RuntimeException("해당 id가 없습니다.");
        }
    }

    @Transactional
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    @Transactional
    public void updateUser(Long id, UserDTO userDTO) {
        Optional<User> optionalUser = userRepository.findById(id);
        if(optionalUser.isPresent()) {
            User user = optionalUser.get();
            user.setUsername(userDTO.getUsername());
            user.setEmail(userDTO.getEmail());
            user.setPassword(userDTO.getPassword());
            userRepository.save(user);
        } else {
            throw new RuntimeException("유저 id가 존재하지 않습니다.");
        }
    }
}
