package com.lgutierrez.blogrestapi.service.impl;

import com.lgutierrez.blogrestapi.entity.Comment;
import com.lgutierrez.blogrestapi.entity.Post;
import com.lgutierrez.blogrestapi.exception.BlogAPIException;
import com.lgutierrez.blogrestapi.exception.ResourceNotFoundException;
import com.lgutierrez.blogrestapi.payload.CommentDto;
import com.lgutierrez.blogrestapi.repository.CommentRepository;
import com.lgutierrez.blogrestapi.repository.PostRepository;
import com.lgutierrez.blogrestapi.service.CommentService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {

    private CommentRepository commentRepository;
    private PostRepository postRepository;
    private ModelMapper modelMapper;

    public CommentServiceImpl(CommentRepository commentRepository,
                              PostRepository postRepository,
                              ModelMapper modelMapper) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
        this.modelMapper = modelMapper;
    }
    @Override
    public CommentDto createComment(long postId, CommentDto commentDto) {
        Comment comment = mapToEntity(commentDto);
        //retrieve post entity by id
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new ResourceNotFoundException("Post", "id", postId));
        //set post to comment entity
        comment.setPost(post);
        //comment entity to DB
        Comment newComment = commentRepository.save(comment);

        return mapToDTO(newComment);
    }

    @Override
    public List<CommentDto> getCommentsByPostId(long postId) {
        List<Comment> comments = commentRepository.findByPostId(postId);
        return comments.stream().map(comment -> mapToDTO(comment)).collect(Collectors.toList());
    }

    @Override
    public CommentDto getCommentById(long postId, long CommentId) {
        //retrieve post entity by id
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new ResourceNotFoundException("Post", "id", postId));

        //retrieve comment entity by id
        Comment comment = commentRepository.findById(CommentId).orElseThrow(
                () -> new ResourceNotFoundException("Comment", "id", CommentId));

        if(!comment.getPost().getId().equals(post.getId()))
            throw new BlogAPIException(HttpStatus.BAD_REQUEST,"Comment does not belong to post");

        return mapToDTO(comment);
    }

    @Override
    public CommentDto updateComment(long postId, long CommentId, CommentDto commentDto) {
        //retrieve post entity by id
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new ResourceNotFoundException("Post", "id", postId));

        //retrieve comment entity by id
        Comment comment = commentRepository.findById(CommentId).orElseThrow(
                () -> new ResourceNotFoundException("Comment", "id", CommentId));

        if(!comment.getPost().getId().equals(post.getId()))
            throw new BlogAPIException(HttpStatus.BAD_REQUEST,"Comment does not belong to post");

        comment.setName(commentDto.getName());
        comment.setBody(commentDto.getBody());
        comment.setEmail(commentDto.getEmail());

        Comment newComment = commentRepository.save(comment);
        return mapToDTO(newComment);
    }

    @Override
    public void deleteComment(long postId, long CommentId) {
        //retrieve post entity by id
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new ResourceNotFoundException("Post", "id", postId));

        //retrieve comment entity by id
        Comment comment = commentRepository.findById(CommentId).orElseThrow(
                () -> new ResourceNotFoundException("Comment", "id", CommentId));

        if(!comment.getPost().getId().equals(post.getId()))
            throw new BlogAPIException(HttpStatus.BAD_REQUEST,"Comment does not belong to post");

        commentRepository.delete(comment);
    }


    private CommentDto mapToDTO(Comment comment){
        return modelMapper.map(comment,CommentDto.class);
    }

    private Comment mapToEntity(CommentDto commentDto){
        return modelMapper.map(commentDto, Comment.class);
    }
}
