package com.lgutierrez.blogrestapi.service;

import com.lgutierrez.blogrestapi.payload.CommentDto;

import java.util.List;

public interface CommentService {
    CommentDto createComment(long postId, CommentDto commentDto);
    List<CommentDto> getCommentsByPostId(long postId);
    CommentDto getCommentById(long postId, long CommentId);
    CommentDto updateComment(long postId, long CommentId, CommentDto commentDto);
    void deleteComment(long postId, long CommentId);
}
