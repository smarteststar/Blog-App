package com.springboot.blog.service;

import com.springboot.blog.entity.Post;
import com.springboot.blog.payload.PostResponse;

import java.util.Optional;

public interface PostService {

    Post createPost(Post post);

    PostResponse findAllPosts(int pageNo, int pageSize,String sortBy,String sortDir);

    Optional<Post> findPostById(Long id);

    void deletePostById(Long id);
}
