package com.sparta.scheduleappjpa.dto;

import com.sparta.scheduleappjpa.entity.Schedule;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor // 기본 생성자 추가
public class ScheduleDTO {

    @NotBlank
    private String title;

    private String content;

    // Schedule 엔터티를 ScheduleDTO로 변환하는 생성자
    public ScheduleDTO(Schedule schedule) {
        this.title = schedule.getTitle();
        this.content = schedule.getContent();
    }
}
