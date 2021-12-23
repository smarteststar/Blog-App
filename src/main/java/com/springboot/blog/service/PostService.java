package com.springboot.blog.service;

import com.springboot.blog.entity.Post;
import com.springboot.blog.payload.PostDto;
import com.springboot.blog.payload.PostResponse;

import java.util.Optional;

public interface PostService {

    PostDto createPost(PostDto PostDto);

    PostResponse findAllPosts(int pageNo, int pageSize,String sortBy,String sortDir);

    PostDto findPostById(Long id);

    void deletePostById(Long id);
}
