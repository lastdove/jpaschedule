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
    public User createUser(UserDTO userDTO) {
        if(userRepository.existsByUsername(userDTO.getUsername())) {
            throw new RuntimeException("유저명이 이미 존재합니다.");
        } else {
            User user = new User();
            user.setUsername(userDTO.getUsername());
            user.setEmail(userDTO.getEmail());
            user.setPassword(userDTO.getPassword()); // 비밀번호 암호화
            return userRepository.save(user);
        }
    }

    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    @Transactional
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}
