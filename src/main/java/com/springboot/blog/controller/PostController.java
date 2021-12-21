package com.springboot.blog.controller;

import com.springboot.blog.entity.Post;
import com.springboot.blog.mapper.PostMapper;
import com.springboot.blog.payload.PostDto;
import com.springboot.blog.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostController {

    public final PostService postService;

    private final PostMapper postMapper;

    @PostMapping
    public ResponseEntity<PostDto> createPost(@RequestBody PostDto postDto){
        postService.createPost(postMapper.dtoToPost(postDto));
        return ResponseEntity.status(HttpStatus.CREATED).body(postDto);
    }

    @GetMapping
    public ResponseEntity<List<PostDto>> findAllPosts() {
        return ResponseEntity.ok(postMapper.toPostDTOs(postService.findAllPosts()));
    }

   @GetMapping("/{id}")
   public ResponseEntity<PostDto> findPostById(@PathVariable Long id) {
       Optional<Post> post = postService.findPostById(id);
       return ResponseEntity.ok(postMapper.postToDto(post.orElse(null)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PostDto> updatePostById(@PathVariable Long id, @RequestBody PostDto postDto) {
        Post post = postMapper.dtoToPost(postDto);
        post.setId(id);
        postService.createPost(post);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(postDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePostById(@PathVariable Long id) {
        postService.deletePostById(id);
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }

}
