package com.sparta.scheduleappjpa.controller;

import com.sparta.scheduleappjpa.dto.ScheduleDTO;
import com.sparta.scheduleappjpa.entity.Schedule;
import com.sparta.scheduleappjpa.service.ScheduleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/schedules")
@RequiredArgsConstructor
public class ScheduleController {

    private final ScheduleService scheduleService;

    @PostMapping("/{userId}")
    public ResponseEntity<ScheduleDTO> createSchedule(@PathVariable Long userId, @Valid @RequestBody ScheduleDTO scheduleDTO) {
        Schedule schedule = scheduleService.createSchedule(scheduleDTO, userId);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ScheduleDTO(schedule));
    }

    @GetMapping
    public ResponseEntity<List<ScheduleDTO>> getAllSchedules() {
        List<ScheduleDTO> schedules = scheduleService.getAllSchedules();
        return ResponseEntity.ok(schedules);
    }

    @GetMapping("/{id}")
    public ResponseEntity<List<ScheduleDTO>> getScheduleById(@PathVariable Long id) {
        List<ScheduleDTO> schedules = scheduleService.getScheduleById(id);
        if (schedules.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(schedules);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSchedule(@PathVariable Long id) {
        scheduleService.deleteSchedule(id);
        return ResponseEntity.noContent().build();
    }
}
