package com.sparta.scheduleappjpa.repository;

import com.sparta.scheduleappjpa.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
