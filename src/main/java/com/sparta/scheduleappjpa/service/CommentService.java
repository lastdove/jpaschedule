package com.sparta.scheduleappjpa.service;

import com.sparta.scheduleappjpa.dto.CommentDTO;
import com.sparta.scheduleappjpa.entity.Comment;
import com.sparta.scheduleappjpa.entity.Schedule;
import com.sparta.scheduleappjpa.repository.CommentRepository;
import com.sparta.scheduleappjpa.repository.ScheduleRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final ScheduleRepository scheduleRepository;

    @Transactional
    public CommentDTO createComment(CommentDTO commentDTO, Long scheduleId) {
        Comment comment = new Comment();
        comment.setContent(commentDTO.getContent());
        Schedule schedule = scheduleRepository.findById(scheduleId)
                .orElseThrow(() -> new RuntimeException("Schedule not found"));
        comment.setSchedule(schedule);
        commentRepository.save(comment);
        return new CommentDTO(comment); // CommentDTO로 반환
    }

    public List<CommentDTO> getCommentsByScheduleId(Long scheduleId) {
        List<Comment> comments = commentRepository.findAllByScheduleId(scheduleId);
        return comments.stream()
                .map(CommentDTO::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public void deleteComment(Long id) {
        commentRepository.deleteById(id);
    }
}
