package com.lgutierrez.blogrestapi.repository;

import com.lgutierrez.blogrestapi.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {

}
