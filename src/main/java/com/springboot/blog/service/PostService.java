package com.springboot.blog.service;

import com.springboot.blog.entity.Post;


import java.util.List;
import java.util.Optional;

public interface PostService {

    Post createPost(Post post);

    List<Post> findAllPosts();

    Optional<Post> findPostById(Long id);

    void deletePostById(Long id);
}
