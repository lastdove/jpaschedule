package com.sparta.scheduleappjpa.dto;

import com.sparta.scheduleappjpa.entity.Comment;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor // 기본 생성자 추가
public class CommentDTO {

    private Long id; // 댓글 ID
    private String content; // 댓글 내용
    private LocalDateTime createdAt; // 생성 시간

    // Comment 엔티티를 CommentDTO로 변환하는 생성자
    public CommentDTO(Comment comment) {
        this.id = comment.getId();
        this.content = comment.getContent();
        this.createdAt = comment.getCreatedAt();
    }
}
