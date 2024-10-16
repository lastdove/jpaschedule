package com.sparta.scheduleappjpa.dto;

import com.sparta.scheduleappjpa.entity.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
public class UserDTO {

    private Long id; // 조회 시 필요한 ID 추가

    @NotBlank(message = "사용자명은 필수입니다.")
    @Size(max = 20, message = "사용자명은 20자 이하여야 합니다.")  // 더 넉넉하게 수정
    private String username;

    @Email(message = "올바른 이메일 형식이어야 합니다.")
    private String email;

    @NotBlank
    @Size(min = 6, message = "비밀번호 최소 6자 이상 입력하세요.")
    private String password; // 비밀번호 필드 추가

    private List<ScheduleDTO> schedules; // 일정 목록 추가

    // User 엔터티를 UserDTO로 변환하는 생성자
    public UserDTO(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.email = user.getEmail();
        this.schedules = user.getSchedules().stream()
                .map(ScheduleDTO::new)
                .collect(Collectors.toList());
    }
}
