package com.sparta.scheduleappjpa.controller;

import com.sparta.scheduleappjpa.dto.ScheduleDTO;
import com.sparta.scheduleappjpa.dto.UserDTO;
import com.sparta.scheduleappjpa.entity.Schedule;
import com.sparta.scheduleappjpa.entity.User;
import com.sparta.scheduleappjpa.service.UserService;
import com.sparta.scheduleappjpa.repository.UserRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final UserRepository userRepository;

    @PostMapping
    public ResponseEntity<UserDTO> createUser(@Valid @RequestBody UserDTO userDTO) {
        UserDTO user = userService.createUser(userDTO);
        UserDTO responseDTO = new UserDTO(); // DTO 객체 생성
        responseDTO.setUsername(user.getUsername());
        responseDTO.setEmail(user.getEmail());
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable Long id) {
        Optional<UserDTO> user = userService.getUserById(id);
        return user.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.ok("삭제되었습니다.");
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateUser(@PathVariable Long id, @Valid @RequestBody UserDTO userDTO) {
        userService.updateUser(id, userDTO);
        return ResponseEntity.ok("수정이 완료되었습니다.");
    }

    @GetMapping
    public ResponseEntity<UserDTO> getUserByUsername(@RequestParam String username) {
        Optional<User> user = userRepository.findByUsername(username);
        return user.map(u -> ResponseEntity.ok(new UserDTO(u)))
                        .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/login")
    public ResponseEntity<UserDTO> loginUser(@RequestBody UserDTO userDTO) {
        Optional<User> userOptional = userRepository.findByUsername(userDTO.getUsername());
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            if (Objects.equals(userDTO.getPassword(), user.getPassword())) {
                return ResponseEntity.ok(new UserDTO(user));
            }
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

}

