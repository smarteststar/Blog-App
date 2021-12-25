package com.springboot.blog.service;

import com.springboot.blog.payload.PostDto;
import com.springboot.blog.payload.PostResponse;

public interface PostService {

    PostDto createPost(PostDto PostDto);

    PostResponse findAllPosts(int pageNo, int pageSize,String sortBy,String sortDir);

    PostDto findPostById(Long id);

    void deletePostById(Long id);

    PostDto updatePost(PostDto postDto, long id);
}
