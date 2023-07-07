package com.lgutierrez.blogrestapi.service;

import com.lgutierrez.blogrestapi.payload.PostDto;
import com.lgutierrez.blogrestapi.payload.PostResponse;

import java.util.List;

public interface PostService {

    PostDto createPost(PostDto postDto);

    PostResponse getAllPosts(int pageNo, int pageSize, String sortBy);

    PostDto getPostById(long id);

    PostDto updatePost(PostDto postDto, long id);

    void deletePostById(long id);

}
