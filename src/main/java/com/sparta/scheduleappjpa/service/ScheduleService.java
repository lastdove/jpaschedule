package com.sparta.scheduleappjpa.service;

import com.sparta.scheduleappjpa.dto.ScheduleDTO;
import com.sparta.scheduleappjpa.entity.Schedule;
import com.sparta.scheduleappjpa.entity.User;
import com.sparta.scheduleappjpa.exception.ResourceNotFoundException;
import com.sparta.scheduleappjpa.repository.ScheduleRepository;
import com.sparta.scheduleappjpa.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final UserRepository userRepository;

    @Transactional
    public ScheduleDTO createSchedule(ScheduleDTO scheduleDTO, Long userId) {
        Schedule schedule = new Schedule();
        schedule.setTitle(scheduleDTO.getTitle());
        schedule.setContent(scheduleDTO.getContent());
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        schedule.setUser(user);
        scheduleRepository.save(schedule);
        return new ScheduleDTO(schedule);
    }

    public List<ScheduleDTO> getAllSchedules() {
        List<Schedule> schedules = scheduleRepository.findAll();
        return schedules.stream()
                .map(ScheduleDTO::new).toList();
    }

    public List<ScheduleDTO> getScheduleById(Long id) {
        List<Schedule> schedules = scheduleRepository.findAllByUserId(id);
        return schedules.stream()
                .map(ScheduleDTO::new).toList();
    }

    @Transactional
    public void deleteSchedule(Long id) {
        scheduleRepository.deleteById(id);
    }

    @Transactional
    public ScheduleDTO updateSchedule(Long id, ScheduleDTO scheduleDTO) {
        Schedule schedule = scheduleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("해당 일정이 존재하지 않습니다."));
        schedule.setTitle(scheduleDTO.getTitle());
        schedule.setContent(scheduleDTO.getContent());
        scheduleRepository.save(schedule);
        return new ScheduleDTO(schedule);
    }
}
