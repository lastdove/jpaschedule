package com.sparta.scheduleappjpa.service;

import com.sparta.scheduleappjpa.dto.CommentDTO;
import com.sparta.scheduleappjpa.entity.Comment;
import com.sparta.scheduleappjpa.entity.Schedule;
import com.sparta.scheduleappjpa.repository.CommentRepository;
import com.sparta.scheduleappjpa.repository.ScheduleRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final ScheduleRepository scheduleRepository;

    @Transactional
    public Comment createComment(CommentDTO commentDTO, Long scheduleId) {
        Comment comment = new Comment();
        comment.setContent(commentDTO.getContent());
        Schedule schedule = scheduleRepository.findById(scheduleId).orElseThrow(() -> new RuntimeException("Schedule not found"));
        comment.setSchedule(schedule);
        return commentRepository.save(comment);
    }

    public Optional<Comment> getCommentById(Long id) {
        return commentRepository.findById(id);
    }

    @Transactional
    public void deleteComment(Long id) {
        commentRepository.deleteById(id);
    }
}
