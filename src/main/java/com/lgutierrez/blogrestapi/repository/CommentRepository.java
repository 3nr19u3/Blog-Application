package com.lgutierrez.blogrestapi.repository;

import com.lgutierrez.blogrestapi.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment,Long> {
    List<Comment> findByPostId(long postId);


}
